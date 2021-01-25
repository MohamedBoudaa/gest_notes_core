package com.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Etudiant implements Personne{

	@Id
	@GeneratedValue(generator="increment")
	private Long id;
	
	private String firstname;
	
	private String secondName;
	
	private String cne;
	
	private String cin;
	
	
	@OneToMany(mappedBy="etudiant",cascade = CascadeType.ALL, targetEntity=InscriptionAdministrative.class)
	private List<InscriptionAdministrative> listInscAdmin;

	@OneToMany(mappedBy="etudiant",cascade = CascadeType.ALL, targetEntity=InscriptionPedagogique.class)
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
	
	
	public Etudiant(String firstname, String secondName, String cne, String cin,
			List<InscriptionAdministrative> listInscAdmin, List<InscriptionPedagogique> listInscPedago) {
		super();
		this.firstname = firstname;
		this.secondName = secondName;
		this.cne = cne;
		this.cin = cin;
		this.listInscAdmin = listInscAdmin;
		this.listInscPedago = listInscPedago;
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
	public Integer getInscPedagoIndex(String pyear) {
		int year = Integer.parseInt(pyear);
		for(int i = 0 ; i < listInscPedago.size();i++) {
			if(listInscPedago.get(i).getYear() == year) {
				return i;
			}
		}
		return null;
	}
	
	public void setInscPedagoAt(int index,InscriptionPedagogique element) {
		listInscPedago.set(index, element);
	}
	
	

	public void setListInscPedago(List<InscriptionPedagogique> listInscPedago) {
		this.listInscPedago = listInscPedago;
	}
	
	
	public void inscrirPedago(InscriptionPedagogique ins) {
		this.listInscPedago.add(ins);
	}
	
	public void addInscrPedago(InscriptionPedagogique O) {
		if(this.listInscPedago == null) {
			this.listInscPedago = new ArrayList<InscriptionPedagogique>();
		}
		this.listInscPedago.add(O);
	}
	
	public void addInscrAdmin(InscriptionAdministrative O) {
		if(this.listInscAdmin == null) {
			this.listInscAdmin = new ArrayList<InscriptionAdministrative>();
		}
		this.listInscAdmin.add(O);
	}


	@Override
	public String toString() {
		return "Etudiant [id=" + id + ", firstname=" + firstname + ", secondName=" + secondName + ", cne=" + cne
				+ ", cin=" + cin + ", listInscAdmin=" + listInscAdmin + ", listInscPedago=" + listInscPedago + "]";
	}
	
	
	
	
	
	

}
