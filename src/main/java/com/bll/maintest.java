package com.bll;

import com.bo.InscriptionAdministrative;
import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.Note;
import com.dao.HibernateGenericDao;
import com.dao.IDao;

public class maintest {

	
	
	public static void main(String[] args) {
		
		InscriptionMatiere ins = new InscriptionMatiere();
		Note n  = new Note();
		
		IDao<Long, InscriptionMatiere> dao = new HibernateGenericDao<Long, InscriptionMatiere>(InscriptionMatiere.class);
		
		
		dao.save(ins);
	}
}
