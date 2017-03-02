package de.privat.ciupka.schedule.gui.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.gui.mainmenu.MainMenu;

public class SchedulePanel extends JPanel {

	private boolean showed;

	private GUIController guiCon;

	public SchedulePanel() {
		setBounds(0, 0, 350, 275);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		guiCon = GUIController.getInstance();
	}
	
	public SchedulePanel display() {
		if(!showed) {
			createItems();
			showed = true;
		}
		return this;
	}

	private void createItems() {
		JMenuBar menubar = new JMenuBar();
		
		//JMenus
		JMenu file = new JMenu("Schedule");
		JMenu subjects = new JMenu("Subjects");
		JMenu export = new JMenu("Export");
		
		//JMenuItems - file
		JMenuItem open = new JMenuItem("Open Schedule");
		JMenuItem save = new JMenuItem("Save Schedule");
		JMenuItem back = new JMenuItem("To Mainmenu");
		
		//JMenuItems - export (submenu of file)
		JMenuItem exportPNG = new JMenuItem("Export to PNG");
		JMenuItem exportJPG = new JMenuItem("Export to JPG");
		JMenuItem exportPDF = new JMenuItem("Export to PDf");
		JMenuItem exportXLS = new JMenuItem("Export to XLS");
		
		//TODO: Edit action listener if needed
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.openNewSchedule();
			}
		});
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.saveSchedule();
			}
		});
		
		
		//JMenuItems - subjects
		JMenuItem add = new JMenuItem("Add Subject");
		JMenuItem manage = new JMenuItem("Manage Subjects");
		
		
		menubar.add(file);
		menubar.add(subjects);
		
		
		this.guiCon.getMainFrame().updateMenu(menubar);
	}
	
	private void addKeyMaps() {
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "up");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "up");
	}

	public void setSchedule(Schedule schedule) {
		setSize(schedule.getSize());
		add(schedule);
	}
}
