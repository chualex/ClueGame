package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCardsPanel extends JPanel{
	//instance variables for the text-fields
	private JTextField people;
	private JTextField weapons;
	private JTextField rooms;
	
	private static Board board;
	
	public MyCardsPanel() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoard.csv", "ClueLegend.txt", "PlayerFile.txt", "WeaponsFile.txt");
		board.initialize();
	//Creates layout for the board panel
	setLayout(new GridLayout(3,0));
	JPanel panel = createPeoplePanel();
	add(panel);
	panel = createWeaponsPanel();
	add(panel);
	panel = createRoomsPanel();
	add(panel);
	setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
	}
	
	private JPanel createWeaponsPanel() {
		boolean mult = false;
		String text  = "";
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel label = new JLabel("Weapons:");
		weapons = new JTextField(20);
		int i = 0;
		for (Card card: board.getPlayers()[0].getMyCards()) {
			if (card.getCardType() == CardType.WEAPON) {
				text += card.getCardName() + "---";
			}
			
		}
		weapons.setText(text);
		weapons.setEditable(false);
		panel.add(label);
		panel.add(weapons);
		return panel;
	}
	
	private JPanel createPeoplePanel() {
		String text  = "";
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel label = new JLabel("People:");
		people = new JTextField(20);
		for (Card card: board.getPlayers()[0].getMyCards()) {
			if (card.getCardType() == CardType.PERSON) {
				text += card.getCardName() + "---";
			}
			
		}
		people.setText(text);
		people.setEditable(false);
		panel.add(label);
		panel.add(people);
		return panel;
	}
	
	
	private JPanel createRoomsPanel() {
		String text = "";
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel label = new JLabel("Rooms:");
		rooms = new JTextField(20);
		for (Card card: board.getPlayers()[0].getMyCards()) {
			if (card.getCardType() == CardType.ROOM) {
				text += card.getCardName() + "---";
			}
			
		}
		rooms.setText(text);
		rooms.setEditable(false);
		panel.add(label);
		panel.add(rooms);
		return panel;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 150);	
		MyCardsPanel panel = new MyCardsPanel();
		frame.add(panel,  BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
