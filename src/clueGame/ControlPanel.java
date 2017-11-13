package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel{
	private JTextField turn;
	private JTextField diceRoll; 
	private JTextField guess;
	private JTextField response;
	

	public ControlPanel() {
		//Creates layout for control panel
		setLayout(new GridLayout(2,0));
		JPanel panel = createWhoseTurnPanel();
		add(panel);
		panel = createNextPlayerButton();
		add(panel);
		panel = createMakeAccusationButton();
		add(panel);
		panel = createDiceRollPanel();
		add(panel);
		panel = createGuestPanel();
		add(panel);
		panel = createGuestResultPanel();
		add(panel);
		
	}
	private JPanel createWhoseTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel turnLabel = new JLabel("Player:");
		turn = new JTextField(20);
		panel.add(turnLabel);
		panel.add(turn);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		return panel;
	}
	
	private JPanel createDiceRollPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel rollLabel = new JLabel("Roll:");
		diceRoll = new JTextField(20);
		diceRoll.setEditable(false);
		panel.add(rollLabel);
		panel.add(diceRoll);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "The die said..."));
		return panel;
	}
	
	private JPanel createGuestPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel guessLabel = new JLabel("Guess:");
		guess = new JTextField(20);
		panel.add(guessLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Player thinks..."));
		return panel;
	}
	
	private JPanel createGuestResultPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel responseLabel = new JLabel("Response:");
		response = new JTextField(20);
		response.setEditable(false);
		panel.add(responseLabel);
		panel.add(response);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	private JPanel createNextPlayerButton() {
		JButton nextPlayer = new JButton("Next Player");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		panel.add(nextPlayer);
		return panel;
		
	}
	
	public JPanel createMakeAccusationButton() {
		JButton makeAccusation = new JButton("Make an Accusation");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		panel.add(makeAccusation);
		return panel;
	}
	
	public static void main(String[] args) {
				// Creates a JFrame
				JFrame controlFrame = new JFrame();
				controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				controlFrame.setTitle("CLUE GUI");
				controlFrame.setSize(250, 150);	
				// Creates the controlPanel and adds it to the frame 
				 ControlPanel controlPanel = new ControlPanel();
				controlFrame.add(controlPanel, BorderLayout.CENTER);
				// Let's us view the frame 
				controlFrame.setVisible(true);
	}
}
