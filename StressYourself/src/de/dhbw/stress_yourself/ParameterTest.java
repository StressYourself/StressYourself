package de.dhbw.stress_yourself;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParameterTest {

	public Parameter test;
	public String[][] testArray = { { "AAAAAAAA", "12345678", "a" },
			{ "BBBBBBBB", "12345678", "a" }, { "CCCCCCCC", "12345678", "u" },
			{ "DDDDDDDD", "12345678", "u" },{ "DDDDDDDD", "12345678", "u" } };

	@Test
	public void getInstance() {
		test = Parameter.getInstance();
		
	}

	/**
	 * Start for Test with userdata.xml looking like that: <?xml version="1.0"
	 * encoding="UTF-8"?> <list> </list>
	 */

	@Test
	public void test() {
		test = Parameter.getInstance();
		test.saveUser(testArray[0][0], testArray[0][1], testArray[0][2]);
		test.saveUser(testArray[1][0], testArray[1][1], testArray[1][2]);
		test.saveUser(testArray[2][0], testArray[2][1], testArray[2][2]);
		test.saveUser(testArray[3][0], testArray[3][1], testArray[3][2]);
		test.saveUser(testArray[4][0], testArray[4][1], testArray[4][2]);
		test.saveChangesInXML();
		
		test.changePassword(testArray[0][0], testArray[0][1], testArray[0][2],"NewPW123");
		
		assertEquals(true,test.existsUser(testArray[2][0], testArray[2][1], testArray[2][2]));

		String[][] testArray = test.getUsers();
		for (int i = 0; i < testArray.length; i++) {
			System.out.println("Name:" + testArray[i][0] + " Password: "
					+ testArray[i][1] + " Type: " + testArray[i][2]);

			
		test.deleteUser(testArray[0][0]);
		test.deleteUser(testArray[1][0]);
		test.deleteUser(testArray[2][0]);
		test.deleteUser(testArray[3][0]);
		}

	}

}
