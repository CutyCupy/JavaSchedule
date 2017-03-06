package de.privat.ciupka.schedule.logic.schedule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import de.privat.ciupka.schedule.controller.Controller;
import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.gui.schedule.SubjectLabel;

public class ScheduleHandler {
	
	private Controller controller;
	private GUIController guiCon;
	
	public static final String DEFAULT_PATH = PropertieHandler.SAVE_PATH + PropertieHandler.SEPERATOR + "schedules" + PropertieHandler.SEPERATOR;
	
	
	public ScheduleHandler() {
		controller = Controller.getInstance();
		guiCon = GUIController.getInstance();
		new File(DEFAULT_PATH).mkdirs();
	}
	
	public void saveSchedule() {
		//{days: [Day1, Day2, ...], times: [Time1, Time2, ...], size: {width: width, height: height}, content: {day1: {Subject1: {name: name, start: start, end: end}, day2: ...}}}
		JFileChooser chooser = new JFileChooser(DEFAULT_PATH);
		int result = chooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			JsonObject save = new JsonObject();
			JsonArray days = new JsonArray();
			JsonArray times = new JsonArray();
			JsonObject size = new JsonObject();
			JsonArray content = new JsonArray();
			for(String s : guiCon.getSchedule().getDays()) {
				JsonObject currentDay = new JsonObject();
				currentDay.addProperty("day", s);
				days.add(currentDay);
			}
			for(Time t : guiCon.getSchedule().getTimes()) {
				JsonObject currentTime = new JsonObject();
				currentTime.addProperty("time", t.getTime());
				times.add(currentTime);
			}
			size.addProperty("width", guiCon.getSchedule().getWidth());
			size.addProperty("height", guiCon.getSchedule().getHeight());
			for(SubjectLabel s : guiCon.getSchedule().getSubjects()) {
				JsonObject currentSubject = new JsonObject();
				currentSubject.addProperty("name", s.getSubject().getName());
				currentSubject.addProperty("start", s.getStartTime());
				currentSubject.addProperty("end", s.getEndTime());
				currentSubject.addProperty("room", s.getRoom());
				content.add(currentSubject);
			}
			save.add("days", days);
			save.add("times", times);
			save.add("size", size);
			save.add("content", content);
			try {
				FileWriter writer = new FileWriter(chooser.getSelectedFile());
				writer.write(save.toString());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadSchedule() {
		//{days: [Day1, Day2, ...], times: [Time1, Time2, ...], size: {width: width, height: height}, content: {day1: {Subject1: {name: name, start: start, end: end, room: room}, day2: ...}}}
		JFileChooser chooser = new JFileChooser(DEFAULT_PATH);
		int result = chooser.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			JsonObject loaded = null;
			try {
				loaded = new JsonParser().parse(new FileReader(chooser.getSelectedFile())).getAsJsonObject();
			} catch (JsonIOException e) {
				//TODO: Display correct error message dialog
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				//TODO: Display correct error message dialog
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				//TODO: Display correct error message dialog
				e.printStackTrace();
			}
			JsonArray days = loaded.getAsJsonArray("days");
			JsonArray times = loaded.getAsJsonArray("times");
			JsonArray subjects = loaded.getAsJsonArray("content");
			int width = loaded.get("size").getAsJsonObject().get("width").getAsInt();
			int height = loaded.get("size").getAsJsonObject().get("height").getAsInt();
			guiCon.newSchedule();
			for(int i = 0; i < days.size(); i++) {
				guiCon.getSchedule().addDay(days.get(i).getAsString());
			}
			for(int i = 0; i < times.size(); i++) {
				Time currentTime = new Time();
				currentTime.setTime(times.get(i).getAsInt());
				guiCon.getSchedule().addTime(currentTime);
			}
			for(int i = 0; i < subjects.size(); i++) {
				JsonArray currentDay = subjects.get(0).getAsJsonArray();
				for(int j = 0; j < currentDay.size(); j++) {
					JsonObject currentObject = currentDay.get(j).getAsJsonObject()
					Time start = new Time();
					Time end = new Time();
					start.setTime(currentObject.get("start").getAsInt());
					end.setTime(currentObject.get("end").getAsInt());
					guiCon.getSchedule().addSubjectLabelBounds(controller.getPropertieHandler().getSubjectByName(currentObject.get("name").getAsString()), 
							start, end, guiCon.getSchedule().getDays().get(i), currentObject.get("room").getAsString());
				}
			}
			guiCon.displayPanel(guiCon.getSchedule());
		} 
	}
}
