package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.Matiere;
import com.dao.DaoException;
import com.dao.HibernateGenericDao;
import com.dao.InscMatiereDao;
import com.dao.ServicesDao;
import com.dao.SessionFactoryBuilder;

public class InscMatiereDaoImpl extends HibernateGenericDao<Long, InscriptionMatiere> implements InscMatiereDao{

	private final String hqlExists="from InscriptionMatiere where idInscriptionModule=?0 and idMatiere=?1";
	
	public InscMatiereDaoImpl() {
		super(InscriptionMatiere.class);
	}

	@Override
	public boolean exists(InscriptionModule inscModule, Matiere m) {
		
		Session s=null;
		Transaction tx = null;
		List<InscriptionMatiere> list = new ArrayList();
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			Query query =ServicesDao.initializeCreateQuery(s, hqlExists, inscModule.getId(), m.getId());
					
			list=query.getResultList();
			
			tx.commit();
		} catch (HibernateException ex) {
			LOGGER.debug("error due to :" + ex);	
			if (tx != null) {
				tx.rollback();
			}
			throw new DaoException(ex);
		} finally {
			ServicesDao.closeResources(s);
		}
		
		return !list.isEmpty();
	}
}
