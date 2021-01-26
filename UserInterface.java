import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserInterface extends JFrame {
	private class NextWordListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] rateWoerter = Main.getRateWoerter();
			if (Main.wordIndex < rateWoerter.length - 1) {
				Main.wordIndex += 1;
				Main.rateWort = rateWoerter[Main.wordIndex];
				output.setText("Bitte erkl\u00e4re: " + Main.rateWort);
				helpOutput.setText("");
				helpIndex = 0;
				help.setEnabled(true);
				startTime = System.nanoTime();
				if (Main.rateWort == rateWoerter[rateWoerter.length - 1]) {
					helpOutput.setVisible(false);
					timerOnOff.setEnabled(false);
					timerOnOff.setSelected(false);
					time.setVisible(false);
					nextWord.setEnabled(false);
					help.setEnabled(false);
					output.setText(Main.rateWort);
				}
			}
		}
	}

	private class HelpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] helpWords = PythonBackground.hilfsWoerter(Main.rateWort);
			if (helpIndex < helpWords.length) {
				String helpWord = helpWords[helpIndex];
				helpOutput.setText("Tipp: " + helpWord);
				helpIndex++;
			} else {
				help.setEnabled(false);
				helpOutput.setText("Kein Tipp vorhanden!");
			}
		}
	}

	private static final long serialVersionUID = 541391875104762428L;
	private static JButton nextWord;
	private static JButton help;
	private static JLabel output;
	private static JLabel helpOutput;
	private static JLabel time;
	private static JCheckBox timerOnOff;
	private static int helpIndex = 0;
	private static long startTime;

	public UserInterface() {
		getContentPane().setLayout(null);
		setupGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startTime = System.nanoTime();
	}

	private void setupGUI() {
		nextWord = new JButton();
		nextWord.setLocation(50, 300);
		nextWord.setSize(150, 50);
		nextWord.setText("N\u00e4chstes Wort");
		nextWord.setToolTipText("Zeigt das n\u00e4chste Wort an!");
		nextWord.addActionListener(new NextWordListener());
		getContentPane().add(nextWord);

		help = new JButton();
		help.setLocation(245, 300);
		help.setSize(100, 50);
		help.setText("Hilfe");
		help.setToolTipText("Zeigt einen Tipp an!");
		help.addActionListener(new HelpListener());
		getContentPane().add(help);

		output = new JLabel();
		output.setLocation(90, 150);
		output.setSize(200, 50);
		output.setText("Bitte erkl\u00e4re: " + Main.rateWort);
		getContentPane().add(output);

		helpOutput = new JLabel();
		helpOutput.setLocation(90, 205);
		helpOutput.setSize(200, 50);
		helpOutput.setText("");
		getContentPane().add(helpOutput);

		time = new JLabel();
		time.setLocation(90, 80);
		time.setSize(200, 50);
		time.setVisible(false);
		time.setText("Ben\u00f6tigte Zeit: ");
		getContentPane().add(time);

		timerOnOff = new JCheckBox();
		timerOnOff.setLocation(90, 25);
		timerOnOff.setSize(200, 50);
		timerOnOff.setText("Ben\u00f6tigte Zeit anzeigen?");
		timerOnOff.setSelected(false);
		getContentPane().add(timerOnOff);

		setTitle("Informatrix Random Word Generator");
		setSize(400, 400);
		setVisible(true);
		setResizable(false);
	}

	public static void switchTimerState() {
		try {
			Thread.sleep(1L);
		} catch (Exception exception) {}

		if (timerOnOff.isSelected()) {
			time.setVisible(true);
		} else {
			time.setVisible(false);
		}
	}

	public static void timer() {
		String timeStr = String.valueOf((System.nanoTime() - startTime) * 0.000000001);
		time.setText(timeStr.substring(0, timeStr.indexOf(".", 0)) + " Sekunden");
	}
}