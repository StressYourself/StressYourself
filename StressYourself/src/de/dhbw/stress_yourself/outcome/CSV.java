package de.dhbw.stress_yourself.outcome;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import de.dhbw.stress_yourself.params.ModuleInformation;
import de.dhbw.stress_yourself.params.Parameter;
import de.dhbw.stress_yourself.params.UserData;

/**
 * The CSV Class is used to create the outcome csv which contains all collected
 * data about the test.
 * 
 * @author Tobias Roeding <tobias@roeding.eu>
 */
public class CSV {

	private LinkedList<ModuleInformation> configuration;
	private UserData users;

	public CSV(Parameter params, UserData users) {
		configuration = params.getConfiguration();
		this.users = users;
	}

	/**
	 * Function to create the csv under the specified path
	 * 
	 * @param path
	 * 			Path in which the csv should be created
	 * @return boolean
	 * 			Returns true if the creation worked and false if not
	 */
	public boolean createCSV(String path) {
		try {
			FileWriter writer = new FileWriter(path
					+ users.getCurrentUserName() + "_data.csv");

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
