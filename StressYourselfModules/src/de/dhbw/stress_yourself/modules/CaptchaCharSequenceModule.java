package de.dhbw.stress_yourself.modules;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * Module to create captchas where the user has to repeat a sequence of
 * characters
 * 
 * @author Moritz Herbert <moritz.herbert@gmx.de>
 */
public class CaptchaCharSequenceModule extends ModuleClass {

	private final String moduleName = "CaptchaCharSequenceModule";
	private final String moduleArea = "Concentration";
	private final String moduleDescription = "Example Description";

	private ArrayList<Boolean> results = new ArrayList<Boolean>();
	private int captchaCount = 1;
	private int nextTaskIntervall = 5000;

	public CaptchaCharSequenceModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		setTimerIntervall();
	}
	
	public void setTimerIntervall() {
		switch(getDifficulty()) {
		case(0):
			break;
		case(1):
			nextTaskIntervall = 4000;
			break;
		case(2):
			nextTaskIntervall = 3000;
			break;
		}
	}

	public JPanel getModuleJPanel() {
		return new ModuleGUI();
	}

	@Override
	public String getModuleName() {
		return moduleName;
	}

	@Override
	public String getModuleArea() {
		return moduleArea;
	}

	@Override
	public String getModuleDescription() {
		return moduleDescription;
	}

	/**
	 * This method calculates whether coordinates of mouse click are in open
	 * circle with help of the Pythagorean theorem
	 * 
	 * @param typedSequence
	 *            contains contains the String typed in by the user
	 * @param displayedSequence
	 *            contains the sequence displayed in the captcha picture
	 */
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
		private JPanel thisPanel = this;

		public ModuleGUI() {
			buttons = new ArrayList<JButton>();
			init();
			setLayout(null);
			setBounds(0, 0, 800, 600);
		}

		public RandomSequence createCaptcha() {
			RandomSequence captcha = new RandomSequence(getDifficulty());

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
			setNextTaskTimer(nextTaskIntervall, nextTaskIntervall, new NextTask());
			setNextModuleTimer(getTime(), new NextModule());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (buttons.indexOf(e.getSource())) {
			case 0:// nextCaptchaButton
				resetNextTaskTimer(nextTaskIntervall, nextTaskIntervall, new NextTask());
				isValidSequence(captchaText.getText(), c.getSequence());
				this.remove(c);
				c = createCaptcha();
				this.revalidate();
				captchaCount++;
				break;
			case 1:// nextModuleButton
				sendResult();
				tellFinished();
				break;
			}
		}
		
		public class NextTask extends TimerTask {
			@Override
			public void run() {
				thisPanel.remove(c);
				c = createCaptcha();
				thisPanel.revalidate();
				captchaCount++;
				
			}
		}
		
		public class NextModule extends TimerTask {
			@Override
			public void run() {
				sendResult();
				tellFinished();
			}
		}
	}

	
	
	
	/**
	 * This class contains a blueprint for a canvas which draws a random
	 * character sequence used as a captcha test
	 * 
	 * @author Moritz Herbert <moritz.herbert@gmx.de>
	 */
	public class RandomSequence extends Canvas {
		private static final long serialVersionUID = 1L;

		private int difficulty = 0;
		private String sequence = "";
		Random r = new Random();

		public RandomSequence(int difficulty) {
			this.difficulty = difficulty;
		}

		public char getRandomChar() {
			String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			char rand = validChars.charAt(r.nextInt(validChars.length()));

			return rand;
		}

		public int randomNumber(int lowest, int highest) {
			highest++;
			return (int) (Math.random() * (highest - lowest) + lowest);
		}

		public String getSequence() {
			return sequence;
		}

		public void paint(Graphics g) {
			int charCount = 6, incrementationStep, x;
			char randomChar;

			switch (difficulty) {
			case (0):
				break;
			case (1):
				charCount = 8;
				break;
			case (2):
				charCount = 10;
				break;
			}

			x = incrementationStep = 300 / charCount;
			x /= 2;

			g.setFont(new Font("Monotype Corsiva", Font.PLAIN, 23));

			// get random string and paint it char by char
			for (int i = 0; i < charCount; i++) {
				// get random char
				randomChar = getRandomChar();

				// paint background graphics
				for (int j = 0; j < 2; j++) {
					g.setColor(new Color(r.nextInt(255), r.nextInt(255), r
							.nextInt(255)).darker().darker());

					g.drawLine(randomNumber(0, 300), randomNumber(0, 100),
							randomNumber(0, 300), randomNumber(0, 100));
				}

				// paint random char
				g.setColor(new Color(r.nextInt(255), r.nextInt(255), r
						.nextInt(255)).brighter().brighter());

				g.drawString(String.valueOf(randomChar),
						randomNumber(x - 10, x - 6), randomNumber(15, 95));

				sequence += randomChar;

				x += incrementationStep;
			}
		}
	}
}
