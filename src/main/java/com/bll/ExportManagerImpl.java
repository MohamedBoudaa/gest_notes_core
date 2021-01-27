package com.bll;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bo.Etudiant;
import com.bo.Module;
import com.bo.InscriptionAdministrative;
import com.bo.InscriptionPedagogique;
import com.bo.Niveau;
import com.dao.DaoFactory;
import com.dao.impl.EtudiantDaoImpl;
import com.dao.impl.InscAdminDaoImpl;
import com.dao.impl.InscMatiereDaoImpl;
import com.dao.impl.InscModuleDaoImpl;
import com.dao.impl.InscPedagoDaoImpl;
import com.dao.impl.MatiereDaoImpl;
import com.dao.impl.NiveauDaoImpl;

public class ExportManagerImpl implements ExportManager {

	private static final EtudiantDaoImpl etuDAO = (EtudiantDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_ETUDIANT);
	private static final InscAdminDaoImpl inscAdminDAO = (InscAdminDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);
	private static final NiveauDaoImpl nivDAO= (NiveauDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_NIVEAU);
	private static final InscPedagoDaoImpl inscPedagDAO=(InscPedagoDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCPEDAGO);
	private static final SearchManagerImpl search=new SearchManagerImpl();

	public static File exportBulltinToPDF
	(String cne ,int year ,String niveauTitle) throws ExportException {

		List<Etudiant> etudiants = etuDAO.getByCne(cne);

		if( !etudiants.isEmpty() ) {

			Etudiant etudiant=etudiants.get(0);
			Niveau niveau=nivDAO.getIdNiveau(niveauTitle);
			List<InscriptionAdministrative> inscAdmin=inscAdminDAO.getInscription(year, etudiant.getId(), niveau.getId());

			if( !inscAdmin.isEmpty() ) {

				if(inscAdmin.get(0).getNoteFinal() != -1) {
					InscriptionPedagogique inscPedag=inscPedagDAO.getInscriptionPedagogique(etudiant.getId(), year);
					return PDFCreator.generatePDF(etudiant, niveau, inscAdmin.get(0), inscPedag,year);
				}else {
					throw new ExportException("Aucune délibération n'a eu lieu !" );					
				}
			}else {
				throw new ExportException("L'étudiant n'est pas inscrit pour l'année " + year +"et/ou le niveau "+niveauTitle);
			}
		}else {
			throw new ExportException("Etudiant introuvable");
		}
	}
	
}