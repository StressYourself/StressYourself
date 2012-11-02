package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

public class TestModule extends ModuleClass {

	public static final String moduleName = "TestModule";
	public static final String moduleArea = "Algorithm";
	public static final String moduleDescription = "Example Description";

	public TestModule(Object o) {
		super(o);
	}

	public JPanel getModuleJPanel() {
		return new ModuleGUI();
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
			pane.setText("Beispiel TextPane");
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
			case 0:
				sendResult();
				tellFinished();
				break;
			}
		}
	}
}
