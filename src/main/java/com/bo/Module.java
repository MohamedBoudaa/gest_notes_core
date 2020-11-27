package com.bo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Module {
	
	@Id
	@GeneratedValue(generator="increment")
	private Long id;
	
	private String title;
	
	private Niveau niveau;
	
	private int semester;
	
	
	private List<Matiere> matieres;
	
	private double coeff;

}
