package de.privat.ciupka.schedule.gui.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import de.privat.ciupka.schedule.controller.GUIController;

public class SchedulePanel extends JPanel {

	private boolean showed;
	
	private Action addAction;
	private Action removeAction;
	private Action openAction;
	private Action saveAction;
	private Action returnBackAction;

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

	@SuppressWarnings("serial")
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
		JMenuItem exportPDF = new JMenuItem("Export to PDF");
		JMenuItem exportXLS = new JMenuItem("Export to XLS");
		
		exportPNG.setName("png");
		exportJPG.setName("jpg");
		exportPDF.setName("pdf");
		exportXLS.setName("xls");
		
		export.add(exportPNG);
		export.add(exportJPG);
		export.add(exportPDF);
		export.add(exportXLS);
		
		//TODO: Edit action listener if needed
		this.openAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.openNewSchedule();
			}
		};
		open.addActionListener(this.openAction);
		this.saveAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.saveSchedule();
			}
		};
		save.addActionListener(saveAction);
		this.returnBackAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.backToMainMenu();
			}
		};
		back.addActionListener(returnBackAction);
		
		for(int i = 0; i < export.getItemCount(); i++) {
			export.getItem(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					guiCon.export(((JComponent) e.getSource()).getName());
				}
			});
		}
		
		//JMenuItems - subjects
		JMenuItem add = new JMenuItem("Add Subject");
		JMenuItem remove = new JMenuItem("Remove Subject");
		JMenuItem manage = new JMenuItem("Manage Subjects");
		
		this.addAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.displayAddSubjectToSchedule();
			}
		};
		add.addActionListener(addAction);
		this.removeAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.swapRemoveCursor();
			}
		};
		remove.addActionListener(removeAction);
		manage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.displayPanel(guiCon.getManageSubjects().display());
			}
		});
		
		
		
		
		menubar.add(file);
		menubar.add(subjects);
		
		addKeyMaps();
		this.guiCon.getMainFrame().updateMenu(menubar);
	}
	
	private void addKeyMaps() {
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("+"), "add");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("a"), "add");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("-"), "remove");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("r"), "remove");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("o"), "open");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("s"), "save");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "return");
		
		
		this.getActionMap().put("add", this.addAction);
		this.getActionMap().put("remove", this.removeAction);
		this.getActionMap().put("open", this.openAction);
		this.getActionMap().put("save", this.saveAction);
		this.getActionMap().put("return", this.returnBackAction);
		
	}

	public void setSchedule(Schedule schedule) {
		setSize(schedule.getSize());
		add(schedule);
	}
}
