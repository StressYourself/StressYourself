package de.dhbw.stress_yourself;


import javax.swing.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//Beschreibung bei Modulen anzeigen?? Tooltip?
//bekomme 2 LinkedLists von Userdata mit 



public class Admin {
	
	public static JPanel aPanel = new JPanel();

	public JPanel loadAdminGUI() {
		aPanel.setBounds(10, 25, 500, 350);
		return aPanel;
	}

	public boolean createUser(String username, String password, String type) {
		//rufe saveUser() aus UserData auf
		return false;
	}

	public boolean deleteUser(String username) {
		// rufe deleteUser() aus UserData auf
		return false;
	}

	public boolean changePassword(String username, String newpassword) {
		// changePassword(username, password) aus UserData
		return false;
	}
	
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
