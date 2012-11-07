package de.dhbw.stress_yourself;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.StyleSheet.ListPainter;

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
			setLayout(new GridLayout(3,2));
			setBorder(new EmptyBorder(300, 300, 300, 300));
			
			label1 = new JLabel("Username:");
			text1 = new JTextField(15);

			label2 = new JLabel("Password:");
			text2 = new JPasswordField(8);

			submit = new JButton("SUBMIT");
			
			
			add(label1);
			add(text1);
			add(label2);
			add(text2);
			add(new JLabel());
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
			if (users.existsUser(username, password)) {
				JOptionPane.showMessageDialog(this, "Login Successfull");
				}
				else {
				JOptionPane.showMessageDialog(this,"Eingabe fehlerhaft! Username oder Passwort falsch");
				}
		}
	}
}
