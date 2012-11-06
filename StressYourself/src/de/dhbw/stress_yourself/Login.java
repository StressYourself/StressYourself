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
		JPanel panel = new JPanel();
		return panel;
	}

	/**
	 * 
	 * defines the GUI for the Login
	 * 
	 */
	class LoginGUI extends JPanel implements ActionListener {

		JButton submit;
		JPanel panel;
		JLabel label1;
		JLabel label2;

		JTextField text1, text2;

		public void init() {

			label1 = new JLabel();
			label1.setText("Username:");
			text1 = new JTextField(15);

			label2 = new JLabel();
			label2.setText("Password:");
			text2 = new JPasswordField(8);

			submit = new JButton("SUBMIT");
			panel = new JPanel(new GridLayout(3, 1));
			panel.add(label1);
			panel.add(text1);
			panel.add(label2);
			panel.add(text2);
			panel.add(submit);
			add(panel);
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
