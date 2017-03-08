package de.privat.ciupka.schedule.controller;

import java.util.ArrayList;

import de.privat.ciupka.schedule.gui.popups.Messages;
import de.privat.ciupka.schedule.logic.schedule.PropertieHandler;
import de.privat.ciupka.schedule.logic.schedule.ScheduleHandler;
import de.privat.ciupka.schedule.logic.schedule.Subject;

public class Controller {
	
	private static Controller instance;
	
	private PropertieHandler propertieHandler;
	private ScheduleHandler scheduleHandler;
	
	private Controller() {}
	
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
			instance.start();
		}
		return instance;
	}
	
	public void start() {
		GUIController.getInstance();
		propertieHandler = PropertieHandler.getInstance();
		scheduleHandler = new ScheduleHandler();
		loadAllSubjects();
	}
	
	public ArrayList<Subject> loadAllSubjects() {
		return propertieHandler.loadSubjects();
	}
	
	public void addSubject(Subject subject) {
		propertieHandler.addSubject(subject);
	}
	
	public void editSubject(Subject oldSubject, Subject newSubject) {
		propertieHandler.editSubject(oldSubject, newSubject);
	}
	
	public void saveAllSubjects(ArrayList<Subject> subjects) {
		for (Subject subject : subjects) {
			propertieHandler.addSubject(subject);
		}
		saveSubjects();
	}
	
	public void saveSubjects() {
		propertieHandler.saveSubjects();
	}
	
	public void exportImage(String type) {
		scheduleHandler.exportImage(type);
	}
	
	public void exportToPDF() {
		//TODO: Writing a logic function that exports the current schedule as PDF file.
	}
	
	public void exportToXLS() {
		//TODO: Writing a logic function that exports the current schedule as XLS (Excel) file (maybe).
	}

	public void removeSubject(Subject subject) {
		propertieHandler.removeSubject(subject);
	}

	public PropertieHandler getPropertieHandler() {
		return propertieHandler;
	}

	public void saveSchedule() {
		scheduleHandler.saveSchedule();
	}

	public void loadSchedule() {
		scheduleHandler.loadSchedule();
	}
	
	public void askSave() {
		if(Messages.openYesNoDialog("Save Schedule", "Do you want to save your schedule before leaving?")) {
			saveSchedule();
		}
	}
}
