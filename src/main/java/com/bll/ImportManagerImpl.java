package com.bll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.bo.Etudiant;
import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Module;
import com.bo.Niveau;
import com.bo.Note;
import com.dao.DaoFactory;
import com.dao.HibernateGenericDao;
import com.dao.impl.EtudiantDaoImpl;
import com.dao.impl.ModuleDaoImpl;
import com.dao.impl.NiveauDaoImpl;
import com.dao.impl.NoteDaoImpl;
import com.opencsv.CSVReader;

public class ImportManagerImpl implements ImportManager {

	private static final NiveauDaoImpl DAO_NIVEAU = (NiveauDaoImpl) DaoFactory.getDaoFactory()
			.getDao(DaoFactory.DAO_NIVEAU);

	private static final EtudiantDaoImpl DAO_ETUDIANT = (EtudiantDaoImpl) DaoFactory.getDaoFactory()
			.getDao(DaoFactory.DAO_ETUDIANT);

	private static final ModuleDaoImpl DAO_MODULE = (ModuleDaoImpl) DaoFactory.getDaoFactory()
			.getDao(DaoFactory.DAO_MODULE);

	private static final NoteDaoImpl DAO_NOTE = (NoteDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_NOTE);

	private static final SearchManagerImpl searchManager = new SearchManagerImpl();

	public static String DEST = "";

	private  PrintWriter LOGGER ;
	
	public ImportManagerImpl() {
		

		
	}
	
	
	
	

	
	/**
	 * check student list in file with all students registered in the module in that year
	 * @throws ImportException 
	 * 
	 */
	public void checkRegisteredStudentsByModule(List<String> students,String module,String year) throws ImportException {
		LOGGER.println("*************CHECKING STUDENTS*************");
		Long idModule = DAO_MODULE.getByColName("title", module, "Module").get(0).getId();
		
		List<Etudiant> list = DAO_ETUDIANT.getEtudiantByModule(idModule, year);
		if(list.size()!= students.size()) {
			LOGGER.println("Erreur !! la liste des etudiants est incorrecte");
			throw new ImportException("La liste des etudiants est incorrecte - database :"+list.size()+" - file : "+students.size());
		}
		
		Collections.sort(students);
		if (list != null) {
			
			for (int i = 0; i < list.size(); i++) {
				
				LOGGER.println("Database : "+list.get(i).getCne()+" --  File : "+students.get(i));
				if (!list.get(i).getCne().equalsIgnoreCase(students.get(i))) {
					LOGGER.println("Erreur !! la liste des etudiants est incorrecte");
					throw new ImportException("La liste des etudiants est incorrecte");
				}
			}

		} else {
			LOGGER.println("Erreur !! aucun etudiant trouve !");
			throw new ImportException("aucun etudiant trouve !");
		}
		LOGGER.println("**********Checking students list has been done successfully !************");
	}
		


	/**
	 * Check if csv file contains only students of the selected niveau
	 */
	public  void checkStudentList(List<String> students, String niveauTitle, String year) throws ImportException {
		LOGGER.println("checking Students list....");

		Niveau niveau = DAO_NIVEAU.getByTitle(niveauTitle);

		List<HashMap<String, String>> list = searchManager.searchStudent(null, null, null, niveau.getId(),
				Integer.parseInt(year));

		
		if (list != null) {

			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).get("cne").equalsIgnoreCase(students.get(i))) {
					throw new ImportException("La liste des etudiants est incorrecte");
				}
			}

		} else {
			throw new ImportException("aucun etudiant trouve !");
		}
		LOGGER.println("Checking students list has been done successfully !");
	}

	/**
	 * check if niveau and module exist
	 */
	public  void CheckModuleAndNiveau(String niveau, String module) throws ImportException {
		LOGGER.println("*************Checking module and niveau .... *************");
		Niveau niv = DAO_NIVEAU.getByTitle(niveau);

		if ((niv == null)) {
			LOGGER.println("Erreur !! Le Niveau " + niveau + " n'existe pas !");
			throw new ImportException("Le Niveau " + niveau + " n'existe pas !");
		}
		String idNiveau = niv.getId().toString();
		List<Module> modules = DAO_MODULE.getByColName("idNiveau", idNiveau, "Module");
		
		boolean found = false;
		for(int i = 0 ; i<modules.size();i++) {
			if(modules.get(i).getTitle().equalsIgnoreCase(module)) {
				found = true;
			}
		}
		if(!found) {
			LOGGER.println("Erreur !! Le module \"" + module + "\" n'existe pas dans le niveau " + niveau + " !");
			throw new ImportException("Le module \"" + module + "\" n'existe pas dans le niveau " + niveau + " !");
		}
		
		
		LOGGER.println("*************Checking module and niveau has been done successfully ! *************");
	}

	/**
	 * Reads notes of each student in "niveau" for "module" the csv file should be
	 * on the following forme :
	 * 
	 * 
	 * CNE , note 
	 * .. , .. 
	 * .. , ..
	 * 
	 * @param path
	 */

	public  String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	public  void importNotesFromCsv(File file, String niveau, String module, String year)  {

		try {
			File tmp_file = new File("log\\ImportLog.txt");
			LOGGER = new PrintWriter("log\\ImportLog.txt", "UTF-8");
			DEST = tmp_file.getAbsolutePath();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
		String path = file.getAbsolutePath();
		LOGGER.println("Fichier selectionne : "+ path);
		
		List<String> students = new ArrayList<String>();
		List<String> studentsAdjourned = new ArrayList<String>();
		List<Long> notes = new ArrayList<Long>();
		

		try {
			String extension = getFileExtension(file);

			if (!extension.equalsIgnoreCase(".csv")) {
				LOGGER.println("Erreur !! l'extension du fichier n'est pas 'csv'.");
				throw new Exception("Erreur! l'extension du fichier n'est pas 'csv'.");
			}
			
			CSVReader reader = new CSVReader(new FileReader(path));
			String[] nextLine;
			
			LOGGER.println("Lecture depuis le fichier ....");
			while ((nextLine = reader.readNext()) != null) {
				// add students and notes to their respective arraylists
				if (nextLine != null) {
					if(nextLine[1].startsWith("#")) {
						notes.add(null);
						
						continue;
					}else {
						notes.add(Long.parseLong(nextLine[1]));
					}
					if(nextLine[0].startsWith("#")) {
						studentsAdjourned.add(nextLine[0].split("#")[1]);
					}else {
						students.add(nextLine[0]);
					}

					
				}
			}
			
			List<String> allStudents = new ArrayList<String>();
			allStudents.addAll(students);
			allStudents.addAll(studentsAdjourned);
			
			CheckModuleAndNiveau(niveau, module);
//			checkStudentList(students, niveau, year);
			checkRegisteredStudentsByModule(allStudents, module, year);
			
			// import notes
			LOGGER.println("Verification avec succes !!\n Debut de l'importation des notes...");
			for (int i = 0; i < allStudents.size(); i++) {
				if(notes.get(i) == null) {
					allStudents.add(i, null);
					continue;
				}
				Note note = new Note(-1, -1, notes.get(i));
				// get Object Etudiant
				Etudiant etu = DAO_ETUDIANT.getByCne(allStudents.get(i)).get(0);
				LOGGER.println("Etudiant "+etu.getFirstname()+" note : "+notes.get(i));
				InscriptionPedagogique inscPedago = etu.getListInscPedago().get(etu.getInscPedagoIndex(year));
				inscPedago.getInscriptionModuleByModuleTitle(module).setNote(note);

				etu.setInscPedagoAt(etu.getInscPedagoIndex(year), inscPedago);
				DAO_ETUDIANT.update(etu);
				
				
			}
			LOGGER.println("Importation des notes avec succes");

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "erreur : " + e.getMessage());
			e.printStackTrace();
		} catch (ImportException e) {
			JOptionPane.showMessageDialog(null, "erreur : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "erreur : " + e.getMessage());
			e.printStackTrace();
		}finally {
			LOGGER.close();
		}
	}
	
	public String getImportLog() throws IOException {
		File file = new File("log\\ImportLog.txt");
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data);
		return str;
	}
}
