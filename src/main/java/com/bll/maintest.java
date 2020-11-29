package com.bll;

import java.util.ArrayList;
import java.util.List;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Matiere;
import com.bo.Module;
import com.bo.Niveau;
import com.bo.Note;
import com.dao.HibernateGenericDao;
import com.dao.IDao;

public class maintest {

	
	
	public static void main(String[] args) {
		
		Etudiant e = new Etudiant("med", "toto", "15159", "RB9999");
		
		Matiere mt =  new Matiere("math1", 1.5, null);
		

		Module m = new Module("math", null, 1, 1);
		
		m.addMatiere(mt);

		Niveau n = new Niveau("genie info ", "Label", 1);
		
		
		
		n.addModule(m);
		
		
		
		
		InscriptionAdministrative inscAdmin = new InscriptionAdministrative(n, e, 2020, 0, -1, -1, -1);
		
		InscriptionPedagogique inscPedago = new InscriptionPedagogique(2020, e, null);
		
		InscriptionModule inscModule = new InscriptionModule(m, null, null, inscPedago, 0);
		
		InscriptionMatiere inscMatiere = new InscriptionMatiere(mt, null, null);
		
		inscModule.addInscriptionMatiere(inscMatiere);
		
		inscPedago.addInscriptionModule(inscModule);
				
		
				
		IDao<Long, Etudiant> etuDao = new HibernateGenericDao<Long, Etudiant>(Etudiant.class);
		
		e.addInscrAdmin(inscAdmin);
		e.addInscrPedago(inscPedago);
		etuDao.save(e);
		
	}
}
