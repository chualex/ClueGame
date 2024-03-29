package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel{
	//instance variables for the text-fields
	private JTextField turn;
	private JTextField diceRoll; 
	private static JTextField guess;
	private static JTextField response;
	private JButton nextPlayer;
	private JButton makeAccusation;
	private Board board;
	private Player[] players;
	private int currentPlayer;
	private int die;
	private static ControlPanel theInstance = new ControlPanel();
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
		panel = createGuessPanel();
		add(panel);
		panel = createGuessResultPanel();
		add(panel);
		// gets instance of board
		board = Board.getInstance();
		players = board.getPlayers();
		currentPlayer = 5;
	}
	
	public static ControlPanel getInstance() {
		return theInstance;
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
		diceRoll = new JTextField(1);
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
		guess = new JTextField(100);
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
		nextPlayer = new JButton("Next Player");
		JPanel panel = new JPanel();
		ButtonListener listener = new ButtonListener();  // create a button listener
		nextPlayer.addActionListener(listener);  // add the listener to the button
		panel.setLayout(new GridLayout(1,1));
		panel.add(nextPlayer);
		return panel;
		
	}
	
	private JPanel createMakeAccusationButton() {
		//Creates the button for making an accusation
		makeAccusation = new JButton("Make an Accusation");
		JPanel panel = new JPanel();
		ButtonListener listener = new ButtonListener();  // create a button listener
		makeAccusation.addActionListener(listener);  // add the listener to the button
		panel.setLayout(new GridLayout(1,1));
		panel.add(makeAccusation);
		return panel;
	}
	public void rollDie() {
		Random rand = new Random();
		die = rand.nextInt(6) + 1;
	}

	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nextPlayer) {
				if (board.isMouseInput()) {
					JOptionPane splash = new JOptionPane();
					splash.showMessageDialog(null, "You must move first" , "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					// increment to next player
					currentPlayer = (currentPlayer + 1) % players.length;
					// display player name
					turn.setText(players[currentPlayer].getPlayerName());
					rollDie();
					diceRoll.setText(String.valueOf(die));
					board.setCurrentPlayer(currentPlayer);
					board.nextPlayer(die);
				}
				
			}
			else if (e.getSource() == makeAccusation) {
				if (players[currentPlayer].isHuman()) {
					if (board.hasMoved() == false) {
						AccusationWindow accusationWindow = new AccusationWindow();
					}
					else {
						JOptionPane splash = new JOptionPane();
						splash.showMessageDialog(null, "It's not your turn!" , "Error", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					JOptionPane splash = new JOptionPane();
					splash.showMessageDialog(null, "It's not your turn!" , "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	public static void setGuess(Solution suggestion) {
		guess.setText(suggestion.getPerson() + " -- " + suggestion.getRoom() + " -- " + suggestion.getWeapon());
	}
	
	public static void setResponse(String input) {
		response.setText(input);
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
				// Lets us view the frame 
				controlFrame.setVisible(true);
	}
	
}
