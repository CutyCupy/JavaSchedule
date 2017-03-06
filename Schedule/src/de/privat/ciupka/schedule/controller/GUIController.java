package de.privat.ciupka.schedule.controller;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import de.privat.ciupka.schedule.gui.mainmenu.MainFrame;
import de.privat.ciupka.schedule.gui.mainmenu.MainMenu;
import de.privat.ciupka.schedule.gui.popups.ErrorMessages;
import de.privat.ciupka.schedule.gui.popups.Messages;
import de.privat.ciupka.schedule.gui.schedule.CreateSchedule;
import de.privat.ciupka.schedule.gui.schedule.Schedule;
import de.privat.ciupka.schedule.gui.schedule.SchedulePanel;
import de.privat.ciupka.schedule.gui.schedule.ScheduleSubjectEditor;
import de.privat.ciupka.schedule.gui.schedule.SubjectLabel;
import de.privat.ciupka.schedule.gui.subjects.AddOrEditSubject;
import de.privat.ciupka.schedule.gui.subjects.ManageSubjects;
import de.privat.ciupka.schedule.logic.schedule.PropertieHandler;
import de.privat.ciupka.schedule.logic.schedule.ScheduleHandler;
import de.privat.ciupka.schedule.logic.schedule.Subject;
import de.privat.ciupka.schedule.logic.schedule.Time;

public class GUIController {

	private static GUIController instance;

	private MainFrame mainFrame;
	private Controller controller;

	private MainMenu mainMenu;
	private CreateSchedule createSchedule;
	private Schedule schedule;
	private SchedulePanel schedulePanel;
	private ManageSubjects manageSubjects;
	private AddOrEditSubject addSubject;
	private ScheduleSubjectEditor addSubjectToSchedule;

	private GUIController() {}

	public static GUIController getInstance() {
		if (instance == null) {
			instance = new GUIController();
			instance.start();
		}
		return instance;
	}

	public void start() {
		controller = Controller.getInstance();
		mainFrame = new MainFrame();
		mainMenu = new MainMenu();
		createSchedule = new CreateSchedule();
		schedule = new Schedule();
		schedulePanel = new SchedulePanel();
		manageSubjects = new ManageSubjects();
		addSubject = new AddOrEditSubject();
		addSubjectToSchedule = new ScheduleSubjectEditor();
		mainFrame.updateContentPane(mainMenu.display());
	}

	public void createSchedule(String startHour, String startMinute, String endHour, String endMinute,
			String intervalHour, String intervalMinute, String startDay, String endDay, String height, String width,
			boolean editable) {
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
			int startTime = startH * 60 + startM;
			int endTime = endH * 60 + endM;
			int intervalTime = intervalH * 60 + intervalM;
			if (startTime > endTime) {
				System.out.println("startTime > endTime");
				start.setTime(endTime);
				end.setTime(startTime);
			} else {
				System.out.println("endTime > startTime");
				start.setTime(startTime);
				end.setTime(endTime);
			}
			int currentTime = start.getTime();
			while (currentTime <= end.getTime()) {
				System.out.println(currentTime);
				Time currentT = new Time();
				currentT.setTime(currentTime);
				schedule.addTime(currentT);
				currentTime += intervalTime;
			}
			int startDayIndex = -1;
			int endDayIndex = -1;
			for (int i = 0; i < Schedule.ALL_DAYS.length; i++) {
				if (Schedule.ALL_DAYS[i].equals(startDay)) {
					startDayIndex = i;
				}
				if (Schedule.ALL_DAYS[i].equals(endDay)) {
					endDayIndex = i;
				}
			}
			if (startDayIndex > endDayIndex) {
				int temp = startDayIndex;
				startDayIndex = endDayIndex;
				endDayIndex = temp;
			}
			IntStream.range(startDayIndex, endDayIndex + 1).forEach(n -> {
				System.out.println(n);
				schedule.addDay(Schedule.ALL_DAYS[n]);
			});
			schedulePanel.setSchedule(schedule.generateSchedule(w, h));
			displayPanel(schedulePanel.display());
			schedule.setEditable(editable);
		} catch (Exception e) {
			ErrorMessages.createErrorMessage("Creating Schedule",
					"An error occured while creating your Schedule - please check your data!");
		}
	}

	public void displayAddSubjectToSchedule() {
		this.addSubjectToSchedule.display();
	}

	public void restartSchedule() {
		// TODO: Ask if sure and let the user create a new blank schedule
	}

	public void openNewSchedule() {
		// TODO: Open a JFileChooser and let the user decide which schedule he
		// wants to open
	}

	public void saveSchedule() {
		controller.saveSchedule();
	}

	public void backToMainMenu() {
		// TODO: Ask if sure and go back to main menu
	}

	public void displayPanel(Container panel) {
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

	public void export(String fileType) {
		// TODO: Check for name and call right controller export function.
	}

	public ManageSubjects getManageSubjects() {
		return manageSubjects;
	}

	public void swapRemoveCursor() {
		schedule.swapRemove();
	}

	public AddOrEditSubject getAddSubject() {
		return this.addSubject;
	}

	public void addSubject(String name, String shortName, String teacher, Color color) {
		Subject newSubject = new Subject();
		newSubject.setName(name);
		newSubject.setShortName(shortName);
		newSubject.setTeacher(teacher);
		newSubject.setColor(color);
		Controller.getInstance().addSubject(newSubject);
	}

	public void removeSubject(Subject subject) {
		if(Messages.openYesNoDialog("Remove " + subject.getName() + "?", "Do you really want to remove " + subject.getName() + "?"))
			Controller.getInstance().removeSubject(subject);
	}

	public void editSubject(Subject old, String name, String shortName, String teacher, Color color) {
		Subject newSubject = new Subject();
		newSubject.setName(name);
		newSubject.setShortName(shortName);
		newSubject.setTeacher(teacher);
		newSubject.setColor(color);
		Controller.getInstance().editSubject(old, newSubject);
	}

	public static Color getForegroundColor(int red, int green, int blue) {
		return (0.2126*red + 0.7152*green + 0.0722*blue) / 255 > 0.5 ? Color.BLACK : Color.WHITE;
	}

	public void addSubjectToSchedule(String startMinute, String startHour, String endMinute, String endHour, String room, String subjectName, String day) {
		try {
			Time start = new Time();
			Time end = new Time();
			start.setTime(Integer.parseInt(startHour), Integer.parseInt(startMinute));
			end.setTime(Integer.parseInt(endHour), Integer.parseInt(endMinute));
			schedule.addSubjectLabelBounds(controller.getPropertieHandler().getSubjectByName(subjectName), start, end, day, room);
			schedule.generateSchedule();
			this.addSubjectToSchedule.setVisible(false);
		} catch(Exception e) {
			ErrorMessages.createErrorMessage("Add Subject error!", "Please enter a valid Time for the subject.");
		}
	}
	
	public ArrayList<String> getScheduleDays() {
		return schedule.getDays();
	}

	public void newSchedule() {
		this.schedule = new Schedule();
	}
}
