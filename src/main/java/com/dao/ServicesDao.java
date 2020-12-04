package com.dao;

import org.hibernate.Query;
import org.hibernate.Session;

public final class ServicesDao {
	
	/**
	 * constructeur par defaut est cach� car c'est une classe final
	 * ne contient que des m�thodes statiques
	 */
	private ServicesDao() {
	}
	
	public static Query initializeCreateQuery
	(Session session, String strHql ,Object... objects) {
		
		Query query = session.createQuery(strHql);

		for(int i=0 ; i<objects.length ;i++) {
			query.setParameter(i, objects[i]);
		}
		return query;
		
	}
	/**
	 * fermeture des resources qui interviennent dans la communication avec la BD
	 */
	public static void closeResources(Session session) {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}
	
}