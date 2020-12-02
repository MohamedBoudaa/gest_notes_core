package com.dao.impl;

import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Module;
import com.dao.HibernateGenericDao;
import com.dao.InscModuleDao;

public class InscModuleDaoImpl extends HibernateGenericDao<Long, InscriptionModule> implements InscModuleDao{

	public InscModuleDaoImpl() {
		super(InscriptionModule.class);
	}

	@Override
	public boolean exists(InscriptionPedagogique inscPdg, Module m) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
