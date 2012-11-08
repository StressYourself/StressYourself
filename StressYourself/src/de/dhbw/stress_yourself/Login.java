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
	 * 
	 * defines the GUI for the Login
	 * 
	 */
	private class LoginGUI extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;
		private JButton submit;
		private JLabel label1;
		private JLabel label2;
		private JLabel label3;

		private JTextField text1, text2;

		public LoginGUI() {
			init();
		}

		private void init() {
			setLayout(new GridLayout(4, 2));
			setBorder(new EmptyBorder(280, 300, 280, 300));

			label1 = new JLabel("Username:");
			text1 = new JTextField(15);

			label2 = new JLabel("Password:");
			text2 = new JPasswordField(8);

			submit = new JButton("Submit");
			
			label3 = new JLabel("");

			add(label1);
			add(text1);
			add(label2);
			add(text2);
			add(new JLabel());
			add(submit);
			add(label3);
			
			submit.addActionListener(this);
		}

		/**
		 * Gets the Text from the TextFields
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = text1.getText();
			String password = text2.getText();
			int result = users.existsUser(username, password);
			if (result == 0) {
				text1.setText("");
				text2.setText("");
				label3.setText("Wrong username or password!");
			} else if (result == 1) {
				// user - start the test with standard configuration
				main.nextModule();
			} else if (result == 2) {
				// admin - launch the admin panel
				main.startAdminPanel();
			}
		}
	}
}
