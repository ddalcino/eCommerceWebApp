package edu.csueb.cs6320.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ImageResourceController {
	
//	@RequestMapping(value="/images/{filename}", method=RequestMethod.GET)
//    public void doGet(@PathVariable String filename, //HttpServletRequest request, 
//    		HttpServletResponse response)
////        throws ServletException, IOException
//    {
//        //String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
//        File file = new File("/path/to/files", filename);
//        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
//        response.setHeader("Content-Length", String.valueOf(file.length()));
//        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
//        Files.copy(file.toPath(), response.getOutputStream());
//    }

	
}
