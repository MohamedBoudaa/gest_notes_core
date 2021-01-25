package com.bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Matiere;
import com.bo.Module;
import com.bo.Niveau;
import com.bo.Note;
import com.dao.DaoFactory;
import com.dao.EtudiantDao;
import com.dao.HibernateGenericDao;
import com.dao.IDao;
import com.dao.impl.InscAdminDaoImpl;

public class maintest {

	public static void main(String[] args) throws InscriptionException {

		
	//	ImportExportManagerImpl.exportBulltinToPDF("X3213", 2020, "CP1");
		
//		Etudiant e = new Etudiant("qsdfmedq", "tossto", "15s5qsdfqsdf59", "RB9999");
//		
//		Matiere mt =  new Matiere("math1", 1.5, null);
//		
//
//		Module m = new Module("math", null, 1, 1);
//		
//		m.addMatiere(mt);
//
//		Niveau n = new Niveau("genie info ", "Label", 1);
//		
//		
//		
//		n.addModule(m);
//		
//		
//		
//		
//		InscriptionAdministrative inscAdmin = new InscriptionAdministrative(n, e, 2020, 0, -1, -1, -1);
//		
//		InscriptionPedagogique inscPedago = new InscriptionPedagogique(2020, e, null);
//		
//		InscriptionModule inscModule = new InscriptionModule(m, null, null, inscPedago, 0);
//		
//		InscriptionMatiere inscMatiere = new InscriptionMatiere(mt, null, null);
//		
//		inscModule.addInscriptionMatiere(inscMatiere);
//		
//		inscPedago.addInscriptionModule(inscModule);
//
//		EtudiantDao etuDao = (EtudiantDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_ETUDIANT);
//
//		e.addInscrAdmin(inscAdmin);
//		e.addInscrPedago(inscPedago);
//		InscriptionManager inscManager = new InscriptionManagerImpl();
////		inscManager.inscription(e);
//		etuDao.save(e);

		


//		InscAdminDaoImpl inscAdminDao = new InscAdminDaoImpl();
//
//		SearchManagerImpl sm = new SearchManagerImpl();
//
//		List<HashMap<String, String>> list = sm.searchStudent(null, null, "22", null);
//				
//				
//		if (list != null) {
//
//			for (int i = 0; i < list.size(); i++) {
//				for(Map.Entry<String,String> entry : list.get(i).entrySet()) {
//					System.out.println(entry.getKey()+" : "+entry.getValue());
//				}
//			}
//
//		}


	}
}
