package de.dhbw.stress_yourself;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.params.ModuleInformation;
import de.dhbw.stress_yourself.params.Parameter;

public class Outcome {

	private Parameter params;
	private String path;
	private LinkedList<ModuleInformation> configuration;

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
		System.out.println("Create Outcome");
		path = params.getOutcomePath();
		configuration = params.getConfiguration();
		if (!createCSV(path) || !createPDF(path)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean createCSV(String path) {
		try {
			FileWriter writer = new FileWriter(path + "outcome.csv");
			
			writer.append("ModuleName;ModuleArea;Points\n");
			
			for(int i = 0; i < configuration.size(); i++){
				ModuleInformation module = configuration.get(i);
				writer.append(module.getName()+";"+module.getArea()+";"+module.getPoints()+"\n");
			}
			
			writer.flush();
			writer.close();
			
			return true;
		} catch (IOException e) {
			System.err.println("Could not create the csv " + e);
		}
		return false;
	}

	public boolean createPDF(String path) {
		return true;
	}

}
