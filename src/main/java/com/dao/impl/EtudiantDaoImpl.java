package com.dao.impl;

import java.util.List;

import com.bo.Etudiant;
import com.dao.EtudiantDao;
import com.dao.HibernateGenericDao;

public class EtudiantDaoImpl extends HibernateGenericDao<Long, Etudiant> implements EtudiantDao {

	public EtudiantDaoImpl() {
		super(Etudiant.class);
	}

	@Override
	public boolean exists(Etudiant etudiant) {
		
		return !getByColName("cin", etudiant.getCin(), "Etudiant").isEmpty() ;	
		
	}

	@Override
	public List<Etudiant> getByCne(String cne) {
		
		return getByColName("cne", cne, "Etudiant");
	}

}
