package com.bll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;
import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Module;
import com.bo.Niveau;
import com.dao.DaoFactory;
import com.dao.impl.InscAdminDaoImpl;
import com.dao.impl.InscModuleDaoImpl;
import com.dao.impl.InscPedagoDaoImpl;
import com.dao.impl.ModuleDaoImpl;
import com.dao.impl.NiveauDaoImpl;

public class MoyennesManagerImpl implements MoyennesManager{
	
	private static final ModuleDaoImpl modDAOImpl = (ModuleDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_MODULE);
	private static final NiveauDaoImpl nivDAO= (NiveauDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_NIVEAU);
	private static final InscAdminDaoImpl inscAdminDAO = (InscAdminDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);
	private static final InscPedagoDaoImpl inscPedagDAO=(InscPedagoDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCPEDAGO);
	private static final InscModuleDaoImpl inscModuleDAO=(InscModuleDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCMODULE);

	
	
	
	public static void calculerMoyennesByNiveau(String niveauTitle, int year) throws MoyennesException {

		Niveau niveau=nivDAO.getIdNiveau(niveauTitle);
		List<Module> modules=modDAOImpl.getModuleByNiveauOrderByS(niveau.getId());
		int nbrModule=modules.size();
		
		List<InscriptionAdministrative> result=inscAdminDAO.getInscriptionsByNiveau(year, niveau.getId());
		List<Etudiant> etudiants=new ArrayList();
		
		if(!result.isEmpty()) {
			
			for(InscriptionAdministrative it:result) {
				etudiants.add(it.getEtudiant());
			}
			
			List<InscriptionPedagogique> listInscPedag=new ArrayList();
			List<List<InscriptionModule>> list=new ArrayList();
			
			for(Etudiant etu:etudiants) {
				InscriptionPedagogique inscPedag=inscPedagDAO.getInscriptionPedagogique(etu.getId(), year);
				List<InscriptionModule> listInscModule=inscModuleDAO.getInscriptionModuleByEtudiant(inscPedag.getId()); 
				
				for(InscriptionModule it2:listInscModule) {
					if(it2.getNote()==null) {
						throw new MoyennesException("Module sans note !");
					}
				}
				list.add(listInscModule);
			}
			
			int i=0;
			for(List<InscriptionModule> it3:list) {
				double noteF=0;
				double somme=0;
				for(InscriptionModule it4:it3) {
					double note=it4.getNote().getNoteFinal();
					double coeff=it4.getModule().getCoeff();
					somme+=note*coeff;
				}
				noteF=somme/nbrModule;
				double noteFinale= (double) Math.round(noteF * 100) / 100;
				result.get(i).setNoteFinal(noteFinale);
				inscAdminDAO.update(result.get(i));
				i++;
			}
			
			//Rank
			List<InscriptionAdministrative> resultSorted=inscAdminDAO.getInscriptionsByNiveau(year, niveau.getId());
			int j=1;
			for(InscriptionAdministrative it3:resultSorted) {
				it3.setRank(j);
				inscAdminDAO.update(it3);
				j++;
			}
		}else {
			throw new MoyennesException("Année universitaire et/ou niveau incorrect !");
		}
		
	}

}
