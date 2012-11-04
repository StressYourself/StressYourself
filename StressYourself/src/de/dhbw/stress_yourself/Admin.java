package de.dhbw.stress_yourself;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 * @author Florian Albert <email>
 */
public class Admin {

	private JPanel pnlUserManagement = new JPanel();
	
	private JButton btnCreateUser = new JButton("Benutzer anlegen");
	private JButton btnDeleteUser = new JButton("Benutzer lšschen");
	private JButton btnChangePassword = new JButton("Passwort Šndern");
	
	private JPasswordField pfPassword = new JPasswordField();
	private JTextField tfUsername = new JTextField();
	
	private JLabel lblUserManagement = new JLabel("Benutzerverwaltung:");
	private JLabel lblUsername = new JLabel("Benutzername:");
	private JLabel lblPassword = new JLabel("Passwort:");
	
	
	private UserData users;
	private Parameter params;
	
	public Admin(UserData users, Parameter params){
		this.users = users;
		this.params = params;
	}
	
	
	public static JPanel aPanel;


	/**
	 * creates a JPanel
	 * the content is: Usermanagement / Testmanagement
	 * @return - JPanel
	 */
	public JPanel getAdminPanel(){
		aPanel = new JPanel();
		aPanel.setLayout(null);
		aPanel.setBounds(0,0,900, 400);
		
		createUserManagementPanel();
		
		lblUserManagement.setBounds(0, 0, 150, 20);
		aPanel.add(lblUserManagement);
		aPanel.add(pnlUserManagement);
		return aPanel;
	}
	
	private void createUserManagementPanel(){
		pnlUserManagement.setLayout(null);
		pnlUserManagement.setBounds(0, 20, 300, 150);
		
		Color bg = new Color(aPanel.getBackground().getRed()-7,aPanel.getBackground().getGreen()-7,aPanel.getBackground().getBlue()-7);
		pnlUserManagement.setBackground(bg);
		
		lblUsername.setBounds(5, 5, 100, 20);
		pnlUserManagement.add(lblUsername);
		
		tfUsername.setBounds(110, 5, 170, 20);
		pnlUserManagement.add(tfUsername);
		
		lblPassword.setBounds(5, 30, 100, 20);
		pnlUserManagement.add(lblPassword);
		
		pfPassword.setBounds(110, 30, 170, 20);
		pnlUserManagement.add(pfPassword);
		
		btnCreateUser.setBounds(75, 60, 150, 20);
		pnlUserManagement.add(btnCreateUser);
		
		btnChangePassword.setBounds(75, 85, 150, 20);
		pnlUserManagement.add(btnChangePassword);
		
		btnDeleteUser.setBounds(75, 110, 150, 20);
		pnlUserManagement.add(btnDeleteUser);
	
	}
	
	/**
	 * 
	 * @param username - String with the username which should be created
	 * @param password - String with the password of the user
	 * @param type - depends on weather he is an admin(a) or an user(u) 
	 * @return true if user was created / false is some param is not ok
	 */
	public boolean createUser(String username, String password, String type) {
		return users.saveUser(username, password, type);
	}
	
	
	/**
	 * 
	 * @param username - String with the username of the user who should be deleted
	 * @return true if the user is marked as deleted. Will be saved after regular closing 
	 * 			of the programm
	 */
	public boolean deleteUser(String username) {
		return users.deleteUser(username);
	}
	
	
	/**
	 * 
	 * @param username - String with the username from whom u wannt to change the password
	 * @param newpassword - String with the new password of the named user
	 * @return true if the password was changed !the password isn't written in xml yet!
	 * 			if the programm chrashes the changes are lost!
	 */
	public boolean changePassword(String username, String newPassword) {
		return users.changePassword(username, newPassword);
	}

}
