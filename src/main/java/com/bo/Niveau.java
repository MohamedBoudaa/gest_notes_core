package com.bo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Niveau {
	
	@Id
	@GeneratedValue(generator = "increment")
	private Long id;
	
	private String title;
	
	private String label;
		
	private int cycle;
	
	@OneToMany(mappedBy="id",cascade = CascadeType.ALL, targetEntity=Module.class)
	private List<Module> modules;

}