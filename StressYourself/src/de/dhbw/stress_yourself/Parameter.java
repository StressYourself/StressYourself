package de.dhbw.stress_yourself;

import java.util.HashMap;

public final class Parameter {

	public Parameter() {

	}

	private final String pathToJar = "../stress_yourself_modules.jar";
	private final String packageName = "de.dhbw.stress_yourself.modules";
	private String parameters[][];
	private HashMap<String[], Float> result;
	private int difficulty;

	private String[][] existingModules;
	private static boolean started = false;

	/*
	 * existingModules i[n] modul elementes j[0] modulname j[1] URL j[2] area
	 */
	// public getter and setter

	public String getPathToJar() {
		return pathToJar;
	}

	public String getPackageName() {
		return packageName;
	}

	/**
	 * 
	 * @return parameters i[n] sequence of modules, in j[0] module name, in j[1]
	 *         time in seconds
	 */
	public String[][] getParameters() {
		return parameters;
	}

	/**
	 * 
	 * @return difficulty 0-"Beginner" 1-"Intermediate" 2-"Expert"
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * 
	 * @param parameters
	 *            i[n] sequence of modules, in j[0] module name, in j[1] time in
	 *            seconds
	 * @param difficulty
	 *            0-"Beginner" 1-"Intermediate" 2-"Expert"
	 * @return
	 */
	public boolean setConfig(String[][] parameters, int difficulty) {
		this.parameters = parameters;
		this.difficulty = difficulty;
		return false;
	}

	// methodes for set and get result

	public void saveResult(String moduleName, String area, Float percent) {
		String[] tmpArray = { moduleName, area };
		result.put(tmpArray, percent);
	}

	/**
	 * 
	 * @return [module name][area], percentage
	 */
	public HashMap<String[], Float> getResult() {
		return result;
	}

	// some private functions for the implementation

}