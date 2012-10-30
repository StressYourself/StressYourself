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

	public boolean startModule(Class<?> clazz, int difficulty, String time) {
		runningModuleMethodsMap = Reflection.getClassMethods(clazz);

		runningModuleObject = createModuleInstance(clazz);

		if (runningModuleMethodsMap.containsKey("getModuleJPanel")) {
			panel = (JPanel) Reflection.runMethod(
					runningModuleMethodsMap.get("getModuleJPanel"), runningModuleObject,
					(Object[]) null);
			frame.add(panel);
		}

		return true;
	}
	
	public Object createModuleInstance(Class<?> clazz){
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

	public void initModules() {
		params = new Parameter();
		url = Reflection.getURL(params.getPathToJar());
		classes = Reflection.getClassNames(params.getPathToJar(),
				params.getPackageName());
	}

	public void nextModule() {
		if (panel != null) {
			frame.remove(panel);
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
