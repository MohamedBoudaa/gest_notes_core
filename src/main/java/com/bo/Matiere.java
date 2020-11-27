package com.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Matiere {

	@Id
	@GeneratedValue(generator = "increment")
	private Long id;
	
	private String title;
	
	private double coeff;
	
}
