package de.dhbw.stress_yourself.modules;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

public class TestModule extends ModuleClass implements Runnable{
	
	private String time;
	private int difficulty;

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
		System.out.println(this.time + "   " + this.difficulty);
		
	}

	@Override
	public void sendResult() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDifficulty(int diff) {
		this.difficulty = diff;
		
	}

	@Override
	public void setTime(String time) {
		this.time = time;
		
	}
}
