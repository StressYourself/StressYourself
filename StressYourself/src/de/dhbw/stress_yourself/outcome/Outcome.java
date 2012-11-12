package de.dhbw.stress_yourself.outcome;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.params.*;


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
		JLabel label = new JLabel("OUTCOME");
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
