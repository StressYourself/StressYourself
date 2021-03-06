package de.dhbw.stress_yourself.extend;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

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

	/**
	 * Methods to handle the timer to call the next module.
	 * 
	 * @param time
	 * @param timer
	 */
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
		double pointsPerTest = (double) 100 / (double) numberOfTests;
		double points = pointsPerTest * solvedCorrectly;
		if (points > 100) {
			points = 100;
		}
		return (int) points;
	}

	/**
	 * Creates the panel introducing the next module (description etc.)
	 * 
	 * @param nextTaskIntervall
	 * @param taskcount
	 * @param al
	 * @return
	 * @author Moritz Herbert <moritz.herbert@gmx.de>
	 */
	public JPanel getIntroductionPanel(double timePerTask, int taskcount,
			ActionListener al) {
		JPanel introductionPanel = new JPanel();
		JLabel moduleName = new JLabel(getModuleName());
		JLabel moduleDescriptionLabel1 = new JLabel("Description:");
		JMultilineLabel moduleDescriptionLabel2 = new JMultilineLabel(
				getModuleDescription());
		JLabel moduleTimeLabel = new JLabel("Maximum time for module: "
				+ String.valueOf(getTime() / 1000) + " seconds");
		JLabel moduleDesIntervallLabel = new JLabel("Estimated time per Task: "
				+ String.valueOf(timePerTask / 1000) + " seconds");
		JLabel taskCountLabel = new JLabel(
				"Estimated tasks which can be solved: " + taskcount);
		JButton startTasksButton = new JButton("Start");
		introductionPanel.setLayout(null);
		introductionPanel.setBounds(0, 0, 900, 700);

		moduleName.setBounds(280, 150, 400, 50);
		moduleName.setFont(new Font("SansSerif", Font.PLAIN, 25));
		introductionPanel.add(moduleName);

		moduleDescriptionLabel1.setBounds(280, 220, 300, 25);
		moduleDescriptionLabel1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		introductionPanel.add(moduleDescriptionLabel1);

		moduleDescriptionLabel2.setBounds(300, 250, 300, 50);
		moduleDescriptionLabel2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		introductionPanel.add(moduleDescriptionLabel2);

		moduleTimeLabel.setBounds(280, 310, 300, 25);
		moduleTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		introductionPanel.add(moduleTimeLabel);

		moduleDesIntervallLabel.setBounds(280, 340, 300, 25);
		moduleDesIntervallLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		introductionPanel.add(moduleDesIntervallLabel);

		taskCountLabel.setBounds(280, 370, 300, 25);
		taskCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		introductionPanel.add(taskCountLabel);

		startTasksButton.setBounds(380, 420, 75, 25);
		introductionPanel.add(startTasksButton);
		startTasksButton.addActionListener(al);

		return introductionPanel;
	}

	/**
	 * The JMultilineLabel class allows to create Labels which have a line break
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public class JMultilineLabel extends JTextArea {
		private static final long serialVersionUID = 1L;

		public JMultilineLabel(String text) {
			super(text);
			setEditable(false);
			setCursor(null);
			setOpaque(false);
			setFocusable(false);
			setFont(UIManager.getFont("Label.font"));
			setWrapStyleWord(true);
			setLineWrap(true);
		}
	}

	/**
	 * Function to send the result of the module to the MainClass
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public void sendResult(int result) {
		stopNextModuleTimer();

		URL url = null;
		try {
			url = new File("stress_yourself.jar").toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		URLClassLoader urlcl = null;
		urlcl = URLClassLoader.newInstance(new URL[] { url });

		Class<?> clazz = null;
		try {
			clazz = urlcl.loadClass("de.dhbw.stress_yourself.MainApplication");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found " + e);
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
		URL url = null;
		try {
			url = new File("stress_yourself.jar").toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		URLClassLoader urlcl = null;
		urlcl = URLClassLoader.newInstance(new URL[] { url });

		Class<?> clazz = null;
		try {
			clazz = urlcl.loadClass("de.dhbw.stress_yourself.MainApplication");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found " + e);
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
