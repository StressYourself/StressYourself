package de.dhbw.stress_yourself;

import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.Document;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Admin {
	
	public boolean loadAdminGUI(){
		return false;
	}
	
	public boolean createUser(String username, String password){
		return false;
	}
	
	public boolean deleteUser(String username){
		return false;
	}
	
	public boolean changePassword(String username, String newpassword){
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
