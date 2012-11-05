package de.dhbw.stress_yourself.modules;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;
import de.dhbw.stress_yourself.extend.RandomCircles;
import de.dhbw.stress_yourself.extend.RandomSequence;

/**
 * Module to create captchas where the user has to find a open circle 
 * in a canvas full of circles.
 * 
 * @author Moritz Herbert <moritz.herbert@gmx.de>
 * */

public class CaptchaCirclesModule extends ModuleClass {

	public static final String moduleName = "CaptchaCirclesModule";
	public static final String moduleArea = "Concentration";
	public static final String moduleDescription = "Example Description";

	private int diff = 2;
	private String time = "";
	private ArrayList<Boolean> results = new ArrayList<Boolean>();

	public CaptchaCirclesModule(Object o) {
		super(o);
	}

	public JPanel getModuleJPanel() {
		return new ModuleGUI();
	}

	@Override
	public void setDifficulty(int diff) {
		this.diff = diff;
	}

	@Override
	public void setTime(String time) {
		this.time = time;
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
	 * */
	public void isValidCircle(int clickedX, int clickedY,
			Point openCircleCoordinates, int openCircleRadius) {
		int x = 0, y = 0;
		x = clickedX - openCircleCoordinates.x;
		y = clickedY - openCircleCoordinates.y;

		if (((x * x) + (y * y)) <= (openCircleRadius * openCircleRadius)) {
			System.out.println("drin");
			results.add(true);
		} else {
			System.out.println("draußen");
			results.add(false);
		}

	}

	class ModuleGUI extends JPanel implements ActionListener, MouseListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;
		private RandomCircles captcha;
		private JTextPane pane = new JTextPane();
		private JButton button = new JButton("next module");

		public ModuleGUI() {
			buttons = new ArrayList<JButton>();
			init();
			setLayout(null);
			setBounds(0, 0, 800, 600);
		}
		
		public RandomCircles createCaptcha() {
			RandomCircles c = new RandomCircles(diff);
			
			c.setBounds(50, 100, 300, 100);
			c.setBackground(Color.darkGray);
			c.addMouseListener(this);
			this.add(c);
			return c;
		}

		public void registerButton(JButton button) {
			buttons.add(button);
		}

		public void init() {
			captcha = createCaptcha();
			pane.setBounds(50, 10, 300, 30);

			pane.setText("CaptchaCirclesModules");
			pane.setBounds(50, 50, 175, 30);
			this.add(pane);

			registerButton(button);
			button.addActionListener(this);
			button.setBounds(230, 50, 120, 30);
			this.add(button);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (buttons.indexOf(e.getSource())) {
			case 0:
				sendResult();
				tellFinished();
				break;
			default:
				break;
			}
			
			
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			isValidCircle(e.getX(), e.getY(), 
					captcha.getOpenCircleCoordinates(), 
					captcha.getOpenCircleRadius());
			this.remove(captcha);
			captcha = createCaptcha();
			this.revalidate();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
