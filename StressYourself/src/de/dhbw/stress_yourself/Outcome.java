package de.dhbw.stress_yourself;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.params.Parameter;

public class Outcome {

	private Parameter params;

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
		String path = params.getOutcomePath();
		if (!createCSV(path) || !createPDF(path)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean createCSV(String path) {
		return false;
	}

	public boolean createPDF(String path) {
		return false;
	}

}
