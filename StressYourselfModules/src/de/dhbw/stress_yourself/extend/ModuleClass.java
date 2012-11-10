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
	private int time;
	private Object mainClass = null;
	private Timer nextTaskTimer;
	private Timer nextModuleTimer;

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
	
	public void setNextTaskTimer(int time, int intervall, TimerTask timer) {
	    nextTaskTimer = new Timer();
	    nextTaskTimer.schedule( timer, time, intervall );
	}
	
	public void resetNextTaskTimer(int time, int intervall, TimerTask timer) {
		nextTaskTimer.cancel();
		nextTaskTimer.purge();
	    nextTaskTimer = new Timer();
		nextTaskTimer.schedule( timer, time, intervall );
		
	}
	
	public void stopNextTaskTimer() {
		nextTaskTimer.cancel();
		nextTaskTimer.purge();
	}
	
	abstract class NextTask extends TimerTask{}
	
	public void setNextModuleTimer(int time, TimerTask timer) {
		nextModuleTimer = new Timer();
		nextModuleTimer.schedule(timer, time);
	}	
	
	public abstract void setTimerIntervall() ;
	
	abstract class NextModule extends TimerTask{}

	
	
	public abstract String getModuleName();
	public abstract String getModuleArea();
	public abstract String getModuleDescription();

	public abstract JPanel getModuleJPanel();

	
	/**
	 * Function to send the result of the module to the MainClass
	 * 
	 * @author Tobias Roeding <tobias@roeding.eu>
	 */
	public void sendResult(int result) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName("de.dhbw.stress_yourself.MainApplication");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method nextModule = null;
		try {
			nextModule = clazz.getMethod("sendModuleResult",new Class[] {String.class, Integer.class});
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		try {
			nextModule.invoke(mainClass, new Object[]{ getModuleName(), new Integer(result)});
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
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
