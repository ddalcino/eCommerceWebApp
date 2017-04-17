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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/////////////////////////////////////////////////////////////////
	// DATA MEMBERS
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="Buyer_id")
	private long buyerId;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="Buyer_id")
//	private User buyer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="SaleItemOffer_id")
	private SaleItemOffer offer;

	@Column(name="SaleItem_id")
	private long saleItemId;

//	@Column(name="SaleItemOffer_id", insertable = false, updatable = false)
//	private long saleItemOfferID;
	
	private int quantity;
	
	@Column(name = "AddedDate")
	@Temporal(TemporalType.TIMESTAMP)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private java.util.Calendar timestampField;

	/////////////////////////////////////////////////////////////////
	// MEMBER METHODS

	public CartItem() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public User getBuyer() {
//		return buyer;
//	}
//
//	public void setBuyer(User buyer) {
//		this.buyer = buyer;
//	}

	public SaleItemOffer getOffer() {
		return offer;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

//	public long getSaleItemOfferID() {
//		return saleItemOfferID;
//	}
//
//	public void setSaleItemOfferID(long saleItemOfferID) {
//		this.saleItemOfferID = saleItemOfferID;
//	}

	public void setOffer(SaleItemOffer offer) {
		this.offer = offer;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

//	public SaleItem getSaleItem() {
//		return saleItem;
//	}
//
//	public void setSaleItem(SaleItem saleItem) {
//		this.saleItem = saleItem;
//	}

	public long getSaleItemId() {
		return saleItemId;
	}

	public void setSaleItemId(long saleItemId) {
		this.saleItemId = saleItemId;
	}

	public java.util.Calendar getTimestampField() {
		return timestampField;
	}

	public void setTimestampField(java.util.Calendar timestampField) {
		this.timestampField = timestampField;
	}
	
	
}
