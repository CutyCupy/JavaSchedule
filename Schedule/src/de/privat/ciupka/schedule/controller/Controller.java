package de.privat.ciupka.schedule.controller;

import java.util.ArrayList;

import de.privat.ciupka.schedule.gui.popups.Messages;
import de.privat.ciupka.schedule.logic.schedule.PropertieHandler;
import de.privat.ciupka.schedule.logic.schedule.ScheduleHandler;
import de.privat.ciupka.schedule.logic.schedule.Subject;

/**
 * The Controller class of ciupkaschedule which is the interface between logic and GUIController.
 * Handles all the saving / loading tasks.
 * Singleton pattern with private constructor.
 * @author Alexander
 *
 */
public class Controller {

	private static Controller instance;

	private PropertieHandler propertieHandler;
	private ScheduleHandler scheduleHandler;

	/**
	 * Private constructor to create a Singleton pattern.
	 */
	private Controller() {}

	/**
	 * Controllers getInstance() method will return the only instance of this class.
	 * Checks if the static instance attribute is null and if so, it will create a new instance of Controller.
	 * Also will call the start() method to initialize all the important attributes.
	 * @return Controller - only instance of this class.
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
			instance.start();
		}
		return instance;
	}

	/**
	 * Constructor like function, which creates the important attributes and loads all subjects.
	 */
	public void start() {
		propertieHandler = PropertieHandler.getInstance();
		scheduleHandler = new ScheduleHandler();
		loadAllSubjects();
	}

	/**
	 * Calls the loadSubjects function of the PropertieHandler to load all the saved Subjects.
	 * @return ArrayList(Subject) - Contains all the saved Subjects.
	 */
	public ArrayList<Subject> loadAllSubjects() {
		return propertieHandler.loadSubjects();
	}

	public boolean checkSubject(Subject newSubject) {
		if(propertieHandler.getSubjectByName(newSubject.getName(), false) == null && propertieHandler.checkUniqueShortName(newSubject.getShortName())) {
			return true;
		}
		return false;
	}

	/**
	 * Adds a newly created Subject.
	 * @param subject - newly created Subject object.
	 */
	public void addSubject(Subject subject) {
		propertieHandler.addSubject(subject);
	}

	/**
	 * Edits an existing subject.
	 * @param oldSubject - old Subject that will be edited.
	 * @param newSubject - new Subject with the changes of the old Subject.
	 */
	public void editSubject(Subject oldSubject, Subject newSubject) {
		propertieHandler.editSubject(oldSubject, newSubject);
	}

	/**
	 * Adds the given subjects to the current subjects and saves them into the .cfg file.
	 * @param subjects - ArrayList(Subject) which contains all the subjects that will be added + saved.
	 */
	public void saveAllSubjects(ArrayList<Subject> subjects) {
		for (Subject subject : subjects) {
			propertieHandler.addSubject(subject);
		}
		saveSubjects();
	}

	/**
	 * Saves all the current subjects in the .cfg file.
	 */
	public void saveSubjects() {
		propertieHandler.saveSubjects();
	}

	/**
	 * Deletes the given subject.
	 * @param subject - Subject that will be deleted.
	 */
	public void removeSubject(Subject subject) {
		propertieHandler.removeSubject(subject);
	}

	/**
	 * Calls the necessary function to export the current schedule as image (.type format).
	 * @param type - File ending without '.'.
	 */
	public void exportImage(String type) {
		scheduleHandler.exportImage(type);
	}

	/**
	 * TODO: Not implemented.
	 */
	public void exportToPDF() {
		//TODO: Writing a logic function that exports the current schedule as PDF file.
	}

	/**
	 * TODO: Not implemented.
	 */
	public void exportToXLS() {
		//TODO: Writing a logic function that exports the current schedule as XLS (Excel) file (maybe).
	}


	public PropertieHandler getPropertieHandler() {
		return propertieHandler;
	}
	/**
	 * Calls the function that lets the user save his schedule.
	 */
	public void saveSchedule() {
		scheduleHandler.saveSchedule();
	}

	/**
	 * Calls the function that lets the user load his schedule.
	 */
	public void loadSchedule() {
		scheduleHandler.loadSchedule();
	}

	/**
	 * Asks the user if he wants to save his schedule - and if so, the schedule will be saved using saveSchedule function.
	 */
	public void askSave() {
		if(Messages.openYesNoDialog("Save Schedule", "Do you want to save your schedule before leaving?")) {
			saveSchedule();
		}
	}

}
