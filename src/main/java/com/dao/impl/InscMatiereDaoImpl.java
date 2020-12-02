package com.dao.impl;

import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.Matiere;
import com.dao.HibernateGenericDao;
import com.dao.InscMatiereDao;

public class InscMatiereDaoImpl extends HibernateGenericDao<Long, InscriptionMatiere> implements InscMatiereDao{

	public InscMatiereDaoImpl() {
		super(InscriptionMatiere.class);
	}

	@Override
	public boolean exists(InscriptionModule inscModule, Matiere m) {
		// TODO Auto-generated method stub
		return false;
	}
}
