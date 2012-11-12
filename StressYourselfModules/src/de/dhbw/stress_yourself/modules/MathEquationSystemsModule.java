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
 * The MathPotenciesModule class builds a module where user has to solve simple
 * equation systems.
 * 
 * @author Philipp Willems
 */

public class MathEquationSystemsModule extends ModuleClass {
	private int result = 0;

	public static final String moduleName = "MathEquationSystemsModule";
	public static final String moduleArea = "Math";
	public static final String moduleDescription = "Calculate x and y!";

	private static final int TIMEFOREXERCISE = 20000;
	private static int maxExercises;
	private int exercisesMade = 0;
	private int solvedCorrectly = 0;

	public MathEquationSystemsModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		setMaxExercises(time);
	}

	public void setMaxExercises(Integer time) {
		maxExercises = (int) time / TIMEFOREXERCISE;
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
	 * @return String[] exercise exercise[0] is the x value exercise[1] is the y
	 *         value exercise[2] is the first equation exercise[3] is the second
	 *         equation
	 * 
	 * @author Philipp Willems
	 */
	public String[] createExercise() {
		int range = 6 + 5 * getDifficulty();
		Random r = new Random();

		String[] exercise = new String[4];

		// create the solution of the equation system
		int x = r.nextInt(range) + 2;
		int y = r.nextInt(range) + 2;

		exercise[0] = String.valueOf(x);
		exercise[1] = String.valueOf(y);

		// create first equation
		int xFactor = r.nextInt(range) + 2;
		int yFactor = r.nextInt(range) + 2;
		int solution = xFactor * x + yFactor * y;

		exercise[2] = xFactor + "x + " + yFactor + "y = " + solution;

		// create second equation
		xFactor = r.nextInt(range) + 2;
		yFactor = r.nextInt(range) + 2;
		solution = xFactor * x + yFactor * y;

		exercise[3] = xFactor + "x + " + yFactor + "y = " + solution;

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
		private JLabel firstEquationLabel = new JLabel();
		private JLabel secondEquationLabel = new JLabel();
		private JLabel xLabel = new JLabel("x:");
		private JLabel yLabel = new JLabel("y:");
		private JTextField xText = new JTextField();
		private JTextField yText = new JTextField();
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

			xText.setText("");
			xText.addKeyListener(new KeyListener() {
				
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
					// TODO Auto-generated method stub
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						nextExerciseButton.doClick();
					}
				}
			});
			yText.setText("");
			yText.addKeyListener(new KeyListener() {
				
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
			firstEquationLabel.setText(exercise[2]);
			secondEquationLabel.setText(exercise[3]);
		}

		public void init() {
			introductionPanel = getIntroductionPanel(TIMEFOREXERCISE,
					maxExercises, this);
			thisPanel.add(introductionPanel);
		}
		
		public void startExercise() {
			initExercise();

			firstEquationLabel.setBounds(340, 220, 200, 20);
			secondEquationLabel.setBounds(340, 245, 200, 20);
			xLabel.setBounds(320, 275, 20, 20);
			xText.setBounds(430, 275, 100, 20);
			yLabel.setBounds(320, 300, 20, 20);
			yText.setBounds(430, 300, 100, 20);

			this.add(firstEquationLabel);
			this.add(secondEquationLabel);
			this.add(xLabel);
			this.add(xText);
			this.add(yLabel);
			this.add(yText);

			registerButton(nextExerciseButton);
			nextExerciseButton.addActionListener(this);
			nextExerciseButton.setBounds(320, 325, 140, 30);
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
					if (xText.getText().equals(exercise[0])
							&& xText.getText().equals(exercise[1])) {
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
