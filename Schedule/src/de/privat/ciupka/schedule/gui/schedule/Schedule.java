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
	
	private static final long serialVersionUID = -6249327829663267917L;

	public static final String[] ALL_DAYS = {"Monday", "Tuesday", "Wendesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	private ArrayList<Time> times;
	private ArrayList<String> days;
	private ArrayList<SubjectLabel> subjects;
	
	private int labelHeight;
	private int labelWidth;
	private boolean editable;
	private boolean remove;
	private GUIController guiCon;
	
	
	public Schedule(int width, int height) {
		this.times = new ArrayList<Time>();
		this.days = new ArrayList<String>();
		this.subjects = new ArrayList<SubjectLabel>();
		this.setSize(width, height);
		guiCon = GUIController.getInstance();
	}
	
	public Schedule() {
		this.times = new ArrayList<Time>();
		this.days = new ArrayList<String>();
		this.subjects = new ArrayList<SubjectLabel>();
		guiCon = GUIController.getInstance();
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
		for(int i = -1; i < days.size(); i++) {
			JLabel currentLabel = new JLabel();
			currentLabel.setHorizontalAlignment(SwingConstants.CENTER);
			currentLabel.setVerticalAlignment(SwingConstants.CENTER);
			if(i != -1) {
				currentLabel.setText(days.get(i));
				currentLabel.setName(days.get(i));
				currentLabel.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {}
					
					@Override
					public void mouseExited(MouseEvent e) {}
					
					@Override
					public void mouseEntered(MouseEvent e) {}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						guiCon.displayAddSubjectToSchedule();
						setDay(e.getComponent().getName());
					}
				});
			}
			currentLabel.setBounds(labelWidth * (i + 1), 0, labelWidth, labelHeight);
			currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			add(currentLabel);
		}
		for(int i = 0; i < times.size(); i++) {
			JLabel currentLabel = new JLabel(times.get(i).toString());
			currentLabel.setName(String.valueOf(i));
			currentLabel.setBounds(0, labelHeight * (i + 1), labelWidth, labelHeight);
			currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			currentLabel.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int index = Integer.parseInt(e.getComponent().getName());
					guiCon.displayAddSubjectToSchedule();
					setTime(index);
				}
			});
			add(currentLabel);
		}
		for(int i = 1; i < days.size() + 1; i++) {
			for(int j = 1; j < times.size() + 1; j++) {
				JLabel currentLabel = new JLabel();
				currentLabel.setBounds(labelWidth * i,  labelHeight * j, labelWidth, labelHeight);
				currentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				currentLabel.setName(days.get(i-1) + "," + String.valueOf(j));
				currentLabel.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {}
					
					@Override
					public void mouseExited(MouseEvent e) {}
					
					@Override
					public void mouseEntered(MouseEvent e) {}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						guiCon.displayAddSubjectToSchedule();
						String[] name = e.getComponent().getName().split(",");
						setDay(name[0]);
						setTime(Integer.valueOf(name[1])-1);
					}
				});
				add(currentLabel);
			}
		}
		for(SubjectLabel subject : this.subjects) {
			subject.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			add(subject);
			setComponentZOrder(subject, 1);
		}
		repaint();
		return this;
	}
	
	public boolean addSubjectLabelBounds(Subject subject, Time start, Time end, String day, String room) {
		refreshSize();
		int startTime = start.getTime();
		int endTime = end.getTime();
		if(startTime >= endTime || startTime < times.get(0).getTime() || endTime > times.get(times.size() - 1).getTime()) {
			return false;
		}
		for(int i = 0; i < subjects.size(); i++) {
			if(!day.equals(subjects.get(i).getDay())) {
				continue;
			}
			boolean error = false;
			int curStartTime = subjects.get(i).getStartTime();
			int curEndTime = subjects.get(i).getEndTime();
			if(curStartTime < startTime && startTime < curEndTime) {
				System.out.println("curStartTime < startTime && startTime < curEndTime");
				error = true;
			} else if(curStartTime < endTime && endTime < curEndTime) {
				System.out.println("curStartTime < endTime && endTime < curEndTime");
				error = true;
			} else if(curStartTime == startTime || curEndTime == endTime) {
				System.out.println("curStartTime == startTime || curEndTime == endTime");
				error = true;
			}
			if(error) {
				if(!Messages.openYesNoDialog("Overlap!", "The subject " + subject.getName() + " would overlap with " + subjects.get(i).getSubject().getName() + " at " + day + "! Do you want to move on?")) {
					return false;
				}
			}
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
						subjects.remove(findSubject((SubjectLabel) e.getSource()));
						GUIController.getInstance().setSchedule();
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
	
	public void currentReset() {
		this.subjects = new ArrayList<SubjectLabel>();
		generateSchedule();
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
			System.out.println(subjects.get(i).getBounds() + " - " + source.getBounds());
			if(subjects.get(i).getBounds().equals(source.getBounds())) {
				System.out.println("YAY");
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
	
	private void setTime(int index) {
		if(index != times.size() - 1) {
			guiCon.getAddSubjectToSchedule().setStartTime(times.get(index).getTime());
			guiCon.getAddSubjectToSchedule().setEndTime(times.get(index+1).getTime());
		} else {
			if(index != 0) {
				guiCon.getAddSubjectToSchedule().setStartTime(times.get(index).getTime());
				guiCon.getAddSubjectToSchedule().setEndTime(times.get(index).getTime() + times.get(1).getTime() - times.get(0).getTime());
			} else {
				guiCon.getAddSubjectToSchedule().setStartTime(times.get(index).getTime());
			}
		}
	}
	
	private void setDay(String day) {
		guiCon.getAddSubjectToSchedule().setSelectedDay(day);
	}
}
