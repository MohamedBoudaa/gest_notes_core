package com.dao.impl;

import com.bo.Niveau;
import com.dao.HibernateGenericDao;
import com.dao.NiveauDao;

public class NiveauDaoImpl extends HibernateGenericDao<Long, Niveau> implements NiveauDao{

	public NiveauDaoImpl() {
		super(Niveau.class);
	}
	
	

}
