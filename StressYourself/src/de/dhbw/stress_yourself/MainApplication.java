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
 * @author Tobias Ršding <tobias@roeding.eu>
 */
public class MainApplication {

	private JFrame frame;

	private Parameter params;

	private Class<?> runningModuleClass = null;
	private Object runningModuleObject = null;
	private HashMap<String, Method> runningModuleMethodsMap = null;
	private URL url = null;
	private LinkedList<String> classes = null;
	private JPanel panel = null;
	
	int index = 0;

	public MainApplication() {
		params = new Parameter();

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
		
		//Admin admin = new Admin();
		//frame.add(admin.loadAdminGUI());
		//initModules();
		nextModule();

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
	 * @author Tobias Ršding <tobias@roeding.eu>
	 */
	public boolean startModule(Class<?> clazz, int difficulty, String time) {
		runningModuleMethodsMap = Reflection.getClassMethods(clazz);

		runningModuleObject = Reflection.createClassInstance(clazz, this);

		if (runningModuleMethodsMap.containsKey("getModuleJPanel")) {
			panel = (JPanel) Reflection.runMethod(
					runningModuleMethodsMap.get("getModuleJPanel"),
					runningModuleObject, (Object[]) null);
			frame.add(panel);
		}

		return true;
	}

	/**
	 * Inits the Modules by getting the url and the names of the modules
	 * 
	 * @author Tobias Ršding <tobias@roeding.eu>
	 */
	public void getAvaiableModules() {
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
	 * @author Tobias Ršding <tobias@roeding.eu>
	 */
	public ModuleInformation getModuleInformation(URL url, String name) {
		String area = null;
		String description = null;
		 
		runningModuleClass = Reflection.getClass(url, name);

		runningModuleMethodsMap = Reflection
				.getClassMethods(runningModuleClass);

		runningModuleObject = Reflection.createClassInstance(
				runningModuleClass, this);

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

		return new ModuleInformation(name, area, description);
	}

	/**
	 * Changes the current module with the next module in the classes list
	 * 
	 * @author Tobias Ršding <tobias@roeding.eu>
	 */
	public void nextModule() {
		if (panel != null) {
			frame.remove(panel);
			panel = null;
		}

		if (index < classes.size()) {
			runningModuleClass = Reflection.getClass(url, classes.get(index));
			index++;
			System.out.println(runningModuleClass.getName());

			int difficulty = 0;
			String time = "";
			startModule(runningModuleClass, difficulty, time);
		} else {
			// test finished, time to call the evaluation
		}
	}
}
