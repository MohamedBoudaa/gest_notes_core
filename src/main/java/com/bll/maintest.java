package com.bll;

import com.bo.InscriptionAdministrative;
import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.Note;
import com.dao.Dao;
import com.dao.HibernateDaoImpl;

public class maintest {

	
	
	public static void main(String[] args) {
		
		InscriptionMatiere ins = new InscriptionMatiere();
		Note n  = new Note();
		
		Dao<Long, InscriptionMatiere> dao = new HibernateDaoImpl<Long, InscriptionMatiere>(InscriptionMatiere.class);
		
		
		dao.create(ins);
	}
}
