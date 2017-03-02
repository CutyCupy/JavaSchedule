package de.privat.ciupka.schedule.gui.mainmenu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.privat.ciupka.schedule.controller.GUIController;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainMenu extends JPanel {

	private boolean showed;
	
	private GUIController guiCon;

	public MainMenu() {
		setBounds(0, 0, 350, 275);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		guiCon = GUIController.getInstance();
	}
	
	public MainMenu display() {
		if(!showed) {
			createItems();
			showed = true;
		}
		return this;
	}
	
	public void createItems() {
		
		JButton openB = new JButton("Open Schedule");
		openB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO: Add openB ActionListener
			}
		});
		openB.setSize(150, 25);
		openB.setLocation(WindowHandler.placeItemToHMiddle(openB.getWidth(), getWidth()), 65);
		
		JButton createB = new JButton("Create Schedule");
		createB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiCon.displayPanel(guiCon.getCreateSchedule().display());
			}
		});
		createB.setSize(150, 25);
		createB.setLocation(WindowHandler.placeItemToHMiddle(createB.getWidth(), getWidth()), 100);

		JButton subjectsB = new JButton("Manage Subjects");
		subjectsB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Add subjectsB ActionListener
			}
		});
		subjectsB.setSize(150, 25);
		subjectsB.setLocation(WindowHandler.placeItemToHMiddle(subjectsB.getWidth(), getWidth()), 135);
		
		JButton optionB = new JButton("Options");
		optionB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Add optionB ActionListener
			}
		});
		optionB.setSize(150, 25);
		optionB.setLocation(WindowHandler.placeItemToHMiddle(optionB.getWidth(), getWidth()), 170);
		
		add(openB);
		add(createB);
		add(optionB);
		add(subjectsB);
	}
	
}
