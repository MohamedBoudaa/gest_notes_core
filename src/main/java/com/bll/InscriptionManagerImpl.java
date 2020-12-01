package com.bll;

import com.dao.DaoFactory;
import com.dao.InscAdminDao;

public class InscriptionManagerImpl implements InscriptionManager{
	
	
	private InscAdminDao inscAdminDao = (InscAdminDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_INSCADMIN);
	
	

}
