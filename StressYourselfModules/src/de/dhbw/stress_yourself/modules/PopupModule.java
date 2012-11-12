package de.dhbw.stress_yourself.modules;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;
import de.dhbw.stress_yourself.extend.ModuleClass;

/**
 * This class generates a reaction test. The aim is to click on a button as
 * fast as you can
 * 
 * @author Florian Albert <florian-albert@gmx.de
 *
 */
public class PopupModule extends ModuleClass {

		private final String moduleName = "PopupModule";
		private final String moduleArea = "Reaction";
		private final String moduleDescription = "This module generates a button on a random " +
				"location of the window. You have to click on the button as fast as you can to solve " +
				"this module.";
		private int timePerButton;
		private int numberOfButtons;
		private int buttonCounter = 0;
		private int buttonClicked = 0;
		
		public final int FRAMEWIDTH = 900;
		public final int FRAMEHEIGHT = 700;
		private final int BUTTONHEIGHT = 20;
		private final int BUTTONWIDTH = 20;

		public PopupModule(Object o, Integer difficulty, Integer time) {
			super(o, difficulty.intValue(), time.intValue());
			initTestValues();
		}
		
		/**
		 * Configurates the time per button and the number of buttons
		 * which can be clicked depending on the difficulty
		 */
		public void initTestValues() {
			switch (getDifficulty()) {
			case 0:
				timePerButton = 2000;
				break;
			case 1:
				timePerButton = 1500;
				break;
			case 2:
				timePerButton = 1000;
				break;
			}
			numberOfButtons = (getTime() / timePerButton);
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
		 * Generates the button for the test
		 * 
		 * @return
		 * 			JButton for the test
		 */
		public JButton generateTest() {
			
			JButton testButton = new JButton("X");
			
			testButton.setBackground(Color.RED);
			testButton.setForeground(Color.WHITE);
			testButton.setBorder(null);
			testButton.setOpaque(true);
			testButton.setSize(BUTTONWIDTH, BUTTONHEIGHT);
			testButton.setLocation(generateButtonLocation());
			buttonCounter++;
			
			return testButton;
		}
		
		/**
		 * Generates the location for the actual button
		 * 
		 * @return	
		 * 			Point for button location
		 */
		private Point generateButtonLocation() {
			int x = -1;
			int y = -1;
			do {
				x = myRandom( 0, FRAMEWIDTH-20);
				y = myRandom( 0, FRAMEHEIGHT-50);
			}while(y > FRAMEHEIGHT-50 || x > FRAMEWIDTH-20);
			
			Point loc = new Point(x,y);
			return loc;
		}
		
		public int myRandom(int low, int high) {
			return (int) (Math.random() * (high - low)+low);
		}
		
		/**
		 * 
		 * Generates the panel for the test
		 *
		 */
		class ModuleGUI extends JPanel implements ActionListener {
			
			private static final long serialVersionUID = 1L;
			private final String moduleName = "PopupModule";
			private final String moduleArea = "Reaction";
			private final String moduleDescription = "Example Description";
			private ArrayList<JButton> buttons = null;
	
			
			private JButton runningTest;

			private int result;
			
			/**
			 * Constructor
			 */
			public ModuleGUI() {
				buttons = new ArrayList<JButton>();
				setLayout(null);
				setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
				this.add(getIntroductionPanel(timePerButton, numberOfButtons,
						this));
			}
			
			/**
			 * 
			 * @param al
			 *  		ActionListener for the buttons
			 * @return
			 * 			JPanel with test
			 */
			public JPanel createTestPanel(ActionListener al) {
				
				JPanel testPanel = new JPanel();
				
				int c = myRandom(1, 10);
				for(int i=0;i<c;i++) {
					if(buttonCounter == numberOfButtons) {
						break;
					}
					runningTest = generateTest();

					testPanel.setLayout(null);
					testPanel.setBounds(0, 0, 900, 700);
				
					registerButton(runningTest);
					if (runningTest.getActionListeners().length < 1) {
						runningTest.addActionListener(al);
					}
					testPanel.add(runningTest);
				}
				return testPanel;
			}
			
			public void addTestPanel() {
				this.removeAll();
				this.invalidate();
				this.add(createTestPanel(this));
				this.revalidate();
				this.repaint();
			}
			
			public String getModuleName() {
				return moduleName;
			}

			public String getModuleArea() {
				return moduleArea;
			}

			public String getModuleDescription() {
				return moduleDescription;
			}
			
			public void startTest() {
				setNextModuleTimer(getTime(), new NextModule());
			}
			
			public void registerButton(JButton button) {
				if (!buttons.contains(button)) {
					buttons.add(button);
				}
			}
			
			/**
			 * Calculates the result
			 * Close the module
			 */
			public class NextModule extends TimerTask {
				@Override
				public void run() {
					result = calculateResult(numberOfButtons, buttonClicked);
					sendResult(result);
					tellFinished();
				}
			}
			
			/**
			 * Actionlistener for the buttons.
			 * Finishes the modules
			 * Starts the test
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (buttons.contains(e.getSource())) {
					buttonClicked++;
					if (buttonClicked >= numberOfButtons) {
						result = calculateResult(numberOfButtons, buttonClicked);
						sendResult(result);
						tellFinished();
					}
					if(buttonClicked == buttonCounter) {
						addTestPanel();
					} else {
						buttons.get(buttons.indexOf(e.getSource())).setVisible(false);
					}
					
				} else {
					startTest();
					addTestPanel();
				}
			}

		}
	
}



