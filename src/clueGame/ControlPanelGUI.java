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

public class ControlPanelGUI extends JPanel{
	//instance variables for the text-fields
	private JTextField turn;
	private JTextField diceRoll; 
	private JTextField guess;
	private JTextField response;
	

	public ControlPanelGUI() {
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
		panel = createGuessPanel();
		add(panel);
		panel = createGuessResultPanel();
		add(panel);
		
	}
	private JPanel createWhoseTurnPanel() {
		//Creates the panel for displaying which player has the turn
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel turnLabel = new JLabel("Player:");
		turn = new JTextField(20);
		turn.setEditable(false);
		panel.add(turnLabel);
		panel.add(turn);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn?"));
		return panel;
	}
	
	private JPanel createDiceRollPanel() {
		//Creates the panel for displaying the dice roll
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
	
	private JPanel createGuessPanel() {
		//Creates a panel for displaying the player's guess
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel guessLabel = new JLabel("Guess:");
		guess = new JTextField(20);
		panel.add(guessLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Player thinks..."));
		return panel;
	}
	
	private JPanel createGuessResultPanel() {
		//Creates the panel 
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel responseLabel = new JLabel("Response:");
		response = new JTextField(20);
		panel.add(responseLabel);
		panel.add(response);
		panel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		return panel;
	}
	
	private JPanel createNextPlayerButton() {
		//Creates the button for moving to the next player
		JButton nextPlayer = new JButton("Next Player");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		panel.add(nextPlayer);
		return panel;
		
	}
	
	private JPanel createMakeAccusationButton() {
		//Creates the button for making an accusation
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
				 ControlPanelGUI controlPanel = new ControlPanelGUI();
				controlFrame.add(controlPanel, BorderLayout.CENTER);
				// Let's us view the frame 
				controlFrame.setVisible(true);
	}
}
