package de.dhbw.stress_yourself.outcome;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import de.dhbw.stress_yourself.params.ModuleInformation;
import de.dhbw.stress_yourself.params.Parameter;

public class CSV {
	
	private LinkedList<ModuleInformation> configuration;

	public CSV(Parameter params) {
		configuration = params.getConfiguration();
	}

	public boolean createCSV(String path) {
		try {
			FileWriter writer = new FileWriter(path + "outcome.csv");

			writer.append("ModuleName;ModuleArea;Points\n");

			for (int i = 0; i < configuration.size(); i++) {
				ModuleInformation module = configuration.get(i);
				writer.append(module.getName() + ";" + module.getArea() + ";"
						+ module.getPoints() + "\n");
			}

			writer.flush();
			writer.close();

			return true;
		} catch (IOException e) {
			System.err.println("Could not create the csv " + e);
		}
		return false;
	}

}
