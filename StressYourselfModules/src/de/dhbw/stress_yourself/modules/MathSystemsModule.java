package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * The MathSystemsModule class builds a module where user has to convert numbers
 * from decimal, hexadecimal or binary in another number system.
 * 
 * @author Philipp Willems
 */

public class MathSystemsModule extends ModuleClass {
	private int result = 0;

	public static final String moduleName = "MathSystemsModule";
	public static final String moduleArea = "Math";
	public static final String moduleDescription = "Transform the given value to the given number system!";

	private static final int TIMEFOREXERCISE = 10000;
	private static int maxExercises;
	private int exercisesMade = 0;
	private int solvedCorrectly = 0;

	public MathSystemsModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		setMaxExercises(time);
	}

	public void setMaxExercises(Integer time) {
		maxExercises = time / TIMEFOREXERCISE;
	}

	public JPanel getModuleJPanel() {
		return new moduleGUI();
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
	 * Creates an exercise for this module
	 * 
	 * @return String[] exercise exercise[0] is the number with the number
	 *         system exercise[1] is the number system to which the user should
	 *         convert the given number exercise[2] is the result of the
	 *         conversion
	 * 
	 * @author Philipp Willems
	 */
	public String[] createExercise() {
		int range = 3;
		Random r = new Random();

		// generate a random value which represents the value to be converted by
		// the user
		int value = (int) r.nextInt(10) * (getDifficulty() + 1) + 10;

		// numbersystem1: starting number system, numbersystem2: ending number
		// system
		int numbersystem1 = r.nextInt(range);
		int numbersystem2 = r.nextInt(range);

		// ensure that the ending number system is different from the starting
		// number system
		while (numbersystem1 == numbersystem2) {
			numbersystem2 = r.nextInt(range);
		}

		String[] exercise = new String[3];

		// case differentiation: fill the String[] exercise with the associated
		// values and transform the starting value to the solution
		switch (numbersystem1) {
		// numbersystem1 is binary
		case 0:
			exercise[0] = "Given: " + Integer.toBinaryString(value) + " binary"; // exercise

			if (numbersystem2 == 1) {
				exercise[1] = "In hexadecimal:";
				exercise[2] = Integer.toHexString(value); // solution
			} else {
				exercise[1] = "In Decimal:";
				exercise[2] = String.valueOf(value); // solution
			}
			break;
		// numbersystem1 is hexadecimal
		case 1:
			exercise[0] = "Given: " + Integer.toHexString(value)
					+ " hexadecimal"; // exercise

			if (numbersystem2 == 0) {
				exercise[1] = "In binary:";
				exercise[2] = Integer.toBinaryString(value); // solution
			} else {
				exercise[1] = "In decimal:";
				exercise[2] = String.valueOf(value); // solution
			}
			break;
		// numbersystem1 is decimal
		default:
			exercise[0] = "Given: " + value + " decimal"; // exercise

			if (numbersystem2 == 1) {
				exercise[1] = "In hexadecimal:";
				exercise[2] = Integer.toHexString(value); // solution
			} else {
				exercise[1] = "In binary:";
				exercise[2] = Integer.toBinaryString(value); // solution
			}
			break;
		}

		return exercise;
	}

	/**
	 * The moduleGUI class builds the user interface for the module. The
	 * moduleGUI represents a JPanel in which other components like buttons are
	 * added.
	 * 
	 * @author Philipp Willems
	 */
	class moduleGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;
		private String[] exercise = null;
		private JTextField solutionText = new JTextField();
		private JLabel givenLabel = new JLabel();
		private JLabel solutionLabel = new JLabel();
		private JButton nextExerciseButton = new JButton("Next exercise");
		private JPanel introductionPanel = null;
		private JPanel thisPanel = this;

		public moduleGUI() {
			buttons = new ArrayList<JButton>();
			init();
			this.setLayout(null);
		}

		public void registerButton(JButton button) {
			buttons.add(button);
		}

		public void initExercise() {
			exercise = createExercise();

			solutionText.setText("");

			givenLabel.setText(exercise[0]);
			solutionLabel.setText(exercise[1]);
		}

		public void init() {
			introductionPanel = getIntroductionPanel(TIMEFOREXERCISE,
					maxExercises, this);
			thisPanel.add(introductionPanel);
		}

		public void startExercise() {
			initExercise();

			givenLabel.setBounds(320, 220, 200, 20);
			solutionLabel.setBounds(320, 245, 100, 20);
			solutionText.setBounds(430, 245, 100, 20);

			this.add(givenLabel);
			this.add(solutionLabel);
			this.add(solutionText);

			registerButton(nextExerciseButton);
			nextExerciseButton.addActionListener(this);
			nextExerciseButton.setBounds(320, 270, 140, 30);
			this.add(nextExerciseButton);

			setNextModuleTimer(getTime(), new NextModule());
		}

		/**
		 * Called when a button is pressed
		 * 
		 * @param ActionEvent
		 *            e Event when button is pressed
		 * 
		 * @author Philipp Willems
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (buttons.contains(e.getSource())) {
				switch (buttons.indexOf(e.getSource())) {
				case 0:
					exercisesMade++;

					// increment result when the solution given by the user was
					// right
					if (solutionText.getText().equals(exercise[2])) {
						solvedCorrectly++;
					}

					if (exercisesMade < maxExercises) {
						initExercise();
						this.revalidate();
					} else {
						result = calculateResult(maxExercises, solvedCorrectly);
						sendResult(result);
						tellFinished();
					}

					break;
				}
			} else {
				thisPanel.removeAll();
				startExercise();
				thisPanel.repaint();
			}
		}

		public class NextTask extends TimerTask {
			@Override
			public void run() {

			}
		}

		public class NextModule extends TimerTask {
			@Override
			public void run() {
				sendResult(result);
				tellFinished();
			}
		}
	}
}
