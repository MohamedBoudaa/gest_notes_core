package com.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Module {

	@Id
	@GeneratedValue(generator = "increment")
	private Long id;

	private String title;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idNiveau")
	private Niveau niveau;

	private int semester;

	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, targetEntity = Matiere.class)
	private List<Matiere> matieres;

	private double coeff;

	public Module() {

	}

	
	
	
	public Module(String title, Niveau niveau, int semester, double coeff) {
		super(); 
		this.title = title;
		this.niveau = niveau;
		this.semester = semester;
		this.coeff = coeff;
	}




	public Module(String title, Niveau niveau, int semester, List<Matiere> matieres, double coeff) {
		super();
		this.title = title;
		this.niveau = niveau;
		this.semester = semester;
		this.matieres = matieres;
		this.coeff = coeff;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public List<Matiere> getMatieres() {
		return matieres;
	}

	public void setMatieres(List<Matiere> matieres) {
		this.matieres = matieres;
	}

	public double getCoeff() {
		return coeff;
	}

	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}
	
	public void addMatiere(Matiere O) {
		if(this.matieres == null) {
			this.matieres = new ArrayList<Matiere>();
		}
		this.matieres.add(O);
	}
	

}
