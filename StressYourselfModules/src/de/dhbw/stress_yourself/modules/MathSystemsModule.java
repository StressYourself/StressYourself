package de.dhbw.stress_yourself.modules;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * The MathSystemsModule class builds a module where user has to convert numbers from decimal, hexadecimal or binary in another number system.
 * 
 * @author Philipp Willems
 */

public class MathSystemsModule extends ModuleClass {

	public static final String moduleName = "MathSystemsModule";
	public static final String moduleArea = "Zahlensysteme";
	public static final String moduleDescription = "Rechne die Zahlen um!";
	
	private int diff = 0;
	private String time = "";

	private int result = 0;

	private Object mainClass = null;

	public MathSystemsModule(Object o) {
		mainClass = o;
	}

	public JPanel getModuleJPanel() {
		return new moduleGUI();
	}

	@Override
	public void setDifficulty(int diff) {
		this.diff = diff;
	}

	@Override
	public void setTime(String time) {
		this.time = time;
	}
	
	@Override
	public void sendResult() {
		System.out.println("sending Result " + result);

	}

	public void tellFinished() {
		Class<?> clazz = null;
		try {
			clazz = Class.forName("de.dhbw.stress_yourself.MainApplication");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method nextModule = null;
		try {
			nextModule = clazz.getMethod("nextModule");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			nextModule.invoke(mainClass);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Creates an exercise for this module
	 * 
	 * @return String[] exercise
	 * 			exercise[0] is the number with the number system
	 * 			exercise[1] is the number system to which the user should convert the given number
	 * 			exercise[2] is the result of the conversion
	 * 
	 * @author Philipp Willems
	 */	
	public static String[] createExercise() {
		int range = 2;
		Random r = new Random();
		
		// generate a random value in the range of 10 - 90 which represents the value to be converted by the user
		int value = (int) Math.round(r.nextDouble()*80) + 10;
		
		// numbersystem1: starting number system, numbersystem2: ending number system
		int numbersystem1 = (int) Math.round(r.nextDouble()*range);
		int numbersystem2 = (int) Math.round(r.nextDouble()*range);
		
		// ensure that the ending number system is different from the starting number system
		while (numbersystem1 == numbersystem2) {
			numbersystem2 = (int) Math.round(r.nextDouble()*range);
		}
		
		String[] exercise = new String[3];

		// case differentiation: fill the String[] exercise with the associated values and transform the starting value to the solution
		switch (numbersystem1) {
			// numbersystem1 is binary
			case 0:
				exercise[0] = Integer.toBinaryString(value) + " Binär"; // exercise
				
				if (numbersystem2 == 1) {
					exercise[1] = "In Hexadezimal:";
					exercise[2] = Integer.toHexString(value); // solution
				}
				else {
					exercise[1] = "In Dezimal:";
					exercise[2] = String.valueOf(value); // solution
				}
				break;
			// numbersystem1 is hexadecimal
			case 1:
				exercise[0] = Integer.toHexString(value) + " Hexadezimal"; // exercise
				
				if (numbersystem2 == 0) {
					exercise[1] = "In Binär:";
					exercise[2] = Integer.toBinaryString(value); // solution
				}
				else {
					exercise[1] = "In Dezimal:";
					exercise[2] = String.valueOf(value); // solution
				}
				break;
			// numbersystem1 is decimal
			default:
				exercise[0] = value + " Dezimal"; // exercise
				
				if (numbersystem2 == 1) {
					exercise[1] = "In Hexadezimal:";
					exercise[2] = Integer.toHexString(value); // solution
				}
				else {
					exercise[1] = "In Binär:";
					exercise[2] = Integer.toBinaryString(value); // solution
				}
				break;
		}
		
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
		private int i;
		private String[] exercise = null;
		private JTextPane solutionpane = null;

		public moduleGUI() {
			this.i = 0;
			init();
		}

		public void registerButton(JButton button) {
			buttons.add(button);
		}
		
		public void init() {
			buttons = new ArrayList<JButton>();
			
			if (this != null) {
				this.removeAll();
			}

			exercise = createExercise();
			
			this.setLayout(new GridLayout(3,2));
			
			JLabel given = new JLabel("Gegeben:");
			this.add(given);
			
			JLabel given2 = new JLabel(exercise[0]);
			this.add(given2);
			
			JLabel solutionlabel = new JLabel(exercise[1]);
			this.add(solutionlabel);
			
			solutionpane = new JTextPane();
			solutionpane.setBounds(50, 50, 100, 100);
			this.add(solutionpane);

			JButton button = new JButton();
			button.setText("Weiter");
			registerButton(button);
			button.addActionListener(this);
			this.add(button);
			
			this.revalidate();
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
				// Rebuilds the GUI 10 times with new exercises, so the user is asked 10 exercises. After the 10 exercises, this module is finished.
				if (i < 10) {
					i++;
					
					// increment result when the solution given by the user was right
					if (solutionpane.getText().equals(exercise[2])) {
						result++;
					}
					
					init();
				}
				else {
					sendResult();
					tellFinished();
				}
				break;
			}
		}

	}
}
