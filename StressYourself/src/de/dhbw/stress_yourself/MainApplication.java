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

		URL url = loadJar(pathToJar);
		List<String> classes = getModuleNames();
		Class<?> clazz = loadModule(url, classes.get(0));
		int difficulty = 0;
		String time = "";
		startModule(clazz, difficulty, time);
	}

	public URL loadJar(String path) {
		URL url = null;
		try {
			url = new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	public Class<?> loadModule(URL url, String name) {
		URLClassLoader urlcl = null;
		urlcl = URLClassLoader.newInstance(new URL[] { url });

		Class<?> clazz = null;
		try {
			clazz = urlcl.loadClass(name);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found " + e);
		}

		return clazz;
	}

	public boolean startTest() {
		return true;
	}

	public boolean startModule(Class<?> clazz, int difficulty, String time) {
		Method getJPanel = null;
		try {
			getJPanel = clazz.getMethod("getModuleJPanel", (Class<?>) null);
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println("Couldn't find the getModuleJPanle Method " + e);
		}

		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Couldn't create Object " + e);
		}

		JPanel panel = null;
		try {
			panel = (JPanel) getJPanel.invoke(o, (Object) null);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.err
					.println("Couldn't get JPanle from getModuleJPanel method "
							+ e);
		}
		frame.add(panel);
		return true;
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
