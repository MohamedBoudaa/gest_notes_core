package com.bo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class InscriptionPedagogique {

	@Id
	@GeneratedValue(generator="increment")
	private Long id;
	
	private int year;
	
	@ManyToOne
    @JoinColumn(name="idEtudiant")
	private Etudiant etudiant;
	
	@OneToMany(mappedBy="id",cascade = CascadeType.ALL, targetEntity=InscriptionModule.class)
	private List<InscriptionModule> inscriptionModule;
	
	
	
}
