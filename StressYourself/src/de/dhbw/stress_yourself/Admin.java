package de.dhbw.stress_yourself;


import javax.swing.*;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
/**
 * need this imports for createMD5()
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//Beschreibung bei Modulen anzeigen?? Tooltip?
//bekomme 2 LinkedLists von Userdata mit 



public class Admin {
	
	private Container userManagementContainer;
	private JButton createUser;
	private JButton deleteUser;
	private JButton changePassword;
	

	
	public static JPanel aPanel;


	/**
	 * creates a JPanel
	 * the content is: Usermanagement / Testmanagement
	 * @return
	 */
	public JPanel loadAdminGUI(JFrame frame){
		aPanel = new JPanel();
		aPanel.setBackground(new Color(100,60,90));
		aPanel.setBounds(frame.getContentPane().getBounds());
		createUserManagement();
		aPanel.add(userManagementContainer);
		return aPanel;
	}
	
	private void createUserManagement(){
		userManagementContainer = new Container();
		userManagementContainer.setBounds(10, 10, 350, 300);
		userManagementContainer.setBackground(new Color(100,100,50));
				
		createUser = new JButton("Benutzer anlegen");
		createUser.setSize(150, 20);
		createUser.setLocation(0, 0);
		userManagementContainer.add(createUser);
		
		deleteUser = new JButton("Benutzer lšschen");
		deleteUser.setSize(150, 20);
		deleteUser.setLocation(0, 25);
		userManagementContainer.add(deleteUser);
		
		changePassword = new JButton("Passwort Šndern");
		changePassword.setSize(150, 20);
		changePassword.setLocation(0, 50);
		userManagementContainer.add(changePassword);
	
	}
	
	/**
	 * 
	 * @param username - String with the username which should be created
	 * @param password - String with the password of the user
	 * @param type - depends on weather he is an admin(a) or an user(u) 
	 * @return true if user was created / false is some param is not ok
	 */
	public boolean createUser(String username, String password, String type) {
		//rufe saveUser() aus UserData auf
		
		
		// fragen ob linkedlist von usern static sein kann
		// damit ich ein objekt erzeugen kann und in selbe liste schreibe
		return false;
	}
	
	
	/**
	 * 
	 * @param username - String with the username of the user who should be deleted
	 * @return true if the user is marked as deleted. Will be saved after regular closing 
	 * 			of the programm
	 */
	public boolean deleteUser(String username) {
		// rufe deleteUser() aus UserData auf
		
		
		//siehe createUser()
		return false;
	}
	
	
	/**
	 * 
	 * @param username - String with the username from whom u wannt to change the password
	 * @param newpassword - String with the new password of the named user
	 * @return true if the password was changed !the password isn't written in xml yet!
	 * 			if the programm chrashes the changes are lost!
	 */
	public boolean changePassword(String username, String newpassword) {
		// changePassword(username, password) aus UserData
		
		// siehe createUser()
		return false;
	}
	
	/**
	 * 
	 * @param password - Passwordstring with the userpassword
	 * @return the passwordstring transformed in MD5
	 */
	private String createMD5(String password){
		String hashword = null;
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);
		} catch (NoSuchAlgorithmException nsae) {
			
		}
		return hashword;
	}

}
