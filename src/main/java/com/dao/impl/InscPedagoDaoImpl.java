package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bo.Etudiant;
import com.bo.InscriptionPedagogique;
import com.dao.DaoException;
import com.dao.HibernateGenericDao;
import com.dao.InscPedagoDao;
import com.dao.ServicesDao;
import com.dao.SessionFactoryBuilder;

public class InscPedagoDaoImpl extends HibernateGenericDao<Long, InscriptionPedagogique> implements InscPedagoDao{

	private final String hqlExists="from InscriptionPedagogique where idEtudiant=?0 and year=?1";
	
	public InscPedagoDaoImpl() {
		super(InscriptionPedagogique.class);
	}

	@Override
	public boolean exists(Etudiant e, int y) {

		Session s=null;
		Transaction tx = null;
		List<InscriptionPedagogique> list = new ArrayList();
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			Query query = ServicesDao.initializeCreateQuery(s, hqlExists, e.getCne(), y);
			
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
	public InscriptionPedagogique getInscriptionPedagogique(Long idEtudiant, int year) {
		InscriptionPedagogique inscPedag = null;
		Session s=null;
		Transaction tx = null;
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();
			
			String hql = "from InscriptionPedagogique where idEtudiant=:idE and year=:y";
			Query query = s.createQuery(hql);
			query.setParameter("idE", idEtudiant);
			query.setParameter("y", year);
			
			inscPedag = (InscriptionPedagogique) query.list().get(0);

		}catch (HibernateException ex) {
			LOGGER.debug("error due to :" + ex);	
			if (tx != null) {
				tx.rollback();
			}
			throw new DaoException(ex);
		} finally {
			ServicesDao.closeResources(s);
		}
		return inscPedag;
	}
	
}
