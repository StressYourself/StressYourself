package de.dhbw.stress_yourself;


import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


//Beschreibung bei Modulen anzeigen?? Tooltip?
//bekomme 2 LinkedLists von Userdata mit 



public class Admin {
	
	private JPanel pnlUserManagement = new JPanel();
	private JPanel pnlTestManagement = new JPanel();
	
	private Color backgroundColor; 
	
	
	private final int COMPONENTHEIGHT = 20;
	private final int TEXTFIELDWIDTH = 170;
	private final int LABELWIDTH = 100;
	private final int BUTTONWIDTH = 150;
	
	
	private JButton btnCreateUser = new JButton("Benutzer anlegen");
	private JButton btnDeleteUser = new JButton("Benutzer lšschen");
	private JButton btnChangePassword = new JButton("Passwort Šndern");
	private JButton btnActivateModule = new JButton("<< Modul aktivieren");
	private JButton btnInactivateModule = new JButton("Modul deaktivieren >>");
	private JButton btnModuleUp = new JButton("Module vorher");
	private JButton btnModuleDown = new JButton("Module nachher");
	
	private JPasswordField pfPassword = new JPasswordField();
	private JTextField tfUsername = new JTextField();
	
	private JList<String> activeModules;
	private JList<String> availableModules;
	
	private JScrollPane spActiveModules;
	private JScrollPane spAvailableModules;
	
	private JLabel lblUserManagement = new JLabel("Benutzerverwaltung:");
	private JLabel lblTestManagement = new JLabel("Testeinstellungen:");
	private JLabel lblUsername = new JLabel("Benutzername:");
	private JLabel lblPassword = new JLabel("Passwort:");
	

	
	public static JPanel aPanel;


	/**
	 * creates a JPanel
	 * the content is: Usermanagement / Testmanagement
	 * @return - JPanel
	 */
	public JPanel loadAdminGUI(LinkedList<String> classes){
		aPanel = new JPanel();
		aPanel.setLayout(null);
		aPanel.setBounds(0,0,900, 400);
		
		backgroundColor = new Color(aPanel.getBackground().getRed()-7,
				  aPanel.getBackground().getGreen()-7,
				  aPanel.getBackground().getBlue()-7);
		
		createUserManagementPanel();
		createTestManagementPanel(classes);
		
		lblUserManagement.setBounds(0, 0, 150, COMPONENTHEIGHT);
		lblTestManagement.setBounds(310, 0, 150, COMPONENTHEIGHT);
		
		aPanel.add(lblUserManagement);
		aPanel.add(pnlUserManagement);
		
		aPanel.add(lblTestManagement);
		aPanel.add(pnlTestManagement);
		return aPanel;
	}
	
	private void createUserManagementPanel(){
		pnlUserManagement.setLayout(null);
		pnlUserManagement.setBounds(0, 20, 300, 150);
		
		pnlUserManagement.setBackground(backgroundColor);
		
		lblUsername.setBounds(5, 5, LABELWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(lblUsername);
		
		tfUsername.setBounds(110, 5, TEXTFIELDWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(tfUsername);
		
		lblPassword.setBounds(5, 30, LABELWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(lblPassword);
		
		pfPassword.setBounds(110, 30, TEXTFIELDWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(pfPassword);
		
		btnCreateUser.setBounds(75, 60, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(btnCreateUser);
		
		btnChangePassword.setBounds(75, 85, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(btnChangePassword);
		
		btnDeleteUser.setBounds(75, 110, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(btnDeleteUser);
	
	}
	
	private void createTestManagementPanel(LinkedList<String> classes){
		pnlTestManagement.setLayout(null);
		pnlTestManagement.setBounds(310, 20, 580, 350);
		
		pnlTestManagement.setBackground(backgroundColor);
		
		btnActivateModule.setBounds(215, 40, BUTTONWIDTH, COMPONENTHEIGHT);
		
		pnlTestManagement.add(btnActivateModule);
		
		btnInactivateModule.setBounds(215, 80, BUTTONWIDTH, COMPONENTHEIGHT);
		
		pnlTestManagement.add(btnInactivateModule);
		
		
		String[] mydata = new String[100];
		String[] mydata1 = new String[100];
		for (int i=0;i<100;i++) {
			mydata[i] = ""+i;
			mydata1[i] = ""+(100-i);
		}
		activeModules = new JList<String>(mydata);
		
		spActiveModules = new JScrollPane(activeModules);
		spActiveModules.setBounds(5, 20, 200, 300);
		
		pnlTestManagement.add(spActiveModules);
		
		
		availableModules = new JList<String>(mydata1);
		
		spAvailableModules = new JScrollPane(availableModules);
		spAvailableModules.setBounds(375, 20, 200, 300);
		
		pnlTestManagement.add(spAvailableModules);
		
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

}
