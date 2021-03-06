package de.privat.ciupka.schedule.logic.schedule;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import de.privat.ciupka.schedule.gui.popups.ErrorMessages;

public class PropertieHandler {
	
	public static final String HOME = System.getProperty("user.home");
	public static final String SEPERATOR = System.getProperty("file.separator");
	public static final String SAVE_PATH = HOME + SEPERATOR + "ciupkaschedule" + SEPERATOR;
	public static final String SUBJECT_PROPERTIES = SAVE_PATH + "subjects.cfg";
	public static final String SUBJECT_NAMES_PROPERTIES = SAVE_PATH + "subjectnames.cfg";
	
	private Properties subjects;
	private Properties subjectNames;
	private ArrayList<Subject> loadedSubjects;
	
	private static PropertieHandler instance;
	
	private PropertieHandler() {}
	
	public static PropertieHandler getInstance() {
		if(instance == null) {
			instance = new PropertieHandler();
			instance.start();
		}
		return instance;
	}
	
	public void start() {
		try {
			subjects = new Properties();
			subjectNames = new Properties();
			new File(SAVE_PATH).mkdirs();
			File subjectProperties = new File(SUBJECT_PROPERTIES);
			File subjectNameProperties = new File(SUBJECT_NAMES_PROPERTIES);
			subjectProperties.createNewFile();
			subjectNameProperties.createNewFile();
			subjects.load(new FileInputStream(subjectProperties));
			subjectNames.load(new FileInputStream(subjectNameProperties));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Subject loadSubject(String name) {
		loadSubjects();
		for(int i = 0; i < loadedSubjects.size(); i++) {
			if(loadedSubjects.get(i).getName().equals(name)) {
				return loadedSubjects.get(i);
			}
		}
		return null;
//		String short_name = subjectNames.getProperty(name);
//		Subject subject = null;
//		if(short_name != null) {
//			subject = new Subject();
//			String color = subjects.getProperty(short_name + "_color", "0");
//			String teacher = subjects.getProperty(short_name + "_teacher", "");
//			subject.setName(name);
//			subject.setShortName(short_name);
//			subject.setTeacher(teacher);
//			try {
//				subject.setColor(new Color(Integer.parseInt(color)));
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return subject;
	}
	
	public void addSubject(Subject subject) {
		String shortName = subject.getShortName();
		subjectNames.put(subject.getName(), shortName);
		subjects.put(shortName + "_color", String.valueOf(subject.getColor().getRGB()));
		subjects.put(shortName + "_teacher", subject.getTeacher());
		loadedSubjects.add(subject);
		saveSubjects();
	}
	
	public void editSubject(Subject oldSubject, Subject newSubject) {
		removeSubject(oldSubject);
		addSubject(newSubject);
	}
	
	public void saveSubjects() {
		try {
			subjects.store(new FileOutputStream(new File(SUBJECT_PROPERTIES)), "");
			subjectNames.store(new FileOutputStream(new File(SUBJECT_NAMES_PROPERTIES)), "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Subject readAndCreateSubject(String name) {
		String shortName = subjectNames.getProperty(name);
		Subject newSubject = null;
		if(shortName != null) {
			newSubject = new Subject();
			int rgb = 0;
			try {
				rgb = Integer.parseInt(subjects.getProperty((shortName) + "_color", "0"));
			} catch(Exception e) {
				e.printStackTrace();
			}
			newSubject.setName(name);
			newSubject.setShortName(shortName);
			newSubject.setTeacher(subjects.getProperty(shortName + "_teacher", ""));
			newSubject.setColor(new Color(rgb));
		}
		return newSubject;
	}

	public ArrayList<Subject> loadSubjects() {
		if(loadedSubjects == null) {
			loadedSubjects = new ArrayList<Subject>();
			for(Object name : subjectNames.keySet()) {
				Subject newSubject = readAndCreateSubject((String) name);
				if(newSubject != null) {
					loadedSubjects.add(newSubject);
					System.out.println("NEW: " + newSubject);
				}
			}
		}
		return loadedSubjects;
	}

	public void removeSubject(Subject subject) {
		subjectNames.remove(subject.getName());
		loadedSubjects.remove(subject);
		subjects.remove(subject.getShortName() + "_teacher");
		subjects.remove(subject.getShortName() + "_color");
	}
	
	public Subject getSubjectByName(String name, boolean showError) {
		if(loadedSubjects == null) {
			loadSubjects();
		}
		for(Subject subject : loadedSubjects) {
			if(subject.getName().equals(name)) {
				return subject;
			}
		}
		if(showError) {
			ErrorMessages.createErrorMessage("Subject not found!", "We couldnt find the subject " + name + "!");
		}
		return null;
	}

	
	public boolean checkUniqueShortName(String shortName) {
		return !subjectNames.containsValue(shortName);
	}
}
