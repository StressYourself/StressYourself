package de.dhbw.stress_yourself.extend;

import javax.swing.JPanel;

//Test

public abstract class ModuleClass {
	
	public abstract void sendResult();

	public abstract void setDifficulty(int diff);
	
	public abstract void setTime(String time);
	
	public abstract JPanel getModuleJPanel();
	
}

