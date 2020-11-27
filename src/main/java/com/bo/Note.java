package com.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Note {

	
	@Id
	@GeneratedValue(generator = "increment")
	private Long idNote;
	
	private double noteSN;
	
	private double noteSR; 
	
	private double noteFinal;
	
}
