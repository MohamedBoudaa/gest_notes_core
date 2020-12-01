package com.bo;

import java.util.ArrayList;
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
	
	@OneToMany(mappedBy="niveau",cascade = CascadeType.ALL, targetEntity=Module.class)
	private List<Module> modules;

	
	public Niveau() {
		
	}

	public Niveau(String title, String label, int cycle) {
		super();
		this.title = title;
		this.label = label;
		this.cycle = cycle;
	}

	public Niveau(String title, String label, int cycle, List<Module> modules) {
		super();
		this.title = title;
		this.label = label;
		this.cycle = cycle;
		this.modules = modules;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public int getCycle() {
		return cycle;
	}


	public void setCycle(int cycle) {
		this.cycle = cycle;
	}


	public List<Module> getModules() {
		return modules;
	}


	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	
	public void addModule(Module O) {
		if(this.modules == null) {
			this.modules = new ArrayList<Module>();
		}
		this.modules.add(O);
	}

	@Override
	public String toString() {
		return "Niveau [id=" + id + ", title=" + title + ", label=" + label + ", cycle=" + cycle + ", modules="
				+ modules + "]";
	}
	
	
	
	
}