package com.dao.impl;

import com.bo.Etudiant;
import com.bo.InscriptionPedagogique;
import com.dao.HibernateGenericDao;
import com.dao.InscPedagoDao;

public class InscPedagoDaoImpl extends HibernateGenericDao<Long, InscriptionPedagogique> implements InscPedagoDao{

	
	public InscPedagoDaoImpl() {
		super(InscriptionPedagogique.class);
	}

	@Override
	public boolean exists(Etudiant e, int year) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
