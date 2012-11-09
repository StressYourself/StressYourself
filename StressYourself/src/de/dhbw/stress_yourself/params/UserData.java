package de.dhbw.stress_yourself.params;

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
 * @author LukasBuchert <Lukas.Buchert@gmx.de>
 */
public class UserData {

	private final String filename = "config/userdata.xml";
	private LinkedList<User> users = new LinkedList<User>();
	private User currentUser;

	public enum UserType {
		NOT, USER, ADMIN;
	}

	public UserData() {
		readXML();
	}

	public String getCurrentUserName() {
		return this.currentUser.getUserName();
	}

	/**
	 * get current user type as Integer
	 * 
	 * @return 0 - NOT 1 - USER 2 - ADMIN
	 */
	public int getCurrentUserTypeOrdinal() {
		return this.currentUser.getUserTypeOrdinal();
	}

	public UserType getCurrentUserType() {
		return this.currentUser.getUserType();
	}

	/**
	 * checks if the user exists in this user - password constellation
	 * 
	 * @param username
	 *            name of user
	 * @param password
	 *            users password
	 * @return NOT - if it doesn't exist USER - if normal user ADMIN - if admin
	 */
	public UserType existsUser(String username, String password) {
		UserType user = UserType.NOT;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(username, password)) {
				currentUser = users.get(i);
				user = users.get(i).getUserType();
				break;
			}
		}
		return user;
	}

	/**
	 * saves a new user
	 * 
	 * @param username
	 *            name of user
	 * @param password
	 *            password of user
	 * @param type
	 *            user type ADMIN or USER
	 * @return false - if user already exists / couldn't save
	 */
	public boolean saveUser(String username, String password, UserType type) {
		boolean back = false;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equalsLightly(username)) {
				back = true;
				break;
			}
		}
		if (!back) {
			users.addLast(new User(username, createMD5(password), type));
			back = true;
		} else {
			back = false;

		}
		return back;
	}

	/**
	 * changes the password of user with username
	 * 
	 * @param username
	 *            name of user
	 * @param newPassword
	 *            new password of user
	 * @return false - if user doesn't exist / password isn't changed
	 */
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

	/**
	 * deletes the user with this name
	 * 
	 * @param username
	 *            name of the user to delete
	 * @return false - if user try to delete himself / user doesn't exist / user
	 *         isn't deleted
	 */
	public boolean deleteUser(String username) {
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

	/**
	 * get all users with name and type
	 * 
	 * @return [i][j] i = nth user, j = 0 user name, j = 1 user type as Integer
	 */
	public String[][] getUsers() {
		String[][] returnArray = new String[users.size()][2];
		for (int i = 0; i < users.size(); i++) {
			User tmp = users.get(i);
			returnArray[i][0] = tmp.getUserName();
			returnArray[i][1] = Integer.toString(tmp.getUserTypeOrdinal());
		}
		return returnArray;
	}

	/**
	 * saves the user list users in the xml data
	 */
	public void saveChangesInXML() {
		resetXML();
		for (int i = 0; i < users.size(); i++) {
			User tmp = users.get(i);
			addUserXML(tmp.getUserName(), tmp.getUserPassword(),
					tmp.getUserTypeOrdinal());
		}

	}

	/**
	 * Class that store information about specific user object
	 * 
	 * @author LukasBuchert <Lukas.Buchert@gmx.de>
	 * 
	 */
	private class User {
		private String name;
		private String password;
		private UserType type;

		public User(String name, String password, UserType type) {
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

		public int getUserTypeOrdinal() {
			return this.type.ordinal();
		}

		public UserType getUserType() {
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

	/**
	 * resets the XML data
	 */
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

	/**
	 * adds a user in the xml
	 * 
	 * @param name
	 *            name of user that should be added in xml
	 * @param password
	 *            password of user
	 * @param type
	 *            type of user as Integer 1 - USER 2 - ADMIN
	 */
	private void addUserXML(String name, String password, int type) {
		Element nameElement = new Element("name");
		Element passwordElement = new Element("password");
		Element typeElement = new Element("type");
		nameElement.addContent(name);
		passwordElement.addContent(password);
		typeElement.addContent(Integer.toString(type));
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

	/**
	 * read the userdata.xml into the user list users
	 */
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
						UserType.values()[Integer.valueOf(userList.get(i)
								.getChild("type").getValue())]);

				users.addLast(tmp);

			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * creates an MD5 hash of password
	 * 
	 * @author FlorianAlbert <floria-albert@gmx.de>
	 * @param password
	 *            that should be hashed
	 * @return hashed password
	 */
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
