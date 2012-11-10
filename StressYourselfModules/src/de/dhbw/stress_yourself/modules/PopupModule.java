package de.dhbw.stress_yourself.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import de.dhbw.stress_yourself.extend.ModuleClass;


public class PopupModule extends ModuleClass {

	private final String moduleName = "PopupModule";
	private final String moduleArea = "Reaction";
	private final String moduleDescription = "Example Description";

	private ArrayList<Boolean> results = new ArrayList<Boolean>();
	private int buttonCount = 1;
	private int nextButtonIntervall = 3000;

	public PopupModule(Object o, Integer difficulty, Integer time) {
		super(o, difficulty.intValue(), time.intValue());
		setTimerIntervall();
	}
	
	public void setTimerIntervall() {
		switch(getDifficulty()) {
		case(0):
			break;
		case(1):
			nextButtonIntervall = 4000;
			break;
		case(2):
			nextButtonIntervall = 3000;
			break;
		}
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

	class ModuleGUI extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private ArrayList<JButton> buttons = null;
		private JTextPane pane = new JTextPane();
		private JTextField captchaText = new JTextField();
		private JButton nextCaptchaButton = new JButton("next captcha");
		private JButton nextModuleButton = new JButton("next module");
		private JPanel thisPanel = this;

		public ModuleGUI() {
			buttons = new ArrayList<JButton>();
			init();
			setLayout(null);
			setBounds(0, 0, 800, 600);
		}

		public void registerButton(JButton button) {
			buttons.add(button);
		}

		public void init() {
			pane.setText("CaptchaCharSequenceModule");
			pane.setBounds(50, 50, 175, 30);
			this.add(pane);

			captchaText.setBounds(50, 210, 150, 30);
			this.add(captchaText);

			nextCaptchaButton.setBounds(220, 210, 70, 30);
			registerButton(nextCaptchaButton);
			nextCaptchaButton.addActionListener(this);
			this.add(nextCaptchaButton);

			nextModuleButton.setBounds(230, 50, 120, 30);
			registerButton(nextModuleButton);
			nextModuleButton.addActionListener(this);
			this.add(nextModuleButton);
			setNextTaskTimer(nextButtonIntervall, nextButtonIntervall, new NextTask());
			setNextModuleTimer(getTime(), new NextModule());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (buttons.indexOf(e.getSource())) {
			case 0:// nextCaptchaButton
				resetNextTaskTimer(nextButtonIntervall, nextButtonIntervall, new NextTask());
				this.revalidate();
				buttonCount++;
				break;
			case 1:// nextModuleButton
				sendResult();
				tellFinished();
				break;
			}
		}
		
		public class NextTask extends TimerTask {
			@Override
			public void run() {
				thisPanel.revalidate();
			}
		}
		
		public class NextModule extends TimerTask {
			@Override
			public void run() {
				sendResult();
				tellFinished();
			}
		}
	}
}
