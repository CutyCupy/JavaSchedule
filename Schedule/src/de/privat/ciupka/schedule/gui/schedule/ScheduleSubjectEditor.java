package de.privat.ciupka.schedule.gui.schedule;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.privat.ciupka.schedule.controller.Controller;
import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.gui.mainmenu.WindowHandler;
import de.privat.ciupka.schedule.logic.schedule.Subject;

public class ScheduleSubjectEditor extends JFrame {
	
	private static final long serialVersionUID = 7836747918093308861L;
	
	private boolean showed;
	private GUIController guiCon;
	
	private SubjectLabel editedSubject;

	private JComboBox<String> subjectsCB;
	private JComboBox<String> daysCB;
	private HintTextField startHourT;
	private JPanel contentPane;
	private HintTextField startMinuteT;
	private HintTextField endHourT;
	private HintTextField endMinuteT;
	private JButton addB;
	private JButton cancelB;
	private HintTextField roomT;
	
	public ScheduleSubjectEditor() {
		guiCon = GUIController.getInstance();
		this.setVisible(false);
	}
	
	public ScheduleSubjectEditor display() {
		if(!showed) {
			showed = true;
			createItems();
		}
		resetComponents();
		editedSubject = null;
		this.setVisible(true);
		return this;
	}
	
	public ScheduleSubjectEditor display(SubjectLabel subject) {
		if(!showed) {
			showed = true;
			createItems();
		}
		resetComponents(subject);
		editedSubject = subject;
		this.setVisible(true);
		return this;
	}
	
	private void resetComponents() {
		startHourT.setText("");
		startMinuteT.setText("");
		endHourT.setText("");
		endMinuteT.setText("");
		roomT.setText("");
		focusHints();
		subjectsCB.removeAllItems();
		daysCB.removeAllItems();
		for(Subject subject : Controller.getInstance().loadAllSubjects()) {
			subjectsCB.addItem(subject.getName());
		}
		for(String day : guiCon.getScheduleDays()) {
			daysCB.addItem(day);
		}
		subjectsCB.setSelectedIndex(0);
		daysCB.setSelectedIndex(0);
		this.addB.setText("Add");
	}
	
	private void resetComponents(SubjectLabel subject) {
		startHourT.setText(String.valueOf((int) subject.getStartTime() / 60));
		System.out.println((int) subject.getStartTime() / 60);
		startMinuteT.setText(String.valueOf((int) subject.getStartTime() % 60));
		endHourT.setText(String.valueOf((int) subject.getEndTime() / 60));
		endMinuteT.setText(String.valueOf((int) subject.getEndTime() % 60));
		endHourT.getText();
		endMinuteT.getText();
		startHourT.getText();
		startMinuteT.getText();
		roomT.setText(subject.getRoom());
		focusHints();
		subjectsCB.removeAllItems();
		daysCB.removeAllItems();
		int counter = 0;
		for(Subject currentSubject : Controller.getInstance().loadAllSubjects()) {
			subjectsCB.addItem(currentSubject.getName());
			if(currentSubject.getName().equals(subject.getSubject().getName())) {
				subjectsCB.setSelectedIndex(counter);
			}
			counter++;
		}
		counter = 0;
		for(String day : guiCon.getScheduleDays()) {
			daysCB.addItem(day);
			if(subject.getDay().equals(day)) {
				daysCB.setSelectedIndex(counter);
			}
			counter++;
		}
		this.addB.setText("Edit");
	}

	public void createItems() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setSize(255, 160);
		this.setLocation(WindowHandler.placeFrameToMiddle(this));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		subjectsCB = new JComboBox<String>();
		subjectsCB.setBounds(10, 10, 212, 20);
		contentPane.add(subjectsCB);
		
		daysCB = new JComboBox<String>();
		daysCB.setBounds(10, 40, 150, 20);
		contentPane.add(daysCB);
		
		roomT = new HintTextField("Room");
		roomT.setBounds(162, 40, 50, 20);
		roomT.checkInputs(false);
		contentPane.add(roomT);
		
		startHourT = new HintTextField("HH");
		startHourT.setBounds(10, 70, 30, 20);
		startHourT.setMinMax(0, 23);
		contentPane.add(startHourT);
		
		JLabel label = new JLabel(":");
		label.setBounds(42, 72, 4, 14);
		contentPane.add(label);
		
		startMinuteT = new HintTextField("MM");
		startMinuteT.setBounds(46, 70, 30, 20);
		startMinuteT.setMinMax(0, 59);
		contentPane.add(startMinuteT);
		
		JButton swapT = new JButton("<-->");
		swapT.setFont(new Font("Tahoma", Font.PLAIN, 11));
		swapT.setBounds(86, 70, 60, 20);
		swapT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swapText(startHourT, endHourT);
				swapText(startMinuteT, endMinuteT);
			}
		});
		contentPane.add(swapT);
		
		endHourT = new HintTextField("HH");
		endHourT.setBounds(156, 70, 30, 20);
		endHourT.setMinMax(0, 23);
		contentPane.add(endHourT);
		
		JLabel label_1 = new JLabel(":");
		label_1.setBounds(188, 72, 4, 14);
		contentPane.add(label_1);
		
		endMinuteT = new HintTextField("MM");
		endMinuteT.setBounds(192, 70, 30, 20);
		endMinuteT.setMinMax(0, 59);
		contentPane.add(endMinuteT);
		
		addB = new JButton("Add");
		addB.setBounds(133, 101, 89, 23);
		addB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(addB.getText().equals("Add")) {
					guiCon.addSubjectToSchedule(startMinuteT.getText(), startHourT.getText(), endMinuteT.getText(), 
							endHourT.getText(), roomT.getText(), (String) subjectsCB.getSelectedItem(), (String) daysCB.getSelectedItem());
				} else {
					guiCon.getSchedule().editSubject(editedSubject, guiCon.createSubjectLabel(startMinuteT.getText(), startHourT.getText(), endMinuteT.getText(), 
							endHourT.getText(), roomT.getText(), (String) subjectsCB.getSelectedItem(), (String) daysCB.getSelectedItem()));
				}
				setVisible(false);
			}
		});
		contentPane.add(addB);
		
		cancelB = new JButton("Cancel");
		cancelB.setBounds(10, 101, 89, 23);
		cancelB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		contentPane.add(cancelB);
	}
	
	public String checkInput(int min, int max, String lastInput, KeyEvent e) {
		String newLastInput = lastInput;
		try {
			Integer.parseInt(String.valueOf(e.getKeyChar()));
			int value = Integer.parseInt(((JTextField) e.getSource()).getText());
			if(value < min || value > max) {
				newLastInput = value < min ? String.valueOf(min) : String.valueOf(max);
			} else {
				newLastInput = String.valueOf(value);
			}
		} catch(Exception exc) {
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				newLastInput = ((JTextField) e.getSource()).getText();						
			}
		}
		if((min == 0 && (max == 23 || max == 59)) && newLastInput.length() == 1) {
			newLastInput = "0" + newLastInput;
		}
		return newLastInput;
	}
	
	private void focusHints() {
		endHourT.focusLost(null);
		endMinuteT.focusLost(null);
		startHourT.focusLost(null);
		startMinuteT.focusLost(null);
		roomT.focusLost(null);
	}

	public void swapText(JTextField one, JTextField two) {
		String temp = one.getText();
		one.setText(two.getText());
		two.setText(temp);
	}
	
	public SubjectLabel getEditedSubject() {
		return this.editedSubject;
	}

}
