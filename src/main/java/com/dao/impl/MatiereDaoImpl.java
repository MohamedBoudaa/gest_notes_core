package com.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bo.Matiere;
import com.dao.DaoException;
import com.dao.HibernateGenericDao;
import com.dao.MatiereDao;
import com.dao.ServicesDao;
import com.dao.SessionFactoryBuilder;

public class MatiereDaoImpl extends HibernateGenericDao<Long, Matiere> implements MatiereDao{
	
	public MatiereDaoImpl() {
		super(Matiere.class);
	}
	
	public List<Matiere> getMatiereByModule(Long idModule) {
			List<Matiere> list = null;
			Session s=null;
			Transaction tx = null;
			try {
				s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
				tx = s.beginTransaction();
				
				String hql = "FROM  Matiere WHERE idModule = :idM";
				Query query = s.createQuery(hql);
				query.setParameter("idM", idModule);
				
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
