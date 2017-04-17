package edu.csueb.cs6320.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

import edu.csueb.cs6320.bean.CartItem;
import edu.csueb.cs6320.bean.SaleItemOffer;

@Service
public class CartService {
	
	// TODO: Fix configuration of persistence unit. The EntityManager SHOULD be instantiated once, when the CartService
	// is instantiated; instead, it's instantiated every time a member method is called. This is embarrassing to me,
	// but it's what out professor taught us, and I haven't had time or the need to fix it.
	
	@PersistenceUnit(unitName = "TestPU")
	private EntityManagerFactory emf;

	public List<CartItem> getCartContents(long userid) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		em.getTransaction().begin();
		List<CartItem> items = em.createQuery(
				"SELECT i FROM CartItem i WHERE i.buyerId = :paramId", 
				CartItem.class)
				.setParameter("paramId", userid)
				.getResultList();
		em.getTransaction().commit();
		em.close();

		if (items == null) {
			items = new ArrayList<CartItem>();
		}
		return items;
	}
	
	public CartItem addItemToCart(long buyerID, SaleItemOffer offer, int quantity) { 
		CartItem item = new CartItem();
		item.setBuyerId(buyerID);
		item.setOffer(offer);
		item.setQuantity(quantity);
		
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();
		em.getTransaction().begin();
		em.persist(item);
		// verify that quantity <= qtyAvailable
		// this could be an SQL trigger/constraint; should it?
		if (item != null && item.getOffer() != null) {
			int qtyAvailable = item.getOffer().getQuantityAvailable();
			if (qtyAvailable < quantity) {
				item.setQuantity(qtyAvailable);
			}
		}
		em.getTransaction().commit();
		em.close();
		return item;
	}
	public boolean deleteCartItem(long itemid) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();
		CartItem item = em.find(CartItem.class, itemid);
		em.getTransaction().begin();
		em.remove(item);
		em.getTransaction().commit();
		em.close();
		return true;
	}
	public boolean changeQuantity(long itemid, int newQuantity) {
		if (newQuantity == 0) {
			return deleteCartItem(itemid);
		}
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();
		CartItem item = em.find(CartItem.class, itemid);
		if (item != null && item.getOffer() != null &&
				item.getOffer().getQuantityAvailable() >= newQuantity) {
			em.getTransaction().begin();
			item.setQuantity(newQuantity);
			em.getTransaction().commit();
			em.close();
			return true;
		}
		return false;
	}

}
