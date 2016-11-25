package edu.csueb.cs6320.bean;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.web.util.HtmlUtils;

@Entity
public class SaleItem implements Serializable {

	/////////////////////////////////////////////////////////////////
	// DATA MEMBERS
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String description;
	private String imgPath;
	
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "saleItem")
//	//@JoinColumn(name="SaleItem_id") // join column is in table for SaleItemOffer
//	private Collection<SaleItemOffer> offersForItem;

	/////////////////////////////////////////////////////////////////
	// MEMBER METHODS
	
	public SaleItem() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
//	public Collection<SaleItemOffer> getOffersForItem() {
//		return offersForItem;
//	}
//
//	public void setOffersForItem(Collection<SaleItemOffer> offersForItem) {
//		this.offersForItem = offersForItem;
//	}

	/**
	 * Sanitizes every string that comes directly from user input to prevent XSS attacks
	 * @return	Self reference for chaining commands
	 */
	public SaleItem htmlEscapeStrings() {
		title = HtmlUtils.htmlEscape(title);
		description = HtmlUtils.htmlEscape(description);
		// TODO: sanitize image path properly
		//imgPath = HtmlUtils.htmlEscape(imgPath);
		return this;
	}

	@Override
	public String toString() {
		return "SaleItem #" + id + ": Title='" + title + "', Description='" + 
				description + "'";
	}
	
}
