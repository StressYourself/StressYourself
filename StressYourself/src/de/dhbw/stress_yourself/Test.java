package de.dhbw.stress_yourself;

public class Test {
	public static void main(String[] args) {
		Annoyance annoyance = new Annoyance();
		Thread aThread = new Thread(annoyance);
		aThread.start();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		annoyance.running = false;
		System.exit(0);
	}
}