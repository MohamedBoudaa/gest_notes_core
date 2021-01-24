package com.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bo.Niveau;
import com.dao.DaoException;
import com.dao.HibernateGenericDao;
import com.dao.NiveauDao;
import com.dao.ServicesDao;
import com.dao.SessionFactoryBuilder;

public class NiveauDaoImpl extends HibernateGenericDao<Long, Niveau> implements NiveauDao{

	public NiveauDaoImpl() {
		super(Niveau.class);
	}
	
	public Niveau getIdNiveau(String title) {
		Niveau niveau = null;
		Session s=null;
		Transaction tx = null;
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();
			
			String hql = "FROM Niveau WHERE title=:t";
			Query query = s.createQuery(hql);
			query.setParameter("t", title);

			niveau = (Niveau) query.list().get(0);
			
		}catch (HibernateException ex) {
			LOGGER.debug("error due to :" + ex);	
			if (tx != null) {
				tx.rollback();
			}
			throw new DaoException(ex);
		} finally {
			ServicesDao.closeResources(s);
		}
		return niveau;
	}

}
