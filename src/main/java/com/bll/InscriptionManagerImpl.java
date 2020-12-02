package com.bll;

import com.bo.Etudiant;
import com.bo.Niveau;
import com.dao.DaoFactory;
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

	
	
	/**
	 * Constructor
	 */
	public InscriptionManagerImpl() {
		
	}
	
	public void InscrirAdmin(Etudiant etudiant,Niveau niveau,int year) {
		
		if(inscAdminDao.exists(etudiant,year)) {
			
		}
		
	}
	
	
	
	
}
