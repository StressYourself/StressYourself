package de.dhbw.stress_yourself.extend;

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
	private Object mainClass = null;
	private Timer nextModuleTimer;

	public ModuleClass(Object o, int difficulty, int time) {
		if (mainClass == null) {
			mainClass = o;
		}
		this.diff = difficulty;
		this.time = time;
	}
	
	public abstract String getModuleName();

	public abstract String getModuleArea();

	public abstract String getModuleDescription();

	public abstract JPanel getModuleJPanel();

	public int getDifficulty() {
		return diff;
	}

	public int getTime() {
		return time;
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

	/**
	 * Calculates the Points the users gets for this module
	 * 
	 * @param numberOfTests
	 *            The number of tests, which are possible in the given time
	 *            period
	 * @param solvedCorrectly
	 *            The number of tests the user solved correctly
	 * @return int The points the user gets for this module
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public int calculateResult(int numberOfTests, int solvedCorrectly) {
		double pointsPerTest = 100 / numberOfTests;
		double points = pointsPerTest * solvedCorrectly;
		return (int) points;
	}

	/**
	 * 
	 * @param nextTaskIntervall
	 * @param taskcount
	 * @param al
	 * @return
	 * @author Moritz Herbert <moritz.herbert@gmx.de>
	 */
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
