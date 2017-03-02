package de.privat.ciupka.schedule.gui.mainmenu;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class WindowHandler {

	public static final Dimension SCREEN_RESOLUTION = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static Point placeFrameToMiddle(JFrame frame) {
		return placeFrameToMiddle(frame.getHeight(), frame.getWidth());
	}
	
	public static Point placeFrameToMiddle(int height, int width) {
		int x = (int) ((SCREEN_RESOLUTION.getWidth() / 2) - (width / 2));
		int y = (int) ((SCREEN_RESOLUTION.getHeight() / 2) - (height / 2));
		return new Point(x, y);
	}
	
	public static int placeItemToHMiddle(int itemWidth, int containerWidth) {
		int x = (int) ((containerWidth / 2) - (itemWidth / 2));
		System.out.println(x + " - " + containerWidth + " and " + itemWidth);
		return x;
	}
	
	public static int placeItemToVMiddle(int itemHeight, int containerHeight) {
		int y = (int) ((containerHeight / 2) - (itemHeight / 2));
		return y;
	}
	
	
}
