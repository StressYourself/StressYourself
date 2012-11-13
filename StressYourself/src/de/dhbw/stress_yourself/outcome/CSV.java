package de.dhbw.stress_yourself.outcome;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import de.dhbw.stress_yourself.params.ModuleInformation;
import de.dhbw.stress_yourself.params.Parameter;
import de.dhbw.stress_yourself.params.UserData;

public class CSV {
	
	private LinkedList<ModuleInformation> configuration;
	private UserData users;

	public CSV(Parameter params, UserData users) {
		configuration = params.getConfiguration();
		this.users = users;
	}

	public boolean createCSV(String path) {
		try {
			FileWriter writer = new FileWriter(path + users.getCurrentUserName() + "_data.csv");

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
