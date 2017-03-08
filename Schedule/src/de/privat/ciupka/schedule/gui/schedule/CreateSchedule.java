package de.privat.ciupka.schedule.gui.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.gui.mainmenu.WindowHandler;

public class CreateSchedule extends JPanel {
	
	private static final long serialVersionUID = -2470705690610007225L;

	private boolean showed;
	
	private HintTextField endHourT;
	private HintTextField endMinuteT;
	private HintTextField startHourT;
	private HintTextField startMinuteT;
	private HintTextField widthT;
	private HintTextField heightT;
	private HintTextField intervalHourT;
	private HintTextField intervalMinuteT;
	private JComboBox<String> startDayCB;
	private JComboBox<String> endDayCB;	
	
	private GUIController guiCon;
	
	public CreateSchedule() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		guiCon = GUIController.getInstance();
	}
	
	public CreateSchedule display() {
		setBounds(0, 0, 390, 185);
		if(!showed) {
			createItems();
			showed = true;
		} else {
			endHourT.setText("");
			endMinuteT.setText("");
			startHourT.setText("");
			startMinuteT.setText("");
			widthT.setText("");
			heightT.setText("");
			intervalHourT.setText("");
			intervalMinuteT.setText("");
			focusHints();
		}
		return this;
	}
	
	public void createItems() {
		JButton cancelB = new JButton("Cancel");
		cancelB.setBounds(10, 125, 89, 23);
		cancelB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.displayPanel(guiCon.getMainMenu().display());
			}
		});
		
		JButton startB = new JButton("Start");
		startB.setBounds(285, 125, 89, 23);
		startB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.createSchedule(startHourT.getText(), startMinuteT.getText(), endHourT.getText(), 
						endMinuteT.getText(), intervalHourT.getText(), intervalMinuteT.getText(), 
						String.valueOf(startDayCB.getSelectedItem()), String.valueOf(endDayCB.getSelectedItem()), 
						heightT.getText(), widthT.getText(), true);
				//TODO: Create a new schedule and display it.
			}
		});
		
		endHourT = new HintTextField("HH");
		endHourT.setBounds(228, 46, 30, 20);
		endHourT.setMinMax(0, 23);
		
		endMinuteT = new HintTextField("MM");
		endMinuteT.setBounds(265, 46, 30, 20);
		endMinuteT.setMinMax(0, 59);
		
		JButton swapTimesB = new JButton("<-->");
		swapTimesB.setBounds(158, 46, 60, 20);
		swapTimesB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swapText(startHourT, endHourT);
				swapText(startMinuteT, endMinuteT);
				focusHints();
			}
		});
		
		JLabel label = new JLabel(":");
		label.setBounds(260, 49, 4, 14);
		
		startHourT = new HintTextField("HH");
		startHourT.setBounds(81, 46, 30, 20);
		startHourT.setMinMax(0, 23);
		
		JLabel label_1 = new JLabel(":");
		label_1.setBounds(113, 49, 4, 14);
		
		startMinuteT = new HintTextField("MM");
		startMinuteT.setBounds(118, 46, 30, 20);
		startMinuteT.setMinMax(0, 59);
		
		JButton swapDaysB = new JButton("<-->");
		swapDaysB.setBounds(158, 77, 60, 20);
		swapDaysB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swapSelected(startDayCB, endDayCB);
			}
		});
		
		startDayCB = new JComboBox<String>();
		startDayCB.setBounds(48, 77, 100, 20);
		
		endDayCB = new JComboBox<String>();
		endDayCB.setBounds(228, 77, 100, 20);
		
		for(String day : Schedule.ALL_DAYS) {
			startDayCB.addItem(day);
			endDayCB.addItem(day);
		}
		
		startDayCB.setSelectedItem("Monday");
		endDayCB.setSelectedItem("Friday");
		
		widthT = new HintTextField("Width");
		widthT.setBounds(118, 15, 60, 20);
		widthT.setMinMax(0, (int) WindowHandler.SCREEN_RESOLUTION.getWidth());
		
		heightT = new HintTextField("Height");
		heightT.setBounds(198, 15, 60, 20);
		heightT.setMinMax(0, (int) WindowHandler.SCREEN_RESOLUTION.getHeight());
		
		JLabel lblX = new JLabel("x");
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setBounds(176, 18, 25, 14);
		
		
		JLabel label_2 = new JLabel(":");
		label_2.setBounds(185, 125, 6, 20);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		intervalHourT = new HintTextField("HH");
		intervalHourT.setBounds(155, 125, 30, 20);
		intervalHourT.setMinMax(0, 23);
		
		intervalMinuteT = new HintTextField("MM");
		intervalMinuteT.setBounds(194, 125, 30, 20);
		intervalMinuteT.setMinMax(0, 59);
		
		
		add(label_2);
		add(intervalHourT);
		add(intervalMinuteT);
		add(widthT);
		add(heightT);
		add(lblX);
		add(endDayCB);
		add(startDayCB);
		add(swapDaysB);
		add(startMinuteT);
		add(label_1);
		add(startHourT);
		add(label);
		add(swapTimesB);
		add(cancelB);
		add(startB);
		add(endHourT);
		add(endMinuteT);
	}
	
	public void swapText(JTextField one, JTextField two) {
		String temp = one.getText();
		one.setText(two.getText());
		two.setText(temp);
	}
	
	public void swapSelected(JComboBox<String> one, JComboBox<String> two) {
		String temp = (String) one.getSelectedItem();
		one.setSelectedItem(two.getSelectedItem());
		two.setSelectedItem(temp);
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
		widthT.focusLost(null);
		heightT.focusLost(null);
		intervalHourT.focusLost(null);
		intervalMinuteT.focusLost(null);
	}

}
