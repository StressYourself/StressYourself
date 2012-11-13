package de.dhbw.stress_yourself.outcome;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.MainApplication;
import de.dhbw.stress_yourself.params.Parameter;
import de.dhbw.stress_yourself.params.UserData;

public class Outcome {

	private Parameter params;
	private UserData usr;
	private MainApplication main;
	private String path;

	public Outcome(MainApplication main, Parameter params, UserData usr) {
		this.main = main;
		this.params = params;
		this.usr = usr;
	}

	public JPanel getOutcomeGUI() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 900, 700);
		JLabel label = new JLabel(
				"You have completed the test! Thank you for participating!");
		label.setBounds(250, 250, 400, 25);
		JButton back = new JButton("Back to Login");
		back.setBounds(350, 300, 150, 25);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.restartStressYourself();
			}
		});

		panel.add(label);
		panel.add(back);
		return panel;
	}

	public boolean createOutcome() {
		path = params.getOutcomePath();
		CSV csv = new CSV(params, usr);
		PDF pdf = new PDF(params, usr);
		if (!csv.createCSV(path) || !pdf.createPDF(path)) {
			return true;
		} else {
			return false;
		}
	}
}
