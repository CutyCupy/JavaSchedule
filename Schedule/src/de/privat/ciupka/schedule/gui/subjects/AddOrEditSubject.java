package de.privat.ciupka.schedule.gui.subjects;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.privat.ciupka.schedule.controller.Controller;
import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.logic.schedule.Subject;

public class AddOrEditSubject extends JPanel {
		
	public static final int ADD = 0;
	public static final int EDIT = 1;
	
	private boolean showed;
	private Subject subject;
	private ActionListener addAction;
	private ActionListener editAction;
	private GUIController guiCon;
	private JTextField nameT;
	private JTextField shortNameT;
	private JTextField teacherT;
	private JSlider redS;
	private JSlider greenS;
	private JSlider blueS;
	private JButton addOrEditB;
	private JButton cancelB;
	private JButton removeB;
	private JLabel colorL;
	
	
	public AddOrEditSubject() {
		guiCon = GUIController.getInstance();
	}
	
	public AddOrEditSubject display(int type, Subject subject) {
		if(!showed) {
			createItems();
			showed = true;
		}
		if(type == 0) {
			addOrEditB.setText("Add");
			addOrEditB.removeActionListener(editAction);
			addOrEditB.addActionListener(addAction);
		} else if (type == 1) {
			addOrEditB.setText("Edit");
			this.subject = subject;
			redS.setValue(subject.getColor().getRed());
			greenS.setValue(subject.getColor().getGreen());
			blueS.setValue(subject.getColor().getBlue());
			nameT.setText(subject.getName());
			shortNameT.setText(subject.getShortName());
			teacherT.setText(subject.getTeacher());
			addOrEditB.removeActionListener(addAction);
			addOrEditB.addActionListener(editAction);
		} else {
			System.err.println(1/0);
		}
		setBounds(0, 0, 350, 300);
		return this;
	}
	
	public void createItems() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel nameL = new JLabel("Name: ");
		nameL.setHorizontalAlignment(SwingConstants.RIGHT);
		nameL.setBounds(0, 40, 99, 14);
		add(nameL);
		
		JLabel shortNameL = new JLabel("Shortname: ");
		shortNameL.setHorizontalAlignment(SwingConstants.RIGHT);
		shortNameL.setBounds(0, 65, 99, 14);
		add(shortNameL);
		
		JLabel teacherL = new JLabel("Teacher: ");
		teacherL.setHorizontalAlignment(SwingConstants.RIGHT);
		teacherL.setBounds(0, 90, 99, 14);
		add(teacherL);
		
		nameT = new JTextField();
		nameT.setBounds(107, 37, 86, 20);
		add(nameT);
		
		shortNameT = new JTextField();
		shortNameT.setColumns(10);
		shortNameT.setBounds(107, 62, 86, 20);
		add(shortNameT);
		
		teacherT = new JTextField();
		teacherT.setColumns(10);
		teacherT.setBounds(107, 87, 86, 20);
		add(teacherT);
		
		ChangeListener sliderListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateColor(redS.getValue(), greenS.getValue(), blueS.getValue());
			}
		};
		
		redS = new JSlider();
		redS.setValue(0);
		redS.setMaximum(255);
		redS.setBounds(107, 125, 200, 26);
		redS.addChangeListener(sliderListener);
		add(redS);
		
		greenS = new JSlider();
		greenS.setValue(0);
		greenS.setMaximum(255);
		greenS.setBounds(107, 162, 200, 26);
		greenS.addChangeListener(sliderListener);
		add(greenS);
		
		blueS = new JSlider();
		blueS.setValue(0);
		blueS.setMaximum(255);
		blueS.setBounds(107, 197, 200, 26);
		blueS.addChangeListener(sliderListener);
		add(blueS);

		JLabel redL = new JLabel("Red:");
		redL.setHorizontalAlignment(SwingConstants.RIGHT);
		redL.setBounds(0, 125, 99, 26);
		add(redL);
		
		JLabel greenL = new JLabel("Green:");
		greenL.setHorizontalAlignment(SwingConstants.RIGHT);
		greenL.setBounds(0, 162, 99, 24);
		add(greenL);
		
		JLabel blueL = new JLabel("Blue:");
		blueL.setHorizontalAlignment(SwingConstants.RIGHT);
		blueL.setBounds(0, 197, 99, 26);
		add(blueL);
		
		colorL = new JLabel("");
		colorL.setBounds(248, 40, 64, 64);
		updateColor(0, 0, 0);
		colorL.setOpaque(true);
		add(colorL);
		
		addOrEditB = new JButton("Add");
		addOrEditB.setBounds(235, 227, 89, 23);
		add(addOrEditB);
		
		cancelB = new JButton("Cancel");
		cancelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiCon.displayPanel(guiCon.getManageSubjects().displaySubjects(Controller.getInstance().loadAllSubjects()));
			}
		});
		cancelB.setBounds(10, 227, 89, 23);
		add(cancelB);
		
		addAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.addSubject(nameT.getText(), shortNameT.getText(), teacherT.getText(), colorL.getBackground());
				guiCon.displayPanel(guiCon.getManageSubjects().displaySubjects(Controller.getInstance().loadAllSubjects()));
			}
		};
		editAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.editSubject(subject, nameT.getText(), shortNameT.getText(), teacherT.getText(), colorL.getBackground());
				guiCon.displayPanel(guiCon.getManageSubjects().displaySubjects(Controller.getInstance().loadAllSubjects()));
			}
		};
	}
	
	public void updateColor(int red, int green, int blue) {
		this.colorL.setBackground(new Color(red, green, blue));
	}
	
}
