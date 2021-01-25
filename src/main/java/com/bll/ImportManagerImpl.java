package com.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.IDN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.bo.Etudiant;
import com.bo.InscriptionModule;
import com.bo.InscriptionPedagogique;
import com.bo.Module;
import com.bo.Niveau;
import com.bo.Note;
import com.dao.DaoFactory;
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

	public static final String DEST = "";

	public ImportManagerImpl() {

	}

	/**
	 * Check if csv file contains only students of the selected niveau
	 */
	public static void checkStudentList(List<String> students, String niveauTitle, String year) throws ImportException {
		System.out.println("checking Students list....");

		Niveau niveau = DAO_NIVEAU.getByTitle(niveauTitle);

		List<HashMap<String, String>> list = searchManager.searchStudent(null, null, null, niveau.getId(),
				Integer.parseInt(year));

		if (list.size() != students.size()) {
			throw new ImportException("La liste des étudiants est incorrecte");
		}
		if (list != null) {

			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).get("cne").equalsIgnoreCase(students.get(i))) {
					throw new ImportException("La liste des étudiants est incorrecte");
				}
			}

		} else {
			throw new ImportException("aucun étudiant trouvé !");
		}
		System.out.println("Checking students list has been done successfully !");
	}

	/**
	 * check if niveau and module exist
	 */
	public static void CheckModuleAndNiveau(String niveau, String module) throws ImportException {
		System.out.println("Checking module and niveau ....");
		Niveau niv = DAO_NIVEAU.getByTitle(niveau);

		if ((niv == null)) {
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
			throw new ImportException("Le module \"" + module + "\" n'existe pas dans le niveau " + niveau + " !");
		}
		
		
		System.out.println("Checking module and niveau has been done successfully ! ");
	}

	/**
	 * Reads notes of each student in "niveau" for "module" the csv file should be
	 * on the following forme :
	 * 
	 * 
	 * CNE , note .. , .. .. , ..
	 * 
	 * @param path
	 */

	public static String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	public static void importNotesFromCsv(File file, String niveau, String module, String year)  {

		

		String path = file.getAbsolutePath();
		List<String> students = new ArrayList<String>();
		List<Long> notes = new ArrayList<Long>();

		try {
			String extension = getFileExtension(file);

			if (!extension.equalsIgnoreCase(".csv")) {
				throw new Exception("Erreur! l'extension du fichier n'est pas 'csv'.");
			}
			
			CSVReader reader = new CSVReader(new FileReader(path));
			String[] nextLine;

			while ((nextLine = reader.readNext()) != null) {
				// add students and notes to their respective arraylists
				if (nextLine != null) {
					students.add(nextLine[0]);

					notes.add(Long.parseLong(nextLine[1]));
				}
			}

			CheckModuleAndNiveau(niveau, module);
			checkStudentList(students, niveau, year);

			// import notes
			for (int i = 0; i < students.size(); i++) {
				Note note = new Note(-1, -1, notes.get(i));
				// get Object Etudiant
				Etudiant etu = DAO_ETUDIANT.getByCne(students.get(i)).get(0);
				InscriptionPedagogique inscPedago = etu.getListInscPedago().get(etu.getInscPedagoIndex(year));
				inscPedago.getInscriptionModuleByModuleTitle(module).setNote(note);

				etu.setInscPedagoAt(etu.getInscPedagoIndex(year), inscPedago);
				DAO_ETUDIANT.update(etu);
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "erreur : " + e.getMessage());
			e.printStackTrace();
		} catch (ImportException e) {
			JOptionPane.showMessageDialog(null, "erreur : " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "erreur : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
