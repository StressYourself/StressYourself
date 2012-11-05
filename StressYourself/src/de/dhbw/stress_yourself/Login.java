package de.dhbw.stress_yourself;

import javax.swing.JPanel;

/**
 * 
 * @author Christoph Schollmeyer <email>
 */
public class Login {
	private UserData users;
	
	public Login(UserData users){
		this.users = users;
	}
	
	public JPanel getLoginPanel() {
		JPanel panel = new JPanel();
		return panel;
	}

	public int login(String username, String password) {
		return 0;
	}

}
