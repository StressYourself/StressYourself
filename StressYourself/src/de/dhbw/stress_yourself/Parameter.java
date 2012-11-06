package de.dhbw.stress_yourself;

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
 * @author LukasBuchert <email>
 */
public class Parameter {

	public Parameter() {
		readXML();
	}

	private final String pathToJar = "../stress_yourself_modules.jar";
	private final String packageName = "de.dhbw.stress_yourself.modules";
	private final String filename = "config/configuration.xml";

	private LinkedList<ModuleInformation> configuration = new LinkedList<ModuleInformation>();

	private LinkedList<ModuleInformation> availableModules = new LinkedList<ModuleInformation>();

	private int difficulty;

	private boolean checkStatus = false;

	public String getPathToJar() {
		return pathToJar;
	}

	public String getPackageName() {
		return packageName;
	}

	/**
	 * used by Admin, MainApplication and Outcome
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<ModuleInformation> getConfiguration() {
		check();
		return (LinkedList<ModuleInformation>) this.configuration.clone();
	}

	/**
	 * used by Admin
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<ModuleInformation> getAvailableModules() {
		return (LinkedList<ModuleInformation>) this.availableModules.clone();
	}

	/**
	 * used by MainApplication
	 */
	public void addModuleInformation(String name,String classname, String area,
			String description) {
		availableModules
				.addLast(new ModuleInformation(name,classname, area, description));
	}

	public void addModuleInformation(ModuleInformation mi) {
		availableModules.addLast(mi);
	}

	/**
	 * used by Admin for storing the new configuration
	 */
	public void overwriteConfiguration(
			LinkedList<ModuleInformation> configuration, int difficulty) {
		this.configuration = configuration;
		this.difficulty = difficulty;
	}

	/**
	 * used by MainApplication to store the points
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
	 * used by MainApplication when Admin is closed
	 */
	public void saveChangesInXML() {
		resetXML();
		for (int i = 0; i < configuration.size(); i++) {
			ModuleInformation tmp = configuration.get(i);
			addModuleXML(tmp.getName(), tmp.getTime());
		}
	}

	// some private functions for the implementation

	private void check() {
		if (!checkStatus) {
			boolean exists;
			for (int i = 0; i < configuration.size(); i++) {
				exists = false;
				for (int j = 0; j < availableModules.size(); j++) {
					if (configuration.get(i).equals(availableModules.get(j))) {
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

	// methods for Interface XML
	private void resetXML() {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document oldList = builder.build(filename);

			oldList.removeContent(0);
			Element configElement = new Element("configuration");
			Element diffElement = new Element("difficulty");
			diffElement.addContent(Integer.toString(difficulty));
			configElement.addContent(diffElement);
			oldList.addContent(configElement);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(oldList, new FileWriter(filename));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (JDOMException jdome) {
			jdome.printStackTrace();
		}
	}

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

	private void readXML() {

		Document list = null;
		File file = new File(filename);
		try {

			SAXBuilder builder = new SAXBuilder();
			list = builder.build(file);

			List<Element> moduleList = list.getRootElement().getChildren();

			difficulty = Integer.valueOf(moduleList.get(0).getValue());

			for (int i = 1; i < moduleList.size(); i++) {

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