package com.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
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
import com.dao.SessionFactoryBuilder;

public class EtudiantDaoImpl extends HibernateGenericDao<Long, Etudiant> implements EtudiantDao {

	public EtudiantDaoImpl() {
		super(Etudiant.class);
	}

	@Override
	public boolean exists(Etudiant etudiant) {

		return !getByColName("cne", etudiant.getCne(), "Etudiant").isEmpty();

	}

	@Override
	public List<Etudiant> getByCne(String cne) {

		return getByColName("cne", cne, "Etudiant");
	}

	public HashMap<String, String> getEtudiantDetails(String cne) {
		HashMap<String, String> map = new HashMap<String, String>();
		Etudiant etudiant = getByCne(cne).get(0);

		InscAdminDaoImpl inscAdmin = (InscAdminDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);
		List<InscriptionAdministrative> list = inscAdmin.getByColName("idEtudiant", etudiant.getId().toString(),
				"InscriptionAdministrative");

		map.put("firstName", etudiant.getFirstname());
		map.put("secondName", etudiant.getSecondName());
		map.put("cin", etudiant.getCin());
		map.put("cne", etudiant.getCne());
		map.put("id", String.valueOf(etudiant.getId()));
		map.put("firstName", etudiant.getFirstname());

		return map;

	}

	public List<Etudiant> getEtudiantByModule(Long idModule, String year) {
		Integer pYear = Integer.parseInt(year);
		
		List<Etudiant> res = new ArrayList<Etudiant>();
		List<HashMap<String, String>> resultat = null;
		Session s = null;
		Transaction tx = null;
		try {
			s = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();
			
			Query query;

			String hql = "select e from Etudiant e , InscriptionPedagogique i  "
					+ "JOIN InscriptionModule ii ON (i.id = ii.InscPedago.id) " 
					+ "WHERE i.year = :year "
					+ "AND ii.module.id = :module " 
					+ "AND e.id = i.etudiant.id " 
					+ "GROUP BY ii.id "
					+ "ORDER BY e.cne";

			query = s.createQuery(hql);
			query.setParameter("year", pYear);
			query.setParameter("module", idModule);

//			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			res = query.list();

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
//		// Transforming result into objects
//		for (int i = 0; i < resultat.size(); i++) {
//			String firstname = resultat.get(i).get("firstname");
//			String secondName = resultat.get(i).get("secondName");
//			String cne = resultat.get(i).get("cne");
//			String cin = resultat.get(i).get("cin");
//			res.add(new Etudiant(firstname, secondName, cne, cin));
//		}
		return res;

	}

}
