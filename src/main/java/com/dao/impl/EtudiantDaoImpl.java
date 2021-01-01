package com.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.dao.DaoFactory;
import com.dao.EtudiantDao;
import com.dao.HibernateGenericDao;

public class EtudiantDaoImpl extends HibernateGenericDao<Long, Etudiant> implements EtudiantDao {

	public EtudiantDaoImpl() {
		super(Etudiant.class);
	}

	@Override
	public boolean exists(Etudiant etudiant) {
		
		return !getByColName("cin", etudiant.getCin(), "Etudiant").isEmpty() ;	
		
	}

	@Override
	public List<Etudiant> getByCne(String cne) {
		
		return getByColName("cne", cne, "Etudiant");
	}
	
	public HashMap<String, String> getEtudiantDetails(String cne) {
		HashMap<String,String> map = new HashMap<String, String>();
		Etudiant etudiant = getByCne(cne).get(0);
		
		InscAdminDaoImpl inscAdmin = (InscAdminDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);
		List<InscriptionAdministrative> list = inscAdmin.getByColName("idEtudiant",etudiant.getId().toString() , "InscriptionAdministrative");
		
		map.put("firstName", etudiant.getFirstname());
		map.put("secondName", etudiant.getSecondName());
		map.put("cin", etudiant.getCin());
		map.put("cne", etudiant.getCne());
		map.put("id", String.valueOf(etudiant.getId()));
		map.put("firstName", etudiant.getFirstname());
		
		return map;
		
	}

}
