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

}
