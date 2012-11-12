package de.dhbw.stress_yourself.modules;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private final String moduleDescription = "Repeat the sequence of characters displayed in the picture.";

	private int testCounter;
	private int numberOfTests;
	private int timePerTest;
	private int solvedCorrectly = 0;
	private int result;

	public CaptchaCharSequenceModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		initTestValues();
	}

	/**
	 * This method set the time per task in dependency of the difficulty. It
	 * also sets the amount of tasks that can be solved in the given time and
	 * the counter which is responsible for counting down the remaining tasks.
	 */
	public void initTestValues() {
		switch (getDifficulty()) {
		case 0:
			timePerTest = 14000;
			break;
		case 1:
			timePerTest = 12000;
			break;
		case 2:
			timePerTest = 10000;
			break;
		}
		numberOfTests = (getTime() / timePerTest);
		testCounter = numberOfTests;
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
			solvedCorrectly++;
		} else {
			System.out.println("falsch");
		}
	}

	/**
	 * this class is responsible for building the JPanel which will be inserted
	 * in the main JFrame.
	 */
	class ModuleGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;

		private JTextField captchaText = new JTextField();
		private JButton nextCaptchaButton = new JButton("check");
		private RandomSequence c;
		private JPanel thisPanel = this;
		private JPanel introductionPanel = null;

		public ModuleGUI() {
			buttons = new ArrayList<JButton>();
			init();
			setLayout(null);
			setBounds(0, 0, 900, 700);
		}

		/**
		 * Creates a new captcha canvas ands sets the basic properties like size
		 * and position.
		 * 
		 * @return The created captcha canvas.
		 */
		public RandomSequence createCaptcha() {
			RandomSequence captcha = new RandomSequence(getDifficulty());

			captcha.setBounds(300, 100, 300, 100);
			captcha.setBackground(Color.darkGray);
			this.add(captcha);
			return captcha;
		}

		/**
		 * Adds a button to a ArrayList needed to say which part of the
		 * switch-case block in the actionPerformed Method is used for which
		 * button.
		 * 
		 * @param button
		 *            The button, which has to be registered
		 */
		public void registerButton(JButton button) {
			buttons.add(button);
		}

		/**
		 * This method displays the panel which shows the property of the module
		 * before the module starts. a click on the startTasksButton button
		 * starts the module and calls startTask()
		 */
		public void init() {
			introductionPanel = getIntroductionPanel(timePerTest,
					numberOfTests, this);
			thisPanel.add(introductionPanel);
		}

		/**
		 * This method generates the GUI displaying the module tasks
		 */
		public void startTask() {
			c = createCaptcha();

			captchaText.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						nextCaptchaButton.doClick();
					}
				}
			});
			captchaText.setBounds(300, 210, 150, 20);
			this.add(captchaText);

			nextCaptchaButton.setBounds(490, 210, 110, 20);
			registerButton(nextCaptchaButton);
			nextCaptchaButton.addActionListener(this);
			this.add(nextCaptchaButton);

			setNextModuleTimer(getTime(), new NextModule());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (buttons.contains(e.getSource())) {
				switch (buttons.indexOf(e.getSource())) {
				case 0:
					if (testCounter >= 1) {
						isValidSequence(captchaText.getText(), c.getSequence());
						thisPanel.remove(c);
						c = createCaptcha();
						captchaText.setText("");
						captchaText.requestFocus();
						thisPanel.revalidate();
						testCounter--;
					} else {
						result = calculateResult(numberOfTests, solvedCorrectly);
						sendResult(result);
						tellFinished();
					}
					break;
				}
			} else {
				thisPanel.removeAll();
				startTask();
				thisPanel.repaint();
			}
		}

		/**
		 * An instance of this class will be created and run as a kind of event
		 * every time the timer for the next module counted down to the end.
		 */
		public class NextModule extends TimerTask {
			@Override
			public void run() {
				result = calculateResult(numberOfTests, solvedCorrectly);
				sendResult(result);
				tellFinished();
			}
		}
	}

	/**
	 * This class contains a blueprint for a canvas which draws a random
	 * character sequence used as a captcha test
	 * 
	 */
	public class RandomSequence extends Canvas {
		private static final long serialVersionUID = 1L;

		private int difficulty = 0;
		private String sequence = "";
		Random r = new Random();

		public RandomSequence(int difficulty) {
			this.difficulty = difficulty;
		}

		/**
		 * This method provides a random function
		 * 
		 * @return a random chosen char of the String validChars
		 */

		public char getRandomChar() {
			String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			char rand = validChars.charAt(r.nextInt(validChars.length()));

			return rand;
		}

		/**
		 * This method generates a random number of a given range
		 * 
		 * @param lowest
		 *            provides the downwards limit of the range
		 * @param highest
		 *            provides the upwards limit of the range
		 * @return the random number
		 */

		public int randomNumber(int lowest, int highest) {
			highest++;
			return (int) (Math.random() * (highest - lowest) + lowest);
		}

		public String getSequence() {
			return sequence;
		}

		/**
		 * This method is called automatically every time a new instance of this
		 * class is created. It draws the random alphanumeric sequence into the
		 * canvas.
		 */

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
