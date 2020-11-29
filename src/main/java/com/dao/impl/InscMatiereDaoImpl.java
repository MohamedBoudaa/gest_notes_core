package com.dao.impl;

import com.bo.InscriptionMatiere;
import com.dao.HibernateGenericDao;
import com.dao.InscMatiereDao;

public class InscMatiereDaoImpl extends HibernateGenericDao<Long, InscriptionMatiere> implements InscMatiereDao{

	public InscMatiereDaoImpl() {
		super(InscriptionMatiere.class);
	}
}
