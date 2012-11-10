package de.dhbw.stress_yourself.modules;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * module that creates random sequences that have to be continued
 * 
 * @author LukasBuchert <Lukas.Buchert@gmx.de>
 */
public class ContinueSequenceModule extends ModuleClass {

	private final String moduleName = "Continue Sequence Module";
	private final String moduleArea = "Logic";
	private final String moduleDescription = "The tested person has to contiue sequences"
			+ " of alphabetic characters and numbers";
	
	private int points = 0;

	public ContinueSequenceModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		setTimerIntervall();
	}

	@Override
	public void setTimerIntervall() {
		// TODO set different times for the test

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

	@Override
	public JPanel getModuleJPanel() {
		return new ModuleGUI();
	}

	private void calculateResult(){
		int border = 10; // Highest Points that could be reached / depending on time and difficulty
		int newResult = (points / border) * 100;
		if (newResult > 100){
			newResult = 100;
		}
		// result = newResult; // needs access to result
	}

	

	class ModuleGUI extends JPanel {
		private static final long serialVersionUID = 1L;
		private JLabel x1 = new JLabel();
		private JLabel x2 = new JLabel();
		private JLabel x3 = new JLabel();
		private JLabel x4 = new JLabel();
		private JLabel x5 = new JLabel();
		private JLabel x6 = new JLabel();
		private JLabel x7 = new JLabel();
		private JLabel x8 = new JLabel();
		private JTextField x9 = new JTextField();
		private String solution;
		private JButton submit = new JButton();

		public ModuleGUI() {
			setLayout(null);
			setBounds(0, 0, 800, 600);
			init();
			fill();
			setVisible(true);
		}

		private void init() {
			final int diff = 40;
			int xstart = 50;
			int ystart = 100;
			//x1.setText("x111");
			x1.setBounds(xstart, ystart, 30, 20);
			this.add(x1);
			xstart = xstart + diff;
			//x2.setText("x222");
			x2.setBounds(xstart, ystart, 30, 20);
			this.add(x2);
			xstart = xstart + diff;
			//x3.setText("x333");
			x3.setBounds(xstart, ystart, 30, 20);
			this.add(x3);
			xstart = xstart + diff;
			//x4.setText("x444");
			x4.setBounds(xstart, ystart, 30, 20);
			this.add(x4);
			xstart = xstart + diff;
			//x5.setText("x555");
			x5.setBounds(xstart, ystart, 30, 20);
			this.add(x5);
			xstart = xstart + diff;
			//x6.setText("x666");
			x6.setBounds(xstart, ystart, 30, 20);
			this.add(x6);
			xstart = xstart + diff;
			//x7.setText("x777");
			x7.setBounds(xstart, ystart, 30, 20);
			this.add(x7);
			xstart = xstart + diff;
			//x8.setText("x888");
			x8.setBounds(xstart, ystart, 30, 20);
			this.add(x8);
			xstart = xstart + diff;
			//x9.setText("x999");
			x9.setBounds(xstart, ystart, 50, 20);
			this.add(x9);
			submit.setText("submit");
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (x9.getText().equals(solution)){
						points ++;
						// for debugging 
						System.out.println("Get new point! points: " + points);
					}
					x9.setText("");
					fill();
				}
			});
			submit.setBounds(xstart - 60, ystart + 50, 120, 30);
			this.add(submit);

		}
		
		private void fill(){
			String[] tmp = getSequence(getDifficulty());
			x1.setText(tmp[0]);
			x2.setText(tmp[1]);
			x3.setText(tmp[2]);
			x4.setText(tmp[3]);
			x5.setText(tmp[4]);
			x6.setText(tmp[5]);
			x7.setText(tmp[6]);
			x8.setText(tmp[7]);
			solution = tmp[8];
		}

	}

	// sequence producer

	private char[] alphabet = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };

	private boolean alpha;

	private String[] getSequence(int difficulty) {
		int[] tmp = takeSequence(difficulty);
		String[] back = new String[tmp.length];

		if (alpha) {
			for (int i = 0; i < tmp.length; i++) {
				back[i] = Character.toString(getChar(tmp[i]));
			}
		} else {
			for (int i = 0; i < tmp.length; i++) {
				back[i] = Integer.toString(tmp[i]);
			}

		}
		return back;
	}
	
	private char getChar(int number) {
		while (number < 0) {
			number = number + 26;
		}

		return alphabet[number % 26];
	}

	private int random(int low, int high) {
		high++;
		return (int) (Math.random() * (high - low) + low);
	}

	private int takeFunction(int operation, int input, int number) {
		switch (operation) {
		case 1:
			return functionAddition(input, number);

		case 2:
			return functionSubstraction(input, number);

		case 3:
			number = (number % 3) + 2;
			return functionMultiplication(input, number);

		default:
			return 0;

		}

	}

	private int[] takeSequence(int difficulty) {
		int function = random(1, 2);
		alpha = false;
		int[] back;
		switch (difficulty) {
		case 0:

			switch (function) {
			case 1:
				do {
					back = sequenceOneD0();
				} while (back[8] > 5000);
				break;
			case 2:
				alpha = true;
				back = sequenceOneA0();
				break;

			default:
				back = null;
			}
			break;
		case 1:

			switch (function) {
			case 1:
				do {
					back = sequenceOneD1();
				} while (back[8] > 2000);
				break;
			case 2:
				alpha = true;
				back = sequenceOneA1();
				break;

			default:
				back = null;
			}
			break;
		case 2:

			switch (function) {
			case 1:
				do {
					back = sequenceTwoD2();
				} while (back[8] > 2000);
				break;
			case 2:
				alpha = true;
				back = sequenceOneA2();
				break;

			default:
				back = null;
			}
			break;
		default:
			back = null;
		}

		return back;
	}

	private int functionAddition(int input, int number) {
		return input + number;
	}

	private int functionSubstraction(int input, int number) {
		return input - number;
	}

	private int functionMultiplication(int input, int number) {
		return input * number;
	}

	private int[] sequenceOneD0() {
		int[] sequence = new int[9];

		int operation = random(1, 3);
		int number = random(2, 10);

		sequence[0] = random(2, 10);

		for (int i = 1; i < sequence.length; i++) {
			sequence[i] = takeFunction(operation, sequence[i - 1], number);
		}

		return sequence;
	}

	private int[] sequenceOneA0() {

		int[] sequence = new int[9];

		int operation = random(1, 2);
		int number = random(2, 3);

		sequence[0] = random(0, 26);

		for (int i = 1; i < sequence.length; i++) {
			sequence[i] = takeFunction(operation, sequence[i - 1], number);
		}

		return sequence;
	}

	private int[] sequenceOneD1() {
		int[] sequence = new int[9];

		int operation1 = random(1, 3);
		int number1 = random(2, 8);
		int operation2 = random(1, 3);
		int number2 = random(2, 8);

		while (number1 + number2 == 0 || number1 == number2) {
			number2 = random(2, 8);
		}

		sequence[0] = random(2, 10);

		for (int i = 1; i < sequence.length; i += 2) {
			sequence[i] = takeFunction(operation1, sequence[i - 1], number1);
			if (i + 1 < sequence.length) {
				sequence[i + 1] = takeFunction(operation2, sequence[i], number2);
			}
		}

		return sequence;
	}

	private int[] sequenceOneA1() {

		int[] sequence = new int[9];

		int operation1 = random(1, 2);
		int operation2 = random(1, 2);
		int number1 = random(2, 3);
		int number2 = random(2, 3);
		while (number1 + number2 == 0 || number1 == number2) {
			number2 = random(2, 3);
		}

		sequence[0] = random(0, 26);

		for (int i = 1; i < sequence.length; i = i + 2) {
			sequence[i] = takeFunction(operation1, sequence[i - 1], number1);
			if (i + 1 < sequence.length) {
				sequence[i + 1] = takeFunction(operation2, sequence[i], number2);
			}
		}

		return sequence;
	}
	
	private int[] sequenceTwoD2() {
		int[] sequence = new int[9];

		int operation = random(1, 3);
		int number = random(2, 6);

		sequence[0] = random(2, 10);
		sequence[1] = takeFunction(operation, sequence[0], number);

		for (int i = 2; i < sequence.length; i++) {
			sequence[i] = takeFunction(operation, sequence[i - 2]
					+ sequence[i - 1], number);
		}

		return sequence;
	}
	
	private int[] sequenceOneA2() {

		int[] sequence = new int[9];

		int operation1 = random(1, 2);
		int number1 = random(2, 4);
		int operation2 = random(1, 2);
		int number2 = random(2, 4);
		int operation3 = random(1, 2);
		int number3 = random(2, 4);

		sequence[0] = random(0, 26);

		for (int i = 1; i < sequence.length; i = i + 3) {
			sequence[i] = takeFunction(operation1, sequence[i - 1], number1);
			if (i + 1 < sequence.length) {
				sequence[i + 1] = takeFunction(operation2, sequence[i], number2);
			}
			if (i + 2 < sequence.length) {
				sequence[i + 2] = takeFunction(operation3, sequence[i + 1],
						number3);
			}
		}

		return sequence;
	}

}
