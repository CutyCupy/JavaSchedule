package de.privat.ciupka.schedule.gui.schedule;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;

public class schedulesubjecteditorframe extends JFrame {

	private JPanel contentPane;
	private HintTextField startHourT;
	private HintTextField startMinuteT;
	private HintTextField endHourT;
	private HintTextField endMinuteT;
	private JButton btnAdd;
	private JButton cancelB;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					schedulesubjecteditorframe frame = new schedulesubjecteditorframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public schedulesubjecteditorframe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox subjectsCB = new JComboBox();
		subjectsCB.setBounds(10, 11, 212, 20);
		contentPane.add(subjectsCB);
		
		startHourT = new HintTextField("HH");
		startHourT.setBounds(10, 40, 30, 20);
		contentPane.add(startHourT);
		
		JLabel label = new JLabel(":");
		label.setBounds(42, 42, 4, 14);
		contentPane.add(label);
		
		startMinuteT = new HintTextField("MM");
		startMinuteT.setBounds(46, 40, 30, 20);
		contentPane.add(startMinuteT);
		
		JButton swapT = new JButton("<-->");
		swapT.setFont(new Font("Tahoma", Font.PLAIN, 11));
		swapT.setBounds(86, 40, 60, 20);
		contentPane.add(swapT);
		
		endHourT = new HintTextField("HH");
		endHourT.setBounds(156, 42, 30, 20);
		contentPane.add(endHourT);
		
		JLabel label_1 = new JLabel(":");
		label_1.setBounds(188, 44, 4, 14);
		contentPane.add(label_1);
		
		endMinuteT = new HintTextField("MM");
		endMinuteT.setBounds(192, 42, 30, 20);
		contentPane.add(endMinuteT);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(133, 71, 89, 23);
		contentPane.add(btnAdd);
		
		cancelB = new JButton("Cancel");
		cancelB.setBounds(10, 71, 89, 23);
		contentPane.add(cancelB);
		
	}
}
