package de.privat.ciupka.schedule.logic.schedule;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import de.privat.ciupka.schedule.controller.Controller;
import de.privat.ciupka.schedule.controller.GUIController;
import de.privat.ciupka.schedule.gui.popups.ErrorMessages;
import de.privat.ciupka.schedule.gui.schedule.SubjectLabel;

public class ScheduleHandler {

	private Controller controller;
	private GUIController guiCon;

	public static final String DEFAULT_PATH = PropertieHandler.SAVE_PATH + PropertieHandler.SEPERATOR + "schedules"
			+ PropertieHandler.SEPERATOR;

	public ScheduleHandler() {
		controller = Controller.getInstance();
		guiCon = GUIController.getInstance();
		new File(DEFAULT_PATH).mkdirs();
	}

	public void saveSchedule() {
		// {days: [Day1, Day2, ...], times: [Time1, Time2, ...], size: {width:
		// width, height: height}, content: {{Subject1: {name: name, start:
		// start, end: end, day: day, room: room}}}
		JFileChooser chooser = new JFileChooser(DEFAULT_PATH);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "JSON-File (.json)";
			}

			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".json");
			}
		});
		int result = chooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			JsonObject save = new JsonObject();
			JsonArray days = new JsonArray();
			JsonArray times = new JsonArray();
			JsonObject size = new JsonObject();
			JsonArray content = new JsonArray();
			for (String s : guiCon.getSchedule().getDays()) {
				JsonObject currentDay = new JsonObject();
				currentDay.addProperty("day", s);
				days.add(currentDay);
			}
			for (Time t : guiCon.getSchedule().getTimes()) {
				JsonObject currentTime = new JsonObject();
				currentTime.addProperty("time", t.getTime());
				times.add(currentTime);
			}
			size.addProperty("width", guiCon.getSchedule().getWidth());
			size.addProperty("height", guiCon.getSchedule().getHeight());
			for (SubjectLabel s : guiCon.getSchedule().getSubjects()) {
				JsonObject currentSubject = new JsonObject();
				currentSubject.addProperty("name", s.getSubject().getName());
				currentSubject.addProperty("start", s.getStartTime());
				currentSubject.addProperty("end", s.getEndTime());
				currentSubject.addProperty("room", s.getRoom());
				currentSubject.addProperty("day", s.getDay());
				content.add(currentSubject);
			}
			save.add("days", days);
			save.add("times", times);
			save.add("size", size);
			save.add("content", content);
			try {
				String path = chooser.getSelectedFile().getAbsolutePath();
				if (!path.endsWith(".json")) {
					path += ".json";
				}
				FileWriter writer = new FileWriter(new File(path));
				writer.write(save.toString());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadSchedule() {
		// {days: [Day1, Day2, ...], times: [Time1, Time2, ...], size: {width:
		// width, height: height}, content: {{Subject1: {name: name, start:
		// start, end: end, day: day, room: room}}}
		JFileChooser chooser = new JFileChooser(DEFAULT_PATH);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			JsonObject loaded = null;
			try {
				loaded = new JsonParser().parse(new FileReader(chooser.getSelectedFile())).getAsJsonObject();
			} catch (JsonIOException e) {
				ErrorMessages.createErrorMessage("IO exception!",
						"Couldnt read the given json file. An internal IO error occured.");
				return;
			} catch (JsonSyntaxException e) {
				ErrorMessages.createErrorMessage("Syntax error!",
						"This json file might be corrupted! Wrong syntax was found.");
				return;
			} catch (FileNotFoundException e) {
				ErrorMessages.createErrorMessage("File not found!",
						"Your selected file was not found! Please try again.");
				return;
			}
			JsonArray days = loaded.getAsJsonArray("days");
			JsonArray times = loaded.getAsJsonArray("times");
			JsonArray subjects = loaded.getAsJsonArray("content");
			int width = loaded.get("size").getAsJsonObject().get("width").getAsInt();
			int height = loaded.get("size").getAsJsonObject().get("height").getAsInt();
			guiCon.newSchedule();
			for (int i = 0; i < days.size(); i++) {
				guiCon.getSchedule().addDay(days.get(i).getAsJsonObject().get("day").getAsString());
			}
			for (int i = 0; i < times.size(); i++) {
				Time currentTime = new Time();
				currentTime.setTime(times.get(i).getAsJsonObject().get("time").getAsInt());
				guiCon.getSchedule().addTime(currentTime);
			}
			guiCon.getSchedule().setSize(width, height);
			for (int i = 0; i < subjects.size(); i++) {
				JsonObject currentObject = subjects.get(i).getAsJsonObject();
				Time start = new Time();
				Time end = new Time();
				start.setTime(currentObject.get("start").getAsInt());
				end.setTime(currentObject.get("end").getAsInt());
				guiCon.getSchedule().addSubjectLabelBounds(
						controller.getPropertieHandler().getSubjectByName(currentObject.get("name").getAsString()),
						start, end, currentObject.get("day").getAsString(), currentObject.get("room").getAsString());
			}
			guiCon.displayPanel(guiCon.setSchedule());
			guiCon.getSchedule().setEditable(true);
		}
	}

	public void exportImage(String type) {
		//TODO: Update the code to be more beautiful ^^
		Dimension size = guiCon.getSchedule().getSize();
		BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, size.width, size.height);
		guiCon.getSchedule().paint(g);
		try {
			JFileChooser chooser = new JFileChooser(DEFAULT_PATH);
			chooser.setFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "Pictures (" + type + ")";
				}
				
				@Override
				public boolean accept(File f) {
					return f.getAbsolutePath().endsWith("." + type);
				}
			});
			chooser.showSaveDialog(null);
			ImageIO.write(image, type, new File(chooser.getSelectedFile().getAbsolutePath() + "." + type));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
