package com.dao;

import java.util.List;

import com.bo.Etudiant;

public interface EtudiantDao extends IDao<Long, Etudiant>{

	boolean exists(Etudiant etudiant);
	
	List<Etudiant> getByCne(String cne);

}
