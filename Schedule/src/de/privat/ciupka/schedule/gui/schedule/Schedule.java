package de.privat.ciupka.schedule.gui.schedule;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.gui.popups.Messages;
import de.privat.ciupka.schedule.logic.schedule.Subject;
import de.privat.ciupka.schedule.logic.schedule.Time;

public class Schedule extends JComponent {
	
	public static final String[] ALL_DAYS = {"Monday", "Tuesday", "Wendesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	private ArrayList<Time> times;
	private ArrayList<String> days;
	private ArrayList<SubjectLabel> subjects;
	
	private int labelHeight;
	private int labelWidth;
	private boolean editable;
	private boolean remove;
	
	
	public Schedule(int width, int height) {
		this.times = new ArrayList<Time>();
		this.days = new ArrayList<String>();
		this.subjects = new ArrayList<SubjectLabel>();
		this.setSize(width, height);
	}
	
	public Schedule() {
		this.times = new ArrayList<Time>();
		this.days = new ArrayList<String>();
		this.subjects = new ArrayList<SubjectLabel>();
	}
	
	public void setTimes(Time[] newTimes) {
		this.times.clear();
		for(Time newTime : newTimes) {
			this.times.add(newTime);
		}
		refreshSize();
	}
	
	public void setDays(String[] newDays) {
		this.days.clear();
		for(String newDay : newDays) {
			this.days.add(newDay);
		}
		refreshSize();
	}
	
	public void addTime(Time time) {
		this.times.add(time);
		refreshSize();
	}
	
	public void addTime(int hour, int minute) {
		Time time = new Time();
		if(time.setTime(hour, minute)) {
			this.times.add(time);
		}
		refreshSize();
	}
	
	public void addDay(String day) {
		this.days.add(day);
		refreshSize();
	}
	
	private void refreshSize() {
		labelWidth = this.getWidth() / (this.days.size() + 1);
		labelHeight = this.getHeight() / (this.times.size() + 1);
	}
	
	public Schedule generateSchedule(int width, int height) {
		setSize(width, height);
		return generateSchedule();
	}
	
	public Schedule generateSchedule() {
		refreshSize();
		for(Component component : getComponents()) {
			remove(component);
		}
		for(SubjectLabel subject : this.subjects) {
			subject.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			add(subject);
		}
		for(int i = -1; i < days.size(); i++) {
			JLabel currentLabel = new JLabel();
			currentLabel.setHorizontalAlignment(SwingConstants.CENTER);
			currentLabel.setVerticalAlignment(SwingConstants.CENTER);
			if(i != -1) {
				currentLabel.setText(days.get(i));
			}
			currentLabel.setBounds(labelWidth * (i + 1), 0, labelWidth, labelHeight);
			currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			add(currentLabel);
		}
		for(int i = 0; i < times.size(); i++) {
			JLabel currentLabel = new JLabel(times.get(i).toString());
			currentLabel.setBounds(0, labelHeight * (i + 1), labelWidth, labelHeight);
			currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			add(currentLabel);
		}
		for(int i = 1; i < days.size() + 1; i++) {
			for(int j = 1; j < times.size() + 1; j++) {
				JLabel currentLabel = new JLabel();
				currentLabel.setBounds(labelWidth * i,  labelHeight * j, labelWidth, labelHeight);
				currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				add(currentLabel);
			}
		}
		repaint();
		return this;
	}
	
	public boolean addSubjectLabelBounds(Subject subject, Time start, Time end, String day, String room) {
		refreshSize();
		int startTime = start.getTime();
		int endTime = end.getTime();
		System.out.println("Start: " + startTime + " - End: " + endTime);
		if(startTime >= endTime || startTime < times.get(0).getTime() || endTime > times.get(times.size() - 1).getTime()) {
			return false;
		}
		int startY = 0;
		int endY = 0;
		int startIndex = -1;
		for(int i = 0; i < times.size(); i++) {
			if(times.get(i).getTime() >= startTime) {
				startIndex = i;
				if(i != 0) {
					int delta = times.get(i).getTime() - times.get(i - 1).getTime();
					float percentage = (float) (((startTime - times.get(i - 1).getTime())*1.0) / delta);
					startY = (int) (i * labelHeight + percentage*labelHeight);
					break;
				} else if(times.get(i).getTime() != startTime) {
					return false;
				}
			}
		}
		for(int i = startIndex; i < times.size(); i++) {
			if(times.get(i).getTime() >= endTime) {
				int delta = times.get(i).getTime() - times.get(i-1).getTime();
				float percentage = (float) (((endTime - times.get(i-1).getTime()*1.0))/delta);
				endY = (int) (i * labelHeight + percentage*labelHeight);
				break;
			}
		}
		SubjectLabel newLabel = new SubjectLabel(subject, start, end, room, day);
		newLabel.setBounds(labelWidth*(days.indexOf(day)+1), startY, labelWidth, (endY - startY));
		for(SubjectLabel currentLabel : subjects) {
			boolean error = false;
			int currentStart = currentLabel.getStartTime();
			int currentEnd = currentLabel.getStartTime();
			if(!currentLabel.getDay().equals(day)) {
				continue;
			}
			if(currentStart <= start.getTime() && start.getTime() < currentEnd) {
				error = true;
			} else if(currentStart < end.getTime() && end.getTime() <= currentEnd) {
				error = true;
			} else if(start.getTime() <= currentStart && end.getTime() >= currentEnd) {
				error = true;
			}
			if(error) {
				if(!Messages.openYesNoDialog("Overlap!", "The subject " + subject.getName() + " would overlap with " + currentLabel.getSubject().getName() + " at " + day + "! Do you want to move on?")) {
					System.out.println("Dont move on");
					return false;
				}
			}
		}
		newLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(editable) {
					SubjectLabel source = (SubjectLabel) e.getSource();
					source.setBackground(source.getBackground().brighter());
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(editable) {
					SubjectLabel source = (SubjectLabel) e.getSource();
					source.setBackground(source.getBackground().darker());
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(editable) {
					if(remove) {
						subjects.remove((SubjectLabel) e.getSource());
					} else {
						GUIController.getInstance().displayAddSubjectToSchedule(findSubject((SubjectLabel) e.getSource()));
					}
				}
			}
		});
		this.subjects.add(newLabel);
		return true;
	}
	
	public boolean isEditable() {
		return editable;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public void reset() {
		this.days = new ArrayList<String>();
		this.subjects = new ArrayList<SubjectLabel>();
		this.times = new ArrayList<Time>();
	}
	
	public boolean isRemove() {
		return remove;
	}
	
	public void swapRemove() {
		remove = !remove;
		if(remove) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public ArrayList<String> getDays() {
		return this.days;
	}

	public ArrayList<Time> getTimes() {
		return this.times;
	}
	
	public ArrayList<SubjectLabel> getSubjects() {
		return this.subjects;
	}
	
	private SubjectLabel findSubject(SubjectLabel source) {
		for(int i = 0; i < subjects.size(); i++) {
			if(subjects.get(i).getBounds().equals(source.getBounds())) {
				subjects.get(i).getSubject().getName();
				return subjects.get(i);
			}
		}
		return null;
	}
	
	public void editSubject(SubjectLabel oldSL, SubjectLabel newSL) {
		this.subjects.remove(oldSL);
		if(!addSubjectLabelBounds(newSL.getSubject(), newSL.getStartTimeAsTime(), newSL.getEndTimeAsTime(), newSL.getDay(), newSL.getRoom())) {
			addSubjectLabelBounds(oldSL.getSubject(), oldSL.getStartTimeAsTime(), oldSL.getEndTimeAsTime(), oldSL.getDay(), oldSL.getRoom());
		}
		this.generateSchedule();
		this.repaint();
	}
}
