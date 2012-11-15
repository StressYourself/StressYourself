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
import javax.swing.JTextField;

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
	
	public static final MessageFormat[] exercisePatterns = {new MessageFormat("<html>{0} liters have a weight of {1} kilos.<br />How much weigh {2} liter?</html>"),
										new MessageFormat("<html>{0} smartphones cost {1} euro.<br />How much cost {2} of them?</html>"),
										new MessageFormat("<html>{0} workers do a job in {1} hours.<br />How many hours will {2} workers need?</html>"),
										new MessageFormat("<html>{0} miles will take you {1} minutes.<br />How long will {2} miles take?</html>"),
										new MessageFormat("<html>{0} kilo uran have a mass of {1} liters.<br />How much liters have {2} kilo uran?</html>"),
										new MessageFormat("<html>{0} apples cost {1} cents.<br />How much cost {2} of them?</html>"),
										new MessageFormat("<html>{0} people build a wall in {1} days.<br />How many hours will {2} people need?</html>"),
										new MessageFormat("<html>{0} kilometers will take {1} seconds.<br />How long will {2} kilometers take?</html>")};
	
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
	 */	
	public String[] createExercise() {
		int range = 7 + 5 * getDifficulty();
		Random r = new Random();
		
		int x = (r.nextInt(range) + 2) * 100;
		int c = x * (r.nextInt(range - 2) + 1) / 100;
		
		int b = (r.nextInt(range) + 2) * 100;
		int a = (int) b * c / x;
		
		String[] formatArgs = {String.valueOf(a), String.valueOf(b), String.valueOf(c)};
		
		int pattern = r.nextInt(exercisePatterns.length);
		
		String[] exercise = {exercisePatterns[pattern].format(formatArgs), String.valueOf(x)};
		
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
		private JLabel solutionLabel = new JLabel("Answer:");
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
		}
		
		public void startExercise() {
			initExercise();
			
			givenLabel.setBounds(320, 220, 400, 80);
			solutionLabel.setBounds(320, 305, 100, 20);
			solutionText.setBounds(430, 305, 100, 20);
			
			this.add(givenLabel);
			this.add(solutionLabel);
			this.add(solutionText);
			
			registerButton(nextExerciseButton);
			nextExerciseButton.addActionListener(this);
			nextExerciseButton.setBounds(320, 330, 140, 30);
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
				switch (buttons.indexOf(e.getSource())) {
				case 0:
					exercisesMade++;
					// increment result when the solution given by the user was
					// right
					if (solutionText.getText().equals(exercise[1])) {
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
