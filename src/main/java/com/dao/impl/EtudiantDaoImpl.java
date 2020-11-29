package com.dao.impl;

import com.bo.Etudiant;
import com.dao.EtudiantDao;
import com.dao.HibernateGenericDao;

public class EtudiantDaoImpl extends HibernateGenericDao<Long, Etudiant> implements EtudiantDao {

	public EtudiantDaoImpl() {
		super(Etudiant.class);
	}

}
