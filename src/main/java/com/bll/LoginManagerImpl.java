package com.bll;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import com.bo.Compte;
import com.dao.CompteDao;
import com.dao.DaoFactory;

public class LoginManagerImpl implements LoginManager{

	private CompteDao compteDao = (CompteDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DAO_COMPTE);
	
	protected final Logger LOGGER;
	
	
	/**
	 * Constructor
	 */
	public LoginManagerImpl() {
		LOGGER = Logger.getLogger(LoginManagerImpl.class);
	}
	
	
	public boolean login(Compte compte) throws LoginException {
		if(!compteDao.exists(compte)) {
			throw new LoginException("Le compte n'existe pas !");
		}
		Compte tmp = compteDao.getByColName("username", compte.getUsername(), "Compte").get(0);
		if(compte.getPassword() == tmp.getUsername()) {
			return true;
		}
		
		return false;
	}
	
	
}
