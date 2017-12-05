package clueGame;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class AccusationWindow extends JDialog{

	private Solution solution;
	private JLabel room;
	private JLabel person;
	private JLabel weapon;
	private JButton submit;
	private JButton cancel;
	private Board board;
	private JComboBox<String> peopleBox, weaponsBox, roomsBox;
	// Control panel
	ControlPanel controlPanel;
	public AccusationWindow() {
		setTitle("Accusation");
		setSize(700, 700);
		setLayout(new GridLayout(2, 2));
		board = Board.getInstance();
		peopleBox = new JComboBox<String>();
		weaponsBox = new JComboBox<String>();
		roomsBox = new JComboBox<String>();
		JPanel panel = CreateLabelPanel();
		add(panel);
		panel = CreateComboBoxPanel();
		add(panel);
		panel = CreateSubmitButton();
		add(panel);
		panel = CreateCancelButton();
		add(panel);
		setVisible(true);
	}
	private JPanel CreateLabelPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		room = new JLabel("Room ");
		panel.add(room);
		person = new JLabel("Person ");
		panel.add(person);
		weapon = new JLabel("Weapon ");
		panel.add(weapon);
		return panel;
	}

	public JPanel CreateComboBoxPanel() {
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(3,1));
		Set<Card> rooms = board.getRooms();
		for (Card card: rooms) {
			roomsBox.addItem(card.getCardName());
		}
		inputPanel.add(roomsBox);
		Set<Card> weapons = board.getWeapons();
		Player[] players = board.getPlayers();
		int num = board.getNumPlayers();
		for (Card card: weapons) {
			weaponsBox.addItem(card.getCardName());
		}
		for (int i = 0; i < num; i++) {
			peopleBox.addItem(players[i].getPlayerName());
		}
		inputPanel.add(peopleBox);
		inputPanel.add(weaponsBox);
		return inputPanel;
	}
	private JPanel CreateSubmitButton() {
		//Creates the button for moving to the next player
		submit = new JButton("Submit");
		JPanel panel = new JPanel();
		ButtonListener listener = new ButtonListener();  // create a button listener
		submit.addActionListener(listener);  // add the listener to the button
		panel.setLayout(new GridLayout(1,1));
		panel.add(submit);
		return panel;
	}
	private JPanel CreateCancelButton() {
		//Creates the button for moving to the next player
		cancel = new JButton("Cancel");
		JPanel panel = new JPanel();
		ButtonListener listener = new ButtonListener();  // create a button listener
		cancel.addActionListener(listener);  // add the listener to the button
		panel.setLayout(new GridLayout(1,1));
		panel.add(cancel);
		return panel;
	}

	public Solution getSuggestion() {
		return solution;
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submit) {
				String suggestedPerson = String.valueOf(peopleBox.getSelectedItem());
				String suggestedWeapon = String.valueOf(weaponsBox.getSelectedItem());
				String suggestedRoom = String.valueOf(roomsBox.getSelectedItem());
				solution = new Solution(suggestedPerson, suggestedWeapon, suggestedRoom);
				if (board.getSolution().compareTo(solution)) {
					JOptionPane splash = new JOptionPane();
					splash.showMessageDialog(null, "You won!" , "Congrats!", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				else {
					JOptionPane splash = new JOptionPane();
					splash.showMessageDialog(null, "This guess was incorrect!" , "Sorry!", JOptionPane.INFORMATION_MESSAGE);
				}
				setVisible(false);
			}
			else if (e.getSource() == cancel) {
				setVisible(false);
			}
		}
	}

	public static void main(String[] args) {
		// Creates a JFrame
		JFrame controlFrame = new JFrame();
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlFrame.setTitle("CLUE GUI");
		controlFrame.setSize(250, 150);	
		AccusationWindow a = new AccusationWindow();


	}
}


