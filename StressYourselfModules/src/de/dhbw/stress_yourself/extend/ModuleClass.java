package de.dhbw.stress_yourself.extend;

import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

/**
 * The ModuleClass is an abstract class to predefine some functions and vars for
 * the subclasses.
 * 
 * @author Moritz Herbert <moritz.herbert@gmx.de>
 * @author Tobias Roeding <tobias@roeding.eu>
 */
public abstract class ModuleClass {

	private int diff;
	private int time = 5;

	private int result = 0;

	private Object mainClass = null;

	public ModuleClass(Object o, int difficulty,int time) {
		if (mainClass == null) {
			mainClass = o;
		}
		this.diff = difficulty;
		this.time = time;
	}
	
	public int getDifficulty() {
		return diff;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTimer() {
	    Timer timer = new Timer();
	    timer.schedule(new Task(), time*1000);
	}

	class Task extends TimerTask
	{
	  @Override public void run()
	  {
	    System.out.println( "Immer puenktlich!" );
	  }
	}

	public abstract JPanel getModuleJPanel();

	public void sendResult() {
		System.out.println("sending Result " + result);

	}

	/**
	 * Function to tell the main class, that the module has finshed his test and
	 * the next module can be loaded
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public void tellFinished() {
		Class<?> clazz = null;
		try {
			clazz = Class.forName("de.dhbw.stress_yourself.MainApplication");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method nextModule = null;
		try {
			nextModule = clazz.getMethod("nextModule");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			nextModule.invoke(mainClass);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	abstract class ModuleGUI extends JPanel implements ActionListener {
	}

}
