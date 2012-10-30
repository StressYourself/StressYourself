package de.dhbw.stress_yourself;

import java.awt.EventQueue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The MainApplication Class is used to manage and load all gui classes containing the modules.
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initModules();
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

		runningModuleObject = createModuleInstance(clazz);

		if (runningModuleMethodsMap.containsKey("getModuleJPanel")) {
			panel = (JPanel) Reflection.runMethod(
					runningModuleMethodsMap.get("getModuleJPanel"),
					runningModuleObject, (Object[]) null);
			frame.add(panel);
		}

		return true;
	}

	/**
	 * Create and return an instance of the module class
	 * 
	 * @param clazz
	 *            The module class
	 * @return Object The instance of the module
	 * @author Tobias Ršding <tobias@roeding.eu>
	 */
	public Object createModuleInstance(Class<?> clazz) {
		Object moduleObject = null;
		Constructor<?> cons = null;
		try {
			cons = clazz.getConstructor(new Class[] { Object.class });
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println("Couldn't get the Constructor " + e);
		}
		try {
			moduleObject = cons.newInstance(this);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			System.err.println("Couldn't the object from the module " + e);
		}
		return moduleObject;
	}

	/**
	 * Inits the Modules by getting the url and the names of the modules
	 * 
	 * @author Tobias Ršding <tobias@roeding.eu>
	 */
	public void initModules() {
		params = new Parameter();
		url = Reflection.getURL(params.getPathToJar());
		classes = Reflection.getClassNames(params.getPathToJar(),
				params.getPackageName());
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
			// test finished
		}
	}
}
