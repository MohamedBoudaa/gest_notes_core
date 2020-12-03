package com.bll;

import java.util.List;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Matiere;
import com.bo.Module;
import com.bo.Niveau;
import com.dao.DaoFactory;
import com.dao.EtudiantDao;
import com.dao.InscAdminDao;
import com.dao.InscMatiereDao;
import com.dao.InscModuleDao;
import com.dao.InscPedagoDao;
import com.dao.impl.InscMatiereDaoImpl;

public class InscriptionManagerImpl implements InscriptionManager{
	
	
	private InscAdminDao inscAdminDao = (InscAdminDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);
	
	private InscMatiereDao inscMatiereDao = (InscMatiereDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCMATIERE);

	private InscPedagoDao inscPedagoDao = (InscPedagoDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCPEDAGO);

	private InscModuleDao inscModuleDao = (InscModuleDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCMODULE);

	private EtudiantDao etudiantDao = (EtudiantDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_ETUDIANT);

	
	
	
	/**
	 * Constructor
	 */
	public InscriptionManagerImpl() {
		
	}
	
	public void InscrirAdmin(Etudiant etudiant,Niveau niveau,int year) throws InscriptionException {
		
		if(!etudiantDao.exists(etudiant)) {
			throw new InscriptionException("Etudiant introuvable");
		}
		
		if(inscAdminDao.exists(etudiant,year)) {
			throw new InscriptionException("L'étudiant a déjà une inscription pour l'année "+year);
		}
		
		InscriptionAdministrative inscAdmin = new InscriptionAdministrative(niveau, etudiant, year, 0, -1, -1, -1);
		
		etudiant.addInscrAdmin(inscAdmin);
		etudiantDao.update(etudiant);
	}
	
//	/**
//	 * Fonction pour gerer les inscriptions pedagogiques
//	 * 
//	 */
//	public void inscrirMatiere(InscriptionModule inscModule,Matiere matiere) {
//		
//	}
//	
//	/**
//	 * Fonction pour gerer les inscriptions pedagogiques
//	 * 
//	 */
//	public void inscrirModule(Etudiant etudiant,Module module) {
//		
//		
//	}
	
	public void inscrirPedago(Etudiant etudiant,List<Module> modules,int year) throws InscriptionException {
		
		if(!etudiantDao.exists(etudiant)) {
			throw new InscriptionException("Etudiant introuvable");
		}
		
		if(inscPedagoDao.exists(etudiant,year)) {
			throw new InscriptionException("L'étudiant a déjà une inscription pour l'année "+year);
		}
		InscriptionPedagogique inscPedago = new InscriptionPedagogique(year, etudiant);
		for(int i = 0 ; i< modules.size() ; i ++) {
			InscriptionModule inscModule = new InscriptionModule(modules.get(i),null, null, inscPedago, -1);
			List<Matiere> matieres = modules.get(i).getMatieres();
			for(int j = 0 ; j< matieres.size();j++) {
				InscriptionMatiere inscMatiere = new InscriptionMatiere(matieres.get(j), null, inscModule);
				inscModule.addInscriptionMatiere(inscMatiere);
			}
			inscPedago.addInscriptionModule(inscModule);
		}
		
		etudiant.addInscrPedago(inscPedago);
		etudiantDao.update(etudiant);
	}
	
	
	
	
}
