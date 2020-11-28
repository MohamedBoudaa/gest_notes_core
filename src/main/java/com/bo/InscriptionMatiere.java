package com.bo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class InscriptionMatiere {
	
	@Id
	@GeneratedValue(generator = "increment")
	private Long id;
	
	@ManyToOne
	@JoinColumn( name="idMatiere" )
	private Matiere matiere;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idnote")
	private Note note;
	
	@ManyToOne
    @JoinColumn(name="idInscriptionModule")
	private InscriptionModule inscriptionModule;

	
	public InscriptionMatiere() {
		
	}


	public InscriptionMatiere(Matiere matiere, Note note, InscriptionModule inscriptionModule) {
		super();
		this.matiere = matiere;
		this.note = note;
		this.inscriptionModule = inscriptionModule;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Matiere getMatiere() {
		return matiere;
	}


	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}


	public Note getNote() {
		return note;
	}


	public void setNote(Note note) {
		this.note = note;
	}


	public InscriptionModule getInscriptionModule() {
		return inscriptionModule;
	}


	public void setInscriptionModule(InscriptionModule inscriptionModule) {
		this.inscriptionModule = inscriptionModule;
	}
	
	
	
	
}
