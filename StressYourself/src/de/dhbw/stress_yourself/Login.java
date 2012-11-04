package de.dhbw.stress_yourself;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login {
	/**
	 * 
	 * defines the GUI for the Login 
	 *
	 */
	
	class LoginGUI extends JPanel implements ActionListener {
		
		JButton submit;
		JPanel panel;
		JLabel label1,label2;
		JTextField text1, text2;
		
		public void init(){
			
			label1 = new JLabel();
			label1.setText("Username:");
			text1 = new JTextField(15);

			label2 = new JLabel();
			label2.setText("Password:");
			text2 = new JPasswordField(8);
			 
			submit=new JButton("SUBMIT");
			panel=new JPanel(new GridLayout(3,1));
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
		}
		
		
		
	}
	 
		
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	
	public int login(String username, String password){
	//	Userdata.
	//	if (username.existsUser = 0 && password.existsUser = 0) {
	// 		JOptionPane.showMessageDialog(null, "Login Successfull");
	// 	else
	// 		JOptionPane.showMessageDialog(null, "Eingabe fehlerhaft! Username oder Passwort falsch");
		return 0;
	}

	
	

}
