package de.dhbw.stress_yourself;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApplication {

	private JFrame frame;

	public final String pathToJar = "../stress_yourself_modules.jar";
	public final String packageName = "de.dhbw.stress_yourself.modules";

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

		// Parameter params = Parameter.getInstance();

		URL url = getURL(pathToJar);
		List<String> classes = getModuleNames();
		Class<?> clazz = getModuleClass(url, classes.get(0));
		System.out.println(clazz.getName());

		int difficulty = 0;
		String time = "";
		startModule(clazz, difficulty, time);
	}

	/**
	 * Converts a string path into an URL
	 * 
	 * @param path
	 *            The path as string
	 * @return URL of the file or directory
	 */
	public URL getURL(String path) {
		URL url = null;
		try {
			url = new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * Loads a class with the given name from a specified url
	 * 
	 * @param url
	 *            URL to the class
	 * @param name
	 *            Name of the class
	 * @return The class
	 */
	public Class<?> getModuleClass(URL url, String name) {
		URLClassLoader urlcl = null;
		urlcl = URLClassLoader.newInstance(new URL[] { url });

		Class<?> clazz = null;
		try {
			clazz = urlcl.loadClass(name);
			while (clazz.getName().contains("$")) {
				clazz = clazz.getEnclosingClass();
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found " + e);
		}

		return clazz;
	}

	public boolean startTest() {
		return true;
	}

	Object runningModuleObject = null;
	Method sendResult = null;

	public boolean startModule(Class<?> clazz, int difficulty, String time) {
		Method[] methodsArray = clazz.getMethods();
		HashMap<String, Method> methodsMap = new HashMap<String, Method>();

		for (int i = 0; i < methodsArray.length; i++) {
			methodsMap.put(methodsArray[i].getName(), methodsArray[i]);
		}

		sendResult = methodsMap.get("sendResult");

		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Couldn't create Object " + e);
		}
		runningModuleObject = o;

		long start = System.currentTimeMillis();

		if (methodsMap.containsKey("getModuleJPanel")) {
			JPanel panel = null;

			panel = (JPanel) runMethod(methodsMap.get("getModuleJPanel"), o,
					(Object[]) null);
			frame.add(panel);
		}
		System.out.println("Duration in ms: "
				+ (System.currentTimeMillis() - start));

		return true;
	}

	/**
	 * Runs the specified Method in the context of the specified Object and with
	 * the parameter which were given.
	 * 
	 * @param function
	 *            The method to run
	 * @param o
	 *            The object of the class
	 * @param params
	 *            The parameter the function is called with
	 * @return An object of the return value of the function (has to be casted
	 *         to use)
	 */
	public Object runMethod(Method function, Object o, Object[] params) {
		Object result = null;
		try {
			result = function.invoke(o, params);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.err.println("Couldn't run function " + e);
			return null;
		}

		return result;
	}

	public List<String> getModuleNames() {
		List<String> classes = null;
		try {
			classes = getClassesFromJar(pathToJar, packageName);
		} catch (Exception e) {
			System.err.println("Couldn't get the classes from the jar File "
					+ e);
		}
		return classes;
	}

	/**
	 * Scans all classes accessible from the jar file which belong to the given
	 * package.
	 * 
	 * @param pathToJar
	 *            File path to the jar file
	 * @param packageName
	 *            The base package
	 * @return The names of the classes as LinkedList
	 */
	public List<String> getClassesFromJar(String pathToJar, String packageName) {
		List<String> classes = new LinkedList<String>();
		JarFile jf = null;

		if (packageName.contains(".")) {
			packageName = packageName.replace(".", "/");
		}

		try {
			jf = new JarFile(pathToJar);
			Enumeration<JarEntry> je = jf.entries();
			do {
				JarEntry nextEntry = je.nextElement();
				String nextEntryName = nextEntry.getName();
				if (nextEntryName.startsWith(packageName)
						&& nextEntryName.endsWith(".class")) {
					String className = nextEntryName.replace("/", ".");
					className = className.substring(0,
							className.indexOf(".class"));
					classes.add(className);
				}
			} while (je.hasMoreElements());
			jf.close();
		} catch (IOException e) {
			System.err.println("Couldn't find the jar " + e);
		}

		return classes;
	}
}
