//package de.dhbw.stress_yourself.modules;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.TimerTask;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.JTextPane;
//
//import de.dhbw.stress_yourself.extend.ModuleClass;
//
//
//public class PopupModule extends ModuleClass {
//
//	private final String moduleName = "PopupModule";
//	private final String moduleArea = "Reaction";
//	private final String moduleDescription = "Example Description";
//
//	private ArrayList<Boolean> results = new ArrayList<Boolean>();
//	private int buttonCount = 0;
//	private int nextButtonIntervall = 5000;
//
//	public PopupModule(Object o, Integer difficulty, Integer time) {
//		super(o, difficulty.intValue(), time.intValue());
//		setTimerIntervall();
//	}
//	
//	public void setTimerIntervall() {
//		switch(getDifficulty()) {
//		case(0):
//			break;
//		case(1):
//			nextButtonIntervall = 4000;
//			break;
//		case(2):
//			nextButtonIntervall = 3000;
//			break;
//		}
//	}
//
//	public JPanel getModuleJPanel() {
//		return new ModuleGUI();
//	}
//
//	@Override
//	public String getModuleName() {
//		return moduleName;
//	}
//
//	@Override
//	public String getModuleArea() {
//		return moduleArea;
//	}
//
//	@Override
//	public String getModuleDescription() {
//		return moduleDescription;
//	}
//	
//	
//
//	class ModuleGUI extends JPanel implements ActionListener {
//		private final String moduleName = "CaptchaCirclesModule";
//		private final String moduleArea = "Concentration";
//		private final String moduleDescription = "Example Description";
//		private int nextTaskIntervall = 5000;
//
//		private int captchaCounter;
//		private int captchaCount;
//		private int solvedCorrectly = 0;
//		private int result;
//
//		/**
//		 * This method set the timer intervall in dependency of the difficulty. It
//		 * also sets the amount of tasks that can be solved in the given time and
//		 * the counter which is responsible for counting down the remaining tasks.
//		 */
//
//		public void setTimerIntervall() {
//			switch (getDifficulty()) {
//			case (0):
//				captchaCounter = getTime() / nextTaskIntervall;
//				captchaCount = captchaCounter;
//				break;
//			case (1):
//				nextTaskIntervall = 4000;
//				captchaCounter = getTime() / nextTaskIntervall;
//				captchaCount = captchaCounter;
//				break;
//			case (2):
//				nextTaskIntervall = 3000;
//				captchaCounter = getTime() / nextTaskIntervall;
//				captchaCount = captchaCounter;
//				break;
//			}
//		}
//
//		public JPanel getModuleJPanel() {
//			return new ModuleGUI();
//		}
//
//		@Override
//		public String getModuleName() {
//			return moduleName;
//		}
//
//		@Override
//		public String getModuleArea() {
//			return moduleArea;
//		}
//
//		@Override
//		public String getModuleDescription() {
//			return moduleDescription;
//		}
//
//		/**
//		 * This method calculates whether coordinates of mouse click are in open
//		 * circle with help of the Pythagorean theorem
//		 * 
//		 * @param clickedX
//		 *            contains x-coordinate of mouse click
//		 * @param clickedY
//		 *            contains y-coordinate of mouse click
//		 * @param openCircleCoordinates
//		 *            contains coordinates of open circle
//		 * @param openCircleRadius
//		 *            contains radius of open circle
//		 */
//		public void isValidCircle(int clickedX, int clickedY,
//				Point openCircleCoordinates, int openCircleRadius) {
//			int x = 0, y = 0;
//			x = clickedX - openCircleCoordinates.x;
//			y = clickedY - openCircleCoordinates.y;
//
//			if (((x * x) + (y * y)) <= (openCircleRadius * openCircleRadius)) {
//				System.out.println("drin");
//				solvedCorrectly++;
//			} else {
//				System.out.println("draussen");
//			}
//
//		}
//
//		
//		/**
//		 * this class is responsible for building the JPanel which will be inserted
//		 * in the main JFrame.
//		 */
//
//		class ModuleGUI extends JPanel implements ActionListener, MouseListener {
//
//			private static final long serialVersionUID = 1L;
//			private ArrayList<JButton> buttons = null;
//			private RandomCircles captcha;
//			private JLabel moduleDescriptionLabel = new JLabel(
//					getModuleDescription());
//			private JLabel moduleTimeLabel = new JLabel("Maximum time for module: "
//					+ String.valueOf(getTime() / 1000) + "seconds");
//			private JLabel moduleDesIntervallLabel = new JLabel(
//					"Maximum time per Task: "
//							+ String.valueOf(nextTaskIntervall / 1000) + "seconds");
//			private JLabel taskCountLabel = new JLabel(captchaCount
//					+ " tasks can be solved");
//			private JPanel introductionPanel = new JPanel();
//			private JPanel thisPanel = this;
//			private JButton startTasksButton = new JButton("start");
//
//			public ModuleGUI() {
//				buttons = new ArrayList<JButton>();
//				setLayout(null);
//				setBounds(0, 0, 900, 700);
//				init();
//			}
//
//			/**
//			 * Creates a new captcha canvas ands sets the basic properties like size
//			 * and position.
//			 * 
//			 * @return The created captcha canvas.
//			 */
//
//			public RandomCircles createCaptcha() {
//				RandomCircles c = new RandomCircles(getDifficulty());
//				System.out.println(c.getOpenCircleRadius() + "---");
//				c.setBounds(300, 100, 300, 100);
//				c.setBackground(Color.darkGray);
//				c.addMouseListener(this);
//				this.add(c);
//				return c;
//			}
//			
//			/**
//			 * Adds a button to a ArrayList needed to say which part of the switch-case block
//			 * in the actionPerformed Method is used for which button.
//			 * @param button
//			 * 		The button, which has to be registered
//			 */
//
//			public void registerButton(JButton button) {
//				buttons.add(button);
//			}
//
//			/**
//			 * This method displays the panel which shows the property of the module
//			 * before the module starts. a click on the startTasksButton button
//			 * starts the module and calls startTask()
//			 */
//
//			public void init() {
//				introductionPanel.setLayout(null);
//				introductionPanel.setBounds(0, 0, 800, 600);
//				this.add(introductionPanel);
//
//				moduleDescriptionLabel.setBounds(300, 50, 300, 100);
//				introductionPanel.add(moduleDescriptionLabel);
//
//				moduleTimeLabel.setBounds(300, 155, 300, 30);
//				introductionPanel.add(moduleTimeLabel);
//
//				moduleDesIntervallLabel.setBounds(300, 190, 300, 30);
//				introductionPanel.add(moduleDesIntervallLabel);
//
//				taskCountLabel.setBounds(300, 225, 300, 30);
//				introductionPanel.add(taskCountLabel);
//
//				startTasksButton.setBounds(400, 260, 75, 30);
//				introductionPanel.add(startTasksButton);
//				registerButton(startTasksButton);
//				startTasksButton.addActionListener(this);
//			}
//
//			/**
//			 * This method generates the GUI displaying the module tasks
//			 */
//
//			public void startTask() {
//				captcha = createCaptcha();
//				setNextTaskTimer(nextTaskIntervall, nextTaskIntervall,
//						new NextTask());
//				setNextModuleTimer(getTime(), new NextModule());
//			}
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				switch (buttons.indexOf(e.getSource())) {
//				case 0:
//					thisPanel.removeAll();
//					startTask();
//					thisPanel.repaint();
//					break;
//				default:
//					break;
//				}
//
//			}
//
//
//			/**
//			 * An instance of this class will be created and run as a kind of event
//			 * every time the timer for the next task counted down to the end.
//			 */
//
//			public class NextTask extends TimerTask {
//				@Override
//				public void run() {
//					if (captchaCounter >= 1) {
//						thisPanel.remove(captcha);
//						captcha = createCaptcha();
//						thisPanel.revalidate();
//						captchaCounter--;
//					} else {
//						stopNextTaskTimer();
//						result = (solvedCorrectly / captchaCount) * 100;
//						System.out.println(result + "+" + solvedCorrectly + "/"
//								+ captchaCount);
//						sendResult(result);
//						tellFinished();
//					}
//				}
//			}
//
//			/**
//			 * An instance of this class will be created and run as a kind of event
//			 * every time the timer for the next module counted down to the end.
//			 */
//
//			public class NextModule extends TimerTask {
//				@Override
//				public void run() {
//					stopNextTaskTimer();
//					result = (solvedCorrectly / captchaCount) * 100;
//					System.out.println(result + "+" + solvedCorrectly + "/"
//							+ captchaCount);
//					sendResult(result);
//					tellFinished();
//				}
//			}
//		}
//
//		/**
//		 * This class contains a blueprint for a canvas which draws random circles
//		 * used as a captcha test
//		 */
//		class RandomButton implements ActionListener {
//			private static final long serialVersionUID = 1L;
//
//			private int difficulty = 0;
//			private int openCircleRadius;
//
//			public RandomButton(int difficulty) {
//				this.difficulty = difficulty;
//			}
//
//			/**
//			 * This method generates a random number of a given range
//			 * 
//			 * @param lowest
//			 *            provides the downwards limit of the range
//			 * @param highest
//			 *            provides the upwards limit of the range
//			 * @return the random number
//			 */
//
//			public int randomNumber(int lowest, int highest) {
//				highest++;
//				return (int) (Math.random() * (highest - lowest) + lowest);
//			}
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		}
//}
//}
//
