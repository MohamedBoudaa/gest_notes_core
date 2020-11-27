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
	
}
