package edu.csueb.cs6320.utils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

import edu.csueb.cs6320.bean.SaleItem;
import edu.csueb.cs6320.bean.SaleItemOffer;
import edu.csueb.cs6320.bean.User;

@Service
public class SaleItemOfferService {

	// TODO: Fix configuration of persistence unit. The EntityManager SHOULD be instantiated once, when the CartService
	// is instantiated; instead, it's instantiated every time a member method is called. This is embarrassing to me,
	// but it's what out professor taught us, and I haven't had time or the need to fix it.
	
	@PersistenceUnit(unitName = "TestPU")
	private EntityManagerFactory emf;
	
	public List<SaleItemOffer> getOffersBySellerId(long sellerID) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		em.getTransaction().begin();
		List<SaleItemOffer> items = em.createQuery(
				"SELECT i FROM SaleItemOffer i WHERE i.Seller_id = :paramId", 
				SaleItemOffer.class)
				.setParameter("paramId", sellerID)
				.getResultList();
		em.getTransaction().commit();
		em.close();

		return items;
	}
	public List<SaleItemOffer> getOffersBySeller(User seller) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		em.getTransaction().begin();
		List<SaleItemOffer> items = em.createQuery(
				"SELECT i FROM SaleItemOffer i WHERE i.seller = :paramId", 
				SaleItemOffer.class)
				.setParameter("paramId", seller)
				.getResultList();
		em.getTransaction().commit();
		em.close();

		return items;
	}
	
//	// probably shouldn't work
//	public List<SaleItemOffer> getOffersBySaleItemId(long saleItemID) {
//		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
//				.createEntityManager();
//
//		em.getTransaction().begin();
//		List<SaleItemOffer> items = em.createQuery(
//				"SELECT i FROM SaleItemOffer i WHERE i.saleItemId = :paramId", 
//				SaleItemOffer.class)
//				.setParameter("paramId", saleItemID)
//				.getResultList();
//		em.getTransaction().commit();
//		em.close();
//
//		return items;
//	}

	public List<SaleItemOffer> getOffersBySaleItem(SaleItem saleItem) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		em.getTransaction().begin();
		List<SaleItemOffer> items = em.createQuery(
				"SELECT i FROM SaleItemOffer i WHERE i.saleItem = :paramId", 
				SaleItemOffer.class)
				.setParameter("paramId", saleItem)
				.getResultList();
		em.getTransaction().commit();
		em.close();

		return items;
	}


	public SaleItemOffer getOfferById(long itemID) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		SaleItemOffer item = (SaleItemOffer) em.createQuery(
				"SELECT i FROM SaleItemOffer i WHERE i.id = :paramId", 
				SaleItemOffer.class)
				.setParameter("paramId", itemID)
				.setMaxResults(1)
			    .getSingleResult();

		if (item == null) {
			System.out.println("No items found with that id!");
		} else {
			System.out.println("Item found: " + item);
		}
		em.close();

		return item;
	}
	
	public SaleItemOffer updateOfferById(long itemID, double newPrice, int newQty) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();
		em.getTransaction().begin();
		
		SaleItemOffer offer = (SaleItemOffer) em.createQuery(
				"SELECT i FROM SaleItemOffer i WHERE i.id = :paramId", 
				SaleItemOffer.class)
				.setParameter("paramId", itemID)
				.setMaxResults(1)
			    .getSingleResult();

		if (offer == null) {
			System.out.println("No offers found with that id!");
			em.getTransaction().rollback();
		} else {
			System.out.println("Offer found: " + offer);
			offer.setPrice(newPrice);
			offer.setQuantityAvailable(newQty);
			em.getTransaction().commit();
		}
		em.close();

		return offer;
	}

	public boolean createSaleItemOffer(SaleItemOffer offer) {

		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();
		em.getTransaction().begin();
		em.persist(offer);
		em.getTransaction().commit();
		return true;
	}
	
//	public SaleItemOffer getSaleItemOfferByOfferId(long offerId) {
//		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
//				.createEntityManager();
//		
//		SaleItemOffer offer = null;
//		try {
//			offer = (SaleItemOffer) 
//					em.createQuery(
//				    "SELECT o FROM SaleItemOffer o WHERE o.id = :paramID")
//				    .setParameter("paramID", offerId)
//				    .setMaxResults(1)
//				    .getSingleResult();
//		} catch(javax.persistence.NoResultException e){
//			System.out.println("No offer found with that id");
//			return null;
//		} catch(Exception e) {
//			System.out.println("Exception occurred while trying to find SellItemOffer "
//					+ "by id, of class: " + e.getClass().toGenericString());
//			e.printStackTrace();
//		}
//		return offer;
//
//	}

}
