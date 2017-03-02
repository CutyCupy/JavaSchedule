package de.privat.ciupka.schedule.controller;

import java.util.stream.IntStream;

import javax.swing.JPanel;

import de.privat.ciupka.schedule.gui.mainmenu.MainFrame;
import de.privat.ciupka.schedule.gui.mainmenu.MainMenu;
import de.privat.ciupka.schedule.gui.popups.ErrorMessages;
import de.privat.ciupka.schedule.gui.schedule.CreateSchedule;
import de.privat.ciupka.schedule.gui.schedule.Schedule;
import de.privat.ciupka.schedule.gui.schedule.SchedulePanel;
import de.privat.ciupka.schedule.logic.schedule.Time;

public class GUIController {
	
	private static GUIController instance;
	
	private MainFrame mainFrame;

	private MainMenu mainMenu;
	private CreateSchedule createSchedule;
	private Schedule schedule;
	private SchedulePanel schedulePanel;
	
	private GUIController() {}
	
	public static GUIController getInstance() {
		if(instance == null) {
			instance = new GUIController();
			instance.start();
		}
		return instance;
	}
	
	public void start() {
		mainFrame = new MainFrame();
		mainMenu = new MainMenu();
		createSchedule = new CreateSchedule();
		schedule = new Schedule();
		schedulePanel = new SchedulePanel();
		mainFrame.updateContentPane(createSchedule.display());
	}
	
	public void createSchedule(String startHour, String startMinute, String endHour, String endMinute, String intervalHour, 
			String intervalMinute, String startDay, String endDay, String height, String width, boolean editable) {
		try {
			schedule.reset();
			int startH = Integer.parseInt(startHour);
			int startM = Integer.parseInt(startMinute);
			int endH = Integer.parseInt(endHour);
			int endM = Integer.parseInt(endMinute);
			int intervalH = Integer.parseInt(intervalHour);
			int intervalM = Integer.parseInt(intervalMinute);
			int h = Integer.parseInt(height);
			int w = Integer.parseInt(width);
			Time start = new Time();
			Time end = new Time();
			int startTime = startH*60 + startM;
			int endTime = endH*60 + endM;
			int intervalTime = intervalH*60 + intervalM;
			if(startTime > endTime) {
				System.out.println("startTime > endTime");
				start.setTime(endTime);
				end.setTime(startTime);
			} else {
				System.out.println("endTime > startTime");
				start.setTime(startTime);
				end.setTime(endTime);
			}
			int currentTime = start.getTime();
			while(currentTime <= end.getTime()) {
				System.out.println(currentTime);
				Time currentT = new Time();
				currentT.setTime(currentTime);
				schedule.addTime(currentT);
				currentTime += intervalTime;
			}
			int startDayIndex = -1;
			int endDayIndex = -1;
			for(int i = 0; i < Schedule.ALL_DAYS.length; i++) {
				if(Schedule.ALL_DAYS[i].equals(startDay)) {
					startDayIndex = i;
				} if(Schedule.ALL_DAYS[i].equals(endDay)) {
					endDayIndex = i;
				}
			}
			if(startDayIndex > endDayIndex) {
				int temp = startDayIndex;
				startDayIndex = endDayIndex;
				endDayIndex = temp;
			}
			IntStream.range(startDayIndex, endDayIndex+1).forEach(
			        n -> {
			        	System.out.println(n);
			            schedule.addDay(Schedule.ALL_DAYS[n]);
			        }
			    );
			schedulePanel.setSchedule(schedule.generateSchedule(w, h));
			displayPanel(schedulePanel.display());
		} catch(Exception e) {
			ErrorMessages.createErrorMessage("Creating Schedule", "An error occured while creating your Schedule - please check your data!");
		}
	}
	
	public void displayAddSubjectToSchedule() {
		//TODO: Writing a pop-up for this function
	}
	
	public void restartSchedule() {
		//TODO: Ask if sure and let the user create a new blank schedule
	}
	
	public void openNewSchedule() {
		//TODO: Open a JFileChooser and let the user decide which schedule he wants to open
	}
	
	public void saveSchedule() {
		//TODO: Writing a logic function that allows the user to save his current schedule
	}
	
	public void backToMainMenu() {
		//TODO: Ask if sure and go back to main menu
	}
	
	public void exportToPNG() {
		//TODO: Writing a logic function that exports the current schedule as PNG image.
	}
	
	public void exportToJPG() {
		//TODO: Writing a logic function that exports the current schedule as JPG image.
	}
	
	public void exportToPDF() {
		//TODO: Writing a logic function that exports the current schedule as PDF file.
	}
	
	public void exportToXLS() {
		//TODO: Writing a logic function that exports the current schedule as XLS (Excel) file (maybe).
	}
	
	public void displayPanel(JPanel panel) {
		mainFrame.updateContentPane(panel);
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public CreateSchedule getCreateSchedule() {
		return createSchedule;
	}

	public Schedule getSchedule() {
		return schedule;
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}
}
