package de.dhbw.stress_yourself.extend;

import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JPanel;

public abstract class ModuleClass {
	// here three vars

	private int diff = 0;
	private String time = "";

	private int result = 0;

	private Object mainClass = null;

	public ModuleClass(Object o) {
		if (mainClass == null) {
			mainClass = o;
		}
	}

	public void setDifficulty(int diff) {
		this.diff = diff;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public abstract JPanel getModuleJPanel();

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

	@SuppressWarnings("serial")
	abstract class ModuleGUI extends JPanel implements ActionListener {
	}

}
