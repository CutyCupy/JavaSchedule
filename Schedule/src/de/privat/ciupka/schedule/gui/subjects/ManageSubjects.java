package de.privat.ciupka.schedule.gui.subjects;

import java.util.ArrayList;

import javax.swing.JPanel;

import de.privat.ciupka.schedule.logic.schedule.Subject;

public class ManageSubjects extends JPanel {
	
	private boolean showed;
	
	public ManageSubjects display() {
		if(!showed) {
			createItems();
			showed = true;
		}
		return this;
	}
	
	private void createItems() {
		//TODO: Create all necessary items
	}
	
	public void displaySubjects(ArrayList<Subject> toDisplay) {
		//TODO: Crazy display stuff
	}
}
