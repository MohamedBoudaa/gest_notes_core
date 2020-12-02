package com.dao.impl;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.dao.HibernateGenericDao;
import com.dao.InscAdminDao;

public class InscAdminDaoImpl extends HibernateGenericDao<Long, InscriptionAdministrative> implements InscAdminDao {

	public InscAdminDaoImpl() {
		super(InscriptionAdministrative.class);

	}

	@Override
	public boolean exists(Etudiant e, int year) {
		// TODO Auto-generated method stub
		return false;
	}

}
