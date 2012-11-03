package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

public class OperandModule extends ModuleClass {

	private final String moduleName = "OperandModule";
	private final String moduleArea = "";
	private final String moduleDescription = "Example Description";

	public OperandModule(Object o) {
		super(o);
	}
	
	public String getModuleName(){
		return moduleName;
	}
	
	public String getModuleArea(){
		return moduleArea;
	}
	
	public String getModuleDescription(){
		return moduleDescription;
	}

	@Override
	public JPanel getModuleJPanel() {
		ModuleGUI panel = new ModuleGUI();
		return panel;
	}

	class ModuleGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;

		public ModuleGUI() {
			buttons = new ArrayList<JButton>();
			init();
		}

		public void registerButton(JButton button) {
			buttons.add(button);
		}

		public String generateTest(int difficulty) {
			return "";
		}

		public String evaluateTest(String test) {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			// String foo = "5 == (4+1) && 5*9 != 4*9";
			try {
				return (String) engine.eval(test);
			} catch (ScriptException e) {
				System.err.println("Couldn't evaluate the String " + e);
			}
			return null;
		}

		public void init() {
			JPanel task = new JPanel();

			JLabel operandTask = new JLabel();
			operandTask.setText("ailsdfjoasjdf");

			JTextField solution = new JTextField();

			JButton button = new JButton();
			registerButton(button);
			button.addActionListener(this);

			task.add(operandTask);
			task.add(solution);
			task.add(button);

			this.add(task);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (buttons.indexOf(e.getSource())) {
			case 0:
				sendResult();
				tellFinished();
				break;
			}
		}
	}
}
