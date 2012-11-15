package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.ButtonGroup;
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

	private final String moduleName = "Operand Module";
	private final String moduleArea = "Logic";
	private final String moduleDescription = "In the operand module, you have to solve various mathematical and logic equations with a solution of true or false.";

	private final String[] arithmeticOperators = { "+", "-", "*", "/", "%" };
	private final String[] compareOperators = { "==", "!=", "<", ">", "<=",
			">=" };
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

	/**
	 * Inits the Test variables, like time per operand, the number of tests and
	 * the test counter
	 */
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

	/**
	 * Function to create a random number between high and low
	 * 
	 * @param high
	 *            The maximal the number can be
	 * @param low
	 *            The minimal the number musst be
	 * @return int Returns the random number
	 */
	private int randomNumber(int high, int low) {
		return (int) (Math.random() * (high - low) + low);
	}

	/**
	 * Generates a calculation with an random arithmetic operator and with a
	 * given number of numbers
	 * 
	 * @param number
	 *            Count of numbers in the calculation
	 * @return String Returns the calculation as string
	 */
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

	/**
	 * Generates a number of calculations with an random compare operator
	 * between them
	 * 
	 * @param number
	 *            Count of calculations compared
	 * @return String Returns the calculation as string
	 */
	private String generateCalculationComparison(int number) {
		String result = "";
		result += generateCalculation(number);
		result += compareOperators[randomNumber(compareOperators.length, 0)];
		result += generateCalculation(number);
		return result;
	}

	/**
	 * Generate a calculation on level easy
	 * 
	 * @return String Returns the calculation
	 */
	private String generateEasyCalculation() {
		return generateCalculationComparison(2);
	}

	/**
	 * Generate a calculation on level medium
	 * 
	 * @return String Returns the calculation
	 */
	private String generateMediumCalculation() {
		String result = "";
		result += generateCalculationComparison(1);
		result += logicalOperators[randomNumber(logicalOperators.length - 1, 0)];
		result += generateCalculationComparison(1);
		return result;
	}

	/**
	 * Generate a calculation on level hard
	 * 
	 * @return String Returns the calculation
	 */
	private String generateHardCalculation() {
		String result = "";
		result += generateCalculationComparison(2);
		result += logicalOperators[randomNumber(logicalOperators.length - 1, 0)];
		result += generateCalculationComparison(2);
		return result;
	}

	/**
	 * This function is called by the panel to create a new test with the
	 * outcome true or false
	 * 
	 * @param difficulty
	 *            The specified difficulty of the test 0-easy,1-medium,2-hard
	 * @return String Returns the generated calculation as string
	 */
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

	/**
	 * This function evaluates the calculation given as string and returns the
	 * result
	 * 
	 * @param test
	 *            The specified calculation
	 * @return Object Returns an object, which is true or false
	 */
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
	class ModuleGUI extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1L;
		private String runningTest = "";
		private JRadioButton radioTrue;
		private JRadioButton radioFalse;
		private ButtonGroup radioGroup;

		public ModuleGUI() {
			setLayout(null);
			setBounds(0, 0, 900, 700);
			this.add(getIntroductionPanel(timePerOperandTest, numberOfTests,
					this));
		}

		public JPanel createTestPanel(ActionListener al) {
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
			radioTrue.addActionListener(al);
			testPanel.add(radioTrue);

			radioFalse.setBounds(400, 250, 75, 30);
			radioFalse.addActionListener(al);
			testPanel.add(radioFalse);

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
				result = calculateResult(numberOfTests, solvedCorrectly);
				sendResult(result);
				tellFinished();
			}
		}

		/**
		 * actionPeformed handles all Button Action
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(radioTrue)
					|| e.getSource().equals(radioFalse)) {
				boolean evaluationResult = (boolean) evaluateTest(runningTest);

				if (evaluationResult && radioTrue.isSelected()) {
					solvedCorrectly += 1;
				} else if (!evaluationResult && radioFalse.isSelected()) {
					solvedCorrectly += 1;
				}

				testCounter--;
				if (testCounter == 0) {
					result = calculateResult(numberOfTests, solvedCorrectly);
					sendResult(result);
					tellFinished();
				}
				addTestPanel();
			} else {
				startTest();
				addTestPanel();
			}
		}
	}
}
