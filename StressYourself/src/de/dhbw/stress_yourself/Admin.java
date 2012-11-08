package de.dhbw.stress_yourself;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;



//Beschreibung bei Modulen anzeigen?? Tooltip?
//bekomme 2 LinkedLists von Userdata mit 


/**
 * Class Admin creates a JPanel with configuration components on it
 * 		- User management: 
 * 			- create / delete user 
 * 			- change password
 * 		- Test management:
 * 			- activate / deactivate modules
 * 			- change given time for modules
 * 			- change module order
 * 			- change difficulty of the test
 *
 * @author Florian Albert <floria-albert@gmx.de>
 */


public class Admin {
	
	/**
	 * class SetMaxText
	 * 		- with this class you are able to
	 * 		  set the maxlength of an JTextField / JPasswordField
	 * 		  in a very easy way.
	 *		- use: someTextField.setDocument(new SetMaxText(int Max));
	 */
	public class SetMaxText extends PlainDocument {
	  private int limit;
	  // optional uppercase conversion
	  private boolean toUppercase = false;
	  
	  SetMaxText(int limit) {
	   super();
	   this.limit = limit;
	   }
	   
	  SetMaxText(int limit, boolean upper) {
	   super();
	   this.limit = limit;
	   toUppercase = upper;
	   }
	 
	  public void insertString
	    (int offset, String  str, AttributeSet attr)
	      throws BadLocationException {
	   if (str == null) return;

	   if ((getLength() + str.length()) <= limit) {
	     if (toUppercase) str = str.toUpperCase();
	     super.insertString(offset, str, attr);
	     }
	   }
	}
	
	/**
	 * Keylistener for letters only
	 * 		- used on textfield "Username"
	 */
	public KeyListener klLetters= new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//Allows only letters
			if(e.getSource().equals(tfUsername)) {
				if((e.getKeyCode() != (char)8 && e.getKeyChar() < (char)65) 
						|| (e.getKeyChar() > (char)90 && e.getKeyChar() < 97) 
						|| e.getKeyChar() > (char)122 && e.getKeyChar() < (char)255) {
					tfUsername.setText(tfUsername.getText().substring(0, tfUsername.getText().length()-1));
				}
			} else if(e.getSource().equals(pfPassword)) {
				if((e.getKeyChar() != (char)8 && e.getKeyChar() < (char)48) 
						|| (e.getKeyChar() > (char)57 && e.getKeyChar() < (char)65)
						|| (e.getKeyChar() > 90 && e.getKeyChar() < (char)97)
						|| e.getKeyChar() > (char)122 && e.getKeyChar() < (char)255) {
					pfPassword.setText(String.valueOf(pfPassword.getPassword()).substring(0, pfPassword.getPassword().length-1));
				}
			}
			
		}
	};
	
	//Panels
	private static JPanel aPanel = new JPanel();
	private JPanel pnlUserManagement = new JPanel();
	private JPanel pnlTestManagement = new JPanel();
	private JPanel pnlStatus = new JPanel();
	
	//Color
	private Color backgroundColor; 
	private Color cGreen;
	private Color cRed;
	
	//Constants
	private final int COMPONENTHEIGHT = 20;
	private final int TEXTFIELDWIDTH = 170;
	private final int LABELWIDTH = 100;
	private final int BUTTONWIDTH = 150;
	private final int UP = 1;
	private final int DOWN = 0;
	
	//Buttons 
	private JButton btnCreateUser = new JButton("Create User");
	private JButton btnDeleteUser = new JButton("Delete User");
	private JButton btnChangePassword = new JButton("Change Password");
	private JButton btnActivateModule = new JButton("Activate Modul >>");
	private JButton btnDeactivateModule = new JButton("<< Deactivate Modul");
	private JButton btnModuleUp = new JButton("Module Up");
	private JButton btnModuleDown = new JButton("Module Down");
	private JButton btnSaveConfig = new JButton("Save Config");
	private JButton btnTimeUp = new JButton("+");
	private JButton btnTimeDown = new JButton("-");
	private JButton btnBack = new JButton("Back");
	
	//Textfields
	private JPasswordField pfPassword = new JPasswordField();
	private JTextField tfUsername = new JTextField();
	private JTextField tfTime = new JTextField();
	
	//Lists
	private DefaultListModel<String> dlActiveModules = new DefaultListModel<String>();
	private DefaultListModel<String> dlAvailableModules = new DefaultListModel<String>();
	private JList<String> lActiveModules;
	private JList<String> lAvailableModules;
	private LinkedList<ModuleInformation> llConfig = new LinkedList<ModuleInformation>();
	
	//Scrollpane
	private JScrollPane spActiveModules;
	private JScrollPane spAvailableModules;
	
	//Labels
	private JLabel lblTestManagement = new JLabel("Testconfiguration:");
	private JLabel lblUserManagement = new JLabel("User Management:");
	private JLabel lblUsername = new JLabel("User:");
	private JLabel lblPassword = new JLabel("Password:");
	private JLabel lblSetTime = new JLabel("Set Time (sec):");
	private JLabel lblActiveModules = new JLabel("Active Modules:");
	private JLabel lblAvailableModules = new JLabel("Available Modules:");
	private JLabel lblStatus = new JLabel("  Status:");
	
	//Radiobuttons
	private JRadioButton rbUser = new JRadioButton("User");
	private JRadioButton rbAdmin = new JRadioButton("Admin");
	private JRadioButton rbEasy = new JRadioButton("Easy");
	private JRadioButton rbMedium = new JRadioButton("Medium");
	private JRadioButton rbHard = new JRadioButton("Hard");
	
	//ButtonGroup
	private ButtonGroup bgUserType = new ButtonGroup();
	private ButtonGroup bgDifficulty = new ButtonGroup();
	
	//Information about users and parameters
	private UserData users;
	private Parameter params;
	private String type;
	
	//Tempvars
	private String tmpModule;
	private int selIndex;
	private int movement;
	private String place;
	private int difficulty;
	private boolean newConfig;
	
	/**
	 * Actionlistener for all buttons
	 * 	- checks which button is the caller and calls a
	 * 	  specific function
	 */
	private ActionListener selectBtnFunction = new ActionListener() {
		
		private int index;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//Initialize Status-Label
			lblStatus.setText("  Status:");
			lblStatus.setBackground(backgroundColor);
			
			//Click on button "Activate Module"
			if(e.getSource().equals(btnActivateModule)) {
				if(tfTime.getText().length() > 0) {
					if(!lAvailableModules.isSelectionEmpty()) {
						dlActiveModules.addElement(lAvailableModules.getSelectedValue()+" ; "+tfTime.getText());
						
						index = lAvailableModules.getSelectedIndex();
						dlAvailableModules.remove(index);
						
						lAvailableModules.revalidate();
						spAvailableModules.revalidate();
						
						lActiveModules.revalidate();
						spActiveModules.revalidate();
						
						lblStatus.setText(lblStatus.getText()+" Module activated");
						lblStatus.setBackground(cGreen);
					} else {
						lblStatus.setText(lblStatus.getText()+" No module selected");
						lblStatus.setBackground(cRed);
					}
				} else {
					lblStatus.setText(lblStatus.getText()+" No time set");
					lblStatus.setBackground(cRed);
				}
				
			//Click on button "Deactivate Module"
			} else if(e.getSource().equals(btnDeactivateModule)) {
				if(!lActiveModules.isSelectionEmpty()) {
					dlAvailableModules.addElement(lActiveModules.getSelectedValue().substring(0, lActiveModules.getSelectedValue().indexOf(" ;")));
					
					index = lActiveModules.getSelectedIndex();
					
					dlActiveModules.remove(index);
		
					lAvailableModules.revalidate();
					spAvailableModules.revalidate();
					
					lActiveModules.revalidate();
					spActiveModules.revalidate();
					
					lblStatus.setText(lblStatus.getText()+" Module deactivated");
					lblStatus.setBackground(cGreen);
				} else {
					lblStatus.setText(lblStatus.getText()+" No module selected");
					lblStatus.setBackground(cRed);
				}
				
			//Click on button "Change Password"
			} else if(e.getSource().equals(btnChangePassword)) {
				if(tfUsername.getText().length() != 0 && pfPassword.getPassword().length != 0) {
					if(changePassword()) {
						lblStatus.setText(lblStatus.getText() + " Password changed");
						lblStatus.setBackground(cGreen);
					} else {
						lblStatus.setText(lblStatus.getText() + "User doesn't exist");
						lblStatus.setBackground(cRed);
					}
				} else {
					if(tfUsername.getText().length() == 0) {
						lblStatus.setText(lblStatus.getText() + " Username empty");
						lblStatus.setBackground(cRed);
					} else {
						lblStatus.setText(lblStatus.getText() + " Password empty");
						lblStatus.setBackground(cRed);
					}
				}
				
			//Click on button "Create User"
			} else if(e.getSource().equals(btnCreateUser)) {
				if(tfUsername.getText().length() != 0 && pfPassword.getPassword().length != 0) {
					if(createUser()) {
						lblStatus.setText(lblStatus.getText() + " User Created");
						lblStatus.setBackground(cGreen);
					} else {
						lblStatus.setText(lblStatus.getText() + " User already exists");
						lblStatus.setBackground(cRed);
					}
				} else {
					if(tfUsername.getText().length() == 0) {
						lblStatus.setText(lblStatus.getText() + " Username empty");
						lblStatus.setBackground(cRed);
					} else {
						lblStatus.setText(lblStatus.getText() + " Password empty");
						lblStatus.setBackground(cRed);
					}
				}
				
			//Click on button "Delete User"	
			} else if(e.getSource().equals(btnDeleteUser)) {
				if (tfUsername.getText().length() != 0) {
					if (deleteUser()) {
						lblStatus.setText(lblStatus.getText() + " User Deleted");
						lblStatus.setBackground(cGreen);
					} else {
						lblStatus.setText(lblStatus.getText() + " Failed");
						lblStatus.setBackground(cRed);
					}
				} else {
					lblStatus.setText(lblStatus.getText()+" Username empty");
					lblStatus.setBackground(cRed);
				}
				
			//Click on button "Module Down"
			} else if(e.getSource().equals(btnModuleDown)) {
				moveModule(DOWN);
			
			//Click on button "Module Up"
			} else if(e.getSource().equals(btnModuleUp)) {
				moveModule(UP);
			
			//Click on button "Save Config"
			} else if(e.getSource().equals(btnSaveConfig)) {
				newConfig = false;
				//Creates new config
				llConfig = new LinkedList<ModuleInformation>();
				for(int i = 0; i < dlActiveModules.size(); i++) {
					llConfig.add(new ModuleInformation(dlActiveModules.getElementAt(i).substring(
							0, dlActiveModules.getElementAt(i).indexOf(" ;")),
							Integer.parseInt(dlActiveModules.getElementAt(i).substring(
							dlActiveModules.getElementAt(i).indexOf("; ")+2,
							dlActiveModules.getElementAt(i).length()))));
				}
				if (rbEasy.isSelected()) {
					difficulty = 1;
				} else if (rbMedium.isSelected()) {
					difficulty = 2;
				} else if (rbHard.isSelected()) {
					difficulty = 3;
				}
				
				//Checks whether the config is new
				if (difficulty != params.getDifficulty()) {
					newConfig = true;
				} else if (params.getConfiguration().size() != llConfig.size()) {
					newConfig = true;
				} else {
					for (int i=0; i<llConfig.size(); i++) {
						if (!llConfig.get(i).equals(params.getConfiguration().get(i))) {
							newConfig = true;
							break;
						}
					}
				}
				
				if (!newConfig) {
					lblStatus.setText(lblStatus.getText()+" Nothing changed");
					lblStatus.setBackground(cRed);
				} else if (newConfig){
					params.overwriteConfiguration(llConfig, difficulty);
					lblStatus.setText(lblStatus.getText()+" Configuration saved");
					lblStatus.setBackground(cGreen);
				}
			
			//Click on button "-"
			} else if(e.getSource().equals(btnTimeDown)) {
				if(Integer.parseInt(tfTime.getText()) > 60) {
					tfTime.setText(String.valueOf(Integer.parseInt(tfTime.getText())-30));
				} else {
					lblStatus.setText(lblStatus.getText()+" Minimum time reached");
					lblStatus.setBackground(cRed);
				}
				
			//Click on button "+"
			} else if(e.getSource().equals(btnTimeUp)) {
				if(Integer.parseInt(tfTime.getText()) < 990) {
					tfTime.setText(String.valueOf(Integer.parseInt(tfTime.getText())+30));
				} else {
					lblStatus.setText(lblStatus.getText()+" Maximum time reached");
					lblStatus.setBackground(cRed);
				}
			
			//Click on button "Back"
			}else if(e.getSource().equals(btnBack)) {
				//Hier an Mainapplication -> Admin ende
				
			}
		}
		
	};
		
	/**
	 * Constructor - creates an object of the class Admin
	 * @param users - stores information about registrate users
	 * @param params - stores the saved configuration and modulinformation
	 */
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

		aPanel.setLayout(null);
		aPanel.setBounds(0,0,900, 400);
		
		lblUserManagement.setBounds(0, 0, 150, COMPONENTHEIGHT);
		lblTestManagement.setBounds(310, 0, 150, COMPONENTHEIGHT);
		
		initColors();
		
		createUserManagementPanel();
		createTestManagementPanel();
		createStatusPanel();
		
		aPanel.add(pnlStatus);
	
		aPanel.add(lblUserManagement);
		aPanel.add(pnlUserManagement);
		
		aPanel.add(lblTestManagement);
		aPanel.add(pnlTestManagement);

		return aPanel;
	}
	
	/**
	 * Creates the status panel with label 
	 * and button "Back"
	 */
	private void createStatusPanel() {
		//Creates the panel "Status"
		pnlStatus.setLayout(null);
		pnlStatus.setBounds(0, 275, 300, 95);
		pnlStatus.setBackground(backgroundColor);
		lblStatus.setOpaque(true);
		lblStatus.setBackground(backgroundColor);
		lblStatus.setBounds(25, 10, 250, 35);
		lblStatus.setFont(new Font(null, 0, 14));
		pnlStatus.add(lblStatus);
		
		btnBack.setBounds(25, 55, BUTTONWIDTH, COMPONENTHEIGHT);
		btnBack.addActionListener(selectBtnFunction);
		pnlStatus.add(btnBack);
		
	}

	/**
	 * Initialize all used colors 
	 */
	private void initColors() {
		//Initialize colors
		backgroundColor = new Color(aPanel.getBackground().getRed()-9,
				  aPanel.getBackground().getGreen()-9,
				  aPanel.getBackground().getBlue()-9);
		cGreen = new Color(1,200,1);
		cRed = new Color(200,1,1);
	}
	
	/**
	 * Creates the panel "User Management"
	 * with all components
	 */
	private void createUserManagementPanel(){
		//Creates the panel "User Management"
		pnlUserManagement.setLayout(null);
		pnlUserManagement.setBounds(0, 20, 300, 250);
		
		pnlUserManagement.setBackground(backgroundColor);
		
		lblUsername.setBounds(5, 5, LABELWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(lblUsername);
		
		tfUsername.setBounds(110, 5, TEXTFIELDWIDTH, COMPONENTHEIGHT);
		tfUsername.addKeyListener(klLetters);
		pnlUserManagement.add(tfUsername);
		
		lblPassword.setBounds(5, 30, LABELWIDTH, COMPONENTHEIGHT);
		pnlUserManagement.add(lblPassword);
		
		pfPassword.setBounds(110, 30, TEXTFIELDWIDTH, COMPONENTHEIGHT);
		pfPassword.setDocument(new SetMaxText(8));
		pfPassword.addKeyListener(klLetters);
		pnlUserManagement.add(pfPassword);
		
		bgUserType.add(rbUser);
		bgUserType.add(rbAdmin);
		rbUser.setBounds(65, 100, 80, 20);
		rbUser.setSelected(true);
		rbAdmin.setBounds(150, 100, 140, 20);
		pnlUserManagement.add(rbUser);
		pnlUserManagement.add(rbAdmin);
		
		btnCreateUser.setBounds(75, 160, BUTTONWIDTH, COMPONENTHEIGHT);
		btnCreateUser.addActionListener(selectBtnFunction);
		pnlUserManagement.add(btnCreateUser);
		
		btnChangePassword.setBounds(75, 185, BUTTONWIDTH, COMPONENTHEIGHT);
		btnChangePassword.addActionListener(selectBtnFunction);
		pnlUserManagement.add(btnChangePassword);
		
		btnDeleteUser.setBounds(75, 210, BUTTONWIDTH, COMPONENTHEIGHT);
		btnDeleteUser.addActionListener(selectBtnFunction);
		pnlUserManagement.add(btnDeleteUser);
	
	}
	
	/**
	 * Creates the panel "Test Management" with all components
	 * and fills the scrollpanes with information
	 */
	private void createTestManagementPanel(){
		//Creates the panel "Test Management"
		pnlTestManagement.setLayout(null);
		pnlTestManagement.setBounds(310, 20, 580, 350);
		pnlTestManagement.setBackground(backgroundColor);
		
		btnActivateModule.setBounds(215, 40, BUTTONWIDTH, COMPONENTHEIGHT);
		btnActivateModule.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnActivateModule);
		
		lblSetTime.setBounds(215, 80, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(lblSetTime);
		
		btnTimeDown.setBounds(235, 100, 20, 20);
		btnTimeDown.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnTimeDown);
		tfTime.setBounds(260, 100, 45, 20);
		tfTime.setDocument(new SetMaxText(3));
		tfTime.setEnabled(false);
		tfTime.setText("60");
		
		btnTimeUp.setBounds(310, 100, 20, 20);
		btnTimeUp.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnTimeUp);
		
		pnlTestManagement.add(tfTime);
		
		btnDeactivateModule.setBounds(215, 130, BUTTONWIDTH, COMPONENTHEIGHT);
		btnDeactivateModule.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnDeactivateModule);
		
		btnModuleUp.setBounds(215, 170, BUTTONWIDTH, COMPONENTHEIGHT);
		btnModuleUp.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnModuleUp);
		
		btnModuleDown.setBounds(215, 210, BUTTONWIDTH, COMPONENTHEIGHT);
		btnModuleDown.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnModuleDown);
		
		
		bgDifficulty.add(rbEasy);
		bgDifficulty.add(rbMedium);
		bgDifficulty.add(rbHard);
		rbEasy.setBounds(215, 250, 70, COMPONENTHEIGHT);
		rbMedium.setBounds(215, 275, 90, COMPONENTHEIGHT);
		rbHard.setBounds(280, 250, 70, COMPONENTHEIGHT);
		
		//Select the radiobutton depending on the configuration
		switch(params.getDifficulty()) {
		case 1 :
			rbEasy.setSelected(true);
			break;
		case 2 :
			rbMedium.setSelected(true);
			break;
		case 3 :
			rbHard.setSelected(true);
			break;
		default :
			rbMedium.setSelected(true);
			break;
		}
		pnlTestManagement.add(rbEasy);
		pnlTestManagement.add(rbMedium);
		pnlTestManagement.add(rbHard);
		
		btnSaveConfig.setBounds(215, 305, BUTTONWIDTH, COMPONENTHEIGHT);
		btnSaveConfig.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnSaveConfig);
		
		//Adds items into the scrollpanes
		try {
			for (int i = 0; i < params.getAvailableModules().size();i++) {
				dlAvailableModules.add(i, params.getAvailableModules().get(i).getName());
			}
			for (int i = 0; i < params.getConfiguration().size();i++) {
				dlActiveModules.add(i, params.getConfiguration().get(i).getName()+" "+params.getConfiguration().get(i).getTime());
			}
		}catch(IndexOutOfBoundsException e) {
			lblStatus.setText(lblStatus.getText()+" One list is empty");
			lblStatus.setBackground(cRed);
		}
		
		lblActiveModules.setBounds(375, 5, 200, COMPONENTHEIGHT);
		pnlTestManagement.add(lblActiveModules);
		
		lActiveModules = new JList<String>(dlActiveModules);
		spActiveModules = new JScrollPane(lActiveModules);
		spActiveModules.setBounds(375, 30, 200, 300);
		pnlTestManagement.add(spActiveModules);
		
		lblAvailableModules.setBounds(5, 5, 200, COMPONENTHEIGHT);
		pnlTestManagement.add(lblAvailableModules);
		
		lAvailableModules = new JList<String>(dlAvailableModules);
		spAvailableModules = new JScrollPane(lAvailableModules);
		spAvailableModules.setBounds(5, 30, 200, 300);
		pnlTestManagement.add(spAvailableModules);
	}
	
	/**
	 * Moves the selected module 1 place up or down depending
	 * on the param direction
	 * @param direction
	 * 			- up = 1 // down = 0
	 */
	private void moveModule(int direction) {
		if (!lActiveModules.isSelectionEmpty()) {
			tmpModule = lActiveModules.getSelectedValue();
			selIndex = lActiveModules.getSelectedIndex();
		
			if(direction == 0) {
				movement = 2;
				place = "last";
			} else if(direction == 1) {
				movement = -1;
				place = "first";
			}
		
			try {
				dlActiveModules.add(selIndex+movement, tmpModule);
				dlActiveModules.remove(lActiveModules.getSelectedIndex());
			} catch(IndexOutOfBoundsException ioob) {
				lblStatus.setText(lblStatus.getText()+" Already on "+place+" position");
				lblStatus.setBackground(cRed);
			}
			lActiveModules.revalidate();
			spActiveModules.revalidate();
		} else if (lActiveModules.isSelectionEmpty()) {
			lblStatus.setBackground(cRed);
			lblStatus.setText(lblStatus.getText()+" No module selected");
		}
	}
	
	/**
	 * Creates an user/admin
	 * @return true if user was created / false is some param is not ok
	 */
	private boolean createUser() {
		if(rbAdmin.isSelected()) {
			type = "a";
		}
		else {
			type = "u";
		}
		return users.saveUser(tfUsername.getText(), String.valueOf(pfPassword.getPassword()), type);
	}
	
	/**
	 * Deletes a user/admin
	 * @return true if the user is marked as deleted. Will be saved after regular closing 
	 * 			of the programm
	 */
	private boolean deleteUser() {
		return users.deleteUser(tfUsername.getText());
	}
	
	/**
	 * Changes the password of an user/admin
	 * @return true if the password was changed !the password isn't written in xml yet!
	 * 			if the programm chrashes the changes are lost!
	 */
	private boolean changePassword() {
		String username = tfUsername.getText();
		String password = String.valueOf(pfPassword.getPassword());
		return users.changePassword(username, password);	
	}

}
