package de.privat.ciupka.schedule.controller;

import java.util.ArrayList;

import de.privat.ciupka.schedule.logic.schedule.PropertieHandler;
import de.privat.ciupka.schedule.logic.schedule.Subject;
import de.privat.ciupka.schedule.logic.schedule.SubjectHandler;

public class Controller {
	
	private static Controller instance;
	
	private GUIController guiCon;
	private SubjectHandler subjectHandler;
	private PropertieHandler propertieHandler;
	
	private Controller() {}
	
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
			instance.start();
		}
		return instance;
	}
	
	public void start() {
		guiCon = GUIController.getInstance();
		subjectHandler = new SubjectHandler();
		propertieHandler = PropertieHandler.getInstance();
		loadAllSubjects();
	}
	
	public void loadAllSubjects() {
		subjectHandler.addSubjects(propertieHandler.loadSubjects());
	}
	
	public void editOrAddSubject(Subject subject) {
		propertieHandler.editSubject(subject);
	}
	
	public void saveAllSubjects(ArrayList<Subject> subjects) {
		for (Subject subject : subjects) {
			propertieHandler.editSubject(subject);
		}
		saveSubjects();
	}
	
	public void saveSubjects() {
		propertieHandler.saveSubjects();
	}

	public void saveSubject(Subject subject) {
		propertieHandler.editSubject(subject);
	}
	
	

}
