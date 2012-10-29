package de.dhbw.stress_yourself;

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

public class Reflection {	
	
	/**
	 * Converts a string path into an URL
	 * 
	 * @param path
	 *            The path as string
	 * @return URL of the file or directory
	 */
	public static URL getURL(String path) {
		URL url = null;
		try {
			url = new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	
	/**
	 * Returns all class names in a given package as List
	 * 
	 * @param pathToJar
	 * 			Path to the jar
	 * @param packageName
	 * 			Package name the searched classes are in
	 * @return List<String>
	 * 			List with all class names
	 */
	public static List<String> getClassNames(String pathToJar, String packageName) {
		List<String> classes = null;
		try {
			classes = Reflection.getClassesFromJar(pathToJar, packageName);
		} catch (Exception e) {
			System.err.println("Couldn't get the classes from the jar File "
					+ e);
		}
		return classes;
	}

	/**
	 * Get all methods of a spezified class
	 * 
	 * @param clazz
	 * 			Class to search for methods
	 * @return HashMap<String,Method>
	 * 			A HashMap with the name and the function
	 */
	public static HashMap<String,Method> getClassMethods(Class<?> clazz){
		Method[] methodsArray = clazz.getMethods();
		HashMap<String, Method> methodsMap = new HashMap<String, Method>();

		for (int i = 0; i < methodsArray.length; i++) {
			methodsMap.put(methodsArray[i].getName(), methodsArray[i]);
		}
		return methodsMap;
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
	public static Class<?> getClass(URL url, String name) {
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
	public static Object runMethod(Method function, Object o, Object[] params) {
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
	public static List<String> getClassesFromJar(String pathToJar, String packageName) {
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
