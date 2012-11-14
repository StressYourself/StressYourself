package de.dhbw.stress_yourself.params;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * class manages the information about the modules
 * 
 * @author LukasBuchert <Lukas.Buchert@gmx.de>
 */
public class Parameter {

	public Parameter() {
		readXML();
	}

	private final String pathToJar = "stress_yourself_modules.jar";
	private final String packageName = "de.dhbw.stress_yourself.modules";
	private final String filename = "config/configuration.xml";
	private final String outcomePath = "outcome/";

	private LinkedList<ModuleInformation> configuration = new LinkedList<ModuleInformation>();

	private LinkedList<ModuleInformation> availableModules = new LinkedList<ModuleInformation>();

	private difficultyType difficulty;
	private boolean annoyanceSetting;

	private boolean checkStatus = false;

	public boolean getAnnoyanceSetting() {
		return annoyanceSetting;
	}

	public void setAnnoyanceSetting(boolean annoyanceSetting) {
		this.annoyanceSetting = annoyanceSetting;
	}

	public enum difficultyType {
		EASY, MEDIUM, HARD
	}

	public String getPathToJar() {
		return pathToJar;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getOutcomePath() {
		return outcomePath;
	}

	public int getDifficultyOrdinal() {
		return difficulty.ordinal();
	}

	public difficultyType getDifficulty() {
		return difficulty;
	}

	/**
	 * returns the current configuration
	 * 
	 * @return configuration list
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<ModuleInformation> getConfiguration() {
		check();
		return (LinkedList<ModuleInformation>) this.configuration.clone();
	}

	/**
	 * returns list with all available modules
	 * 
	 * @return available module list
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<ModuleInformation> getAvailableModules() {
		return (LinkedList<ModuleInformation>) this.availableModules.clone();
	}

	/**
	 * add a new available module to the list
	 * 
	 * @param name
	 *            name of module
	 * @param classname
	 *            classname of module
	 * @param area
	 *            area of module
	 * @param description
	 *            description of module
	 */
	public void addModuleInformation(String name, String classname,
			String area, String description) {
		availableModules.addLast(new ModuleInformation(name, classname, area,
				description));
	}

	/**
	 * adds a moduleInformation object to the available module list
	 * 
	 * @param mi
	 *            ModuleInformation object that should be added
	 */
	public void addModuleInformation(ModuleInformation mi) {
		availableModules.addLast(mi);
	}

	/**
	 * overwrites the whole configuration
	 * 
	 * @param configuration
	 *            new configuration list
	 * @param difficulty
	 *            new difficulty
	 */
	public void overwriteConfiguration(
			LinkedList<ModuleInformation> configuration,
			difficultyType difficulty, boolean annoyanceSetting) {
		this.configuration = configuration;
		this.difficulty = difficulty;
		this.annoyanceSetting = annoyanceSetting;
	}

	/**
	 * store point for the module with this name
	 * 
	 * @param moduleName
	 *            name of module
	 * @param points
	 *            point of test between 0-100
	 */
	public void addResult(String moduleName, int points) {
		for (int i = 0; i < configuration.size(); i++) {
			if (configuration.get(i).equals(moduleName)) {
				configuration.get(i).setPoints(points);
				break;
			}
		}
	}

	/**
	 * saves the new configuration in the xml data, with order, time and module
	 * names
	 */
	public void saveChangesInXML() {
		resetXML();
		for (int i = 0; i < configuration.size(); i++) {
			ModuleInformation tmp = configuration.get(i);
			addModuleXML(tmp.getName(), tmp.getTime());
		}
	}

	/**
	 * check's and synchronizes the configuration list with the availableModules
	 * list after the check, the configuration is a subset of avialableModules
	 * list and contains a synchronized data
	 */
	private void check() {
		if (!checkStatus) {
			boolean exists;
			for (int i = 0; i < configuration.size(); i++) {
				exists = false;
				for (int j = 0; j < availableModules.size(); j++) {
					if (configuration.get(i).equals(availableModules.get(j))) {
						configuration.get(i).synchronize(
								availableModules.get(j));
						exists = true;
						break;
					}
				}
				if (!exists) {
					configuration.remove(i);
					i--;
				}
			}
			checkStatus = true;
		}
	}

	/**
	 * resets the xml data
	 */
	private void resetXML() {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document oldList = builder.build(filename);

			oldList.removeContent(0);
			Element configElement = new Element("configuration");
			Element diffElement = new Element("difficulty");
			Element annoyanceElement = new Element("annoyance");
			diffElement.addContent(Integer.toString(difficulty.ordinal()));
			if (annoyanceSetting) {
				annoyanceElement.addContent("1");
			} else {
				annoyanceElement.addContent("0");
			}
			configElement.addContent(diffElement);
			configElement.addContent(annoyanceElement);
			oldList.addContent(configElement);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(oldList, new FileWriter(filename));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (JDOMException jdome) {
			jdome.printStackTrace();
		}
	}

	/**
	 * adds new module to the xml data
	 * 
	 * @param name
	 *            name of module
	 * @param time
	 *            time of last configuration
	 */
	private void addModuleXML(String name, int time) {
		Element nameElement = new Element("name");
		Element timeElement = new Element("time");
		nameElement.addContent(name);
		timeElement.addContent(Integer.toString(time));
		Element module = new Element("module");
		module.addContent(nameElement);
		module.addContent(timeElement);

		try {
			SAXBuilder builder = new SAXBuilder();
			Document List = builder.build(filename);
			List.getRootElement().addContent(module);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(List, new FileWriter(filename));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (JDOMException jdome) {
			jdome.printStackTrace();
		}

	}

	/**
	 * reads the stored configuration into the configuration list
	 */
	private void readXML() {

		Document list = null;
		File file = new File(filename);
		try {

			SAXBuilder builder = new SAXBuilder();
			list = builder.build(file);

			List<Element> moduleList = list.getRootElement().getChildren();

			difficulty = difficultyType.values()[Integer.valueOf(moduleList
					.get(0).getValue())];
			if (moduleList.get(1).getValue().equals("1")){
				annoyanceSetting = true;
			}else{
				annoyanceSetting = false;
			}

			for (int i = 2; i < moduleList.size(); i++) {

				ModuleInformation tmp = new ModuleInformation(moduleList.get(i)
						.getChild("name").getValue(),
						Integer.valueOf(moduleList.get(i).getChild("time")
								.getValue()));

				configuration.addLast(tmp);

			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}