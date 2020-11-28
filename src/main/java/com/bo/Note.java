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
	
	
	public Note() {
		
	}


	public Note(double noteSN, double noteSR, double noteFinal) {
		super();
		this.noteSN = noteSN;
		this.noteSR = noteSR;
		this.noteFinal = noteFinal;
	}


	public Long getIdNote() {
		return idNote;
	}


	public void setIdNote(Long idNote) {
		this.idNote = idNote;
	}


	public double getNoteSN() {
		return noteSN;
	}


	public void setNoteSN(double noteSN) {
		this.noteSN = noteSN;
	}


	public double getNoteSR() {
		return noteSR;
	}


	public void setNoteSR(double noteSR) {
		this.noteSR = noteSR;
	}


	public double getNoteFinal() {
		return noteFinal;
	}


	public void setNoteFinal(double noteFinal) {
		this.noteFinal = noteFinal;
	}
	
	
	
}
