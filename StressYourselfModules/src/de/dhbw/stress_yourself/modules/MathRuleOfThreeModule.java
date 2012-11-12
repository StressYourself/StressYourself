package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * The MathRuleOfThreeModule class builds a module where user has to solve simple rule of the exercises.
 * 
 * @author Philipp Willems
 */

public class MathRuleOfThreeModule extends ModuleClass {
	private int result = 0;

	public static final String moduleName = "MathRuleOfThreeModule";
	public static final String moduleArea = "Math";
	public static final String moduleDescription = "Calculate the solution!";
	
	public static final MessageFormat[] exercisePatterns = {new MessageFormat("<html>On {0} miles, an car needs {1} liters of fuel.<br />With a full tank it rides {2} miles.<br />How many liters holds the tank?</html>"),
										new MessageFormat("<html>{0} liters have a weight of {1} kilos.<br />How much weigh {2} liter?</html>"),
										new MessageFormat("<html>{0} smartphones cost {1} euro.<br />How much cost {2} of them?</html>"),
										new MessageFormat("<html>{0} workers do a job in {1} days.<br />How many hours will {2} workers need?</html>"),
										new MessageFormat("<html>{0} miles will take you {1} minutes.<br />How long will {2} minutes take?</html>"),
										new MessageFormat("<html>{0} kilo milk have a mass of {1} liters.<br />How much liters have {2} kilo milk?</html>"),
										new MessageFormat("<html>On {0} kilometers Mikes plane needs {1} liters of fuel.<br />With a full tank it rides {2} miles.<br />How many liters holds the tank?</html>"),
										new MessageFormat("<html>{0} apples cost {1} euro.<br />How much cost {2} of them?</html>"),
										new MessageFormat("<html>{0} people build a wall in {1} days.<br />How many hours will {2} people need?</html>"),
										new MessageFormat("<html>{0} kilometers will take {1} minutes.<br />How long will {2} kilometers take?</html>")};
	
	private static final int TIMEFOREXERCISE = 20000;
	private static int maxExercises;
	private int exercisesMade = 0;
	private int solvedCorrectly = 0;

	public MathRuleOfThreeModule(Object o, Integer difficulty, Integer time) {
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
	 * @return String[] exercise
	 * 			exercise[0] is the given exercise
	 * 			exercise[1] is the solution of the rule of three
	 * 
	 * @author Philipp Willems
	 */	
	public String[] createExercise() {
		int range = 8 + 5 * getDifficulty();
		Random r = new Random();
		
		int x = r.nextInt(range) + 2;
		int b = r.nextInt(range) + 2;
		
		int c = (r.nextInt(range) + 1) * 100;
		int a = (int) b * c / x;
		
		String[] formatArgs = {String.valueOf(a), String.valueOf(b), String.valueOf(c)};
		
		int pattern = r.nextInt(exercisePatterns.length);
		
		String[] exercise = {exercisePatterns[pattern].format(formatArgs), String.valueOf(x)};
		
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
		private JLabel solutionLabel = new JLabel("Answer:");
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
			
			solutionPane.setText("");	
			solutionPane.addKeyListener(new KeyListener() {
				
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
					if(e.getKeyCode() == (char)13) {
						nextExerciseButton.doClick();
					}
				}
			});
			givenLabel.setText(exercise[0]);
		}
		
		public void init() {
			introductionPanel = getIntroductionPanel(TIMEFOREXERCISE,
					maxExercises, this);
			thisPanel.add(introductionPanel);
		}
		
		public void startExercise() {
			initExercise();
			
			givenLabel.setBounds(20, 20, 400, 80);
			solutionLabel.setBounds(20, 105, 100, 20);
			solutionPane.setBounds(130, 105, 100, 20);
			
			this.add(givenLabel);
			this.add(solutionLabel);
			this.add(solutionPane);
			
			registerButton(nextExerciseButton);
			nextExerciseButton.addActionListener(this);
			nextExerciseButton.setBounds(20, 130, 140, 30);
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
			if (buttons.contains(e.getSource())) {
				switch (buttons.indexOf(e.getSource())) {
				case 0:
					exercisesMade++;
					// increment result when the solution given by the user was
					// right
					if (solutionPane.getText().equals(exercise[1])) {
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
