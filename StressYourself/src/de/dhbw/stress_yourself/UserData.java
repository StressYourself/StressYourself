package de.dhbw.stress_yourself;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

/**
 * class manages the user data
 * 
 * @author LukasBuchert
 */
public class UserData {

	private final String filename = "config/userdata.xml";
	private LinkedList<User> users = new LinkedList<User>();
	private User currentUser;

	public UserData() {
		readXML();
	}

	public String getCurrentUserName() {
		return this.currentUser.getUserName();
	}

	/**
	 * 
	 * @return a - for admin, u - for user
	 */
	public String getCurrentUserType() {
		return this.currentUser.getUserType();
	}

	/**
	 * 
	 * @param username
	 *            name of user
	 * @param password
	 *            users password
	 * @return false - if sum of all parameters dosen't exist true - if
	 *         parameter matches
	 */
	public int existsUser(String username, String password) {
		int result = 0;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(username, password)) {
				currentUser = users.get(i);
				String type = users.get(i).getUserType();
				if (type.equals("u")) {
					result = 1;
				} else if (type.equals("a")) {
					result = 2;
				}
				break;
			}
		}
		return result;
	}

	/**
	 * @param type
	 *            a - for admin, u - for user
	 */
	public boolean saveUser(String username, String password, String type) {
		boolean back = false;
		if (type.equals("a") || type.equals("u")) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).equalsLightly(username)) {
					back = true;
					break;
				}
			}
			if (!back) {
				users.addLast(new User(username, createMD5(password), type));
			} else {
				back = false;
			}

		}
		return back;
	}

	public boolean changePassword(String username, String newPassword) {
		boolean back = false;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equalsLightly(username)) {
				users.get(i).changeUserPassword(createMD5(newPassword));
				back = true;
				break;
			}
		}

		return back;
	}

	public boolean deleteUser(String username) {
		// user couldn't delete himself
		boolean back = false;
		if (currentUser != null) {
			if (!currentUser.equalsLightly(username)) {

				for (int i = 0; i < users.size(); i++) {

					if (users.get(i).equalsLightly(username)) {
						users.remove(i);
						back = true;
						break;
					}
				}
			}
		}
		return back;

	}

	public String[][] getUsers() {
		String[][] returnArray = new String[users.size()][2];
		for (int i = 0; i < users.size(); i++) {
			User tmp = users.get(i);
			returnArray[i][0] = tmp.name;
			returnArray[i][1] = tmp.type;
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

		public boolean equals(String username, String password) {

			return (this.name.equals(username) && this.password
					.equals(createMD5(password)));
		}

		public boolean equalsLightly(String username) {

			return this.name.equals(username);
		}
	}

	// methods for Interface XML
	private void resetXML() {
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

	private void addUserXML(String name, String password, String type) {
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

	private void readXML() {

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

	private String createMD5(String password) {
		String hashword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);
		} catch (NoSuchAlgorithmException nsae) {
		}
		return hashword;
	}
}
