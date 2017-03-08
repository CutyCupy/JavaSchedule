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

import de.privat.ciupka.schedule.controller.Controller;
import de.privat.ciupka.schedule.controller.GUIController;

public class SchedulePanel extends JPanel {

	private static final long serialVersionUID = -6492579071611195643L;

	private boolean showed;
	
	private Action addAction;
	private Action removeAction;
	private Action openAction;
	private Action saveAction;
	private Action returnBackAction;

	private GUIController guiCon;
	private JMenuBar menubar;

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
		this.guiCon.getMainFrame().updateMenu(menubar);
		return this;
	}

	@SuppressWarnings("serial")
	private void createItems() {
		menubar = new JMenuBar();
		
		//JMenus
		JMenu file = new JMenu("File");
		file.setMnemonic('f');
		JMenu subjects = new JMenu("Subjects");
		subjects.setMnemonic('u');
		JMenu export = new JMenu("Export");
		export.setMnemonic('e');
		
		//JMenuItems - file
		JMenuItem open = new JMenuItem("Open Schedule");
		open.setMnemonic('o');
		JMenuItem save = new JMenuItem("Save Schedule");
		save.setMnemonic('s');
		JMenuItem restart = new JMenuItem("Restart Schedule");
		restart.setMnemonic('t');
		JMenuItem back = new JMenuItem("To Mainmenu");
		back.setMnemonic('b');
		
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
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiCon.restartSchedule();
			}
		});
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
		add.setMnemonic('a');
		JMenuItem remove = new JMenuItem("Remove Subject");
		remove.setMnemonic('r');
		JMenuItem manage = new JMenuItem("Manage Subjects");
		manage.setMnemonic('m');
		
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
				guiCon.displayPanel(guiCon.getManageSubjects().displaySubjects(Controller.getInstance().loadAllSubjects()));
			}
		});
		
		file.add(open);
		file.add(save);
		file.add(restart);
		file.addSeparator();
		file.add(export);
		file.addSeparator();
		file.add(back);
		
		export.add(exportJPG);
		export.add(exportPNG);
		export.add(exportPDF);
		export.add(exportXLS);
		
		subjects.add(add);
		subjects.add(remove);
		subjects.addSeparator();
		subjects.add(manage);
		
		
		menubar.add(file);
		menubar.add(subjects);
		addKeyMaps();
	}
	
	private void addKeyMaps() {
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("+"), "add");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "add");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("-"), "remove");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "remove");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("O"), "open");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "save");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "return");
		
		
		this.getActionMap().put("add", this.addAction);
		this.getActionMap().put("remove", this.removeAction);
		this.getActionMap().put("open", this.openAction);
		this.getActionMap().put("save", this.saveAction);
		this.getActionMap().put("return", this.returnBackAction);
	}

	public void setSchedule(Schedule schedule) {
		setSize(schedule.getSize().width + 5, schedule.getSize().height + 30);
		add(schedule);
	}
}
