package com.dao.impl;

import java.net.IDN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.bo.Niveau;
import com.dao.DaoException;
import com.dao.DaoFactory;
import com.dao.EtudiantDao;
import com.dao.HibernateGenericDao;
import com.dao.InscAdminDao;
import com.dao.ServicesDao;
import com.dao.SessionFactoryBuilder;

public class InscAdminDaoImpl extends HibernateGenericDao<Long, InscriptionAdministrative> implements InscAdminDao {

	private final String hqlExists = "from InscriptionAdministrative where idEtudiant=?0 and year=?1";

	public InscAdminDaoImpl() {
		super(InscriptionAdministrative.class);

	}

	@Override
	public boolean exists(Etudiant e, int y) {

		Session s = null;
		Transaction tx = null;
		List<InscriptionAdministrative> list = new ArrayList();
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			Query query = ServicesDao.initializeCreateQuery(s, hqlExists, e.getId(), y);

			list = query.getResultList();

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

	/**
	 * Get idniveau from inscriptionAdmin by Etudiant
	 * 
	 * @return
	 */
	public Long getIdNiveau(Long idEtudiant) {
		Long idNiveau = null;
		List results = null;
		Session s = null;
		Transaction tx = null;
		List<InscriptionAdministrative> list = new ArrayList();
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			String hql = "SELECT a.niveau.id FROM  InscriptionAdministrative AS a WHERE a.etudiant.id = :id";
			Query query = s.createQuery(hql);
			query.setParameter("id", idEtudiant);

//			Criteria cr = s.createCriteria(InscriptionAdministrative.class);
//			cr.add(Restrictions.eq("etudiant.idEtudiant", id));
			idNiveau = (Long) query.list().get(0);

//			results = query.list();
//			InscriptionAdministrative ia = (InscriptionAdministrative) results.get(0);
//			idNiveau = ia.getId();

		} catch (HibernateException ex) {
			LOGGER.debug("error due to :" + ex);
			if (tx != null) {
				tx.rollback();
			}
			throw new DaoException(ex);
		} finally {
			ServicesDao.closeResources(s);
		}
		return idNiveau;
	}

	/**
	 * Get Etudiants By Niveau
	 * 
	 * @param idNiveau , if idNiveau is null , the function will get all students of
	 *                 all classes
	 */
	public List<HashMap<String, String>> getEtudiantByNiveau(Long idNiveau) {
		HashMap<String, String> res = new HashMap<String, String>();
		List<HashMap<String, String>> resultat = null;
		Session s = null;
		Transaction tx = null;
		try {
			s = super.sf.getCurrentSession();
			tx = s.beginTransaction();
			String hql = null;
			Query query;
			if (idNiveau != null) {
				hql = "select e.firstname AS firstName ,e.secondName AS secondName,e.cne AS cne,e.cin AS cin, n.title AS niv "
						+ "FROM Etudiant e, Niveau n "
						+ " INNER JOIN InscriptionAdministrative i ON (i.etudiant.id = e.id) "
						+ "WHERE i.niveau.id = :idNiveau AND n.id = :idNiveau "
						+ " GROUP BY e.id ";
				query = s.createQuery(hql);
				query.setParameter("idNiveau", idNiveau);
			} else {
				hql = "select e.firstname AS firstName ,e.secondName AS secondName,e.cne AS cne,e.cin AS cin, n.title AS niv "
						+ "FROM Etudiant e, InscriptionAdministrative i "
						+ "INNER JOIN Niveau n ON (i.etudiant.id = e.id) "
						+ "WHERE i.niveau.id = n.id"
						+ " GROUP BY e.id ";
				query = s.createQuery(hql);
			}

			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			resultat = query.list();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new DaoException(e);

		} finally {

			if (s != null && s.isOpen()) {
				s.close();
			}

		}
		return resultat;
	}

	public List<HashMap<String, String>> getEtudiantByNiveauYear(Long idNiveau, int year) {
		HashMap<String, String> res = new HashMap<String, String>();
		List<HashMap<String, String>> resultat = null;
		Session s = null;
		Transaction tx = null;
		try {

			s = super.sf.getCurrentSession();
			tx = s.beginTransaction();
			String hql = null;
			Query query;
			if (idNiveau != null) {
				hql = "select e.firstname AS firstName ,e.secondName AS secondName,e.cne AS cne,e.cin AS cin, n.title AS niv "
						+ "FROM Etudiant e, InscriptionAdministrative i "
						+ "INNER JOIN Niveau n ON (i.etudiant.id = e.id) "
						+ "WHERE i.niveau.id = :idNiveau AND n.id = :idNiveau AND i.year = :year" + " GROUP BY e.id ";
				query = s.createQuery(hql);
				query.setParameter("idNiveau", idNiveau);
				query.setParameter("year", year);
			} else {
				hql = "select e.firstname AS firstName ,e.secondName AS secondName,e.cne AS cne,e.cin AS cin, n.title AS niv "
						+ "FROM Etudiant e, InscriptionAdministrative i "
						+ "INNER JOIN Niveau n ON (i.etudiant.id = e.id) " + "WHERE  i.year = :year"
						+ " GROUP BY e.id ";
				query = s.createQuery(hql);
				query.setParameter("year", year);
			}

			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			resultat = query.list();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new DaoException(e);

		} finally {

			if (s != null && s.isOpen()) {
				s.close();
			}

		}
		return resultat;
	}
	public List<InscriptionAdministrative> getInscription(int year, Long idEtudiant,Long idNiveau ) {

		List<InscriptionAdministrative> list = null;
		Session s=null;
		Transaction tx = null;
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			String hql = "from InscriptionAdministrative where year=:y AND idEtudiant=:idE AND idNiveau=:idN";
			Query query = s.createQuery(hql);
			query.setParameter("y", year);
			query.setParameter("idE", idEtudiant);
			query.setParameter("idN", idNiveau);

			list = (List<InscriptionAdministrative>) query.list();


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
	
	public List<InscriptionAdministrative> getInscriptionsByNiveau(int year, Long idNiveau ) {

		List<InscriptionAdministrative> list = null;
		Session s=null;
		Transaction tx = null;
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			String hql = "from InscriptionAdministrative where year=:y AND idNiveau=:idN";
			Query query = s.createQuery(hql);
			query.setParameter("y", year);
			query.setParameter("idN", idNiveau);

			list = (List<InscriptionAdministrative>) query.list();


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
	
	public List<InscriptionAdministrative> getInscriptionsByNiveauSortedByNote(int year, Long idNiveau ) {

		List<InscriptionAdministrative> list = null;
		Session s=null;
		Transaction tx = null;
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			String hql = "from InscriptionAdministrative where year=:y AND idNiveau=:idN ORDER BY noteFinal DESC";
			Query query = s.createQuery(hql);
			query.setParameter("y", year);
			query.setParameter("idN", idNiveau);

			list = (List<InscriptionAdministrative>) query.list();


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
	
	public List<HashMap<String, String>> getMoyenneAndEtudiantByNiveauYear(Long idNiveau, Integer year) {
		HashMap<String, String> res = new HashMap<String, String>();
		List<HashMap<String, String>> resultat = null;
		Session s = null;
		Transaction tx = null;
		try {
			s = super.sf.getCurrentSession();
			tx = s.beginTransaction();
			String hql = null;
			Query query;
			hql = "select e.firstname AS firstName ,e.secondName AS secondName,e.cne AS cne,i.noteFinal AS noteFinal,i.rank AS rank  "
						+ "FROM Etudiant e, InscriptionAdministrative i "
						+ "INNER JOIN Niveau n ON (i.etudiant.id = e.id) "
						+ "WHERE i.niveau.id = :idNiveau AND n.id = :idNiveau AND i.year = :year" + " GROUP BY e.id ";
			query = s.createQuery(hql);
			query.setParameter("idNiveau", idNiveau);
			query.setParameter("year", year);
			
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			resultat = query.list();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new DaoException(e);

		} finally {

			if (s != null && s.isOpen()) {
				s.close();
			}

		}
		return resultat;
	}
}
