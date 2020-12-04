package com.bll;

import java.util.List;

import org.apache.log4j.Logger;

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
import com.dao.InscMatiereDao;


public class InscriptionManagerImpl implements InscriptionManager {

	private InscAdminDao inscAdminDao = (InscAdminDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);

	private InscMatiereDao inscMatiereDao = (InscMatiereDao) DaoFactory.getDaoFactory()
			.getDao(DaoFactory.DAO_INSCMATIERE);

	private InscPedagoDao inscPedagoDao = (InscPedagoDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCPEDAGO);

	private InscModuleDao inscModuleDao = (InscModuleDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCMODULE);

	private EtudiantDao etudiantDao = (EtudiantDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_ETUDIANT);

	protected final Logger LOGGER;

	
	
	
	
	/**
	 * Constructor
	 */
	public InscriptionManagerImpl() {

		LOGGER = Logger.getLogger(InscriptionManagerImpl.class);
	}

	
	/**
	 * Inscrir un nouveau étudiant
	 * @param etd
	 * @throws InscriptionException
	 */
	public void inscription(Etudiant etd) throws InscriptionException {

		// Check if cne exits
		List<Etudiant> e = etudiantDao.getByCne(etd.getCne());
		if (e.size() != 0) {
			LOGGER.warn("Inscription invalide  : ");
			throw new InscriptionException("Cne existe déjà en base de données", InscriptionException.CNE_EXISTS);
		}

		etudiantDao.save(etd);

		LOGGER.info("Inscription validée  : ");

	}

	public void inscrirAdmin(Etudiant etudiant, Niveau niveau, int year) throws InscriptionException {

		if (!etudiantDao.exists(etudiant)) {
			throw new InscriptionException("Etudiant introuvable");
		}

		if (inscAdminDao.exists(etudiant, year)) {
			throw new InscriptionException("L'étudiant a déjà une inscription pour l'année " + year);
		}

		InscriptionAdministrative inscAdmin = new InscriptionAdministrative(niveau, etudiant, year, 0, -1, -1, -1);

		etudiant.addInscrAdmin(inscAdmin);
		etudiantDao.update(etudiant);
	}

	/**
	 * Fonction pour gerer les inscriptions pedagogiques
	 * 
	 */
	public void inscrirMatiere(InscriptionModule inscModule, Matiere matiere) {

	}

	/**
	 * Fonction pour gerer les inscriptions pedagogiques
	 * 
	 */
	public void inscrirModule(Etudiant etudiant, Module module) {

	}

	public void inscrirPedago(Etudiant etudiant, List<Module> modules, int year) throws InscriptionException {

		if (!etudiantDao.exists(etudiant)) {
			throw new InscriptionException("Etudiant introuvable");
		}

		if (inscPedagoDao.exists(etudiant, year)) {
			throw new InscriptionException("L'étudiant a déjà une inscription pour l'année " + year);
		}
		InscriptionPedagogique inscPedago = new InscriptionPedagogique(year, etudiant);
		for (int i = 0; i < modules.size(); i++) {
			
			InscriptionModule inscModule = new InscriptionModule(modules.get(i), null, null, inscPedago, -1);
			List<Matiere> matieres = modules.get(i).getMatieres();
			
			for (int j = 0; j < matieres.size(); j++) {
				
				InscriptionMatiere inscMatiere = new InscriptionMatiere(matieres.get(j), null, inscModule);
				inscModule.addInscriptionMatiere(inscMatiere);
			}
			
			inscPedago.addInscriptionModule(inscModule);
		}

		etudiant.addInscrPedago(inscPedago);
		etudiantDao.update(etudiant);
	}
	
	
	
	
	
	

}
