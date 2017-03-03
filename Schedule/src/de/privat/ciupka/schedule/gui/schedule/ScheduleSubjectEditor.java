package de.privat.ciupka.schedule.gui.schedule;

import java.awt.Font;
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
import de.privat.ciupka.schedule.logic.schedule.Subject;

public class ScheduleSubjectEditor extends JFrame {
	
	private boolean showed;
	private String lastEndHour;
	private String lastEndMinute;
	private String lastStartHour;
	private String lastStartMinute;
	private GUIController guiCon;

	private JComboBox<String> subjectsCB;
	private HintTextField startHourT;
	private JPanel contentPane;
	private HintTextField startMinuteT;
	private HintTextField endHourT;
	private HintTextField endMinuteT;
	private JButton addB;
	private JButton cancelB;
	
	public ScheduleSubjectEditor() {
		guiCon = GUIController.getInstance();
	}
	
	public ScheduleSubjectEditor display() {
		if(!showed) {
			showed = true;
			createItems();
		}
		resetComponents();
		return this;
	}
	
	private void resetComponents() {
		startHourT.setText("");
		startMinuteT.setText("");
		endHourT.setText("");
		endMinuteT.setText("");
		focusHints();
		subjectsCB.removeAllItems();
		//TODO
		for(Subject subject : Controller.getInstance().loadAllSubjects()) {
			subjectsCB.addItem(subject.getName());
		}
	}

	public void createItems() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		subjectsCB = new JComboBox<String>();
		subjectsCB.setBounds(10, 11, 212, 20);
		contentPane.add(subjectsCB);
		
		startHourT = new HintTextField("HH");
		startHourT.setBounds(10, 40, 30, 20);
		contentPane.add(startHourT);
		
		JLabel label = new JLabel(":");
		label.setBounds(42, 42, 4, 14);
		contentPane.add(label);
		
		startMinuteT = new HintTextField("MM");
		startMinuteT.setBounds(46, 40, 30, 20);
		contentPane.add(startMinuteT);
		
		JButton swapT = new JButton("<-->");
		swapT.setFont(new Font("Tahoma", Font.PLAIN, 11));
		swapT.setBounds(86, 40, 60, 20);
		contentPane.add(swapT);
		
		endHourT = new HintTextField("HH");
		endHourT.setBounds(156, 42, 30, 20);
		contentPane.add(endHourT);
		
		JLabel label_1 = new JLabel(":");
		label_1.setBounds(188, 44, 4, 14);
		contentPane.add(label_1);
		
		endMinuteT = new HintTextField("MM");
		endMinuteT.setBounds(192, 42, 30, 20);
		contentPane.add(endMinuteT);
		
		addB = new JButton("Add");
		addB.setBounds(133, 71, 89, 23);
		contentPane.add(addB);
		
		cancelB = new JButton("Cancel");
		cancelB.setBounds(10, 71, 89, 23);
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
		return newLastInput;
	}
	
	private void focusHints() {
		endHourT.focusLost(null);
		endMinuteT.focusLost(null);
		startHourT.focusLost(null);
		startMinuteT.focusLost(null);
	}


}
