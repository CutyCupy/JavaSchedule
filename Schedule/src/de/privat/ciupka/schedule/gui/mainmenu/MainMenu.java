package de.privat.ciupka.schedule.gui.mainmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.privat.ciupka.schedule.controller.Controller;
import de.privat.ciupka.schedule.controller.GUIController;

/**
 * MainMenu extends JPanel.
 * This JPanel is the main menu of this application. It contains options like 'Open Schedule', 'New Schedule', etc.
 * At the start of the program this panel is the first panel that will be displayed.
 * @author Alexander Ciupka / CutyCupy
 *
 */
public class MainMenu extends JPanel {


	private static final long serialVersionUID = -5402416053729375460L;

	private boolean showed;

	private GUIController guiCon;

	public MainMenu() {
		setLayout(null);
		guiCon = GUIController.getInstance();
	}

	public MainMenu display() {
		setBounds(0, 0, 350, 275);
		if(!showed) {
			createItems();
			showed = true;
		}
		guiCon.getMainFrame().updateMenu(null);
		return this;
	}

	public void createItems() {
		JButton openB = new JButton("Open Schedule");
		openB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiCon.loadSchedule();
			}
		});
		openB.setSize(150, 25);
		openB.setLocation(WindowHandler.placeItemToHMiddle(openB.getWidth(), getWidth()), 65);

		JButton createB = new JButton("Create Schedule");
		createB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.displayPanel(guiCon.getCreateSchedule().display());
			}
		});
		createB.setSize(150, 25);
		createB.setLocation(WindowHandler.placeItemToHMiddle(createB.getWidth(), getWidth()), 100);

		JButton subjectsB = new JButton("Manage Subjects");
		subjectsB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.displayPanel(guiCon.getManageSubjects().displaySubjects(Controller.getInstance().loadAllSubjects()));
			}
		});
		subjectsB.setSize(150, 25);
		subjectsB.setLocation(WindowHandler.placeItemToHMiddle(subjectsB.getWidth(), getWidth()), 135);

		JButton optionB = new JButton("Options");
		optionB.addActionListener(new ActionListener() {
			@Override
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
