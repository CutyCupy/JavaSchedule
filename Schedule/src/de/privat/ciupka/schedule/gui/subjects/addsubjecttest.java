package de.privat.ciupka.schedule.gui.subjects;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class addsubjecttest extends JFrame {

	private JPanel contentPane;
	private JTextField nameT;
	private JTextField shortNameT;
	private JTextField teacherT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addsubjecttest frame = new addsubjecttest();
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
	public addsubjecttest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameL = new JLabel("Name: ");
		nameL.setBounds(65, 40, 34, 14);
		contentPane.add(nameL);
		
		JLabel shortNameL = new JLabel("Shortname: ");
		shortNameL.setBounds(40, 65, 59, 14);
		contentPane.add(shortNameL);
		
		JLabel teacherL = new JLabel("Teacher: ");
		teacherL.setBounds(53, 90, 46, 14);
		contentPane.add(teacherL);
		
		nameT = new JTextField();
		nameT.setBounds(107, 37, 86, 20);
		contentPane.add(nameT);
		nameT.setColumns(10);
		
		shortNameT = new JTextField();
		shortNameT.setColumns(10);
		shortNameT.setBounds(107, 62, 86, 20);
		contentPane.add(shortNameT);
		
		teacherT = new JTextField();
		teacherT.setColumns(10);
		teacherT.setBounds(107, 87, 86, 20);
		contentPane.add(teacherT);
		
		JSlider redS = new JSlider();
		redS.setValue(0);
		redS.setMaximum(255);
		redS.setBounds(107, 125, 200, 26);
		contentPane.add(redS);
		
		JLabel redL = new JLabel("Red:");
		redL.setBounds(75, 125, 24, 26);
		contentPane.add(redL);
		
		JLabel greenL = new JLabel("Green:");
		greenL.setBounds(65, 162, 34, 24);
		contentPane.add(greenL);
		
		JLabel blueL = new JLabel("Blue:");
		blueL.setBounds(75, 197, 24, 26);
		contentPane.add(blueL);
		
		JSlider greenS = new JSlider();
		greenS.setValue(0);
		greenS.setMaximum(255);
		greenS.setBounds(107, 162, 200, 26);
		contentPane.add(greenS);
		
		JSlider blueS = new JSlider();
		blueS.setValue(0);
		blueS.setMaximum(255);
		blueS.setBounds(107, 197, 200, 26);
		contentPane.add(blueS);
		
		blueS.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("Hello");
			}
		});
		
		JLabel colorL = new JLabel("");
		colorL.setBounds(248, 40, 64, 64);
		contentPane.add(colorL);
		
		JButton addOrEditB = new JButton("Add");
		addOrEditB.setBounds(235, 227, 89, 23);
		contentPane.add(addOrEditB);
		
		JButton cancelB = new JButton("Cancel");
		cancelB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		cancelB.setBounds(10, 227, 89, 23);
		contentPane.add(cancelB);
		
		JButton removeB = new JButton("Remove");
		removeB.setBounds(136, 227, 89, 23);
		contentPane.add(removeB);
		
	}
}
