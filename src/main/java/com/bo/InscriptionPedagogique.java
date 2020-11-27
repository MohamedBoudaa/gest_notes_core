package com.bo;

import java.util.List;

public class InscriptionPedagogique {

	private Long id;
	
	private int year;

	@ManyToOne
   	@JoinColumn(name="idEtudiant")
	private Etudiant etudiant;
	
	@OneToMany(mappedBy="id",cascade = CascadeType.ALL, targetEntity=InscriptionModule.class)
	private List<InscriptionModule> inscriptionModule;
	
	
}
