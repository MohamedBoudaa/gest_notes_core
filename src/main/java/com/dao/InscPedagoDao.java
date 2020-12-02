package com.dao;

import com.bo.Etudiant;
import com.bo.InscriptionPedagogique;

public interface InscPedagoDao  extends IDao<Long, InscriptionPedagogique>{

	public boolean exists(Etudiant e,int year);

}
