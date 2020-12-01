package com.bo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InscriptionAdministrative {

	@Id
	@GeneratedValue(generator="increment")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL,targetEntity=Niveau.class)
	@JoinColumn(name="idNiveau")
	private Niveau niveau;

	@ManyToOne
    	@JoinColumn(name="idEtudiant")
	private Etudiant etudiant;

	private int year;
	
	private int state; // 1 confirmed , 0 unconfirmed , -1 canceled
	
	private double noteBeforeDelib;
	
	private double noteFinal;
	
	private int rank;	
	
	public InscriptionAdministrative() {
		
	}

	public InscriptionAdministrative(Niveau niveau, Etudiant etudiant, int year, int state, double noteBeforeDelib,
			double noteFinal, int rank) {
		super();
		this.niveau = niveau;
		this.etudiant = etudiant;
		this.year = year;
		this.state = state;
		this.noteBeforeDelib = noteBeforeDelib;
		this.noteFinal = noteFinal;
		this.rank = rank;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getNoteBeforeDelib() {
		return noteBeforeDelib;
	}

	public void setNoteBeforeDelib(double noteBeforeDelib) {
		this.noteBeforeDelib = noteBeforeDelib;
	}

	public double getNoteFinal() {
		return noteFinal;
	}

	public void setNoteFinal(double noteFinal) {
		this.noteFinal = noteFinal;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "InscriptionAdministrative [id=" + id + ", niveau=" + niveau.getTitle() 
				+ ", etudiant=" + etudiant.getFirstname() +" "+ etudiant.getSecondName()
				+ ", year="+ year + ", state=" + state + ", noteBeforeDelib=" 
				+ noteBeforeDelib + ", noteFinal=" + noteFinal
				+ ", rank=" + rank + "]";
	}
	
	
	
	
	
}
