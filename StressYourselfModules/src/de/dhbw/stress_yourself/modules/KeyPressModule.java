package de.dhbw.stress_yourself.modules;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * Module which gives characters or numbers on the screen and the user should
 * press the keys on the keyboard as fast as he can.
 * 
 * @author Christoph Schollmeyer <chr.schollmeyer@web.de>
 * @author Moritz Herbert <moritz.herbert@gmx.de>
 */
public class KeyPressModule extends ModuleClass {

	private final String moduleName = "KeyPress";
	private final String moduleArea = "Reaction";
	private final String moduleDescription = "Repeat the key displyed on the screen as fast as possible.";

	private int testCounter;
	private int numberOfTests;
	private int timePerTest;
	private int solvedCorrectly = 0;
	private int result;

	public KeyPressModule(Object o, Integer difficulty, Integer time) {
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
			timePerTest = 4000;
			break;
		case 1:
			timePerTest = 2500;
			break;
		case 2:
			timePerTest = 1500;
			break;
		}
		numberOfTests = (getTime() / timePerTest);
		testCounter = numberOfTests;
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

	@Override
	public JPanel getModuleJPanel() {
		return new ModuleGUI();
	}

	class ModuleGUI extends JPanel implements KeyListener, ActionListener {

		private static final long serialVersionUID = 1L;
		private final String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		private JLabel keyField = new JLabel();
		private JPanel thisPanel = this;
		private JPanel introductionPanel = null;

		public ModuleGUI() {
			init();
			setLayout(null);
			setBounds(0, 0, 900, 700);
		}

		public void init() {
			introductionPanel = getIntroductionPanel(timePerTest,
					numberOfTests, this);
			thisPanel.add(introductionPanel);

		}

		public void startTask() {
			thisPanel.setFocusable(true);
			thisPanel.requestFocusInWindow();
			keyField.setBounds(415, 315, 50, 50);
			keyField.setFont(new Font("Arial", Font.PLAIN, 30));
			this.addKeyListener(this);
			this.add(keyField);
			this.requestFocus();
			setNextModuleTimer(getTime(), new NextModule());
		}

		private void getNewRandomKey() {
			int rnd = new Random().nextInt(35);
			keyField.setText(character.substring(rnd, rnd + 1));
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (KeyEvent.getKeyText(e.getKeyCode()).equals(keyField.getText())) {
				System.out.println("heyy");
				if (testCounter >= 1) {
					solvedCorrectly++;
					System.out.println(solvedCorrectly);
					getNewRandomKey();
					this.revalidate();
					this.repaint();
					
					testCounter--;
				} else {
					result = calculateResult(numberOfTests, solvedCorrectly);
					sendResult(result);
					tellFinished();
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			thisPanel.removeAll();
			startTask();
			getNewRandomKey();
			thisPanel.revalidate();
			thisPanel.repaint();
		}
		
		public class NextModule extends TimerTask {
			@Override
			public void run() {
				result = calculateResult(numberOfTests, solvedCorrectly);
				sendResult(result);
				tellFinished();
			}
		}

	}

}
