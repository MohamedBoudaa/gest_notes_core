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
import com.dao.SessionFactoryBuilder;

public class InscModuleDaoImpl extends HibernateGenericDao<Long, InscriptionModule> implements InscModuleDao{

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

			Query query = s.createQuery("from InscriptionModule where idInscPedago=:idInsc and idModule=:idM");
			query.setParameter("id", inscPdg.getId());
			query.setParameter("y", m.getId());
					
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
