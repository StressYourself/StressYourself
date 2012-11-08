package de.dhbw.stress_yourself;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
		int result = users.existsUser(username, password);
		if (result == 1 || result == 2) {
			JOptionPane.showMessageDialog(null, "Login Successfull");
		} else {
			JOptionPane.showMessageDialog(null,
					"Eingabe fehlerhaft! Username oder Passwort falsch");
		}
		return result;
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
	private class LoginGUI extends JPanel implements ActionListener, KeyListener {

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
			setLayout(new GridLayout(3, 2));
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
			
			text1.addKeyListener(this);
			text2.addKeyListener(this);
			submit.addActionListener(this);
			
		}
		
		public void Login(String username, String password) {
			username = text1.getText();
			password = text2.getText();
			int result = login(username, password);
			if (result == 0) {
				text1.setText("");
				text2.setText("");
			} else if (result == 1) {
				// user
			} else if (result == 2) {
				// admin
			}
		}

		/**
		 * Gets the Text from the TextFields
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Login(text1.getText(), text2.getText());
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				Login(text1.getText(), text2.getText());
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
