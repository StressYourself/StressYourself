package de.dhbw.stress_yourself;

import java.util.HashMap;

public final class Parameter {
	 
	/** private class attribute, only instance of the class is created. */
    private static final Parameter INSTANCE = new Parameter();

    /** constructor is private, should not be instanced form the outside. */
    private Parameter() {}

    /** static method, delivers the only instance of the class. */
    public static Parameter getInstance() {
        return INSTANCE;
    }
    
    // variables of the singleton, private and not static    
    
	private String parameters[][];
    private String users[][];
    private HashMap<String, Integer> result;
    private int difficulty;
    private int currentUser;
    
    // public getter and setter
    public boolean loadConfig(){
    	return false;
    }
    
    public boolean setConfig(){
    	return false;
    }
    
    public int getCurrentUser(){
    	return this.currentUser;
    }
    
    public void setCurrentUser(int currentUser){
    	this.currentUser = currentUser;
    }
    
    // some private functions for the implementation

    // Wichtig: Methoden, die auf instanz-Variablen zugreifen müssen mit entsprechenden Mitteln
    // synchronisiert werden, da es das Singleton nur 1x gibt und somit die Variablen automatisch global sind
    // und von mehreren Threads gleichzeitig darauf zugegriffen werden kann.

}