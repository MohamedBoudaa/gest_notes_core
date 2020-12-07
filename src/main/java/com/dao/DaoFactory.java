package com.dao;


import com.dao.impl.CompteDaoImpl;
import com.dao.impl.EtudiantDaoImpl;
import com.dao.impl.InscAdminDaoImpl;
import com.dao.impl.InscMatiereDaoImpl;
import com.dao.impl.InscModuleDaoImpl;
import com.dao.impl.InscPedagoDaoImpl;
import com.dao.impl.MatiereDaoImpl;
import com.dao.impl.ModuleDaoImpl;
import com.dao.impl.NiveauDaoImpl;
import com.dao.impl.NoteDaoImpl;

public class DaoFactory {

	public static final String DAO_ETUDIANT = "studentDao";
	public static final String DAO_INSCADMIN = "inscAdminDao";
	public static final String DAO_INSCPEDAGO = "inscPedagoDao";
	public static final String DAO_INSCMATIERE = "inscMatiereDao";
	public static final String DAO_INSCMODULE = "inscModuleDao";
	public static final String DAO_MATIERE = "matiereDao";
	public static final String DAO_MODULE = "moduleDao";
	public static final String DAO_NIVEAU = "niveauDao";
	public static final String DAO_NOTE = "noteDao";
	public static final String DAO_COMPTE = "compteDao";

	private static final DaoFactory instance = new DaoFactory();

	public static final DaoFactory getDaoFactory() {
		return instance;
	}

	public final IDao getDao(String pname) {

		if (DAO_ETUDIANT.equals(pname)) {
			return new EtudiantDaoImpl();
		}
		if (DAO_INSCADMIN.equals(pname)) {
			return new InscAdminDaoImpl();
		}
		if (DAO_INSCPEDAGO.equals(pname)) {
			return new InscPedagoDaoImpl();
		}
		if (DAO_INSCMATIERE.equals(pname)) {
			return new InscMatiereDaoImpl();
		}
		if (DAO_INSCMODULE.equals(pname)) {
			return new InscModuleDaoImpl();
		}
		if (DAO_MATIERE.equals(pname)) {
			return new MatiereDaoImpl();
		}
		if (DAO_MODULE.equals(pname)) {
			return new ModuleDaoImpl();
		}
		if (DAO_NOTE.equals(pname)) {
			return new NoteDaoImpl();
		}
		if (DAO_NIVEAU.equals(pname)) {
			return new NiveauDaoImpl();
		}
		if (DAO_COMPTE.equals(pname)) {
			return new CompteDaoImpl();
		}


		return null;

	}

}
