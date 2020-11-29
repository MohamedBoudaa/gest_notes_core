package com.dao.impl;

import com.bo.Module;
import com.dao.HibernateGenericDao;
import com.dao.ModuleDao;

public class ModuleDaoImpl extends HibernateGenericDao<Long, Module> implements ModuleDao{

	
	public ModuleDaoImpl() {
		super(Module.class);
	}
}
