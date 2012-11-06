package de.dhbw.stress_yourself;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

import de.dhbw.stress_yourself.UserData;

/**
 * 
 * @author Christoph Schollmeyer <email>
 */
public class Login {

	private UserData users;

	public Login(UserData users) {
		this.users = users;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public int login(String username, String password) {
		if (users.existsUser(username, password)) {
			JOptionPane.showMessageDialog(null, "Login Successfull");
			return 0;
		} else {
			JOptionPane.showMessageDialog(null,
					"Eingabe fehlerhaft! Username oder Passwort falsch");
			return 0;
		}
	}

	public JPanel getLoginPanel() {
		LoginGUI panel = new LoginGUI();
		return panel;
	}

	/**
	 * 
	 * defines the GUI for the Login
	 * 
	 */
	private class LoginGUI extends JPanel implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton submit;
		private JLabel label1;
		private JLabel label2;

		private JTextField text1, text2;
		
		public LoginGUI() {
			init();
		}

		private void init() {

			label1 = new JLabel("Username:");
			text1 = new JTextField(15);

			label2 = new JLabel("Password:");
			text2 = new JPasswordField(8);

			submit = new JButton("SUBMIT");
			setLayout(new GridLayout(3, 1));
			add(label1);
			add(text1);
			add(label2);
			add(text2);
			add(submit);
			submit.addActionListener(this);
		}

		/**
		 * Gets the Text from the TextFields
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = text1.getText();
			String password = text2.getText();
			
			int result = login(username, password);
			if (result == 0) {
				// not logged in
			} else if (result == 1) {
				// user
			} else if (result == 2) {
				// admin
			}
		}
	}
}
