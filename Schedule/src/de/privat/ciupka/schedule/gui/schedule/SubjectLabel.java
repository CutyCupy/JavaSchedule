package de.privat.ciupka.schedule.gui.schedule;

import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.logic.schedule.Subject;
import de.privat.ciupka.schedule.logic.schedule.Time;

public class SubjectLabel extends JLabel {

	private Subject subject;
	private Time start;
	private Time end;
	private String room;
	
	public SubjectLabel(Subject subject, Time start, Time end, String room) {
		super();
		this.subject = subject;
		this.setText(convertToMultiline(subject.getName() + "\n" + subject.getShortName() + "\n" + subject.getTeacher() + "\n" + room, true));
		this.setBackground(subject.getColor());
		this.setForeground(GUIController.getForegroundColor(subject.getColor().getRed(), subject.getColor().getGreen(), subject.getColor().getBlue()));
		this.start = start;
		this.end = end;
		this.room = room;
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		setOpaque(true);
	}
	
	public SubjectLabel(Subject subject) {
		super();
		this.setText(convertToMultiline(subject.getName() + "\n" + subject.getShortName() + "\n" + subject.getTeacher(), true));
		this.setBackground(subject.getColor());
		this.setForeground(GUIController.getForegroundColor(subject.getColor().getRed(), subject.getColor().getGreen(), subject.getColor().getBlue()));
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
	
	public HashMap<String, String> getData() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("room", room);
		data.put("start", String.valueOf(start.getTime()));
		data.put("end", String.valueOf(end.getTime()));
		data.put("subjectname", subject.getName());
		return data;
	}

	public Subject getSubject() {
		return subject;
	}
	
	public int getStartTime() {
		if(start == null) {
			return 0;
		}
		return start.getTime();
	}
	
	public int getEndTime() {
		if(end == null) {
			return 0;
		}
		return end.getTime();
	}
	
	public String getRoom() {
		return this.room;
	}
}
