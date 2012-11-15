package de.dhbw.stress_yourself.annoyance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JWindow;

public class Annoyance implements Runnable {
	private Timer closeAnnoyanceTimer = new Timer();
	private Timer nextBackgroundColor = new Timer();
	private int randX, randY;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private JWindow annoyanceFrame = new JWindow();
	private int annoyanceWindowSizeX = 200;
	private int annoyanceWindowSizeY = 200;

	public boolean running = true;

	/**
	 * Main mathod of the class. 
	 * Starts the annoyances and handles the delays between the annoyances. 
	 */
	@Override
	public void run() {
		while (running) {
			try {
				int[] xPos = { 0, 1150 };

				randX = randomNumber(0, 1);
				if (randX == 1)
					randX = randomNumber(xPos[1], getScreenSizeX()
							- annoyanceWindowSizeX);

				randY = randomNumber(0, getScreenSizeY() - annoyanceWindowSizeY);
				createAnnoyance(1500, randX, randY);

				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method to create a new annoyance with a given position 
	 * and the time the annoyance is displayed(milliseconds) 
	 * @param time
	 * @param xPos
	 * @param yPos
	 */
	public void createAnnoyance(int time, int xPos, int yPos) {
		setNextBackgroundColorTimer(20, new NextBackgroundColor());
		annoyanceFrame.setBounds(xPos, yPos, annoyanceWindowSizeX,
				annoyanceWindowSizeY);
		annoyanceFrame.setVisible(true);
		annoyanceFrame.toBack();
		closeAnnoyanceTimer.schedule(new CloseAnnoyance(), time);
	}
	
	/**
	 * Returns the width of the current screen size
	 * @return
	 */
	public int getScreenSizeX() {
		Dimension scrnsize = toolkit.getScreenSize();
		return scrnsize.width;
	}

	/**
	 * Returns the height of the current screen size
	 * @return
	 */
	public int getScreenSizeY() {
		Dimension scrnsize = toolkit.getScreenSize();
		return scrnsize.height;
	}

	/**
	 * Returns a random number of a given range
	 * @return
	 */
	public int randomNumber(int lowest, int highest) {
		highest++;
		return (int) (Math.random() * (highest - lowest) + lowest);
	}
	
	/**
	 * Sets a timer changing the background color of the annoyance 
	 * after a given time
	 *  
	 * @param time
	 * @param timer
	 */
	public void setNextBackgroundColorTimer(int time, TimerTask timer) {
		nextBackgroundColor = new Timer();
		nextBackgroundColor.schedule(timer, time, time);
	}
	
	/**
	 * Stops the timer changing the background color of the annoyance 
	 * after a given time when the annoyance gets hidden 
	 *  
	 * @param time
	 * @param timer
	 */
	public void stopNextBackgroundColor() {
		nextBackgroundColor.cancel();
		nextBackgroundColor.purge();
	}
	
	/**
	 * Timer event to change the background color of the annoyance 
	 */
	public class NextBackgroundColor extends TimerTask {
		@Override
		public void run() {
			Color c = new Color(randomNumber(1, 255), randomNumber(1, 255),
					randomNumber(1, 255));
			annoyanceFrame.getContentPane().setBackground(c);
			annoyanceFrame.revalidate();
			annoyanceFrame.repaint();
		}
	}

	/**
	 * Timer event to close the annoyance window
	 */
	public class CloseAnnoyance extends TimerTask {
		@Override
		public void run() {
			stopNextBackgroundColor();
			annoyanceFrame.setVisible(false);
			annoyanceFrame.dispose();
		}
	}
}
