package de.dhbw.stress_yourself.outcome;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.params.Parameter;
import de.dhbw.stress_yourself.params.UserData;


public class Outcome {

	private Parameter params;
	private UserData usr;
	private String path;

	public Outcome(Parameter params, UserData usr) {
		this.params = params;
		this.usr = usr;
	}

	public JPanel getOutcomeGUI() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("You have completed the test! Thank you for your patience!");
		JButton back = new JButton("Back to Login");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		panel.add(label);
		return panel;
	}

	public boolean createOutcome() {
		path = params.getOutcomePath();
		CSV csv = new CSV(params);
		PDF pdf = new PDF(params,usr);
		if (!csv.createCSV(path) || !pdf.createPDF(path)) {
			return true;
		} else {
			return false;
		}
	}
}
