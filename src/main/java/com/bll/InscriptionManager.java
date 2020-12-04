package com.bll;

import java.util.List;

import com.bo.Etudiant;
import com.bo.Module;
import com.bo.Niveau;

public interface InscriptionManager extends Manager{
	
	void inscrirAdmin(Etudiant etudiant, Niveau niveau, int year) throws InscriptionException;
	
	void inscription(Etudiant etd) throws InscriptionException;
	
	void inscrirPedago(Etudiant etudiant, List<Module> modules, int year) throws InscriptionException;

}
