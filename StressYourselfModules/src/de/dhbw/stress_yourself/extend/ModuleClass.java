package de.dhbw.stress_yourself.extend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The ModuleClass is an abstract class to predefine some functions and vars for
 * the subclasses.
 * 
 * @author Moritz Herbert <moritz.herbert@gmx.de>
 * @author Tobias Roeding <tobias@roeding.eu>
 */
public abstract class ModuleClass {

	private int diff;
	private int time;
	private int timePerModule;
	private Object mainClass = null;
	private Timer nextTaskTimer;
	private Timer nextModuleTimer;

	public ModuleClass(Object o, int difficulty, int time) {
		if (mainClass == null) {
			mainClass = o;
		}
		this.diff = difficulty;
		this.time = time;
	}

	public int getDifficulty() {
		return diff;
	}

	public int getTime() {
		return time;
	}

	public JPanel getIntroductionPanel(int nextTaskIntervall, int taskcount,
			ActionListener al) {
		JPanel introductionPanel = new JPanel();
		JLabel moduleDescriptionLabel = new JLabel(getModuleDescription());
		JLabel moduleTimeLabel = new JLabel("Maximum time for module: "
				+ String.valueOf(getTime() / 1000) + "seconds");
		JLabel moduleDesIntervallLabel = new JLabel("Maximum time per Task: "
				+ String.valueOf(nextTaskIntervall / 1000) + "seconds");
		JLabel taskCountLabel = new JLabel(taskcount + " tasks can be solved");
		JButton startTasksButton = new JButton("start");
		introductionPanel.setLayout(null);
		introductionPanel.setBounds(0, 0, 800, 600);

		moduleDescriptionLabel.setBounds(300, 50, 300, 100);
		introductionPanel.add(moduleDescriptionLabel);

		moduleTimeLabel.setBounds(300, 155, 300, 30);
		introductionPanel.add(moduleTimeLabel);

		moduleDesIntervallLabel.setBounds(300, 190, 300, 30);
		introductionPanel.add(moduleDesIntervallLabel);

		taskCountLabel.setBounds(300, 225, 300, 30);
		introductionPanel.add(taskCountLabel);

		startTasksButton.setBounds(400, 260, 75, 30);
		introductionPanel.add(startTasksButton);
		startTasksButton.addActionListener(al);

		return introductionPanel;
	}

	public void setTimePerModule(int timePerModule) {
		this.timePerModule = timePerModule;
	}

	public int getTimePerModule() {
		return timePerModule;
	}

	public void setNextTaskTimer(int time, int intervall, TimerTask timer) {
		nextTaskTimer = new Timer();
		nextTaskTimer.schedule(timer, time, intervall);
	}

	public void resetNextTaskTimer(int time, int intervall, TimerTask timer) {
		nextTaskTimer.cancel();
		nextTaskTimer.purge();
		nextTaskTimer = new Timer();
		nextTaskTimer.schedule(timer, time, intervall);

	}

	public void stopNextTaskTimer() {
		nextTaskTimer.cancel();
		nextTaskTimer.purge();
	}

	abstract class NextTask extends TimerTask {
	}

	public void setNextModuleTimer(int time, TimerTask timer) {
		nextModuleTimer = new Timer();
		nextModuleTimer.schedule(timer, time);
	}

	public void stopNextModuleTimer() {
		nextModuleTimer.cancel();
		nextModuleTimer.purge();
	}

	abstract class NextModule extends TimerTask {
	}

	public abstract String getModuleName();

	public abstract String getModuleArea();

	public abstract String getModuleDescription();

	public abstract JPanel getModuleJPanel();

	/**
	 * Function to send the result of the module to the MainClass
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public void sendResult(int result) {
		stopNextModuleTimer();
		Class<?> clazz = null;
		try {
			clazz = Class.forName("de.dhbw.stress_yourself.MainApplication");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method nextModule = null;
		try {
			nextModule = clazz.getMethod("sendModuleResult", new Class[] {
					String.class, Integer.class });
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			nextModule.invoke(mainClass, new Object[] { getModuleName(),
					new Integer(result) });
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to tell the main class, that the module has finshed his test and
	 * the next module can be loaded
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
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
