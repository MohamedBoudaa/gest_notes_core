package com.bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.bo.Etudiant;
import com.bo.InscriptionModule;
import com.bo.Module;
import com.bo.Niveau;
import com.dao.DaoException;
import com.dao.DaoFactory;
import com.dao.ModuleDao;
import com.dao.SessionFactoryBuilder;
import com.dao.impl.InscAdminDaoImpl;
import com.dao.impl.InscModuleDaoImpl;
import com.dao.impl.NiveauDaoImpl;

public class SearchManagerImpl implements SearchManager {

	private static final int CURRENT_YEAR = 2020;

	private static final ModuleDao moduleDao = (ModuleDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_MODULE);

	private static final InscModuleDaoImpl inscModuleDao = (InscModuleDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCMODULE);

	private static final InscAdminDaoImpl inscAdminDao = (InscAdminDaoImpl) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);
	
	public SearchManagerImpl() {

	}

	/**
	 * Filter List of hashmap by name
	 */
	private static List<HashMap<String, String>> filterByName(String name, List<HashMap<String, String>> list) {
		List<HashMap<String, String>> res = new ArrayList<HashMap<String,String>>();;

		if (name == null) {
			return list;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("firstName").equalsIgnoreCase(name)) {
				res.add(list.get(i));
			}
		}

		return res;
	}

	/**
	 * Filter List of hashmap by second name
	 */
	private static List<HashMap<String, String>> filterBySecondName(String secondName, List<HashMap<String, String>> list) {
		List<HashMap<String, String>> res = new ArrayList<HashMap<String,String>>();

		if (secondName == null) {
			return list;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("secondName").equalsIgnoreCase(secondName)) {
				res.add(list.get(i));
			}
		}

		return res;
	}

	/**
	 * Filter List of hashmap by second name
	 */
	private static List<HashMap<String, String>> filterByCne(String cne, List<HashMap<String, String>> list) {
		List<HashMap<String, String>> res = new ArrayList<HashMap<String,String>>();

		if (cne == null) {
			return list;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("cne").equalsIgnoreCase(cne)) {
				res.add(list.get(i));
			}
		}

		return res;
	}

	@Override
	public List<HashMap<String, String>> searchStudent(String name, String scndName, String cne, Long niveau,Integer year) {

		List<HashMap<String, String>> result = null;

		

		if(year == null) {
			System.out.println("year is null");
			result = inscAdminDao.getEtudiantByNiveau(niveau);
		}else {
			result = inscAdminDao.getEtudiantByNiveauYear(niveau,year);
		}
		
		result = filterByName(name, result);

		result = filterBySecondName(scndName, result);

		result = filterByCne(cne, result);

		return result;
	}
	
	
	public List<Module> getAllModules(){
		return moduleDao.getAll();
	}
	
	public List<Module> filterModulesByNiveau(String niveau,List<Module> modules){
		List<Module> list = new ArrayList<Module>();
		
		if(niveau == null) {
			return modules;
		}
		
		for (int i = 0; i < modules.size(); i++) {
			String niv = modules.get(i).getNiveau().getTitle();
			if (niv.equals(niveau)) {
				list.add(modules.get(i));
			}
		}
		
		
		
		return list;
	}
	
	
	public List<Module> getModulesByNiveau(String niveau){
		
		List<Module> modules = getAllModules();
		
		modules = filterModulesByNiveau(niveau, modules);
		
		return modules;
	}

	
	public InscriptionModule getInscModuleByInscPedagoAndModule(Long idInscPedago,String idModule,Integer year){
		
		List<InscriptionModule> list = inscModuleDao.getByColName("idModule", idModule, "InscriptionModule");
		
		list = filterInscModuleByInscPedago(list, idInscPedago);
		
		return null;
	}
	
	private static List<InscriptionModule> filterInscModuleByInscPedago(List<InscriptionModule> list,Long idInscPedago){
		List<InscriptionModule> newList = new ArrayList<InscriptionModule>();
		
		
		for(int i = 0 ; i< list.size() ;i++) {
			if(list.get(i).getInscPedago().getId().equals(idInscPedago)) {
				newList.add(list.get(i));
			}
		}
		return newList;
	}
	
	
	

}
