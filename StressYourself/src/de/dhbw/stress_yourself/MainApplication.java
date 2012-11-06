package de.dhbw.stress_yourself;

import java.awt.EventQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	
	private JFrame frame;
	
	private Class<?> runningModuleClass = null;
	private Object runningModuleObject = null;
	private HashMap<String, Method> runningModuleMethodsMap = null;
	private URL url = null;
	private LinkedList<ModuleInformation> configuration = null;
	private JPanel panel = null;
	
	int index = 0;

	public MainApplication() {
		params = new Parameter();
		users = new UserData();
		admin = new Admin(users, params);
		login = new Login(users);
		outcome = new Outcome(params);
		
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

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 0, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getAvaiableModules();
		getConfiguration();
		
		frame.setContentPane(admin.getAdminPanel());
		login.getLoginPanel();
		
		//initModules();
		//nextModule();

	}

	public void getConfiguration() {
		//doesn't work right now, because of admin part
		//configuration = params.getConfiguration();
		configuration = params.getAvailableModules();
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
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public boolean startModule(Class<?> clazz, int difficulty, String time) {
		runningModuleMethodsMap = Reflection.getClassMethods(clazz);

		runningModuleObject = Reflection.createClassInstance(clazz, this);

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
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public void getAvaiableModules() {
		LinkedList<String> classes = new LinkedList<String>();
		url = Reflection.getURL(params.getPathToJar());
		classes = Reflection.getClassNames(params.getPathToJar(),
				params.getPackageName());
		for (int i = 0; i < classes.size(); i++) {
			params.addModuleInformation(getModuleInformation(url, classes.get(i)));
		}
	}

	/**
	 * Returns an Object of ModuleInformation containing all needed Information about the Module
	 * 
	 * @param url
	 * 			URL to the jar
	 * @param name
	 * 			Name of the class
	 * @return
	 * 			ModuleInformation Object
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public ModuleInformation getModuleInformation(URL url, String classname) {
		String name = null;
		String area = null;
		String description = null;
		 
		runningModuleClass = Reflection.getClass(url, classname);

		runningModuleMethodsMap = Reflection
				.getClassMethods(runningModuleClass);

		runningModuleObject = Reflection.createClassInstance(
				runningModuleClass, this);
		
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
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public void nextModule() {
		frame.getContentPane().removeAll();
		frame.getContentPane().invalidate();

		if (index < configuration.size()) {
			runningModuleClass = Reflection.getClass(url, configuration.get(index).getClassName());
			System.out.println(configuration.get(index).getName());
			index++;
			

			int difficulty = 0;
			String time = "";
			startModule(runningModuleClass, difficulty, time);
		} else {
			// Test finished, time to call the evaluation!
			createOutcome();
		}
	}
	
	/**
	 * Generates the Outcome of the Test and creates the GUI for the Outcome
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public void createOutcome(){
		panel = outcome.getOutcomeGUI();
		frame.getContentPane().add(panel);
		frame.getContentPane().revalidate();
	}
}
