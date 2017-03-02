package de.privat.ciupka.schedule.controller;

import java.awt.Color;
import java.util.ArrayList;

import de.privat.ciupka.schedule.gui.schedule.SubjectLabel;
import de.privat.ciupka.schedule.logic.schedule.PropertieHandler;
import de.privat.ciupka.schedule.logic.schedule.Subject;
import de.privat.ciupka.schedule.logic.schedule.Time;

public class Controller {
	
	private static Controller instance;
	
	private GUIController guiCon;
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
		propertieHandler = PropertieHandler.getInstance();
		loadAllSubjects();
	}
	
	public ArrayList<Subject> loadAllSubjects() {
		return propertieHandler.loadSubjects();
	}
	
	public void addSubject(Subject subject) {
		System.out.println(subject.getColor().getRGB());
		System.out.println(Color.BLACK.getRGB());
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
	
	public void exportToPNG(String[] days, Time[] times, ArrayList<SubjectLabel> subjects, String Path) {
		//TODO: Writing a logic function that exports the current schedule as PNG image.
	}
	
	public void exportToJPG(String[] days, Time[] times, ArrayList<SubjectLabel> subjects, String Path) {
		//TODO: Writing a logic function that exports the current schedule as JPG image.
	}
	
	public void exportToPDF(String[] days, Time[] times, ArrayList<SubjectLabel> subjects, String Path) {
		//TODO: Writing a logic function that exports the current schedule as PDF file.
	}
	
	public void exportToXLS(String[] days, Time[] times, ArrayList<SubjectLabel> subjects, String Path) {
		//TODO: Writing a logic function that exports the current schedule as XLS (Excel) file (maybe).
	}

	public void removeSubject(Subject subject) {
		propertieHandler.removeSubject(subject);
	}
}
