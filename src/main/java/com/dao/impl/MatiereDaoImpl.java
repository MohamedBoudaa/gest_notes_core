package com.dao.impl;

import com.bo.Matiere;
import com.dao.HibernateGenericDao;
import com.dao.MatiereDao;

public class MatiereDaoImpl extends HibernateGenericDao<Long, Matiere> implements MatiereDao{
	
	public MatiereDaoImpl() {
		super(Matiere.class);
	}

}
