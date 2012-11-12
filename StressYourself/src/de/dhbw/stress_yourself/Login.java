package de.dhbw.stress_yourself;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
		private JLabel headerLabel;
		private JLabel usernameLabel;
		private JLabel passwordLabel;
		private JLabel errorLabel;
		private JLabel middleLabel;
		private JLabel copyLabel;
		private JLabel teamLabel;
		private JTextField usernameText;
		private JTextField passwordText;

		public LoginGUI() {
			init();
		}

		private void init() {
			usernameLabel = new JLabel("Username:");
			usernameText = new JTextField(10);
			passwordLabel = new JLabel("Password:");
			passwordText = new JPasswordField(10);
			submit = new JButton("Submit");
			errorLabel = new JLabel("");
			headerLabel = new JLabel("StressYourself");
			middleLabel = new JLabel("Informatikertest");
			copyLabel = new JLabel("\u00A9StressYourself 2012");

			teamLabel = new JLabel("Christoph Schollmeyer, Florian Albert, Lukas Buchert, Moritz Herbert, Philipp Willems, Tobias Roeding");
			
			
			headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
			headerLabel.setFont(new Font("Zapfino", Font.BOLD | Font.ITALIC, 47));
			
			middleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			middleLabel.setFont(new Font("Zapfino", Font.PLAIN, 23));
			
			teamLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			copyLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			errorLabel.setHorizontalAlignment(SwingConstants.LEFT);
			
			GroupLayout groupLayout = new GroupLayout(this);

			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(299)
								.addComponent(middleLabel, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE))
							.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(350)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(usernameLabel)
									.addComponent(passwordLabel))
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(errorLabel, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(passwordText, Alignment.LEADING)
										.addComponent(submit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
									.addComponent(usernameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(242))
							.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(copyLabel, GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE))
							.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(teamLabel, GroupLayout.PREFERRED_SIZE, 893, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap())
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addGap(40)
						.addComponent(headerLabel, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
						.addGap(53)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(usernameLabel)
							.addComponent(usernameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(passwordLabel)
							.addComponent(passwordText, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(submit)
						.addGap(48)
						.addComponent(errorLabel)
						.addGap(57)
						.addComponent(middleLabel)
						.addGap(60)
						.addComponent(copyLabel)
						.addGap(18)
						.addComponent(teamLabel)
						.addGap(36))
			);
			setLayout(groupLayout);
 
			usernameText.addKeyListener(this);
			passwordText.addKeyListener(this);

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
			username = usernameText.getText();
			password = passwordText.getText();
			UserType result = users.existsUser(username, password);

			switch (result) {
			case NOT:
				usernameText.setText("");
				passwordText.setText("");
				errorLabel.setText("Wrong username or password! ");
				errorLabel.setForeground(Color.RED);
				break;
			case USER:
				main.nextModule();
				main.startAnnoyance();
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
			login(usernameText.getText(), passwordText.getText());
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		/**
		 * submits the Login when the ENTER-Key is pressed
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				login(usernameText.getText(), passwordText.getText());
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
}
