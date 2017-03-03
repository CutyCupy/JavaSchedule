package de.privat.ciupka.schedule.logic.schedule;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class PropertieHandler {
	
	public static final String HOME = System.getProperty("user.home");
	public static final String SEPERATOR = System.getProperty("file.separator");
	public static final String SAVE_PATH = HOME + SEPERATOR + "ciupkaschedule" + SEPERATOR;
	public static final String SUBJECT_PROPERTIES = SAVE_PATH + "subjects.cfg";
	public static final String SUBJECT_NAMES_PROPERTIES = SAVE_PATH + "subjectnames.cfg";
	
	private Properties subjects;
	private Properties subjectNames;
	
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
		String short_name = subjectNames.getProperty(name);
		Subject subject = null;
		if(short_name != null) {
			subject = new Subject();
			String color = subjects.getProperty(short_name + "_color", "0");
			String teacher = subjects.getProperty(short_name + "_teacher", "");
			subject.setName(name);
			subject.setShortName(short_name);
			subject.setTeacher(teacher);
			try {
				subject.setColor(new Color(Integer.parseInt(color)));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return subject;
	}
	
	public void addSubject(Subject subject) {
		String shortName = subject.getShortName();
		subjectNames.put(subject.getName(), shortName);
		subjects.put(shortName + "_color", String.valueOf(subject.getColor().getRGB()));
		subjects.put(shortName + "_teacher", subject.getTeacher());
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
		System.out.println("SHORTNAME: " + shortName);
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
			System.out.println(newSubject.getColor().getRGB() + " - " + rgb);
			System.out.println(Color.BLACK.getRGB());
		}
		return newSubject;
	}

	public ArrayList<Subject> loadSubjects() {
		ArrayList<Subject> result = new ArrayList<Subject>();
		for(Object name : subjectNames.keySet()) {
			Subject newSubject = readAndCreateSubject((String) name);
			if(newSubject != null) {
				result.add(newSubject);
			}
		}
		return result;
	}

	public void removeSubject(Subject subject) {
		subjectNames.remove(subject.getName());
		subjects.remove(subject.getShortName() + "_teacher");
		subjects.remove(subject.getShortName() + "_color");
	}
	
	public Subject getSubjectByName(String name) {
		ArrayList<Subject> subjects = loadSubjects();
		for(Subject subject : subjects) {
			if(subject.getName().equals(name)) {
				return subject;
			}
		}
		return null;
	}
	
	public 
}
