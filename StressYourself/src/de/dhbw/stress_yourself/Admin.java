package de.dhbw.stress_yourself;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import de.dhbw.stress_yourself.params.ModuleInformation;
import de.dhbw.stress_yourself.params.Parameter;
import de.dhbw.stress_yourself.params.Parameter.difficultyType;
import de.dhbw.stress_yourself.params.UserData;
import de.dhbw.stress_yourself.params.UserData.UserType;

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
		
	//Panels
	private static JPanel aPanel;
	private JPanel pnlUserManagement;
	private JPanel pnlTestManagement;
	private JPanel pnlStatus;
	private JPanel pnlUsers;

	
	//Color
	private Color backgroundColor; 
	
	//Constants
	private final int COMPONENTHEIGHT = 20;
	private final int TEXTFIELDWIDTH = 170;
	private final int LABELWIDTH = 100;
	private final int BUTTONWIDTH = 150;
	private final int UP = 1;
	private final int DOWN = 0;
	private final int PANELX = 160;
	
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
	private JButton btnBack = new JButton("Quit Admin Area And Save All Changes");
	private JButton btnActivateAll = new JButton("Activate All");
	private JButton btnDeactivateAll = new JButton("Deactivate All");
	private JButton btnShuffleModules = new JButton("Shuffle Modules");
	
	//Textfields
	private JPasswordField pfPassword = new JPasswordField();
	private JTextField tfUsername = new JTextField();
	private JTextField tfTime = new JTextField();
	
	//Lists
	private DefaultListModel<String> dlActiveModules;
	private DefaultListModel<String> dlAvailableModules;
	private DefaultListModel<String> dlUsers;
	private static JList<String> lActiveModules;
	private static JList<String> lAvailableModules;
	private static JList<String> lUsers;
	private LinkedList<ModuleInformation> llConfig;
	
	//Scrollpane
	private JScrollPane spActiveModules;
	private JScrollPane spAvailableModules;
	private JScrollPane spUsers;
	
	//Labels
	private JLabel lblTestManagement = new JLabel("Testconfiguration:");
	private JLabel lblUserManagement = new JLabel("User Management:");
	private JLabel lblUsername = new JLabel("User:");
	private JLabel lblPassword = new JLabel("Password:");
	private JLabel lblSetTime = new JLabel("Set Time (sec):");
	private JLabel lblActiveModules = new JLabel("Active Modules:");
	private JLabel lblAvailableModules = new JLabel("Available Modules:");
	private JLabel lblRegUsers = new JLabel("Registered Users");
	
	//Radiobuttons
	private JRadioButton rbUser = new JRadioButton("User");
	private JRadioButton rbAdmin = new JRadioButton("Admin");
	private JRadioButton rbEasy = new JRadioButton("Easy");
	private JRadioButton rbMedium = new JRadioButton("Medium");
	private JRadioButton rbHard = new JRadioButton("Hard");
	
	//ButtonGroup
	private ButtonGroup bgUserType = new ButtonGroup();
	private ButtonGroup bgDifficulty = new ButtonGroup();
	
	//Checkbob
	private JCheckBox chkbAnnoyance = new JCheckBox("Annoyance On");
	
	//Information about users and parameters
	private MainApplication main;
	private UserData users;
	private Parameter params;
	private UserType type;
	
	//Tempvars
	private String tmpModule;
	private int selIndex;
	private int movement;
	private difficultyType difficulty;
	private boolean newConfig;
	private boolean exists;
	private String tmpUsername;
	private boolean del;
	private DefaultListModel<String> tmpDLM  = new DefaultListModel<String>();
	
	/**
	 * Actionlistener for all buttons
	 * 			- checks which button is the caller and calls a
	 * 	 		  specific function
	 */
	private ActionListener selectBtnFunction = new ActionListener() {
		
		private int index;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//Click on button "Activate Module"
			if(e.getSource().equals(btnActivateModule)) {
					if(!lAvailableModules.isSelectionEmpty()) {
						dlActiveModules.addElement(lAvailableModules.getSelectedValue()+" ; "+tfTime.getText());
						
						index = lAvailableModules.getSelectedIndex();
						dlAvailableModules.remove(index);
						
						lAvailableModules.revalidate();
						spAvailableModules.revalidate();
						
						lActiveModules.revalidate();
						spActiveModules.revalidate();
					}
				
			//Click on button "Deactivate Module"
			} else if(e.getSource().equals(btnDeactivateModule)) {
				if(!lActiveModules.isSelectionEmpty()) {
					dlAvailableModules.addElement(lActiveModules.getSelectedValue().substring(
														0, lActiveModules.getSelectedValue().indexOf(" ; ")));
					
					index = lActiveModules.getSelectedIndex();
					dlActiveModules.remove(index);
		
					lAvailableModules.revalidate();
					spAvailableModules.revalidate();
					
					lActiveModules.revalidate();
					spActiveModules.revalidate();
					
				}
				
			//Click on button "Change Password"
			} else if(e.getSource().equals(btnChangePassword)) {
				if(tfUsername.getText().length() != 0 && pfPassword.getPassword().length != 0) {
					changePassword();
				}
				
			//Click on button "Create User"
			} else if(e.getSource().equals(btnCreateUser)) {
				if(tfUsername.getText().length() != 0 && pfPassword.getPassword().length != 0) {
					if(createUser()) {
						dlUsers.addElement(tfUsername.getText());
					}
					
				}
			//Click on button "Delete User"	
			} else if(e.getSource().equals(btnDeleteUser)) {
				del = false;
				if(tfUsername.getText().length() > 0) {
					tmpUsername = tfUsername.getText();
					del = true;
				} else if (!lUsers.isSelectionEmpty()) {
					tmpUsername = lUsers.getSelectedValue();
					del = true;
				} else if (lUsers.isSelectionEmpty() && tfUsername.getText().length() == 0) {
					del = false;
				}
				if (del) {
					if (deleteUser(tmpUsername)) {
						for(int y=0; y<dlUsers.getSize();y++) {
							if(tmpUsername.equals(dlUsers.elementAt(y))){
								dlUsers.remove(y);
							}
						}
					}
				}
				
			//Click on button "Module Down"
			} else if(e.getSource().equals(btnModuleDown)) {
				moveModule(DOWN);
			
			//Click on button "Module Up"
			} else if(e.getSource().equals(btnModuleUp)) {
				moveModule(UP);
			
			//Click on button "Save Config"
			} else if(e.getSource().equals(btnSaveConfig)) {
				saveConfTmp();
			
			//Click on button "-"
			} else if(e.getSource().equals(btnTimeDown)) {
				if(Integer.parseInt(tfTime.getText()) > 60) {
					tfTime.setText(String.valueOf(Integer.parseInt(tfTime.getText())-30));
				}
				
			//Click on button "+"
			} else if(e.getSource().equals(btnTimeUp)) {
				if(Integer.parseInt(tfTime.getText()) < 990) {
					tfTime.setText(String.valueOf(Integer.parseInt(tfTime.getText())+30));
				} 
			
			//Click on button "Quit Admin Area And Save All Changes"
			}else if(e.getSource().equals(btnBack)) {
				saveConfTmp();
				params.saveChangesInXML();
				users.saveChangesInXML();
				
				//launch the login
				main.startLoginPanel();
				//getConfiguratiob
				main.getConfiguration();
			
			//Click on button "Deactivate All"
			} else if(e.getSource().equals(btnDeactivateAll)) {
				for(int i = 0; i < dlActiveModules.getSize(); i++) {
					dlAvailableModules.addElement(dlActiveModules.get(i).substring(
							0, dlActiveModules.get(i).indexOf(" ; ")));
				}
				dlActiveModules.removeAllElements();
				lAvailableModules.revalidate();
				spAvailableModules.revalidate();
				
				lActiveModules.revalidate();
				spActiveModules.revalidate();
				
			//Click on button "Activate All"
			} else if(e.getSource().equals(btnActivateAll)) {
				for(int i = 0; i < dlAvailableModules.getSize(); i++) {
					dlActiveModules.addElement(dlAvailableModules.get(i)+" ; "+tfTime.getText());
				}
				dlAvailableModules.removeAllElements();
				lAvailableModules.revalidate();
				spAvailableModules.revalidate();
				
				lActiveModules.revalidate();
				spActiveModules.revalidate();
				
			//Click on button "Shuffle Modules"
			} else if(e.getSource().equals(btnShuffleModules)) {
				
				for (int i = 0; i < dlActiveModules.getSize(); i++) {
					tmpDLM.addElement(dlActiveModules.get(i));
				}
				dlActiveModules.removeAllElements();
				lActiveModules.revalidate();
				while(tmpDLM.size()>0) {
					index = myRandom(0, tmpDLM.size());
					dlActiveModules.addElement(tmpDLM.get(index));
					tmpDLM.removeElementAt(index);
				}
				lActiveModules.revalidate();
				spActiveModules.revalidate();
			}
		}
	};
	
	public static int myRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
		
	/**
	 * Constructor - creates an object of the class Admin
	 * 				 and gets information about users, parameters and mainapplication
	 * 
	 * @param users 
	 * 				stores information about registrate users
	 * @param params 
	 * 				stores the saved configuration and modulinformation
	 * @param main 
	 * 				stores information about the mainapplication
	 */
	public Admin(MainApplication main, UserData users, Parameter params){
		this.main = main;
		this.users = users;
		this.params = params;
	}

	/**
	 * Creates a JPanel with the contents Usermanagement and Testmanagement
	 * 
	 * @return 
	 * 			JPanel with the named contents on it
	 */
	public JPanel getAdminPanel(){
		
		initComponents();
		initColors();
		createUserManagementPanel();
		createTestManagementPanel();
		createStatusPanel();
		createUsersPanel(); 
		
		aPanel.setLayout(null);
		aPanel.setBounds(0,0,900, 700);
		
		lblUserManagement.setBounds(PANELX, 0, 150, COMPONENTHEIGHT);
		lblTestManagement.setBounds(PANELX, 210, 150, COMPONENTHEIGHT);
		
		//Add components to the panel which is returned
		aPanel.add(pnlStatus);
		aPanel.add(pnlUsers);
		aPanel.add(lblUserManagement);
		aPanel.add(pnlUserManagement);
		aPanel.add(lblTestManagement);
		aPanel.add(pnlTestManagement);

		return aPanel;
	}
	
	/**
	 * Creates a JPanel with scrollpane on it with 
	 * the names of the registered users
	 */
	private void createUsersPanel() {
		pnlUsers.setBackground(backgroundColor);
		pnlUsers.setLayout(null);
		pnlUsers.setBounds(PANELX+300, 20, 280, 180);
		
		lblRegUsers.setBounds(10, 0, 200, COMPONENTHEIGHT);
		pnlUsers.add(lblRegUsers);
		
		for(String[] element : users.getUsers()) {
			dlUsers.addElement(element[0]);
		}
		lUsers = new JList<String>(dlUsers);
		spUsers = new JScrollPane(lUsers);
		spUsers.setBounds(10, 20, 150, 150);
		pnlUsers.add(spUsers);
	}
	
	/**
	 * Creates the status panel with the button
	 * save and go back on it
	 */
	private void createStatusPanel() {
		
		//Creates the panel "Status"
		pnlStatus.setLayout(null);
		pnlStatus.setBounds(PANELX, 590, 580, 55);
		pnlStatus.setBackground(backgroundColor);
		
		btnBack.setBounds(145, 20, (BUTTONWIDTH*2), COMPONENTHEIGHT);
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
	}
	
	/**
	 * Creates the panel "User Management"
	 * with all components which are needed to manage
	 * users
	 */
	private void createUserManagementPanel(){
		
		//Creates the panel "User Management"
		pnlUserManagement.setLayout(null);
		pnlUserManagement.setBounds(PANELX, 20, 300, 180);
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
		rbUser.setBounds(65, 70, 80, 20);
		rbUser.setSelected(true);
		rbAdmin.setBounds(150, 70, 140, 20);
		pnlUserManagement.add(rbUser);
		pnlUserManagement.add(rbAdmin);
		
		btnCreateUser.setBounds(75, 100, BUTTONWIDTH, COMPONENTHEIGHT);
		btnCreateUser.addActionListener(selectBtnFunction);
		pnlUserManagement.add(btnCreateUser);
		
		btnChangePassword.setBounds(75, 125, BUTTONWIDTH, COMPONENTHEIGHT);
		btnChangePassword.addActionListener(selectBtnFunction);
		pnlUserManagement.add(btnChangePassword);
		
		btnDeleteUser.setBounds(75, 150, BUTTONWIDTH, COMPONENTHEIGHT);
		btnDeleteUser.addActionListener(selectBtnFunction);
		pnlUserManagement.add(btnDeleteUser);
	}
	
	/**
	 * Creates the panel "Test Management" with all components
	 * which are needed to manage test routines
	 */
	private void createTestManagementPanel(){
		
		//Creates the panel "Test Management"
		pnlTestManagement.setLayout(null);
		pnlTestManagement.setBounds(PANELX, 230, 580, 350);
		pnlTestManagement.setBackground(backgroundColor);
		
		btnActivateModule.setBounds(215, 30, BUTTONWIDTH, COMPONENTHEIGHT);
		btnActivateModule.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnActivateModule);
		
		lblSetTime.setBounds(215, 60, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(lblSetTime);
		
		btnTimeDown.setBounds(235, 80, 20, 20);
		btnTimeDown.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnTimeDown);
		
		tfTime.setBounds(260, 80, 45, 20);
		tfTime.setDocument(new SetMaxText(3));
		tfTime.setEnabled(false);
		tfTime.setText("60");
		
		btnTimeUp.setBounds(310, 80, 20, 20);
		btnTimeUp.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnTimeUp);
		
		pnlTestManagement.add(tfTime);
		
		btnDeactivateModule.setBounds(215, 110, BUTTONWIDTH, COMPONENTHEIGHT);
		btnDeactivateModule.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnDeactivateModule);
		
		btnModuleUp.setBounds(215, 140, BUTTONWIDTH, COMPONENTHEIGHT);
		btnModuleUp.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnModuleUp);
		
		btnModuleDown.setBounds(215, 170, BUTTONWIDTH, COMPONENTHEIGHT);
		btnModuleDown.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnModuleDown);
		
		
		bgDifficulty.add(rbEasy);
		bgDifficulty.add(rbMedium);
		bgDifficulty.add(rbHard);
		rbEasy.setBounds(215, 200, 70, COMPONENTHEIGHT);
		rbMedium.setBounds(215, 225, 90, COMPONENTHEIGHT);
		rbHard.setBounds(280, 200, 70, COMPONENTHEIGHT);
		
		btnShuffleModules.setBounds(215, 255, BUTTONWIDTH, COMPONENTHEIGHT);
		btnShuffleModules.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnShuffleModules);
		
		chkbAnnoyance.setBounds(215, 285, BUTTONWIDTH, COMPONENTHEIGHT);
		chkbAnnoyance.setSelected(params.getAnnoyanceSetting());
		pnlTestManagement.add(chkbAnnoyance);
		
		//Select the radiobutton depending on the configuration
		switch(params.getDifficulty()) {
		case EASY :
			rbEasy.setSelected(true);
			break;
		case MEDIUM :
			rbMedium.setSelected(true);
			break;
		case HARD :
			rbHard.setSelected(true);
			break;
		default :
			rbMedium.setSelected(true);
			break;
		}
		pnlTestManagement.add(rbEasy);
		pnlTestManagement.add(rbMedium);
		pnlTestManagement.add(rbHard);
		
		//Fills the used lists for the scrollpanes with information
		try {
			for (int i = 0; i < params.getAvailableModules().size(); i++) {
				exists = false;
				for (int b=0; b < params.getConfiguration().size(); b++) {
					
					if (params.getConfiguration().get(b).getName().equals(
													params.getAvailableModules().get(i).getName())) {
						exists = true;
						break;
					}
				}
				if(!exists) {
					dlAvailableModules.addElement(params.getAvailableModules().get(i).getName());
					exists = false;
				}
			}
			for (int i = 0; i < params.getConfiguration().size();i++) {
				dlActiveModules.add(i, params.getConfiguration().get(i).getName()+" ; "+
														params.getConfiguration().get(i).getTime());
			}
		}catch(IndexOutOfBoundsException e) {
			 
		} 
		
		lblActiveModules.setBounds(375, 5, 200, COMPONENTHEIGHT);
		pnlTestManagement.add(lblActiveModules);
		
		lActiveModules = new JList<String>(dlActiveModules);
		spActiveModules = new JScrollPane(lActiveModules);
		spActiveModules.setBounds(375, 30, 200, 265);
		pnlTestManagement.add(spActiveModules);
		
		btnDeactivateAll.addActionListener(selectBtnFunction);
		btnDeactivateAll.setBounds(390, 305, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(btnDeactivateAll);
		
		lblAvailableModules.setBounds(5, 5, 200, COMPONENTHEIGHT);
		pnlTestManagement.add(lblAvailableModules);
		
		lAvailableModules = new JList<String>(dlAvailableModules);
		spAvailableModules = new JScrollPane(lAvailableModules);
		spAvailableModules.setBounds(5, 30, 200, 265);
		pnlTestManagement.add(spAvailableModules);
		
		btnActivateAll.addActionListener(selectBtnFunction);
		btnActivateAll.setBounds(20, 305, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(btnActivateAll);
	}
	
	public boolean getAnnoyanceStatus() {
		if(chkbAnnoyance.isSelected()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Moves the selected module 1 place up or down depending
	 * on the param direction
	 * 
	 * @param direction 
	 * 					value for: up = 1 // down = 0
	 */
	private void moveModule(int direction) {
		if (!lActiveModules.isSelectionEmpty()) {
			tmpModule = lActiveModules.getSelectedValue();
			selIndex = lActiveModules.getSelectedIndex();
		
			if(direction == 0) {
				movement = 2;
			} else if(direction == 1) {
				movement = -1;
			}
		
			try {
				dlActiveModules.add(selIndex+movement, tmpModule);
				dlActiveModules.remove(lActiveModules.getSelectedIndex());
			} catch(IndexOutOfBoundsException ioob) {
			}
			lActiveModules.revalidate();
			spActiveModules.revalidate();
		} else if (lActiveModules.isSelectionEmpty()) {
		}
	}
	
	/**
	 * Class SetMaxText
	 * 		- with this class you are able to
	 * 		  set the maxlength of an JTextField / JPasswordField
	 * 		  in a very easy way.
	 *		- use: someTextField.setDocument(new SetMaxText(int Max));
	 */
	@SuppressWarnings("serial")
	public class SetMaxText extends PlainDocument {
	  private int limit;
	 
	  //Optional uppercase conversion
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
	 * Creates an user or admin
	 * User or admin will be saved after closing the programm.
	 * If the programm chrashes the changes are lost.
	 * 
	 * @return 
	 * 			true if user or admin was created / false if some param is not ok
	 */
	private boolean createUser() {
		if(rbAdmin.isSelected()) {
			type = UserType.ADMIN;
		}
		else {
			type = UserType.USER;
		}
		return users.saveUser(tfUsername.getText(), String.valueOf(pfPassword.getPassword()), type);
	}
	
	/**
	 * Deletes a user or admin
	 * User or admin will be deleted after closing the programm.
	 * If the programm chrashes the changes are lost!
	 * 
	 * @return 
	 * 			true if the user is marked as deleted. 
	 */
	private boolean deleteUser(String username) {
		return users.deleteUser(tmpUsername);
	}
	
	/**
	 * Changes the password of an user or admin.
	 * Password will be saved after returning closing the programm.
	 * If the programm chrashes the changes are lost!
	 * 
	 * @return 
	 * 			true if the password was changed in temp user information.
	 */
	private boolean changePassword() {
		String username = tfUsername.getText();
		String password = String.valueOf(pfPassword.getPassword());
		return users.changePassword(username, password);	
	}
	
	/**
	 * Save configuration temporarely
	 * Will be saved after closing the programm.
	 * If the programm chrashes the configuratin will be lost!
	 */
	public void saveConfTmp() {
		newConfig = false;
		
		//Creates new config
		llConfig = new LinkedList<ModuleInformation>();
		for(int i = 0; i < dlActiveModules.size(); i++) {
			for(int x = 0; x < params.getAvailableModules().size(); x++) {
				
				//Fills a LinkedList with configuration
				if(dlActiveModules.getElementAt(i).substring(
											0, dlActiveModules.getElementAt(i).indexOf(" ; ")).equals(
											params.getAvailableModules().get(x).getName())) {
					params.getAvailableModules().get(x).setTime(
										Integer.parseInt(dlActiveModules.getElementAt(i).substring(
										dlActiveModules.getElementAt(i).indexOf(" ; ")+3,
										dlActiveModules.getElementAt(i).length())));
					llConfig.add(params.getAvailableModules().get(x));
				}
			}
		}
		if (rbEasy.isSelected()) {
			difficulty = difficultyType.EASY;
		} else if (rbMedium.isSelected()) {
			difficulty = difficultyType.MEDIUM;
		} else if (rbHard.isSelected()) {
			difficulty = difficultyType.HARD;
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
		if(params.getAnnoyanceSetting() != getAnnoyanceStatus()) {
			params.setAnnoyanceSetting(getAnnoyanceStatus());
			newConfig = true;
		}
		
		 if (newConfig){
			 params.overwriteConfiguration(llConfig, difficulty, getAnnoyanceStatus());
		}
	}
	
	/**
	 * Initialize the components so that if an admin
	 * calls the admin area twice the components
	 * are reloaded
	 */
	private void initComponents() {
		aPanel = new JPanel();
		pnlStatus = new JPanel();
		pnlTestManagement = new JPanel();
		pnlUserManagement = new JPanel();
		pnlUsers = new JPanel();
		dlActiveModules = new DefaultListModel<String>();
		dlAvailableModules = new DefaultListModel<String>();
		dlUsers = new DefaultListModel<String>();
		tfUsername.setText("");
		llConfig = new LinkedList<ModuleInformation>();
		lActiveModules = null;
		lAvailableModules = null;
		lActiveModules = new JList<String>(dlActiveModules);
		lAvailableModules = new JList<String>(dlAvailableModules);
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
}
