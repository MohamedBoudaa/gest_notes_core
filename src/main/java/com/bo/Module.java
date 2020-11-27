package com.bo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Module {
	
	@Id
	@GeneratedValue(generator="increment")
	private Long id;
	
	private String title;
	
	private Niveau niveau;
	
	private int semester;
	
	@OneToMany(mappedBy="id",cascade = CascadeType.ALL, targetEntity=InscriptionMatiere.class)
	private List<Matiere> matieres;
	
	private double coeff;

}
