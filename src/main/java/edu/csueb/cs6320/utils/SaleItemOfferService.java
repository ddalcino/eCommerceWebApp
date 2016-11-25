package edu.csueb.cs6320.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

import edu.csueb.cs6320.bean.SaleItemOffer;

@Service
public class SaleItemOfferService {
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
	
	public List<SaleItemOffer> getOffersBySaleItemId(long saleItemID) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		em.getTransaction().begin();
		List<SaleItemOffer> items = em.createQuery(
				"SELECT i FROM SaleItemOffer i WHERE i.saleItemId = :paramId", 
				SaleItemOffer.class)
				.setParameter("paramId", saleItemID)
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

}
