package de.dhbw.stress_yourself;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.dhbw.stress_yourself.params.UserData;
import de.dhbw.stress_yourself.params.UserData.UserType;

/**
 * The Login Class is used to manage the Login. It distinguish between the users
 * and the admins.
 * 
 * @author Christoph Schollmeyer <chr.schollmeyer@web.de>
 */
public class Login {

	private UserData users;
	private MainApplication main;

	public Login(MainApplication main, UserData users) {
		this.main = main;
		this.users = users;
	}

	public JPanel getLoginPanel() {
		LoginGUI panel = new LoginGUI();
		return panel;
	}

	/**
	 * defines the GUI for the Login
	 */
	private class LoginGUI extends JPanel implements ActionListener,
			KeyListener {

		private static final long serialVersionUID = 1L;
		private JButton submit;
		private JLabel label1;
		private JLabel label2;
		private JLabel label3;
		private JLabel label4;

		private JTextField text1, text2;

		public LoginGUI() {
			init();
		}

		private void init() {
			setLayout(new GridLayout(5, 2));
			setBorder(new EmptyBorder(280, 300, 280, 300));

			label1 = new JLabel("Username:");
			text1 = new JTextField(15);

			label2 = new JLabel("Password:");
			text2 = new JPasswordField(8);

			submit = new JButton("Submit");

			label3 = new JLabel("");
			label4 = new JLabel("");

			add(label1);
			add(text1);
			add(label2);
			add(text2);
			add(new JLabel());
			add(submit);
			add(new JLabel());
			add(label3);
			add(new JLabel());
			add(label4);
			
			text1.addKeyListener(this);
			text2.addKeyListener(this);

			submit.addActionListener(this);

		}

		/**
		 * Gets the username and the password. Then he checks if the user
		 * exists, with the specified password.
		 * 
		 * @param username
		 *            The name what the user prefers to use
		 * @param password
		 *            The word which is used to pass
		 */
		public void login(String username, String password) {
			username = text1.getText();
			password = text2.getText();
			UserType result = users.existsUser(username, password);

			switch (result) {
			case NOT:
				text1.setText("");
				text2.setText("");
				label3.setText("Wrong username ");
				label4.setText("or password!");
				break;
			case USER:
				main.nextModule();
				break;
			case ADMIN:
				main.startAdminPanel();
				break;

			}

		}

		/**
		 * submits the Login when the Button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			login(text1.getText(), text2.getText());
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		/**
		 * submits the Login when the ENTER-Key is pressed
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				login(text1.getText(), text2.getText());
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
