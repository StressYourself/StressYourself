package de.dhbw.stress_yourself;

import java.awt.Color;
import java.text.Format;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;



//Beschreibung bei Modulen anzeigen?? Tooltip?
//bekomme 2 LinkedLists von Userdata mit 


/**
 * 
 * @author Florian Albert <floria-albert@gmx.de>
 */

public class Admin {

	private JPanel pnlUserManagement = new JPanel();
	private JPanel pnlTestManagement = new JPanel();
	
	private Color backgroundColor; 
	
	
	private final int COMPONENTHEIGHT = 20;
	private final int TEXTFIELDWIDTH = 170;
	private final int LABELWIDTH = 100;
	private final int BUTTONWIDTH = 150;
	
	
	private JButton btnCreateUser = new JButton("Create User");
	private JButton btnDeleteUser = new JButton("Delete User");
	private JButton btnChangePassword = new JButton("Change Password");
	private JButton btnActivateModule = new JButton("<< Activate Modul");
	private JButton btnInactivateModule = new JButton("Deactivate Modul >>");
	private JButton btnModuleUp = new JButton("Module Prev");
	private JButton btnModuleDown = new JButton("Module Back");
	
	private JPasswordField pfPassword = new JPasswordField();
	private JTextField tfUsername = new JTextField();
	private JFormattedTextField tfTime = new JFormattedTextField();
	
	
	private JTable activeModules;
	private JList<String> availableModules;
	
	private JScrollPane spActiveModules;
	private JScrollPane spAvailableModules;
	
	private JLabel lblTestManagement = new JLabel("Testconfiguration:");
	private JLabel lblUserManagement = new JLabel("User Management:");
	private JLabel lblUsername = new JLabel("User:");
	private JLabel lblPassword = new JLabel("Password:");
	
	
	private UserData users;
	private Parameter params;
	
	public static JPanel aPanel;	
	
	public Admin(UserData users, Parameter params){
		this.users = users;
		this.params = params;
	}

	/**
	 * creates a JPanel
	 * the content is: Usermanagement / Testmanagement
	 * @return - JPanel
	 */

	public JPanel getAdminPanel(){

		aPanel = new JPanel();
		aPanel.setLayout(null);
		aPanel.setBounds(0,0,900, 400);
		
		backgroundColor = new Color(aPanel.getBackground().getRed()-7,
				  aPanel.getBackground().getGreen()-7,
				  aPanel.getBackground().getBlue()-7);
		
		createUserManagementPanel();
		createTestManagementPanel();
		
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
	
	private void createTestManagementPanel(){
		pnlTestManagement.setLayout(null);
		pnlTestManagement.setBounds(310, 20, 580, 350);
		pnlTestManagement.setBackground(backgroundColor);
		
		btnActivateModule.setBounds(215, 40, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(btnActivateModule);
		
		tfTime.setBounds(260, 100, 60, 20);
		tfTime.setColumns(3);
		
		pnlTestManagement.add(tfTime);
		
		btnInactivateModule.setBounds(215, 160, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(btnInactivateModule);
		
		btnModuleUp.setBounds(215, 200, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(btnModuleUp);
		
		btnModuleDown.setBounds(215, 240, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(btnModuleDown);
		
		
		
		
		
		activeModules = new JTable();
		activeModules.setBounds(5, 20, 200, 300);
		activeModules.addColumn(new TableColumn(1));
		
		
		DefaultListModel<String> test = new DefaultListModel<String>();//
		
		
		
		String[] mydata = new String[100];
		String[] mydata1 = new String[100];
		for (int i=0;i<100;i++) {
			mydata[i] = ""+i;
			mydata1[i] = ""+(100-i);
		}
		
		for (int i = 0; i < params.getAvailableModules().size();i++) {
			System.out.println(params.getAvailableModules().get(i).getName());
			System.out.println(""+params.getAvailableModules().size());
			test.add(i, params.getAvailableModules().get(i).getName());//
		}
		
		pnlTestManagement.add(activeModules);
		
		
		availableModules = new JList<String>(test);
		
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
