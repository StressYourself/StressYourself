package de.dhbw.stress_yourself;


import javax.swing.*;

/**
 * need this imports for createMD5()
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//Beschreibung bei Modulen anzeigen?? Tooltip?
//bekomme 2 LinkedLists von Userdata mit 



public class Admin {
	
	public static JPanel aPanel = new JPanel();
	
	/**
	 * creates a JPanel
	 * the content is: Usermanagement / Testmanagement
	 * @return
	 */
	public JPanel loadAdminGUI() {
		aPanel.setBounds(10, 25, 500, 350);
		return aPanel;
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
