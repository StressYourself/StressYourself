package de.dhbw.stress_yourself.outcome;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.params.Parameter;

public class Outcome {

	private Parameter params;
	private String path;

	public Outcome(Parameter params) {
		this.params = params;
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
		PDF pdf = new PDF(params);
		if (!csv.createCSV(path) || !pdf.createPDF(path)) {
			return true;
		} else {
			return false;
		}
	}
}
