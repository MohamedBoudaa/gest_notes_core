package com.dao;

import com.bo.InscriptionMatiere;
import com.bo.InscriptionModule;
import com.bo.Matiere;

public interface InscMatiereDao  extends IDao<Long, InscriptionMatiere>{
	
	public boolean exists(InscriptionModule inscModule,Matiere m);
	
}
