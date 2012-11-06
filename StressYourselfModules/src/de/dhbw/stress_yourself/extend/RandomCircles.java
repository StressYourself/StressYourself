package de.dhbw.stress_yourself.extend;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * This class contains a blueprint 
 * for a canvas which draws random circles used as a captcha test 
 * 
 * @author Moritz Herbert <moritz.herbert@gmx.de>
 * */

public class RandomCircles extends Canvas {
	private static final long serialVersionUID = 1L;

	private int difficulty = 0;
	private Point openCircleCoordinates;
	private int openCircleRadius;

	public RandomCircles(int difficulty) {
		this.difficulty = difficulty;
	}

	public int randomNumber(int lowest, int highest) {
		highest++;
		return (int) (Math.random() * (highest - lowest) + lowest);
	}

	public Point getOpenCircleCoordinates() {
		return openCircleCoordinates;
	}

	public int getOpenCircleRadius() {
		return openCircleRadius;
	}

	public void paint(Graphics g) {
		int circleCount = 9, radius = 30, arcAngle = 330, 
				randX = 0, randY, x = 0, incrementationStep, indexOfOpenCircle;
		Random r = new Random();

		switch (difficulty) {
		case (0):
			break;
		case (1):
			circleCount = 12;
			radius = 20;
			arcAngle = 325;
			break;
		case (2):
			circleCount = 14;
			radius = 14;
			arcAngle = 325;
			break;
		}
		
		x = incrementationStep = 300 / circleCount;
		 
		indexOfOpenCircle = randomNumber(0, circleCount-1);
		
		for (int i = 0; i < circleCount; i++) {
			randX = randomNumber(x - radius, x - radius - 2);
			randY = randomNumber(radius + 2, 100 - radius - 2);
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255))
					.brighter());
			if (i == indexOfOpenCircle) {
				//draw open circle
				g.drawArc(randX, randY, radius, radius, 0, arcAngle);
				g.drawArc(randX, randY, radius - 1, radius - 1, 0, arcAngle);
				openCircleCoordinates = new Point(randX, randY);
				openCircleRadius = radius;
			} else {
				//draw other circles
				g.drawOval(randX, randY, radius, radius);
				g.drawOval(randX, randY, radius - 1, radius - 1);
			}
			x += incrementationStep; 
		}
	}

}
