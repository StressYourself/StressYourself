package de.dhbw.stress_yourself.annoyance;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JWindow;

public class Annoyance implements Runnable {
	private Timer nextAnnoyanceTimer = new Timer();
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

	public int getScreenSizeX() {
		Dimension scrnsize = toolkit.getScreenSize();
		return scrnsize.width;
	}

	public int getScreenSizeY() {
		Dimension scrnsize = toolkit.getScreenSize();
		return scrnsize.height;
	}

	public int randomNumber(int lowest, int highest) {
		highest++;
		return (int) (Math.random() * (highest - lowest) + lowest);
	}

	public void setNextAnnoyanceTimer(int time, TimerTask timer) {
		nextAnnoyanceTimer = new Timer();
		nextAnnoyanceTimer.schedule(timer, time);
	}

	public void setNextBackgroundColorTimer(int time, TimerTask timer) {
		nextBackgroundColor = new Timer();
		nextBackgroundColor.schedule(timer, time, time);
	}
	
	public void stopNextBackgroundColor() {
		nextBackgroundColor.cancel();
		nextBackgroundColor.purge();
	}
	
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

	public class CloseAnnoyance extends TimerTask {
		@Override
		public void run() {
			stopNextBackgroundColor();
			annoyanceFrame.setVisible(false);
			annoyanceFrame.dispose();
		}
	}
}
