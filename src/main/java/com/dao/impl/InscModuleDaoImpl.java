package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Module;
import com.dao.DaoException;
import com.dao.HibernateGenericDao;
import com.dao.InscModuleDao;
import com.dao.ServicesDao;
import com.dao.SessionFactoryBuilder;

public class InscModuleDaoImpl extends HibernateGenericDao<Long, InscriptionModule> implements InscModuleDao{

	private final String hqlExists="from InscriptionModule where idInscPedago=?0 and idModule=?1";
	
	
	public InscModuleDaoImpl() {
		super(InscriptionModule.class);
	}

	@Override
	public boolean exists(InscriptionPedagogique inscPdg, Module m) {	
		
		Session s=null;
		Transaction tx = null;
		List<InscriptionModule> list = new ArrayList();
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();
			
			Query query=ServicesDao.initializeCreateQuery(s, hqlExists, inscPdg.getId(), m.getId());
					
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
	
	public List<InscriptionModule> getInscriptionModule(Long idIncPedago, Long idModule) {
		List<InscriptionModule> list=null;
		Session s=null;
		Transaction tx = null;
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();
			
			String hql = "from InscriptionModule where idInscPedago=:idI and idModule=:idM";
			Query query = s.createQuery(hql);
			query.setParameter("idI", idIncPedago);
			query.setParameter("idM", idModule);
			list = (List<InscriptionModule>) query.list();
			
		}catch (HibernateException ex) {
			LOGGER.debug("error due to :" + ex);	
			if (tx != null) {
				tx.rollback();
			}
			throw new DaoException(ex);
		} finally {
			ServicesDao.closeResources(s);
		}
		return list;
	}
}
