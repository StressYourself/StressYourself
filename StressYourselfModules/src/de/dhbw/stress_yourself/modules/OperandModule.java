package de.dhbw.stress_yourself.modules;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * Module to create operand tests
 * 
 * @author Tobias Roeding <tobias@roeding.eu>
 */
public class OperandModule extends ModuleClass {

	private final String moduleName = "OperandModule";
	private final String moduleArea = "Example Area";
	private final String moduleDescription = "Example Description";

	private final String[] arithmeticOperators = { "+", "-", "*", "/", "%" };
	private final String[] compareOperators = { "==", "!=", "<", ">", "<=",
			">=" };
	private final String[] incdecOperators = { "++", "--" };
	private final String[] logicalOperators = { "&&", "&", "||", "|", "^" };

	private int timePerOperandTest = 0;

	private int testCounter;
	private int numberOfTests;
	private int solvedCorrectly = 0;
	private int result;

	public OperandModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		initTestValues();
	}

	public void initTestValues() {
		switch (getDifficulty()) {
		case 0:
			timePerOperandTest = 5000;
			break;
		case 1:
			timePerOperandTest = 7000;
			break;
		case 2:
			timePerOperandTest = 9000;
			break;
		}
		numberOfTests = (getTime() / timePerOperandTest);
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

	private String generateNumberWithIncDec(int high, int low, int incdec) {
		String number = String.valueOf(randomNumber(high, low));
		if (incdec == 1) {
			return incdecOperators[randomNumber(incdecOperators.length - 1, 0)]
					+ number;
		} else if (incdec == 2) {
			return number
					+ incdecOperators[randomNumber(incdecOperators.length - 1,
							0)];
		} else {
			return number;
		}
	}

	private int randomNumber(int high, int low) {
		return (int) (Math.random() * (high - low) + low);
	}

	private String generateCalculation(int number) {
		String result = "";
		for (int i = 1; i <= number; i++) {
			result += String.valueOf(randomNumber(9, 1));
			result += arithmeticOperators[randomNumber(
					arithmeticOperators.length - 1, 0)];
		}
		result += String.valueOf(randomNumber(9, 1));
		return result;
	}

	private String generateCalculationComparison(int number) {
		String result = "";
		result += generateCalculation(number);
		result += compareOperators[randomNumber(compareOperators.length, 0)];
		result += generateCalculation(number);
		return result;
	}

	private String generateEasyCalculation() {
		return generateCalculationComparison(2);
	}

	private String generateMediumCalculation() {
		String result = "";
		result += generateCalculationComparison(1);
		result += logicalOperators[randomNumber(logicalOperators.length - 1, 0)];
		result += generateCalculationComparison(1);
		return result;
	}

	private String generateHardCalculation() {
		String result = "";
		result += generateCalculationComparison(2);
		result += logicalOperators[randomNumber(logicalOperators.length - 1, 0)];
		result += generateCalculationComparison(2);
		return result;
	}

	public String generateTest(int difficulty) {
		String result = "";
		String solution = "";
		do {
			switch (difficulty) {
			case 0:
				result = generateEasyCalculation();
				break;
			case 1:
				result = generateMediumCalculation();
				break;
			case 2:
				result = generateHardCalculation();
				break;
			}
			solution = String.valueOf(evaluateTest(result));
		} while ((solution != "false") && (solution != "true"));
		return result;
	}

	public Object evaluateTest(String test) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		try {
			return engine.eval(test);
		} catch (ScriptException e) {
			System.err.println("Couldn't evaluate the String " + e);
		}
		return null;
	}

	/**
	 * this class is responsible for building the JPanel which will be inserted
	 * in the main JFrame.
	 */
	class ModuleGUI extends JPanel implements MouseListener {

		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;
		private String runningTest = "";
		private JRadioButton radioTrue;
		private JRadioButton radioFalse;
		private ButtonGroup radioGroup;
		private JButton checkButton = new JButton("Check");

		public ModuleGUI() {
			buttons = new ArrayList<JButton>();
			setLayout(null);
			setBounds(0, 0, 900, 700);
			this.add(createIntroductionPanel(this));
		}

		public JPanel createTestPanel(MouseListener ml) {
			runningTest = generateTest(getDifficulty());
			JPanel testPanel = new JPanel();
			JLabel operandTest = new JLabel(runningTest);
			radioTrue = new JRadioButton("true");
			radioFalse = new JRadioButton("false");
			radioGroup = new ButtonGroup();
			radioGroup.add(radioTrue);
			radioGroup.add(radioFalse);

			testPanel.setLayout(null);
			testPanel.setBounds(0, 0, 900, 700);

			operandTest.setBounds(300, 200, 600, 30);
			testPanel.add(operandTest);

			radioTrue.setBounds(300, 250, 75, 30);
			testPanel.add(radioTrue);

			radioFalse.setBounds(400, 250, 75, 30);
			testPanel.add(radioFalse);

			checkButton.setBounds(350, 300, 100, 30);
			registerButton(checkButton);
			if (checkButton.getMouseListeners().length < 2) {
				checkButton.addMouseListener(ml);
			}

			testPanel.add(checkButton);
			return testPanel;
		}

		public void addTestPanel() {
			this.removeAll();
			this.invalidate();
			this.add(createTestPanel(this));
			this.revalidate();
			this.repaint();
		}

		/**
		 * Function to create the Introduction Panel
		 * 
		 * @param al
		 *            ActionListener for the startTestButton
		 * @return JPanel Returns the introduction JPanel
		 * @author Tobias Roeding <tobias@roeding.eu>
		 */
		public JPanel createIntroductionPanel(MouseListener ml) {
			JPanel introductionPanel = new JPanel();
			introductionPanel.setLayout(null);
			JLabel moduleDescriptionLabel = new JLabel(getModuleDescription());
			JLabel moduleTimeLabel = new JLabel("Maximum time for module: "
					+ String.valueOf(numberOfTests / 1000) + "seconds");
			JLabel moduleDesIntervallLabel = new JLabel(
					"Maximum time per Task: " + String.valueOf(testCounter)
							+ "seconds");
			JLabel taskCountLabel = new JLabel(numberOfTests
					+ " tasks can be solved");
			JButton startTestButton = new JButton("start");

			introductionPanel.setLayout(null);
			introductionPanel.setBounds(0, 0, 800, 600);

			moduleDescriptionLabel.setBounds(300, 50, 300, 100);
			introductionPanel.add(moduleDescriptionLabel);

			moduleTimeLabel.setBounds(300, 155, 300, 30);
			introductionPanel.add(moduleTimeLabel);

			moduleDesIntervallLabel.setBounds(300, 190, 300, 30);
			introductionPanel.add(moduleDesIntervallLabel);

			taskCountLabel.setBounds(300, 225, 300, 30);
			introductionPanel.add(taskCountLabel);

			startTestButton.setBounds(400, 260, 75, 30);
			introductionPanel.add(startTestButton);
			registerButton(startTestButton);
			startTestButton.addMouseListener(ml);

			return introductionPanel;
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
			if (!buttons.contains(button)) {
				buttons.add(button);
			}
		}

		/**
		 * This method generates the GUI displaying the module tasks
		 */

		public void startTest() {
			setNextModuleTimer(getTime(), new NextModule());
		}

		/**
		 * An instance of this class will be created and run as a kind of event
		 * every time the timer for the next module counted down to the end.
		 */
		public class NextModule extends TimerTask {
			@Override
			public void run() {
				calculateResult();
				sendResult(result);
				tellFinished();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(buttons.get(0))) {
				startTest();
				addTestPanel();
			} else {
				boolean evaluationResult = (boolean) evaluateTest(runningTest);
				if (evaluationResult && radioTrue.isSelected()) {
					solvedCorrectly += 1;
				} else if (!evaluationResult && radioFalse.isSelected()) {
					solvedCorrectly += 1;
				}
				System.out.println("solvedCorrectly " + solvedCorrectly);
				testCounter--;
				if (testCounter == 0) {
					calculateResult();
					sendResult(result);
					tellFinished();
				}
				addTestPanel();
			}
		}

		private void calculateResult() {
			double pointsPerTest = 100 / numberOfTests;
			double points = pointsPerTest * solvedCorrectly;
			result = (int) points;
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	@Override
	public void setTimerIntervall() {
		// TODO Auto-generated method stub

	}
}
