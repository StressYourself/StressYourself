package de.dhbw.stress_yourself.modules;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * Module which gives characters or numbers on the screen and the user should
 * press the keys on the keyboard as fast as he can.
 * 
 * @author Christoph Schollmeyer <chr.schollmeyer@web.de>
 */
public class KeyPressModule extends ModuleClass {

	private final String moduleName = "KeyPress";
	private final String moduleArea = "Reaction";
	private final String moduleDescription = "Example Description";

	private ArrayList<Boolean> results = new ArrayList<Boolean>();

	public KeyPressModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
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

	class ModuleGUI extends JPanel implements KeyListener {

		private static final long serialVersionUID = 1L;
		private final String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		private JTextField keyField;
		private JLabel scoreLabel;
		private int score;

		private JTextPane pane = new JTextPane();
		
		public ModuleGUI() {
			setLayout(null);
			setBounds(0, 0, 800, 600);
			init();
			setVisible(true);
			keyGame();
		}

		private void init() {
			setBorder(new EmptyBorder(10, 10, 10, 10));
			setLayout(new GridLayout(2, 1));
			keyField = new JTextField();
			keyField.setEditable(false);
			keyField.setColumns(5);
			scoreLabel = new JLabel();
			keyField.addKeyListener(this);
			add(keyField);
			add(scoreLabel);
			pane.setText("CaptchaCirclesModules");
			pane.setBounds(50, 50, 175, 30);
			this.add(pane);
			// setTimer();
		}

		private void keyGame() {
			int rnd = new Random().nextInt(35);
			keyField.setText(character.substring(rnd, rnd + 1));
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (KeyEvent.getKeyText(e.getKeyCode()).equals(keyField.getText())) {
				scoreLabel.setText("" + ++score);
				keyGame();
				results.add(true);
			} else {
				results.add(false);
			}

		}

	}

}
