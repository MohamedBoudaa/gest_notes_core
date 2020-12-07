package com.dao;

import com.bo.Compte;

public interface CompteDao extends IDao<Long, Compte>{



		boolean exists(Compte compte);
		
			

}
