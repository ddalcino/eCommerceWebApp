package edu.csueb.cs6320.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SaleItemOffer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/////////////////////////////////////////////////////////////////
	// DATA MEMBERS
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	// this should be an actual saleitem
//	@Column(name="saleItem_id")
//	private long saleItemId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="SaleItem_id")
	private SaleItem saleItem;

	
	private double price;
	@Column(name="qtyAvailable")
	private int quantityAvailable;
	
//	@Column(name="seller_id")
//	private long sellerId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="Seller_id")
	private User seller;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="SaleItem_id")
//	private SaleItem saleItem;


	/////////////////////////////////////////////////////////////////
	// MEMBER METHODS
	
	public SaleItemOffer() {
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
//	public long getSaleItemId() {
//		return saleItemId;
//	}
//	public void setSaleItemId(long saleItemId) {
//		this.saleItemId = saleItemId;
//	}
	
//	public long getSellerId() {
//		return sellerId;
//	}
//	public void setSellerId(long sellerId) {
//		this.sellerId = sellerId;
//	}
	
	public double getPrice() {
		return price;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantityAvailable() {
		return quantityAvailable;
	}
	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public SaleItem getSaleItem() {
		return saleItem;
	}
	public void setSaleItem(SaleItem saleItem) {
		this.saleItem = saleItem;
	}


}
