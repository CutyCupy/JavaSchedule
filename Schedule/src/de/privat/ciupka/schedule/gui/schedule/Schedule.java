package de.privat.ciupka.schedule.gui.schedule;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import de.privat.ciupka.schedule.logic.schedule.Subject;
import de.privat.ciupka.schedule.logic.schedule.Time;

public class Schedule extends JComponent {
	
	public static final String[] ALL_DAYS = {"Monday", "Tuesday", "Wendesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	private ArrayList<Time> times;
	private ArrayList<String> days;
	private ArrayList<JLabel> subjects;
	
	private int labelHeight;
	private int labelWidth;
	private boolean editable;
	
	
	public Schedule(int width, int height) {
		this.times = new ArrayList<Time>();
		this.days = new ArrayList<String>();
		this.subjects = new ArrayList<JLabel>();
		this.setSize(width, height);
	}
	
	public Schedule() {
		this.times = new ArrayList<Time>();
		this.days = new ArrayList<String>();
		this.subjects = new ArrayList<JLabel>();
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
		System.out.println(days.size());
		System.out.println(times.size());
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
		for(JLabel subject : this.subjects) {
			add(subject);
		}
		return this;
	}
	
	public boolean addSubjectLabelBounds(Subject subject, Time start, Time end, String day) {
		int startTime = start.getTime();
		int endTime = end.getTime();
		if(startTime >= endTime || startTime < times.get(0).getTime() || endTime > times.get(times.size() - 1).getTime()) {
			System.err.println("ERROR 1");
			return false;
		}
		int startY = 0;
		int endY = 0;
		int startIndex = -1;
		System.out.println(startTime);
		for(int i = 0; i < times.size(); i++) {
			System.out.println(times.get(i).getTime() + " - " + startTime);
			if(times.get(i).getTime() >= startTime) {
				System.out.println("Hi");
				startIndex = i;
				if(i != 0) {
					int delta = times.get(i).getTime() - times.get(i - 1).getTime();
					System.out.println("Delta" + delta);
					float percentage = (float) (((startTime - times.get(i - 1).getTime())*1.0) / delta);
					System.out.println("p=" + percentage);
					System.out.println("p*lH" + percentage*labelHeight);
					System.out.println("i*labelHeight" + i*labelHeight);
					startY = (int) (i * labelHeight + percentage*labelHeight);
					System.out.println(startY);
					break;
				} else if(times.get(i).getTime() != startTime) {
					System.err.println("ERROR 2");
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
		System.out.println(endY + " - " + startY);
		System.out.println("DEBUG Start");
		System.out.println(labelWidth*(days.indexOf(day)+1));
		System.out.println(startY);
		System.out.println(labelWidth);
		System.out.println(endY-startY);
		SubjectLabel newLabel = new SubjectLabel(subject, start, end);
		newLabel.setBounds(labelWidth*(days.indexOf(day)+1), startY, labelWidth, (endY - startY));
newLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
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
		this.subjects = new ArrayList<JLabel>();
		this.times = new ArrayList<Time>();
	}
}
