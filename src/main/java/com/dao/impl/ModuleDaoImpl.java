package com.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bo.Matiere;
import com.bo.Module;
import com.dao.DaoException;
import com.dao.HibernateGenericDao;
import com.dao.ModuleDao;
import com.dao.ServicesDao;
import com.dao.SessionFactoryBuilder;

public class ModuleDaoImpl extends HibernateGenericDao<Long, Module> implements ModuleDao{

	
	public ModuleDaoImpl() {
		super(Module.class);
	}
	
	public List<Module> getModuleByNiveauOrderByS(Long idNiveau) {
		List<Module> list = null;
		Session s=null;
		Transaction tx = null;
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();
			
			String hql = "FROM  Module WHERE idNiveau = :idN ORDER by semester";
			Query query = s.createQuery(hql);
			query.setParameter("idN", idNiveau);
			
			list = query.list();

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
