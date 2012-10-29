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

public class UserData {

	private final String filename = "config/userdata.xml";
	private LinkedList<User> users = new LinkedList<User>();
	private String currentUser;

	public UserData() {
		readXML();
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
		// user couldn't delete himself
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
		for (int i = 0; i < users.size(); i++) {
			User tmp = users.get(i);
			addUserXML(tmp.getUserName(), tmp.getUserPassword(),
					tmp.getUserType());
		}

	}

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
