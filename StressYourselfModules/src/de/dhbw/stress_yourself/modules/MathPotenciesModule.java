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
 * The MathPotenciesModule class builds a module where user has to potencies to the base 2.
 * 
 * @author Philipp Willems
 */
public class MathPotenciesModule extends ModuleClass {
	private int result = 0;

	public static final String moduleName = "MathPotenciesModule";
	public static final String moduleArea = "Math";
	public static final String moduleDescription = "Calculate the potencies!";
	
	private static int TIMEFOREXERCISE;
	private static int maxExercises;
	private int exercisesMade = 0;
	private int solvedCorrectly = 0;

	public MathPotenciesModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		initTestValues();
	}
	
	public void initTestValues() {
		switch (getDifficulty()) {
		case 0:
			TIMEFOREXERCISE = 5000;
			break;
		case 1:
			TIMEFOREXERCISE = 7000;
			break;
		case 2:
			TIMEFOREXERCISE = 9000;
			break;
		}
		maxExercises = (getTime() / TIMEFOREXERCISE);
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
	 * @return String[] exercise
	 * 			exercise[0] is the given potency to calculate
	 * 			exercise[1] is the solution of the calculation
	 */	
	public String[] createExercise() {
		int range = 8;
		Random r = new Random();
		
		int power = r.nextInt(range) + 3;
		
		String[] exercise = new String[2];
		
		exercise[0] = "Given: 2^" + power;
		exercise[1] = Integer.toString((int) Math.pow(2, power));
		
		return exercise;
	}
	
	
	/**
	 * The moduleGUI class builds the user interface for the module.
	 * The moduleGUI represents a JPanel in which other components like buttons are added.
	 */
	class moduleGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;
		private String[] exercise = null;
		private JTextField solutionText = new JTextField();
		private JLabel givenLabel = new JLabel();
		private JLabel solutionLabel = new JLabel("Calculated:");
		private JButton nextExerciseButton = new JButton("Next exercise");
		private JPanel introductionPanel = null;
		private JPanel thisPanel = this;

		public moduleGUI() {
			buttons = new ArrayList<JButton>();
			solutionText.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {	
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						nextExerciseButton.doClick();
					}
				}
			});

			init();
			this.setLayout(null);
		}

		public void registerButton(JButton button) {
			if(!buttons.contains(button)) {
				buttons.add(button);
			}
		}
		
		public void initExercise() {
			exercise = createExercise();
			
			solutionText.setText("");
			
			givenLabel.setText(exercise[0]);
		}
		
		public void init() {
			introductionPanel = getIntroductionPanel(TIMEFOREXERCISE,
					maxExercises, this);
			thisPanel.add(introductionPanel);
			
			registerButton(nextExerciseButton);
			nextExerciseButton.addActionListener(this);
			nextExerciseButton.setBounds(320, 270, 140, 30);
			
		}
		
		public void startExercise() {
			initExercise();
			
			givenLabel.setBounds(320, 220, 200, 20);
			solutionLabel.setBounds(320, 245, 100, 20);
			solutionText.setBounds(430, 245, 100, 20);
			
			solutionText.requestFocus();
			
			this.add(givenLabel);
			this.add(solutionLabel);
			this.add(solutionText);
			
			this.add(nextExerciseButton);
			
			setNextModuleTimer(getTime(), new NextModule());
		}

		/**
		 * Called when a button is pressed
		 * 
		 * @param ActionEvent e
		 *            Event when button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
				
			if (buttons.contains(e.getSource())) {
				exercisesMade++;
				// increment result when the solution given by the user was
				// right
				if (solutionText.getText().equals(exercise[1])) {
					solvedCorrectly++;
				}
				if (exercisesMade == maxExercises) {
					result = calculateResult(maxExercises, solvedCorrectly);
					sendResult(result);
					tellFinished();
				}
				initExercise();	
				this.revalidate();
			} else {
				thisPanel.removeAll();
				startExercise();
				thisPanel.repaint();
			}
		}
		
		public class NextModule extends TimerTask {
			@Override
			public void run() {
				result = calculateResult(maxExercises, solvedCorrectly);
				sendResult(result);
				tellFinished();
			}
		}
	}
}
