package de.dhbw.stress_yourself.modules;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

public class TestModule extends ModuleClass implements Runnable{



	public JPanel getModuleJPanel() {
		JPanel panel = new JPanel();
		JTextPane pane = new JTextPane();
		pane.setText("Beispiel TextPane");
		pane.setBounds(50, 50, 100, 100);
		panel.add(pane);
		
		JTextPane textPane = new JTextPane();
		panel.add(textPane);
		return panel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendResult() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDifficulty(int diff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTime(String time) {
		// TODO Auto-generated method stub
		
	}
}
