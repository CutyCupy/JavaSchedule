package de.privat.ciupka.schedule.logic.schedule;

import java.util.ArrayList;

import de.privat.ciupka.schedule.controller.Controller;

public class SubjectHandler {
	
	private static SubjectHandler instance;
	
	private ArrayList<Subject> subjects;
	private Controller controller;
	
	public SubjectHandler() {
		subjects = new ArrayList<Subject>();
		controller = Controller.getInstance();
	}
	
	
	
}
