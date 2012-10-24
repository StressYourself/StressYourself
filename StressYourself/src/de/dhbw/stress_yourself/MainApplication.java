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
	
	public final String pathToJar = "../stress_yourself_modules.jar";

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
		
		Parameter params = Parameter.getInstance();
		
		
		URL url = loadJar(pathToJar);
		String names[] = getModuleNames();
		Class clazz = loadModule(url, names[0]);
		int difficulty = 0;
		String time = "";
		startModule(clazz, difficulty, time);
	}
	
	public URL loadJar(String path){
		URL url = null;
		try {
			url = new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	
	public Class loadModule(URL url, String name) {		
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

	public String[] getModuleNames() {
		URL url = loadJar(pathToJar);
		
		URLClassLoader urlcl = null;
		urlcl = URLClassLoader.newInstance(new URL[] { url });
		
		
		URL[] urls = urlcl.getURLs();
		System.out.println(urls[0].getFile());
		
		
		String[] test = { "de.dhbw.stress_yourself.modules.TestModule" };
		return test;
	}
	
	

	public boolean startTest() {		
		return true;
	}

	public boolean startModule(Class clazz, int difficulty, String time) {
		Method getJPanel = null;
		try {
			getJPanel = clazz.getMethod("getModuleJPanel", null);
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		JPanel panel = null;
		try {
			panel = (JPanel) getJPanel.invoke(o, null);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		frame.add(panel);
		return true;
	}
}
