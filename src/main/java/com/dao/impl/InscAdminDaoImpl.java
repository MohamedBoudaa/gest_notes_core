package com.dao.impl;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.dao.DaoException;
import com.dao.DaoFactory;
import com.dao.EtudiantDao;
import com.dao.HibernateGenericDao;
import com.dao.InscAdminDao;
import com.dao.SessionFactoryBuilder;

public class InscAdminDaoImpl extends HibernateGenericDao<Long, InscriptionAdministrative> implements InscAdminDao {

	public InscAdminDaoImpl() {
		super(InscriptionAdministrative.class);

	}

	@Override
	public boolean exists(Etudiant e, int y) {
		
		Session s=null;
		Transaction tx = null;
		List<InscriptionAdministrative> list = new ArrayList();
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			Query query = s.createQuery("from InscriptionAdministrative where idEtudiant=:id and year=:y");
			query.setParameter("id", e.getId());
			query.setParameter("y", y);
					
			list=query.getResultList();
			
			tx.commit();
		} catch (HibernateException ex) {
			LOGGER.debug("error due to :" + ex);	
			if (tx != null) {
				tx.rollback();
			}
			throw new DaoException(ex);
		} finally {
			if (s != null && s.isOpen()) {
				s.close();
			}
		}
		
		return !list.isEmpty();
	}
	
}
