package edu.csueb.cs6320.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import edu.csueb.cs6320.bean.SaleItem;
import edu.csueb.cs6320.bean.SaleItemOffer;
import edu.csueb.cs6320.bean.User;
import edu.csueb.cs6320.utils.SaleItemOfferService;
import edu.csueb.cs6320.utils.SaleItemService;

/**
 * Servlet that handles requests for the application file upload requests
 * 
 * code borrowed from: 
 * http://www.journaldev.com/2573/spring-mvc-file-upload-example-single-multiple-files
 */
@Controller
public class FileUploadController {
	
	@Autowired
    ServletContext context;
	@Autowired
	SaleItemService saleItemService;
	@Autowired
	SaleItemOfferService saleItemOfferService;

	private static final Logger logger = LoggerFactory
			.getLogger(FileUploadController.class);

	@RequestMapping(value="/images/", method=RequestMethod.GET)
    public void doGet(
    		@RequestParam("filename") String filename,
    		//@PathVariable String filename, //HttpServletRequest request, 
    		HttpServletResponse response) throws IOException
//        throws ServletException, IOException
    {
		System.out.println("Attempting to load " + filename);
        //String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        File file = new File(context.getInitParameter("upload.location"), filename);
        response.setHeader("Content-Type", context.getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }

	
	/**
	 * Upload single file using Spring Controller
	 * Actually, right now it makes a new SellItem, with a SellItemOffer attached
	 */
	@RequestMapping(value = "/sell/newItem", method = RequestMethod.POST)
	public String uploadFileHandler(
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("price") String price,
			@RequestParam("qty") String qty,
			@RequestParam("imageFile") MultipartFile file,
			HttpServletRequest request
//			@RequestParam("name") String name,
//			@RequestParam("file") MultipartFile file
			) {
		String filename = file.getOriginalFilename();
		System.out.println("File is called: " + filename);
		int cutoffIndex = filename.lastIndexOf('/');
		if (cutoffIndex <= 0) {
			cutoffIndex = filename.lastIndexOf('\\');
		}
		if (cutoffIndex > 0) {
			filename = filename.substring(cutoffIndex);
			System.out.println("After cutoff, file is called: " + filename);
		}
		filename = HtmlUtils.htmlEscape(filename);
		Double dPrice;
		int quantity;
		try {
			dPrice = Double.parseDouble(price);
			quantity = Integer.parseInt(qty);
			if (dPrice < 0 || quantity < 0) { return "Numeric inputs unreadable"; }
		} catch (NumberFormatException e) {
			return "Numeric inputs unreadable";
		}
		
		User user = (User) request.getSession().getAttribute("user");
		if (user != null && user.hasSellerPrivileges()) {
			// it's ok to service the request
			
			//Create a new item
			SaleItem item = new SaleItem();
			item.setTitle(HtmlUtils.htmlEscape(title));
			item.setDescription(HtmlUtils.htmlEscape(description));
			item.setImgPath(filename);
			saleItemService.createSaleItem(item);
			
			//Create a new offer for the item
			SaleItemOffer offer = new SaleItemOffer();
			offer.setPrice(dPrice);
			offer.setQuantityAvailable(quantity);
			offer.setSaleItem(item);
//			offer.setSaleItemId(item.getId());
			offer.setSeller(user);
			saleItemOfferService.createSaleItemOffer(offer);
		}
		

		if (!file.isEmpty()) {
			
			try {
				byte[] bytes = file.getBytes();
				String imgPath = context.getInitParameter("upload.location");

				// TODO: PATCH THIS MASSIVE SECURITY HOLE! No sanitization is being done on the image files, and no
				// checks are being done to ensure that the file is even an image file and not a binary executable.
				// This functionality remains in the code because it looks good to be able to upload images. NOT SAFE!
				
				// Create the file on server
				File serverFile = new File(imgPath
						+ File.separator + filename);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());
				return"redirect:/sell";

			} catch (Exception e) {
				return "You failed to upload " + filename + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + filename
					+ " because the file was empty.";
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
}