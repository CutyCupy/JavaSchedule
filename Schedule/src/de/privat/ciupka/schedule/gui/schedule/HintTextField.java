package de.privat.ciupka.schedule.gui.schedule;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

/**
 * http://stackoverflow.com/questions/1738966/java-jtextfield-with-input-hint
 * last visited 28th February 2017
 * Slightly edited by Alexander Ciupka
 * @author Bart Kiers
 *
 */
class HintTextField extends JTextField implements FocusListener, KeyListener {

	private static final long serialVersionUID = 7056596898890644848L;
	
	private final String hint;
	private boolean showingHint;
	private int min;
	private int max;
	private String lastInput;
	
	private boolean check;

	public HintTextField(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
		super.addKeyListener(this);
		this.check = true;
		lastInput = "";
	}

	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("Hint? " + showingHint);
		if(showingHint) {
			super.setText("");
			showingHint = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		this.setText(getTextAfterFocus());
		lastInput = this.getText();
		System.out.println(lastInput + " - " + this.getText());
		if(this.getText().isEmpty()) {
			System.out.println("EMPTY");
			super.setText(hint);
			showingHint = true;
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
	
	@Override
	public void setText(String text) {
		showingHint = false;
		super.setText(text);
		lastInput = text;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		lastInput = checkInput(e);
		setText(lastInput);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void setLastInput(String input) {
		lastInput = input;
	}
	
	public String getLastInput() {
		return lastInput;
	}
	
	public void setMinMax(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public void checkInputs(boolean check) {
		this.check = check;
	}
	
	private String checkInput(KeyEvent e) {
		if(check) {
			String newLastInput = lastInput;
			try {
				Integer.parseInt(String.valueOf(e.getKeyChar()));
				int value = Integer.parseInt(((JTextField) e.getSource()).getText());
				if(value < min || value > max) {
					newLastInput = value < min ? String.valueOf(min) : String.valueOf(max);
				} else {
					newLastInput = String.valueOf(value);
				}
			} catch(Exception exc) {
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					newLastInput = ((JTextField) e.getSource()).getText();						
				}
			}
			System.out.println("Last input: " + newLastInput);
			return newLastInput;
		}
		System.out.println("Last input: " + this.getText());
		return this.getText();
	}
	
	private String getTextAfterFocus() {
		if(check && (min == 0 && (max == 23 || max == 59)) && lastInput.length() == 1) {
			lastInput = "0" + lastInput;
		}
		return lastInput;
	}
}
