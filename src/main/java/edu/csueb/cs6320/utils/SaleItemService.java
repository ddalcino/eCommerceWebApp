package edu.csueb.cs6320.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Service;

import edu.csueb.cs6320.bean.SaleItem;
import edu.csueb.cs6320.bean.SaleItemOffer;

@Service
public class SaleItemService {

	// TODO: Fix configuration of persistence unit. The EntityManager SHOULD be instantiated once, when the CartService
	// is instantiated; instead, it's instantiated every time a member method is called. This is embarrassing to me,
	// but it's what out professor taught us, and I haven't had time or the need to fix it.
	
	@PersistenceUnit(unitName = "TestPU")
	private EntityManagerFactory emf;

	public List<SaleItem> getSaleItemList() {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		em.getTransaction().begin();
		List<SaleItem> items = em.createQuery("SELECT i FROM SaleItem i", 
				SaleItem.class).getResultList();
		em.getTransaction().commit();
		em.close();

		return items;
	}

	public SaleItem getSaleItemWithId(long itemId) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		SaleItem item = (SaleItem) em.createQuery(
				"SELECT i FROM SaleItem i WHERE i.id = :paramId", 
				SaleItem.class)
				.setParameter("paramId", itemId)
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

	public List<SaleItem> searchSaleItemList(String searchTerm) {
		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();

		em.getTransaction().begin();
		List<SaleItem> items = em.createQuery(
				"SELECT i FROM SaleItem i WHERE i.title LIKE :searchString", 
						SaleItem.class)
				.setParameter("searchString", "%"+searchTerm+"%")
				.getResultList();
		em.getTransaction().commit();
		em.close();

		return items;
	}

	public boolean createSaleItem(SaleItem item) {
		item.htmlEscapeStrings();

		EntityManager em = Persistence.createEntityManagerFactory("TestPU")
				.createEntityManager();
		em.getTransaction().begin();
		em.persist(item);
		em.getTransaction().commit();
		return true;
	}
}
