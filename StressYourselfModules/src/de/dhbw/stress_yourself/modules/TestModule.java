package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;

public class TestModule extends ModuleClass implements Runnable {

	private int result = 0;

	private Object mainClass = null;

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

		panel.add(button);
		return panel;
	}

	@Override
	public void run() {
		while (result != 5) {

		}

	}

	@Override
	public void sendResult() {
		System.out.println("sending Result " + result);

	}

	@Override
	public void setDifficulty(int diff) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTime(String time) {
		// TODO Auto-generated method stub

	}

	public void tellFinished() {
		Class<?> clazz = null;
		try {
			clazz = Class.forName("de.dhbw.stress_yourself.MainApplication");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method isFinished = null;
		try {
			isFinished = clazz.getMethod("isFinished");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			isFinished.invoke(mainClass);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	public boolean transferObject(Object o) {
		mainClass = o;
		if (mainClass != null) {
			return true;
		} else {
			return false;
		}
	}
}
