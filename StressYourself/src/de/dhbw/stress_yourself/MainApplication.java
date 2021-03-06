package de.dhbw.stress_yourself;

import java.awt.EventQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.annoyance.Annoyance;
import de.dhbw.stress_yourself.outcome.Outcome;
import de.dhbw.stress_yourself.params.ModuleInformation;
import de.dhbw.stress_yourself.params.Parameter;
import de.dhbw.stress_yourself.params.UserData;
import de.dhbw.stress_yourself.reflection.Reflection;

/**
 * The MainApplication Class is used to manage and load all gui classes
 * containing the modules.
 * 
 * @author Tobias Roeding <tobias@roeding.eu>
 */
public class MainApplication {

	private Admin admin;
	private Login login;
	private Outcome outcome;
	private Parameter params;
	private UserData users;
	private Annoyance annoyance;
	private Thread aThread;

	private JFrame frame;

	private Class<?> runningModuleClass = null;
	private Object runningModuleObject = null;
	private HashMap<String, Method> runningModuleMethodsMap = null;
	private URL url = null;
	private LinkedList<ModuleInformation> configuration = null;
	private JPanel panel = null;

	int index = 0;

	public MainApplication() {
		annoyance = new Annoyance();
		params = new Parameter();
		users = new UserData();
		admin = new Admin(this, users, params);
		login = new Login(this, users);
		outcome = new Outcome(this, params, users);

		initialize();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplication window = new MainApplication();

					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the frame and add the login
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 0, 900, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getAvaiableModules();
		getConfiguration();

		startLoginPanel();
	}

	/**
	 * Discards all Objects and creates new ones to restart the test
	 */
	public void restartStressYourself() {
		annoyance = new Annoyance();
		params = new Parameter();
		users = new UserData();
		admin = new Admin(this, users, params);
		login = new Login(this, users);
		outcome = new Outcome(this, params, users);

		runningModuleClass = null;
		runningModuleObject = null;
		runningModuleMethodsMap = null;
		url = null;
		configuration = null;
		panel = null;

		index = 0;

		getAvaiableModules();
		getConfiguration();

		startLoginPanel();
	}

	/**
	 * Removes all other panels and loads LoginPanel
	 */
	public void startLoginPanel() {
		frame.getContentPane().removeAll();
		frame.getContentPane().invalidate();
		frame.getContentPane().add(login.getLoginPanel());
		frame.getContentPane().revalidate();
	}

	/**
	 * Removes all other panels and loads AdminPanel
	 */
	public void startAdminPanel() {
		frame.getContentPane().removeAll();
		frame.getContentPane().invalidate();
		frame.getContentPane().add(admin.getAdminPanel());
		frame.getContentPane().revalidate();
	}

	/**
	 * Starts the Annoyance Thread
	 */
	public void startAnnoyance() {
		if (aThread == null && params.getAnnoyanceSetting()) {
			aThread = new Thread(annoyance);
			aThread.start();
		}
	}

	/**
	 * Stops the Annoyance Thread
	 */
	public void stopAnnoyance() {
		annoyance.running = false;
	}

	/**
	 * The the actual configuration
	 */
	public void getConfiguration() {
		configuration = params.getConfiguration();
		for (int i = 0; i < configuration.size(); i++) {
			System.out.println("configuration  "
					+ configuration.get(i).getClassName());
		}
	}

	/**
	 * Loads the specified module into the JFrame and starts the test
	 * 
	 * @param clazz
	 *            The module class
	 * @param difficulty
	 *            The difficulty for the Test
	 * @param time
	 *            The time for the Test
	 * @return boolean Bool if the module was sucessfully loaded
	 */
	public boolean startModule(Class<?> clazz, Integer difficulty, Integer time) {
		runningModuleMethodsMap = Reflection.getClassMethods(clazz);

		runningModuleObject = Reflection.createClassInstance(clazz,
				new Object[] { this, difficulty, time });

		if (runningModuleMethodsMap.containsKey("getModuleJPanel")) {
			panel = (JPanel) Reflection.runMethod(
					runningModuleMethodsMap.get("getModuleJPanel"),
					runningModuleObject, (Object[]) null);
			frame.getContentPane().add(panel);
			frame.getContentPane().revalidate();
		}

		return true;
	}

	/**
	 * Inits the Modules by getting the url and the names of the modules
	 */
	public void getAvaiableModules() {
		LinkedList<String> classes = new LinkedList<String>();
		url = Reflection.getURL(params.getPathToJar());
		classes = Reflection.getClassNames(params.getPathToJar(),
				params.getPackageName());
		for (int i = 0; i < classes.size(); i++) {
			params.addModuleInformation(getModuleInformation(url,
					classes.get(i)));
		}
	}

	/**
	 * Returns an Object of ModuleInformation containing all needed Information
	 * about the Module
	 * 
	 * @param url
	 *            URL to the jar
	 * @param name
	 *            Name of the class
	 * @return ModuleInformation Object
	 */
	public ModuleInformation getModuleInformation(URL url, String classname) {
		String name = null;
		String area = null;
		String description = null;

		runningModuleClass = Reflection.getClass(url, classname);

		runningModuleMethodsMap = Reflection
				.getClassMethods(runningModuleClass);

		runningModuleObject = Reflection.createClassInstance(
				runningModuleClass, new Object[] { this, new Integer(0),
						new Integer(0) });

		if (runningModuleMethodsMap.containsKey("getModuleName")) {
			name = (String) Reflection.runMethod(
					runningModuleMethodsMap.get("getModuleName"),
					runningModuleObject, (Object[]) null);
		}

		if (runningModuleMethodsMap.containsKey("getModuleArea")) {
			area = (String) Reflection.runMethod(
					runningModuleMethodsMap.get("getModuleArea"),
					runningModuleObject, (Object[]) null);
		}

		if (runningModuleMethodsMap.containsKey("getModuleDescription")) {
			description = (String) Reflection.runMethod(
					runningModuleMethodsMap.get("getModuleDescription"),
					runningModuleObject, (Object[]) null);
		}

		System.out.println(name);

		return new ModuleInformation(classname, name, area, description);
	}

	/**
	 * Changes the current module with the next module in the classes list
	 */
	public void nextModule() {
		frame.getContentPane().removeAll();
		frame.getContentPane().invalidate();

		if (index < configuration.size()) {
			System.out.println(configuration.get(index).getName());
			runningModuleClass = Reflection.getClass(url,
					configuration.get(index).getClassName());
			startModule(runningModuleClass, params.getDifficultyOrdinal(),
					new Integer(configuration.get(index).getTime() * 1000));
			index++;
		} else {
			// Test finished
			stopAnnoyance();
			// Time to call the evaluation!
			createOutcomeGUI();
		}
	}

	/**
	 * Function to get the result from the modules and save it to the config
	 */
	public void sendModuleResult(String moduleName, Integer points) {
		System.out.println("sendModuleResult called " + moduleName + "  "
				+ points);
		params.addResult(moduleName, points.intValue());
	}

	/**
	 * Generates the Outcome of the Test and creates the GUI for the Outcome
	 */
	public void createOutcomeGUI() {
		outcome.createOutcome();
		panel = outcome.getOutcomeGUI();
		frame.getContentPane().add(panel);
		frame.getContentPane().revalidate();
	}
}
