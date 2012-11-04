package de.dhbw.stress_yourself;

import java.awt.Component;
import java.awt.EventQueue;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApplication {

	private JFrame frame;

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

		String path = "../stress_yourself_modules.jar";
		URL url = null;
		try {
			url = new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		URLClassLoader urlcl = null;
		urlcl = URLClassLoader.newInstance(new URL[] { url });

		Class<?> clazz = null;
		try {
			clazz = urlcl.loadClass("de.dhbw.stress_yourself.modules.TestModule");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found " + e);
		}

		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Method getJPanel = null;
		try {
			getJPanel = clazz.getMethod("getModuleJPanel", null);
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JPanel panel = null;
		try {
			panel = (JPanel) getJPanel.invoke(o, null);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		frame.add(panel);
	}

	
	public Class loadModule(String filePath) {
		Class c = null;
		try {
			c = Class.forName(filePath);
		} catch (ClassNotFoundException e) {
			System.err.println("Could not find the Class: " + e);
		}
		return c;
	}

	public String[] loadModules() {
		String[] test = { "de.dhbw.stress_yourself.modules.TestModule" };
		return test;
	}
	
	

	public boolean startTest() {
		return false;
	}

	public boolean startModule(String moduleName, int difficulty, String time) {
		return false;
	}
}
