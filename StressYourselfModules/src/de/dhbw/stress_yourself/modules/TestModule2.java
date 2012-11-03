package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

public class TestModule2 extends ModuleClass {

	private final String moduleName = "TestModule2";
	private final String moduleArea = "Math";
	private final String moduleDescription = "Example Description";
	
	private int diff = 0;
	private String time = "";

	private int result = 0;

	public TestModule2(Object o) {
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

	public JPanel getModuleJPanel() {
		return new ModuleGUI();
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

		public void init() {
			JTextPane pane = new JTextPane();
			pane.setText("Beispiel TextPane2");
			pane.setBounds(50, 50, 100, 100);
			this.add(pane);

			JButton button = new JButton();
			registerButton(button);
			button.addActionListener(this);
			this.add(button);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (buttons.indexOf(e.getSource())) {
			case 0: sendResult();
					tellFinished();
					break;
			}
		}

	}
}
