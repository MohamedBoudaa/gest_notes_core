package com.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Matiere {

	@Id
	@GeneratedValue(generator = "increment")
	private Long id;
	
	private String title;
	
	private double coeff;
	
	@ManyToOne
    	@JoinColumn(name="idModule")
	private Module module;
	
	
	
	public Matiere() {
		
	}


	public Matiere(String title, double coeff, Module module) {
		super();
		this.title = title;
		this.coeff = coeff;
		this.module = module;
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



	public double getCoeff() {
		return coeff;
	}



	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}


	public Module getModule() {
		return module;
	}


	public void setModule(Module module) {
		this.module = module;
	}
	
	
	
	
	
	
}
