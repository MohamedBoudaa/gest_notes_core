package com.bo;

import java.util.List;

public class Niveau {
	
	private Long id;
	
	private String title;
	
	private String label;
		
	private int cycle;

	@OneToMany(mappedBy="id",cascade = CascadeType.ALL, targetEntity=Module.class)
	private List<Module> modules;

}
