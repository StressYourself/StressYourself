package de.dhbw.stress_yourself;

import static org.junit.Assert.*;

import org.junit.Test;

import de.dhbw.stress_yourself.UserData;

public class UserDataTest {

	UserData usr = new UserData();

	@Test
	public void test() {
		usr.saveUser("ABCD1234", "12345678", "a");
		usr.saveUser("ABCD1233", "12345678", "f");
		usr.changePassword("ABCD1234", "87654321");
		if (usr.existsUser("ABCD1234", "87654321") != 0) {
			System.out.println("Ja er lebt noch");
		}
		System.out.println(usr.getCurrentUserName());
		System.out.println(usr.getCurrentUserType());
		usr.saveUser("DCBA1234", "abcdefgh", "u");
		usr.deleteUser("ABCD1234");
		usr.deleteUser("DCBA1234");
		usr.deleteUser("DCBA1234");
		usr.deleteUser("DCBA1234");
		usr.deleteUser("Blablabal");
		usr.deleteUser(" ");
		String[][] testarray = usr.getUsers();
		for (int i = 0; i < testarray.length; i++) {
			System.out.println(testarray[i][0] + " " + testarray[i][1]);
		}
		usr.saveChangesInXML();
	}

}
