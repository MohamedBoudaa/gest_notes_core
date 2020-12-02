package com.dao;

import com.bo.Etudiant;
import com.bo.InscriptionAdministrative;

public interface InscAdminDao extends IDao<Long, InscriptionAdministrative>{

	public boolean exists(Etudiant e,int year);

}
