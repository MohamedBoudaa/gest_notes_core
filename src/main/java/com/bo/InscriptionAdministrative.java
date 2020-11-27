package com.bo;
	
@Entity
public class InscriptionAdministrative {

	@Id
	@GeneratedValue(generator="increment")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idNiveau")
	private Niveau niveau;

	@ManyToOne
    	@JoinColumn(name="idEtudiant")
	private Etudiant etudiant;

	private int year;
	
	private int state; // 1 confirmed , 0 unconfirmed , -1 canceled
	
	private double noteBeforeDelib;
	
	private double noteFinal;
	
	private int rank;	
	
}
