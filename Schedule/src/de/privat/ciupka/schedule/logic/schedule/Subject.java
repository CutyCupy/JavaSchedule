package de.privat.ciupka.schedule.logic.schedule;

import java.awt.Color;

import de.privat.ciupka.schedule.controller.Controller;

public class Subject {
	
	private String name;
	private String shortName;
	private Color color;
	private String teacher;
	private Controller controller;
	
	
	public Subject() {
		controller = Controller.getInstance();
	}
	
	
	public void save() {
		controller.saveSubject(this);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setShortName(String short_name) {
		this.shortName = short_name;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public Color getColor() {
		return color;
	}

}
