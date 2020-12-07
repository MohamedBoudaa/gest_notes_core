package com.bll;

import javax.security.auth.login.LoginException;

import com.bo.Compte;

public interface LoginManager extends Manager{

	
	boolean login(Compte compte) throws LoginException;
}
