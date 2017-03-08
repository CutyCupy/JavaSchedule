package de.privat.ciupka.schedule.gui.subjects;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.privat.ciupka.schedule.controller.Controller;
import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.gui.schedule.SubjectLabel;
import de.privat.ciupka.schedule.logic.schedule.Subject;

public class ManageSubjects extends JScrollPane {
	

	private static final long serialVersionUID = -2981834550721791099L;
	
	private GUIController guiCon;
	private boolean remove;
	private ArrayList<Subject> displayedSubjects;
	private boolean showed;
	private JMenuBar menuBar;
		
	public ManageSubjects() {
		guiCon = GUIController.getInstance();
	}
	
	public ManageSubjects displaySubjects(ArrayList<Subject> toDisplay) {
		displayedSubjects = toDisplay;
		if(!showed) {
			menuBar = new JMenuBar();
			JMenu manageMenu = new JMenu("Manage");
			JMenuItem back = new JMenuItem("Back");
			back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					guiCon.displayPanel(guiCon.getMainMenu().display());
				}
			});
			JMenuItem add = new JMenuItem("Add Subject");
			add.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					guiCon.displayPanel(guiCon.getAddSubject().display(AddOrEditSubject.ADD, null));
				}
			});
			JMenuItem delete = new JMenuItem("Remove Subject");
			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					swapRemove();
				}
			});
			manageMenu.add(back);
			manageMenu.add(add);
			manageMenu.add(delete);
			
			menuBar.add(manageMenu);
			showed = true;
		}
		if(!menuBar.equals(guiCon.getMainFrame().getMenuBar())) {
			guiCon.getMainFrame().updateMenu(menuBar);
		}
		this.setSize(300, 400);
		JPanel content = new JPanel();
		content.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		content.setLayout(layout);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipadx = 0;
		for(int i = 0; i < toDisplay.size() + 1; i++) {
			gbc.gridx = i % 2;
			gbc.gridy = i / 2;
			if(i == toDisplay.size()) {
				JButton addSubject = new JButton("+");
				addSubject.setFont(new Font("Times New Roman", Font.BOLD, 30));
				addSubject.setPreferredSize(new Dimension(145, 100));
				addSubject.setBackground(Color.white);
				addSubject.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {}
					
					@Override
					public void mouseExited(MouseEvent e) {
						((JButton) e.getSource()).setBackground(((JButton) e.getSource()).getBackground().brighter());
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						((JButton) e.getSource()).setBackground(((JButton) e.getSource()).getBackground().darker());
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						if(!remove) {
							guiCon.displayPanel(guiCon.getAddSubject().display(AddOrEditSubject.ADD, null));
						}
					}
				});
				layout.setConstraints(addSubject, gbc);
				content.add(addSubject);
			} else {
				SubjectLabel currentScheduleL = new SubjectLabel(toDisplay.get(i));
				currentScheduleL.setFont(new Font("Times New Roman", Font.BOLD, 15));
				currentScheduleL.setPreferredSize(new Dimension(145, 100));
				currentScheduleL.setName(String.valueOf(i));
				currentScheduleL.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {}
					
					@Override
					public void mouseExited(MouseEvent e) {
						((SubjectLabel) e.getSource()).setBackground(((SubjectLabel) e.getSource()).getBackground().brighter());
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						((SubjectLabel) e.getSource()).setBackground(((SubjectLabel) e.getSource()).getBackground().darker());
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						if(!remove) {
							guiCon.displayPanel(guiCon.getAddSubject().display(AddOrEditSubject.EDIT, displayedSubjects.get(Integer.parseInt(((SubjectLabel) e.getSource()).getName()))));
						} else {
							guiCon.removeSubject(displayedSubjects.get(Integer.parseInt(((SubjectLabel) e.getSource()).getName())));
							displaySubjects(Controller.getInstance().loadAllSubjects());
							swapRemove();
						}
					}
				});
				layout.setConstraints(currentScheduleL, gbc);
				content.add(currentScheduleL);
			}
		}
		
		this.setViewportView(content);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return this;
	}
	
	public void swapRemove() {
		remove = !remove;
		if(remove) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
}
