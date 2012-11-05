package de.dhbw.stress_yourself.modules;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;
import de.dhbw.stress_yourself.extend.RandomCircles;
import de.dhbw.stress_yourself.extend.RandomSequence;

public class CaptchaCharSequenceModule extends ModuleClass {

	public static final String moduleName = "CaptchaCharSequenceModule";
	public static final String moduleArea = "Concentration";
	public static final String moduleDescription = "Example Description";

	private int diff = 0;
	private String time = "";
	private ArrayList<Boolean> results = new ArrayList<Boolean>();

	private String typedSequence, displayedSequence;

	public CaptchaCharSequenceModule(Object o) {
		super(o);
	}

	public JPanel getModuleJPanel() {
		return new ModuleGUI();
	}

	@Override
	public void setDifficulty(int diff) {
		this.diff = diff;
	}

	@Override
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * This method calculates whether coordinates of mouse click are in open
	 * circle with help of the Pythagorean theorem
	 * 
	 * @param typedSequence
	 *            contains contains the String typed in by the user
	 * @param displayedSequence
	 *            contains the sequence displayed in the captcha picture
	 * */
	public void isValidSequence(String typedSequence, String displayedSequence) {
		if (typedSequence.equals(displayedSequence)) {
			System.out.println("richtig");
			results.add(true);
		} else {
			System.out.println("falsch");
			results.add(false);
		}

	}

	class ModuleGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;
		private JTextPane pane = new JTextPane();
		private JTextField captchaText = new JTextField();
		private JButton nextCaptchaButton = new JButton("next captcha");
		private JButton nextModuleButton = new JButton("next module");
		private RandomSequence c;

		public ModuleGUI() {
			buttons = new ArrayList<JButton>();
			init();
			setLayout(null);
			setBounds(0, 0, 800, 600);
		}

		public RandomSequence createCaptcha() {
			RandomSequence captcha = new RandomSequence(diff);
			
			captcha.setBounds(50, 100, 300, 100);
			captcha.setBackground(Color.darkGray);
			this.add(captcha);
			return captcha;
		}

		public void registerButton(JButton button) {
			buttons.add(button);
		}

		public void init() {
			c = createCaptcha();
			pane.setText("CaptchaCharSequenceModule");
			pane.setBounds(50, 50, 175, 30);
			this.add(pane);

			captchaText.setBounds(50, 210, 150, 30);
			this.add(captchaText);

			nextCaptchaButton.setBounds(220, 210, 70, 30);
			registerButton(nextCaptchaButton);
			nextCaptchaButton.addActionListener(this);
			this.add(nextCaptchaButton);

			nextModuleButton.setBounds(230, 50, 120, 30);
			registerButton(nextModuleButton);
			nextModuleButton.addActionListener(this);
			this.add(nextModuleButton);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (buttons.indexOf(e.getSource())) {
			case 0:// nextCaptchaButton
				isValidSequence(captchaText.getText() ,c.getSequence());
				this.remove(c);
				c = createCaptcha();
				this.revalidate();
				break;
			case 1:// nextModuleButton
				sendResult();
				tellFinished();
				break;
			}
		}
	}
}
