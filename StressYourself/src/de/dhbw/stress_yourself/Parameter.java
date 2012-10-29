package de.dhbw.stress_yourself;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import java.io.FileWriter;
import org.jdom2.output.Format;
import java.util.HashMap;

public final class Parameter {

	/** private class attribute, only one instance of the class is created. */
	private static final Parameter INSTANCE = new Parameter();

	/** constructor is private, should not be instanced form the outside. */
	private Parameter() {
	}

	/** static method, delivers the only instance of the class. */
	public static Parameter getInstance() {
		start();
		return INSTANCE;
	}

	/**
	 * runs the first time getInstance is called
	 */
	private synchronized static void start() {
		if (!started) {
			INSTANCE.readXML();
		}
	}

	// variables of the singleton, private and not static
	

	private static final String filename = "config/userdata.xml";
	private final String pathToJar = "../stress_yourself_modules.jar";
	private final String packageName = "de.dhbw.stress_yourself.modules";
	private String parameters[][];
	private static LinkedList<User> users = new LinkedList<User>();
	private HashMap<String[], Float> result;
	private int difficulty;
	private String currentUser;
	private String[][] existingModules;
	private static boolean started = false;

	/*
	 * existingModules i[n] modul elementes j[0] modulname j[1]
	 * URL j[2] area
	 */
	// public getter and setter
	
	
	public String getPathToJar(){
		return pathToJar;
	}
	
	public String getPackageName(){
		return packageName;
	}
	
	/**
	 * 
	 * @return parameters i[n] sequence of modules, in j[0] module name, in j[1]
	 *         time in seconds
	 */
	public String[][] getParameters() {
		return parameters;
	}

	/**
	 * 
	 * @return difficulty 0-"Beginner" 1-"Intermediate" 2-"Expert"
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * 
	 * @param parameters
	 *            i[n] sequence of modules, in j[0] module name, in j[1] time in
	 *            seconds
	 * @param difficulty
	 *            0-"Beginner" 1-"Intermediate" 2-"Expert"
	 * @return
	 */
	public boolean setConfig(String[][] parameters, int difficulty) {
		this.parameters = parameters;
		this.difficulty = difficulty;
		return false;
	}

	public String getCurrentUser() {
		return this.currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	// methods interface XML userdata

	/**
	 * 
	 * @param username
	 *            name of user
	 * @param password
	 *            users password
	 * @param type
	 *            a - for admin, u - for user
	 * @return false - if sum of all parameters dosen't exist true - if
	 *         parameter matches
	 */
	public boolean existsUser(String username, String password, String type) {
		boolean back = false;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(new User(username, password, type))) {
				back = true;
				break;
			}
		}

		return back;
	}

	/**
	 * @param type
	 *            a - for admin, u - for user
	 */
	public boolean saveUser(String username, String password, String type) {
		boolean back = false;
		if (type == "a" || type == "u") {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).equalsLightly(username)) {
					back = true;
					break;
				}
			}
			if (!back) {
				users.addLast(new User(username, password, type));
			} else {
				back = false;
			}

		}
		return back;
	}

	public boolean changePassword(String username, String password,
			String type, String newPassword) {
		boolean back = false;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(new User(username, password, type))) {
				users.get(i).changeUserPassword(newPassword);
				back = true;
				break;
			}
		}

		return back;
	}

	public boolean deleteUser(String username) {
		// userer couldn't delete himself
		boolean back = false;
		if (username != currentUser) {

			for (int i = 0; i < users.size(); i++) {

				if (users.get(i).equalsLightly(username) == true) {
					users.remove(i);
					back = true;
					break;
				}
			}

		}
		return back;

	}

	public String[][] getUsers() {
		String[][] returnArray = new String[users.size()][3];
		for (int i = 0; i < users.size(); i++) {
			User tmp = users.get(i);
			returnArray[i][0] = tmp.name;
			returnArray[i][1] = tmp.password;
			returnArray[i][2] = tmp.type;
		}
		return returnArray;
	}

	/**
	 * called while leaving the admin-page saves all Changes in XML
	 */
	public void saveChangesInXML() {
		resetXML();
		while (!users.isEmpty()) {
			User tmp = users.removeFirst();
			addUserXML(tmp.getUserName(), tmp.getUserPassword(),
					tmp.getUserType());
		}
		readXML();

	}

	// methodes for set and get result

	public void saveResult(String moduleName, String area, Float percent) {
		String[] tmpArray = { moduleName, area };
		result.put(tmpArray, percent);
	}

	/**
	 * 
	 * @return [module name][area], percentage
	 */
	public HashMap<String[], Float> getResult() {
		return result;
	}

	// some private functions for the implementation

	private class User {
		private String name;
		private String password;
		private String type;

		public User(String name, String password, String type) {
			this.name = name;
			this.password = password;
			this.type = type;
		}

		public void changeUserPassword(String newPassword) {
			this.password = newPassword;
		}

		public String getUserName() {
			return this.name;
		}

		public String getUserPassword() {
			return this.password;
		}

		public String getUserType() {
			return this.type;
		}

		public boolean equals(User user) {
			return (this.name.equals(user.name)
					&& this.password.equals(user.password) && this.type
						.equals(user.type));
		}

		public boolean equalsLightly(String username) {

			return this.name.equals(username);
		}
	}

	// methods for Interface XML 
	private synchronized void resetXML() {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document oldList = builder.build(filename);

			oldList.removeContent(0);
			Element list = new Element("list");
			oldList.addContent(list);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(oldList, new FileWriter(filename));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (JDOMException jdome) {
			jdome.printStackTrace();
		}
	}

	private synchronized void addUserXML(String name, String password,
			String type) {
		Element nameElement = new Element("name");
		Element passwordElement = new Element("password");
		Element typeElement = new Element("type");
		nameElement.addContent(name);
		passwordElement.addContent(password);
		typeElement.addContent(type);
		Element user = new Element("user");
		user.addContent(nameElement);
		user.addContent(passwordElement);
		user.addContent(typeElement);

		try {
			SAXBuilder builder = new SAXBuilder();
			Document List = builder.build(filename);
			List.getRootElement().addContent(user);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(List, new FileWriter(filename));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (JDOMException jdome) {
			jdome.printStackTrace();
		}

	}

	private synchronized void readXML() {

		Document list = null;
		File file = new File(filename);
		try {

			SAXBuilder builder = new SAXBuilder();
			list = builder.build(file);

			List<Element> userList = list.getRootElement().getChildren();

			for (int i = 0; i < userList.size(); i++) {

				User tmp = new User(
						userList.get(i).getChild("name").getValue(), userList
								.get(i).getChild("password").getValue(),
						userList.get(i).getChild("type").getValue());

				users.addLast(tmp);

			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	

}