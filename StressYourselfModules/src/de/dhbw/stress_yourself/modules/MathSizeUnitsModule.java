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
 * The MathSizeUnitsModule class builds a module where user has to convert
 * numbers from kilobyte, megabyte or kilobyte in another size unit.
 * 
 * @author Philipp Willems
 */

public class MathSizeUnitsModule extends ModuleClass {
	private int result = 0;

	public static final String moduleName = "MathSizeUnitsModule";
	public static final String moduleArea = "Math";
	public static final String moduleDescription = "Transform the given value to the given size unit!";

	private static final int TIMEFOREXERCISE = 10000;
	private static int maxExercises;
	private int exercisesMade = 0;
	private int solvedCorrectly = 0;

	public MathSizeUnitsModule(Object o, Integer difficulty, Integer time) {
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
	 * @return String[] exercise exercise[0] is the number with the size unit
	 *         exercise[1] is the size unit to which the user should convert the
	 *         given number exercise[2] is the result of the conversion
	 * 
	 * @author Philipp Willems
	 */
	public String[] createExercise() {
		int range = 3;
		Random r = new Random();

		// sizeunit1: starting size unit, sizeunit2: ending size unit
		int sizeunit1 = r.nextInt(range);
		int sizeunit2;

		if (sizeunit1 != 1) {
			sizeunit2 = 1;
		} else {
			int sign = r.nextInt(1);

			if (sign == 1) {
				sizeunit2 = 0;
			} else {
				sizeunit2 = 2;
			}
		}

		int factor = sizeunit1 < sizeunit2 ? 1024 : 1;

		int value = (r.nextInt(4) + 2 + getDifficulty() * 2) * factor;

		String[] exercise = new String[3];

		// case differentiation: fill the String[] exercise with the associated
		// values and transform the starting value to the solution
		switch (sizeunit1) {
		// sizeunit1 is kilobyte
		case 0:
			exercise[0] = "Given: " + value + " kb"; // exercise

			exercise[1] = "In mb:";
			exercise[2] = String.valueOf(value / 1024); // solution
			break;
		// sizeunit1 is megabyte
		case 1:
			exercise[0] = "Given: " + value + " mb"; // exercise

			if (sizeunit2 == 0) {
				exercise[1] = "In kb:";
				exercise[2] = String.valueOf(value * 1024); // solution
			} else {
				exercise[1] = "In gb:";
				exercise[2] = String.valueOf(value / 1024); // solution
			}
			break;
		// sizeunit1 is gigabyte
		default:
			exercise[0] = "Given: " + value + " gb"; // exercise

			exercise[1] = "In mb:";
			exercise[2] = String.valueOf(value * 1024); // solution
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
			solutionText.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						nextExerciseButton.doClick();
					}
				}
			});

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

		public class NextModule extends TimerTask {
			@Override
			public void run() {
				sendResult(result);
				tellFinished();
			}
		}
	}
}
