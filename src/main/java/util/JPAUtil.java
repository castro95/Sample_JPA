package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("escola");

	public static EntityManager getEntityManager() {
		EntityManager entityManager = FACTORY.createEntityManager();
		return FACTORY.createEntityManager();
	}
}