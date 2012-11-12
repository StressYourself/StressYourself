package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

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
	
	private static final int TIMEFOREXERCISE = 10000;
	private static int maxExercises;
	private int exercisesMade = 0;

	public MathPotenciesModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		setMaxExercises(time);
	}

	public void setMaxExercises(Integer time) {
		maxExercises = (int) time / TIMEFOREXERCISE * (1 + getDifficulty());
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
	 * 
	 * @author Philipp Willems
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
	 * 
	 * @author Philipp Willems
	 */
	class moduleGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;
		private String[] exercise = null;
		private JTextPane solutionPane = new JTextPane();
		private JLabel givenLabel = new JLabel();
		private JLabel solutionLabel = new JLabel("Calculated:");
		private JButton nextExerciseButton = new JButton("Next exercise");

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
			
			solutionPane.setText("");			
			givenLabel.setText(exercise[0]);
		}
		
		public void init() {
			initExercise();
			
			givenLabel.setBounds(20, 20, 200, 20);
			solutionLabel.setBounds(20, 45, 100, 20);
			solutionPane.setBounds(130, 45, 100, 20);
			
			this.add(givenLabel);
			this.add(solutionLabel);
			this.add(solutionPane);
			
			registerButton(nextExerciseButton);
			nextExerciseButton.addActionListener(this);
			nextExerciseButton.setBounds(20, 70, 140, 30);
			this.add(nextExerciseButton);
			
			setNextModuleTimer(getTime(), new NextModule());
		}

		/**
		 * Called when a button is pressed
		 * 
		 * @param ActionEvent e
		 *            Event when button is pressed
		 *            
		 * @author Philipp Willems
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (buttons.indexOf(e.getSource())) {
			case 0:
				exercisesMade++;
				
				// increment result when the solution given by the user was right
				if (solutionPane.getText().equals(exercise[1])) {
					result += (int) 100/maxExercises;
				}				
				
				if (exercisesMade <= maxExercises) {
					initExercise();
					this.revalidate();
				}
				else {
					sendResult(result);
					tellFinished();
				}
				
				break;
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
