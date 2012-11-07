package de.dhbw.stress_yourself;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
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
	
	//Keylistener for letters only
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
			// TODO Auto-generated method stub
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
	private JPanel pnlUserManagement = new JPanel();
	private JPanel pnlTestManagement = new JPanel();
	
	//Color
	private Color backgroundColor; 
	
	//Constants
	private final int COMPONENTHEIGHT = 20;
	private final int TEXTFIELDWIDTH = 170;
	private final int LABELWIDTH = 100;
	private final int BUTTONWIDTH = 150;
	
	//Buttons 
	private JButton btnCreateUser = new JButton("Create User");
	private JButton btnDeleteUser = new JButton("Delete User");
	private JButton btnChangePassword = new JButton("Change Password");
	private JButton btnActivateModule = new JButton("<< Activate Modul");
	private JButton btnInactivateModule = new JButton("Deactivate Modul >>");
	private JButton btnModuleUp = new JButton("Module Prev");
	private JButton btnModuleDown = new JButton("Module Back");
	
	//Textfields
	private JPasswordField pfPassword = new JPasswordField();
	private JTextField tfUsername = new JTextField();
	private JTextField tfTime = new JTextField();
	
	//Lists
	private DefaultListModel<String> dlActiveModules = new DefaultListModel<String>();
	private DefaultListModel<String> dlAvailableModules = new DefaultListModel<String>();
	private JList<String> lActiveModules;
	private JList<String> lAvailableModules;
	
	//Scrollpane
	private JScrollPane spActiveModules;
	private JScrollPane spAvailableModules;
	
	//Labels
	private JLabel lblTestManagement = new JLabel("Testconfiguration:");
	private JLabel lblUserManagement = new JLabel("User Management:");
	private JLabel lblUsername = new JLabel("User:");
	private JLabel lblPassword = new JLabel("Password:");
	private JLabel lblSetTime = new JLabel("Set Time (sec):");
	
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
	private String tmp;
	
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
					}
				}
			} else if(e.getSource().equals(btnInactivateModule)) {
				if(!lActiveModules.isSelectionEmpty()) {
					dlAvailableModules.addElement(lActiveModules.getSelectedValue().substring(0, lActiveModules.getSelectedValue().indexOf(" ;")));
					
					index = lActiveModules.getSelectedIndex();
					
					dlActiveModules.remove(index);
					
					lAvailableModules.revalidate();
					spAvailableModules.revalidate();
					
					lActiveModules.revalidate();
					spActiveModules.revalidate();
				}
			} else if(e.getSource().equals(btnChangePassword)) {
				changePassword();
			} else if(e.getSource().equals(btnCreateUser)) {
				createUser();
			} else if(e.getSource().equals(btnDeleteUser)) {
				deleteUser();
			} else if(e.getSource().equals(btnModuleDown)) {
				tmp = lActiveModules.getSelectedValue();
				
			} else if(e.getSource().equals(btnModuleUp)) {
				
			}
		}
		
	};
	
	public static JPanel aPanel;	
		
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
	
	private void createTestManagementPanel(){
		pnlTestManagement.setLayout(null);
		pnlTestManagement.setBounds(310, 20, 580, 350);
		pnlTestManagement.setBackground(backgroundColor);
		
		btnActivateModule.setBounds(215, 40, BUTTONWIDTH, COMPONENTHEIGHT);
		btnActivateModule.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnActivateModule);
		
		lblSetTime.setBounds(215, 80, BUTTONWIDTH, COMPONENTHEIGHT);
		pnlTestManagement.add(lblSetTime);
		
		tfTime.setBounds(260, 100, 45, 20);
		tfTime.setDocument(new SetMaxText(3));
		tfTime.addKeyListener(new KeyListener() {

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
				// TODO Auto-generated method stub
				if((e.getKeyCode() != 8 && e.getKeyChar() < (char)48) || e.getKeyChar() > (char) 57) {
					tfTime.setText(tfTime.getText().substring(0, tfTime.getText().length()-1));
				}
			}
		});
		
		
		pnlTestManagement.add(tfTime);
		
		btnInactivateModule.setBounds(215, 130, BUTTONWIDTH, COMPONENTHEIGHT);
		btnInactivateModule.addActionListener(selectBtnFunction);
		pnlTestManagement.add(btnInactivateModule);
		
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
		
		pnlTestManagement.add(rbEasy);
		pnlTestManagement.add(rbMedium);
		pnlTestManagement.add(rbHard);
		
		try {
			for (int i = 0; i < params.getAvailableModules().size();i++) {
				dlAvailableModules.add(i, params.getAvailableModules().get(i).getName());
			}
			for (int i = 0; i < params.getConfiguration().size();i++) {
				dlActiveModules.add(i, params.getConfiguration().get(i).getName()+" "+params.getConfiguration().get(i).getTime());
			}
		}catch(IndexOutOfBoundsException e) {
			System.out.println("One list is empty");
		}
		
		lActiveModules = new JList<String>(dlActiveModules);
		spActiveModules = new JScrollPane(lActiveModules);
		spActiveModules.setBounds(5, 20, 200, 300);
		
		pnlTestManagement.add(spActiveModules);
		
		lAvailableModules = new JList<String>(dlAvailableModules);
		spAvailableModules = new JScrollPane(lAvailableModules);
		spAvailableModules.setBounds(375, 20, 200, 300);
		
		pnlTestManagement.add(spAvailableModules);
		
		
	}
	
	/**
	 * Creates an user/admin
	 * @return true if user was created / false is some param is not ok
	 */
	public boolean createUser() {
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
	public boolean deleteUser() {
		return users.deleteUser(tfUsername.getText());
	}
	
	
	/**
	 * Changes the password of an user/admin
	 * @return true if the password was changed !the password isn't written in xml yet!
	 * 			if the programm chrashes the changes are lost!
	 */
	public boolean changePassword() {
		String username = tfUsername.getText();
		String password = String.valueOf(pfPassword.getPassword());
		return users.changePassword(username, password);	
	}

}
