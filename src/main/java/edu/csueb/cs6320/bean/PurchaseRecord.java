package edu.csueb.cs6320.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PurchaseRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	
	/////////////////////////////////////////////////////////////////
	// DATA MEMBERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long saleItemId;
	private long sellerId;
	private long buyerId;
	private double price;
	
	private int purchaseQuantity;

	@Column(name = "PurchaseDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar purchaseDate;


	/////////////////////////////////////////////////////////////////
	// MEMBER METHODS
	
	public PurchaseRecord() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSaleItemId() {
		return saleItemId;
	}

	public void setSaleItemId(long saleItemId) {
		this.saleItemId = saleItemId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public Calendar getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Calendar purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	
}
