package de.privat.ciupka.schedule.gui.mainmenu;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import de.privat.ciupka.schedule.controller.GUIController;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 5125147918665183778L;

	public MainFrame() {
		setVisible(true);
		addWindowListener(new MyWindowListener());
		setResizable(false);
	}
	
	public void updateContentPane(Container contentPane) {
		System.out.println("CP: " + contentPane.getSize());
		setContentPane(contentPane);
		System.out.println("CP: " + contentPane.getSize());
		setSize(contentPane.getWidth(), contentPane.getHeight());
		System.out.println("CP: " + contentPane.getSize());
		setLocation(WindowHandler.placeFrameToMiddle(getHeight(), getWidth()));
		repaint();
	}
	
	public void updateMenu(JMenuBar newMenu) {
		this.setJMenuBar(newMenu);
	}
	
	public static void main(String[] args) {
		GUIController.getInstance();
	}
}
