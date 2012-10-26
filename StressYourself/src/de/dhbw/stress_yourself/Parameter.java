package de.dhbw.stress_yourself;

import java.util.HashMap;

public final class Parameter {
	
	/** private class attribute, only one instance of the class is created. */
    private static final Parameter INSTANCE = new Parameter();

    /** constructor is private, should not be instanced form the outside. */
    private Parameter() {}

    /** static method, delivers the only instance of the class. */
    public static Parameter getInstance() {
        return INSTANCE;
    }
    
    // variables of the singleton, private and not static   
    // deutsche Kommentare können werden im späteren Verlauf wieder gelöscht
    
	private String parameters[][];
    private String users[][];
    private HashMap<String[], Float> result;
    private int difficulty;
    private String currentUser;
    private String [][] existingModules;
    /* 	geplanter Aufbau existingModules
    	i[n] Modulelemente
    		j[0] Modulname
    		j[1] URL
    		j[2] Bereich (Area), wenn die Übergabe hier noch nicht möglich, dann bei Parametern + Ergebnis
    		Bereich wird für die Ausgabe und Auswertung benötigt
    */
    // public getter and setter
       /**
     * 
     * @return parameters i[n] sequence of modules, in j[0] module name, in j[1] time in seconds
     */
    public String [][] getParameters(){
    	return parameters;
    }
    /**
     * 
     * @return difficulty 0-"Einsteiger" 1-"Fortgeschrittener" 2-"Experte"
     */
    public int getDifficulty(){
    	return difficulty;
    }
    /**
     * 
     * @param parameters i[n] sequence of modules, in j[0] module name, in j[1] time in seconds
     * @param difficulty 0-"Einsteiger" 1-"Fortgeschrittener" 2-"Experte"
     * @return
     */
    public boolean setConfig(String [][] parameters, int difficulty){
    	this.parameters = parameters;
    	this.difficulty = difficulty;
    	return false;
    }
    
    public String getCurrentUser(){
    	return this.currentUser;
    }
    
    public void setCurrentUser(String currentUser){
    	this.currentUser = currentUser;
    }
    /**
     * 
     * @param username name of user
     * @param password users password
     * @param type a - for admin, u - for user
     * @return 	false - if sum of all parameters dosen't exist
     * 			true - if parameter matches
     */
    public boolean existsUser(String username, String password, String type){
    	return false;
    }
    /**
    * @param type a - for admin, u - for user
    */
    public void saveUser(String username, String password, String type){
    	// speichert neuen User
    }
    
    public boolean changePassword(String username, String password, String type, String newPassword){
    	return existsUser(username,password,type);
    }
    
    public boolean deleteUser(String username){
    	// Enstscheidung: der aktuelle User darf sich nicht selbst loeschen
    	return (username != currentUser);
    }
    
    public String[][] getUsers(){
    	return users;
    }
    
    public void saveResult(String moduleName, Float percent){
    	// Speichert für jedes ausgeführte Modul einen Prozentwert
    }
    /**
     * 
     * @return [module name][area], percentage
     */
    public HashMap<String[], Float> getResult(){
    	return null;
    }
    
    // some private functions for the implementation

    // Wichtig: Methoden, die auf instanz-Variablen zugreifen mssen mit entsprechenden Mitteln
    // synchronisiert werden, da es das Singleton nur 1x gibt und somit die Variablen automatisch global sind
    // und von mehreren Threads gleichzeitig darauf zugegriffen werden kann.

}