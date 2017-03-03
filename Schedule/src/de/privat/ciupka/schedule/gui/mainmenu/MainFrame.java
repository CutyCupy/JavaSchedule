package de.privat.ciupka.schedule.gui.mainmenu;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import de.privat.ciupka.schedule.controller.GUIController;

public class MainFrame extends JFrame {
	
	public MainFrame() {
		setVisible(true);
		addWindowListener(new MyWindowListener());
	}
	
	public void updateContentPane(Container contentPane) {
		setContentPane(contentPane);
		setSize(contentPane.getWidth() + 16,contentPane.getHeight() + 41);
		setLocation(WindowHandler.placeFrameToMiddle(getHeight(), getWidth()));
		repaint();
	}
	
	public void updateMenu(JMenuBar newMenu) {
		this.setJMenuBar(newMenu);
//		if(newMenu != null) {
//			this.setSize((int) this.getSize().getWidth(), (int) this.getSize().getHeight() + 10);
//		}
	}
	
	public static void main(String[] args) {
		GUIController.getInstance();
//		MainMenu test = new MainMenu();
//		Schedule blub = new Schedule(900, 275);
//		Subject subject = new Subject();
//		subject.setColor(Color.CYAN);
//		subject.setName("Mathe");
//		subject.setTeacher("Samuel Hetterich");
//		subject.setShortName("MAT");
//		subject.setRoom("H IV");	
//		subject.saveSubject();
//		Time start = new Time();
//		Time end = new Time();
//		start.setTime(10, 00);
//		end.setTime(12, 00);
//		blub.addTime(10, 0);
//		blub.addTime(11, 0);
//		blub.addTime(12, 0);
//		blub.addTime(13, 0);
//		blub.addTime(14, 0);
//		blub.setDays(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
//		blub.addSubjectLabelBounds(subject, start, end, "Monday");
//		test.add(blub.generateSchedule());
//		blub.setEditable(true);
//		CreateSchedule cs = new CreateSchedule();
//		new MainFrame().updateContentPane(cs.display());
	}
}
