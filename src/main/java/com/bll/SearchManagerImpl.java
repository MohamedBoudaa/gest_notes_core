package com.bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.bo.Etudiant;
import com.bo.Niveau;
import com.dao.DaoException;
import com.dao.DaoFactory;
import com.dao.SessionFactoryBuilder;
import com.dao.impl.InscAdminDaoImpl;
import com.dao.impl.NiveauDaoImpl;

public class SearchManagerImpl implements SearchManager {

	private SessionFactory sf;

	public SearchManagerImpl() {
		sf = SessionFactoryBuilder.getSessionFactory();
	}

	/**
	 * Filter List of hashmap by name
	 */
	private static List<HashMap<String, String>> filterByName(String name, List<HashMap<String, String>> list) {
		List<HashMap<String, String>> res = new ArrayList<HashMap<String,String>>();;

		if (name == null) {
			return list;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("firstName").equalsIgnoreCase(name)) {
				res.add(list.get(i));
			}
		}

		return res;
	}

	/**
	 * Filter List of hashmap by second name
	 */
	private static List<HashMap<String, String>> filterBySecondName(String secondName, List<HashMap<String, String>> list) {
		List<HashMap<String, String>> res = new ArrayList<HashMap<String,String>>();

		if (secondName == null) {
			return list;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("secondName").equalsIgnoreCase(secondName)) {
				res.add(list.get(i));
			}
		}

		return res;
	}

	/**
	 * Filter List of hashmap by second name
	 */
	private static List<HashMap<String, String>> filterByCne(String cne, List<HashMap<String, String>> list) {
		List<HashMap<String, String>> res = new ArrayList<HashMap<String,String>>();;

		if (cne == null) {
			return list;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("cne").equalsIgnoreCase(cne)) {
				res.add(list.get(i));
			}
		}

		return res;
	}

	@Override
	public List<HashMap<String, String>> searchStudent(String name, String scndName, String cne, Long niveau) {

		List<HashMap<String, String>> result = null;

		InscAdminDaoImpl inscAdminDao = (InscAdminDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);

		result = inscAdminDao.getEtudiantByNiveau(niveau);
		
		result = filterByName(name, result);

		result = filterBySecondName(scndName, result);

		result = filterByCne(cne, result);

		return result;
	}

}
