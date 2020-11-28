package com.bo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class InscriptionPedagogique {

	@Id
	@GeneratedValue(generator="increment")
	private Long id;
	
	private int year;
	
	@ManyToOne
    @JoinColumn(name="idEtudiant")
	private Etudiant etudiant;
	
	@OneToMany(mappedBy="id",cascade = CascadeType.ALL, targetEntity=InscriptionModule.class)
	private List<InscriptionModule> inscriptionModule;

	public InscriptionPedagogique() {
		
	}
	
	public InscriptionPedagogique(int year, Etudiant etudiant, List<InscriptionModule> inscriptionModule) {
		super();
		this.year = year;
		this.etudiant = etudiant;
		this.inscriptionModule = inscriptionModule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public List<InscriptionModule> getInscriptionModule() {
		return inscriptionModule;
	}

	public void setInscriptionModule(List<InscriptionModule> inscriptionModule) {
		this.inscriptionModule = inscriptionModule;
	}
	
	
	
}
