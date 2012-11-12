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


public class PopupModule extends ModuleClass {

		private final String moduleName = "PopupModule";
		private final String moduleArea = "Reaction";
		private final String moduleDescription = "Example Description";
		private int timePerButton;
		private int numberOfButtons;
		private int buttonCounter;
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
		 * 
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
				timePerButton = 750;
				break;
			}
			numberOfButtons = (getTime() / timePerButton);
			buttonCounter = 0;
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
		
		public JButton generateTest() {
			
			JButton testButton = new JButton("X");
			
			testButton.setBackground(Color.ORANGE);
			testButton.setSize(BUTTONWIDTH, BUTTONHEIGHT);
			testButton.setLocation(generateButtonLocation());
			buttonCounter++;
			
			return testButton;
		}
		
		private Point generateButtonLocation() {
			int x = -1;
			int y = -1;
			do {
				x = myRandom( 20, FRAMEWIDTH-20);
				y = myRandom( 20, FRAMEHEIGHT-20);
			}while(y > FRAMEHEIGHT-20 || x > FRAMEWIDTH-20);
			
			System.out.println( "X: "+x+" Y: "+y);
			
			Point loc = new Point(x,y);
			return loc;
		}
		
		public int myRandom(int low, int high) {
			return (int) (Math.random() * (high - low) + low);
		}
		

		class ModuleGUI extends JPanel implements ActionListener {
			/**
			 * 
			 */
			private final String moduleName = "PopupModule";
			private final String moduleArea = "Reaction";
			private final String moduleDescription = "Example Description";
			private ArrayList<JButton> buttons = null;
	
			
			private JButton runningTest;

			private int buttonCount;
			private int result;

			public ModuleGUI() {
				buttons = new ArrayList<JButton>();
				setLayout(null);
				setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
				this.add(getIntroductionPanel(timePerButton, numberOfButtons,
						this));
			}
			
			public JPanel createTestPanel(ActionListener al) {
				
				runningTest = generateTest();
				JPanel testPanel = new JPanel();

				testPanel.setLayout(null);
				testPanel.setBounds(0, 0, 900, 700);
				
				registerButton(runningTest);
				if (runningTest.getActionListeners().length < 1) {
					runningTest.addActionListener(al);
				}
				testPanel.add(runningTest);
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
			
			public class NextModule extends TimerTask {
				@Override
				public void run() {
					result = calculateResult(numberOfButtons, buttonClicked);
					sendResult(result);
					tellFinished();
				}
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (buttons.contains(e.getSource())) {
					buttonClicked++;
					System.out.println("solvedCorrectly " + buttonClicked);
					if (buttonCounter == numberOfButtons) {
						result = calculateResult(numberOfButtons, buttonClicked);
						sendResult(result);
						tellFinished();
					}
					addTestPanel();
				} else {
					startTest();
					addTestPanel();
				}
			}

		}
	
}



