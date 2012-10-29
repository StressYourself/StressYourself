package de.dhbw.stress_yourself;

import java.awt.EventQueue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApplication {

	private JFrame frame;
	
	private Parameter params;
	
	//private Class<?> rClass = null;
	//private HashMap<String,Method> rMethodsMap = null;

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

		params = Parameter.getInstance();

		URL url = Reflection.getURL(params.getPathToJar());
		List<String> classes = Reflection.getClassNames(params.getPathToJar(),params.getPackageName());
		Class<?> clazz = Reflection.getClass(url, classes.get(0));
		System.out.println(clazz.getName());

		int difficulty = 0;
		String time = "";
		startModule(clazz, difficulty, time);
	}

	

	public boolean startTest() {
		return true;
	}

	Object runningModuleObject = null;
	Method sendResult = null;

	public boolean startModule(Class<?> clazz, int difficulty, String time) {
		HashMap<String, Method> methodsMap = Reflection.getClassMethods(clazz);
		sendResult = methodsMap.get("sendResult");


		Object o = null;
		Constructor<?> cons = null;
		try {
			cons = clazz.getConstructor(new Class[]{Object.class});
		} catch (NoSuchMethodException | SecurityException e) {
			System.err.println("Couldn't get the Constructor " +e);
		}
		try {
			o = cons.newInstance(this);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			System.err.println("Couldn't the object from the module " +e);
		}
		runningModuleObject = o;

		long start = System.currentTimeMillis();

		if (methodsMap.containsKey("getModuleJPanel")) {
			JPanel panel = null;

			panel = (JPanel) Reflection.runMethod(methodsMap.get("getModuleJPanel"), o,
					(Object[]) null);
			frame.add(panel);
		}
		
		System.out.println("Duration in ms: "
				+ (System.currentTimeMillis() - start));
		
		Runnable runObject = (Runnable) o;
		Thread test = new Thread(runObject);
		test.start();
		
		/*
		try {
			test.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		

		return true;
	}
	
	public void isFinished(){
		System.out.println("finished");
		Reflection.runMethod(sendResult, runningModuleObject, (Object[]) null);
	}
}
