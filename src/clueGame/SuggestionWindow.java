package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class SuggestionWindow extends JDialog{
	private Solution suggestion;
	private JTextField roomTextField;
	private JLabel room;
	private JLabel person;
	private JLabel weapon;
	private JButton submit;
	private JButton cancel;
	private Board board;
	private JComboBox<String> peopleBox, weaponsBox;
	private String currentRoom;
	
	public SuggestionWindow(String room) {
		currentRoom = room;
		setTitle("Suggestion");
		setSize(700, 700);
		setLayout(new GridLayout(2, 2));
		board = Board.getInstance();
		peopleBox = new JComboBox<String>();
		weaponsBox = new JComboBox<String>();
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
		room = new JLabel("Current Room ");
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
		roomTextField = new JTextField(20);
		roomTextField.setEditable(false);
		roomTextField.setText(currentRoom);
		inputPanel.add(roomTextField);
		ArrayList<String> weapons = board.getWeapons();
		Player[] players = board.getPlayers();
		int num = board.getNumPlayers();
		for (int i = 0; i < weapons.size(); i++) {
			weaponsBox.addItem(weapons.get(i));
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
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submit) {
				String suggestedPerson = String.valueOf(peopleBox.getSelectedItem());
				String suggestedWeapon = String.valueOf(weaponsBox.getSelectedItem());
				suggestion = new Solution(suggestedPerson, suggestedWeapon, currentRoom);
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
		// Creates the controlPanel and adds it to the frame 
		SuggestionWindow a = new SuggestionWindow("Dining Room");
		
		
}
}
