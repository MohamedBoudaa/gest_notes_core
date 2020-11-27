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

	private int ValidationModule;

}
