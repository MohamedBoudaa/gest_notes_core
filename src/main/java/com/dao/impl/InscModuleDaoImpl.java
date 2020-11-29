package com.dao.impl;

import com.bo.InscriptionModule;
import com.dao.HibernateGenericDao;
import com.dao.InscModuleDao;

public class InscModuleDaoImpl extends HibernateGenericDao<Long, InscriptionModule> implements InscModuleDao{

	public InscModuleDaoImpl() {
		super(InscriptionModule.class);
	}
	
	
}
