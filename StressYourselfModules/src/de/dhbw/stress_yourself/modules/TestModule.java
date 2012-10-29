package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

public class TestModule extends ModuleClass implements Runnable{
	
	private int result = 0;


	public JPanel getModuleJPanel() {
		JPanel panel = new JPanel();
		JTextPane pane = new JTextPane();
		pane.setText("Beispiel TextPane");
		pane.setBounds(50, 50, 100, 100);
		panel.add(pane);
		
		JButton button = new JButton();
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				result = 5;
			}
		});
		return panel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendResult() {
		System.out.println("Das Ergebnis wird gesendet " + result);
		
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
