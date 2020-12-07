package com.dao.impl;

import com.bo.Compte;
import com.dao.CompteDao;
import com.dao.HibernateGenericDao;

public class CompteDaoImpl extends HibernateGenericDao<Long, Compte> implements CompteDao {

	public CompteDaoImpl() {
		super(Compte.class);
	}

	@Override
	public boolean exists(Compte compte) {
		return !getByColName("username",compte.getUsername(), "Compte").isEmpty() ;	
	}

	

}
