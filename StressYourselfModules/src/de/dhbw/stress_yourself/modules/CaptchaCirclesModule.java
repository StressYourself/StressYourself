package de.dhbw.stress_yourself.modules;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * Module to create captchas where the user has to find a open circle in a
 * canvas full of circles.
 * 
 * @author Moritz Herbert <moritz.herbert@gmx.de>
 */
public class CaptchaCirclesModule extends ModuleClass {

	private final String moduleName = "CaptchaCirclesModule";
	private final String moduleArea = "Concentration";
	private final String moduleDescription = "Click in the open circle displayed in the picture.";

	private int testCounter;
	private int numberOfTests;
	private int timePerTest;
	private int solvedCorrectly = 0;
	private int result;

	public CaptchaCirclesModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		initTestValues();
	}

	/**
	 * This method set the timer interval in dependency of the difficulty. It
	 * also sets the amount of tasks that can be solved in the given time and
	 * the counter which is responsible for counting down the remaining tasks.
	 */
	public void initTestValues() {
		switch (getDifficulty()) {
		case 0:
			timePerTest = 5000;
			break;
		case 1:
			timePerTest = 4000;
			break;
		case 2:
			timePerTest = 3000;
			break;
		}
		numberOfTests = (getTime() / timePerTest);
		testCounter = numberOfTests;
	}

	public JPanel getModuleJPanel() {
		return new ModuleGUI();
	}

	@Override
	public String getModuleName() {
		return moduleName;
	}

	@Override
	public String getModuleArea() {
		return moduleArea;
	}

	@Override
	public String getModuleDescription() {
		return moduleDescription;
	}

	/**
	 * This method calculates whether coordinates of mouse click are in open
	 * circle with help of the Pythagorean theorem
	 * 
	 * @param clickedX
	 *            contains x-coordinate of mouse click
	 * @param clickedY
	 *            contains y-coordinate of mouse click
	 * @param openCircleCoordinates
	 *            contains coordinates of open circle
	 * @param openCircleRadius
	 *            contains radius of open circle
	 */
	public void isValidCircle(int clickedX, int clickedY,
			Point openCircleCoordinates, int openCircleRadius) {
		int x = 0, y = 0;
		x = clickedX - openCircleCoordinates.x;
		y = clickedY - openCircleCoordinates.y;

		if (((x * x) + (y * y)) <= (openCircleRadius * openCircleRadius)) {
			// in
			solvedCorrectly++;
		} else {
			// out
		}

	}

	/**
	 * this class is responsible for building the JPanel which will be inserted
	 * in the main JFrame.
	 */
	class ModuleGUI extends JPanel implements ActionListener, MouseListener {

		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;
		private RandomCircles captcha = null;
		private JPanel introductionPanel = new JPanel();
		private JPanel thisPanel = this;

		public ModuleGUI() {
			buttons = new ArrayList<JButton>();
			setLayout(null);
			setBounds(0, 0, 900, 700);
			init();
		}

		/**
		 * Creates a new captcha canvas ands sets the basic properties like size
		 * and position.
		 * 
		 * @return The created captcha canvas.
		 */
		public RandomCircles createCaptcha() {
			RandomCircles c = new RandomCircles(getDifficulty());
			c.setBounds(300, 100, 300, 100);
			c.setBackground(Color.darkGray);
			c.addMouseListener(this);
			return c;
		}

		/**
		 * Adds a button to a ArrayList needed to say which part of the
		 * switch-case block in the actionPerformed Method is used for which
		 * button.
		 * 
		 * @param button
		 *            The button, which has to be registered
		 */
		public void registerButton(JButton button) {
			buttons.add(button);
		}

		/**
		 * This method displays the panel which shows the property of the module
		 * before the module starts. a click on the startTasksButton button
		 * starts the module and calls startTask()
		 */
		public void init() {
			introductionPanel = getIntroductionPanel(timePerTest,
					numberOfTests, this);
			thisPanel.add(introductionPanel);
		}

		/**
		 * This method generates the GUI displaying the module tasks
		 */
		public void startTask() {
			thisPanel.remove(introductionPanel);
			captcha = createCaptcha();
			thisPanel.add(captcha);
			setNextModuleTimer(getTime(), new NextModule());
			thisPanel.revalidate();
			thisPanel.repaint();
		}
		
		/**
		 * The event called when a button is clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!buttons.contains(e.getSource())) {
				thisPanel.removeAll();
				startTask();
				thisPanel.repaint();
			}

		}

		/**
		 * These method handles the mouse event needed for the module. It calls
		 * the validation method and created a new captcha canvas on the panel
		 * or sends the result to the main application and tells the main
		 * application, that the modules has finished, if all tasks are passed.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (testCounter >= 1) {
				isValidCircle(e.getX(), e.getY(),
						captcha.getOpenCircleCoordinates(),
						captcha.getOpenCircleRadius());
				thisPanel.removeAll();
				captcha = createCaptcha();
				thisPanel.add(captcha);
				thisPanel.revalidate();
				thisPanel.repaint();
				testCounter--;
			} else {
				result = calculateResult(numberOfTests, solvedCorrectly);
				sendResult(result);
				tellFinished();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		/**
		 * An instance of this class will be created and run as a kind of event
		 * every time the timer for the next task counted down to the end.
		 */

		public class NextTask extends TimerTask {
			@Override
			public void run() {
				if (testCounter >= 1) {
					thisPanel.removeAll();
					captcha = createCaptcha();
					thisPanel.add(captcha);
					thisPanel.revalidate();
					thisPanel.repaint();
					testCounter--;
				} else {
					result = calculateResult(numberOfTests, solvedCorrectly);
					sendResult(result);
					tellFinished();
				}
			}
		}

		/**
		 * An instance of this class will be created and run as a kind of event
		 * every time the timer for the next module counted down to the end.
		 */

		public class NextModule extends TimerTask {
			@Override
			public void run() {
				result = calculateResult(numberOfTests, solvedCorrectly);
				sendResult(result);
				tellFinished();
			}
		}
	}

	/**
	 * This class contains a blueprint for a canvas which draws random circles
	 * used as a captcha test
	 */
	class RandomCircles extends Canvas {
		private static final long serialVersionUID = 1L;

		private int difficulty = 0;
		private Point openCircleCoordinates;
		private int openCircleRadius;

		public RandomCircles(int difficulty) {
			this.difficulty = difficulty;
		}

		/**
		 * This method generates a random number of a given range
		 * 
		 * @param lowest
		 *            provides the downwards limit of the range
		 * @param highest
		 *            provides the upwards limit of the range
		 * @return the random number
		 */
		public int randomNumber(int lowest, int highest) {
			highest++;
			return (int) (Math.random() * (highest - lowest) + lowest);
		}
		
		/**
		 * Returns the coordinates if the open circle
		 * @return
		 */
		public Point getOpenCircleCoordinates() {
			return openCircleCoordinates;
		}
		
		/**
		 * Returns the radius of the open circle
		 * @return
		 */
		public int getOpenCircleRadius() {
			return openCircleRadius;
		}

		/**
		 * This method is called automatically every time a new instance of this
		 * class is created. It draws the circles inside the canvas.
		 */

		public void paint(Graphics g) {
			int circleCount = 9, radius = 30, arcAngle = 330, randX = 0, randY, randArcOpening, x = 0, incrementationStep, indexOfOpenCircle;
			Random r = new Random();

			//enable antialiasing
			((Graphics2D)g).setRenderingHint
			  (RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON);
			
			//set default values with regard to the difficulty
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

			indexOfOpenCircle = randomNumber(0, circleCount - 1);

			for (int i = 0; i < circleCount; i++) {
				//set x achsis position of current circle
				randX = randomNumber(x - radius, x - radius - 2);
				//set x achsis position of current circle
				randY = randomNumber(radius + 2, 100 - radius - 2);
				//set point where the open circle has his opening
				randArcOpening = randomNumber(0, 360);
				g.setColor(new Color(r.nextInt(255), r.nextInt(255), r
						.nextInt(255)).brighter());
				if (i == indexOfOpenCircle) {
					// draw open circle
					g.drawArc(randX, randY, radius, radius, randArcOpening,
							arcAngle);

					openCircleCoordinates = new Point(randX, randY);
					openCircleRadius = radius;
				} else {
					// draw other circles
					g.drawOval(randX, randY, radius, radius);
				}
				x += incrementationStep;
			}
		}
	}
}
