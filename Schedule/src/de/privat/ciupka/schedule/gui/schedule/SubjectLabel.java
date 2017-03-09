package de.privat.ciupka.schedule.gui.schedule;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.logic.schedule.Subject;
import de.privat.ciupka.schedule.logic.schedule.Time;

public class SubjectLabel extends JLabel {

	private static final long serialVersionUID = -7541174598188523730L;
	
	private Subject subject;
	private Time start;
	private Time end;
	private String room;
	private String day;
	
	public SubjectLabel(Subject subject, Time start, Time end, String room, String day) {
		super();
		this.subject = subject;
		this.setText(convertToMultiline(subject.getName() + "\n" + subject.getShortName() + "\n" + subject.getTeacher() + "\n" + room, true));
		this.setBackground(subject.getColor());
		this.setForeground(GUIController.getForegroundColor(subject.getColor().getRed(), subject.getColor().getGreen(), subject.getColor().getBlue()));
		this.start = start;
		this.end = end;
		this.room = room;
		this.day = day;
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
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		int rows = calculateRows();
		while(getFontMetrics(getFont()).getHeight() * rows >= height) {
			Font f = getFont().deriveFont((float) (getFont().getSize()-1.0));
			if(f.getSize() <= 9) {
				setText(convertToMultiline(subject.getName() + "\n" + (room == null ? subject.getTeacher() : room), true));
				rows = 1 + calculateRows(subject.getName());
				rows = 1 + (room == null ? calculateRows(subject.getTeacher()) : calculateRows(room));
			}
			setFont(f);
			
		}
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

	public String getDay() {
		return this.day;
	}

	public Time getStartTimeAsTime() {
		return this.start;
	}
	
	public Time getEndTimeAsTime() {
		return this.end;
	}
	
	private int calculateRows() {
		int rows = 0;
		FontMetrics fm = getFontMetrics(getFont());
		rows += 1 + fm.stringWidth(subject.getName()) / getWidth();
		rows += 1 + fm.stringWidth(subject.getShortName()) / getWidth();
		rows += 1 + fm.stringWidth(subject.getTeacher()) / getWidth();
		rows += day == null ? 0 : 1;
		return rows;
	}
	
	private int calculateRows(String string) {
		int rows = 0;
		FontMetrics fm = getFontMetrics(getFont());
		rows += 1 + fm.stringWidth(string) / getWidth();
		return rows;
	}
}
