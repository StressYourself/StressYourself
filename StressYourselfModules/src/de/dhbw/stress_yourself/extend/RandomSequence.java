package de.dhbw.stress_yourself.extend;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Random;

public class RandomSequence extends Canvas {
	private static final long serialVersionUID = 1L;

	private int difficulty = 0;
	private String sequence = "";
	Random r = new Random();

	public RandomSequence(int difficulty) {
		this.difficulty = difficulty;
	}

	public char getRandomChar() {
		String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		char rand = validChars.charAt(r.nextInt(validChars.length()));
		
		return rand;
	}

	public int randomNumber(int lowest, int highest) {
		highest++;
		return (int) (Math.random() * (highest - lowest) + lowest);
	}

	public String getSequence() {
		return sequence;
	}

	public void paint(Graphics g) {
		int charCount = 6, incrementationStep, x;
		char randomChar;

		switch (difficulty) {
		case (0):
			break;
		case (1):
			charCount = 8;
			break;
		case (2):
			charCount = 10;
			break;
		}
		
		x = incrementationStep = 300 / charCount;
		x/=2;
		
		g.setFont(new Font("Monotype Corsiva", Font.PLAIN, 23));
		
		//get random string and paint it char by char
		for (int i = 0; i < charCount; i++) {
			//get random char
			randomChar = getRandomChar();			

			//paint background graphics
			for(int j = 0; j < 2; j++) {
				g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255))
				.darker().darker());
				 
				g.drawLine(randomNumber(0, 300), randomNumber(0, 100), 
						randomNumber(0, 300), randomNumber(0, 100));
			}
			
			//paint random char
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255))
			.brighter().brighter());
			
			g.drawString(String.valueOf(randomChar), randomNumber(x - 10, x - 6), randomNumber(15, 95));
			
			sequence += randomChar;
			
			x += incrementationStep;
		}
		
	}

}
