package de.privat.ciupka.schedule.gui.schedule;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import de.privat.ciupka.schedule.logic.schedule.Subject;
import de.privat.ciupka.schedule.logic.schedule.Time;

public class SubjectLabel extends JLabel {

	private Subject subject;
	private Time start;
	private Time end;
	
	public SubjectLabel(Subject subject, Time start, Time end) {
		super();
		this.setText(convertToMultiline(subject.getName() + "\n" + subject.getShortName() + "\n" + subject.getTeacher() + "\n" + subject.getRoom(), true));
		this.setBackground(subject.getColor());
		this.start = start;
		this.end = end;
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		setOpaque(true);
	}
	
	
	public static String convertToMultiline(String text, boolean center) {
		if(center) {
			return "<html><div style='text-align: center;'>" + text.replace("\n", "<br>");
		} else {
			return "<html>" + text.replaceAll("\n", "<br>");			
		}
	}
	
	
	
}
