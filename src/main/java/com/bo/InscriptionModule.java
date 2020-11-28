package com.bo;

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
public class InscriptionModule {
	
	@Id
	@GeneratedValue(generator = "increment")
	private Long id;
	
	@ManyToOne  
	@JoinColumn( name="idModule" )
	private Module module;

	@OneToMany(mappedBy="id",cascade = CascadeType.ALL, targetEntity=InscriptionMatiere.class)
	private List<InscriptionMatiere> inscriptionsMatieres;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idnote")
	private Note note;
	
	@ManyToOne  
	@JoinColumn( name="idInscPedago" )
	private InscriptionPedagogique InscPedago;

	private int ValidationModule;
	
	
	public InscriptionModule() {
		
	}

	public InscriptionModule(Module module, List<InscriptionMatiere> inscriptionsMatieres, Note note,
			InscriptionPedagogique inscPedago, int validationModule) {
		super();
		this.module = module;
		this.inscriptionsMatieres = inscriptionsMatieres;
		this.note = note;
		InscPedago = inscPedago;
		ValidationModule = validationModule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<InscriptionMatiere> getInscriptionsMatieres() {
		return inscriptionsMatieres;
	}

	public void setInscriptionsMatieres(List<InscriptionMatiere> inscriptionsMatieres) {
		this.inscriptionsMatieres = inscriptionsMatieres;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public InscriptionPedagogique getInscPedago() {
		return InscPedago;
	}

	public void setInscPedago(InscriptionPedagogique inscPedago) {
		InscPedago = inscPedago;
	}

	public int getValidationModule() {
		return ValidationModule;
	}

	public void setValidationModule(int validationModule) {
		ValidationModule = validationModule;
	}
	
	

}
