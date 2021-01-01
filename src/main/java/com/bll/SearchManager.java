package com.bll;

import java.util.HashMap;
import java.util.List;

import com.bo.Etudiant;

public interface SearchManager {
	
	List<HashMap<String,String>> searchStudent(String name,String scndName,String cne,Long niveau);

}
