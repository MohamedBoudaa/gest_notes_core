package com.bo;

import java.util.ArrayList;
import java.util.List;

public class Etudiant implements Personne{
	
	
	private Long id;
	
	private String firstname;
	
	private String secondName;
	
	private String cne;
	
	private String cin;
	
	private List<InscriptionAdministrative> listInscAdmin;

	private List<InscriptionPedagogique> listInscPedago;

	
	
	
	public Etudiant() {
		
	}
	
	public Etudiant(String firstname, String secondName, String cne, String cin) {
		super();
		this.firstname = firstname;
		this.secondName = secondName;
		this.cne = cne;
		this.cin = cin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public List<InscriptionAdministrative> getListInscAdmin() {
		return listInscAdmin;
	}

	public void setListInscAdmin(List<InscriptionAdministrative> listInscAdmin) {
		this.listInscAdmin = listInscAdmin;
	}

	public List<InscriptionPedagogique> getListInscPedago() {
		return listInscPedago;
	}

	public void setListInscPedago(List<InscriptionPedagogique> listInscPedago) {
		this.listInscPedago = listInscPedago;
	}
	
	
	public void inscrirPedago(InscriptionPedagogique ins) {
		this.listInscPedago.add(ins);
	}
	
	
	
	
	
	
	
	
	

}
