package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
	
public class DetectiveNotesDialog extends JDialog{
	private JComboBox<String> peopleBox, weaponsBox, roomsBox;
	private ArrayList<JCheckBox> peopleCB, weaponsCB, roomsCB;
	private JPanel roomsPanel, weaponsPanel, peoplePanel;
	private Board board;
	public DetectiveNotesDialog() {
		setTitle("Detective Notes");
		setSize(1000, 1000);
		setLayout(new GridLayout(3, 2));
		board = Board.getInstance();
		peopleBox = new JComboBox<String>();
		weaponsBox = new JComboBox<String>();
		roomsBox = new JComboBox<String>();
		roomsPanel = new JPanel();
		weaponsPanel = new JPanel();
		peoplePanel = new JPanel();
		CreateComboBox();
		add(roomsPanel);
		add(roomsBox);
		add(weaponsPanel);		
		add(weaponsBox);
		add(peoplePanel);
		add(peopleBox);
		roomsBox.setBorder(new TitledBorder (new EtchedBorder(), "Rooms Guess:"));
		weaponsBox.setBorder(new TitledBorder (new EtchedBorder(), "Weapons Guess:"));
		peopleBox.setBorder(new TitledBorder (new EtchedBorder(), "Persons Guess:"));
		roomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms:"));
		weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons:"));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People:"));
	}
	public void CreateComboBox() {
		ArrayList<String> rooms = board.getRooms();
		ArrayList<String> weapons = board.getWeapons();
		Player[] players = board.getPlayers();
		int num = board.getNumPlayers();
		for (int i = 0; i < rooms.size(); i++) {
			roomsBox.addItem(rooms.get(i));
			JCheckBox checkBox = new JCheckBox(rooms.get(i));
			roomsPanel.add(checkBox);
		}
		for (int i = 0; i < weapons.size(); i++) {
			weaponsBox.addItem(weapons.get(i));
			JCheckBox checkBox = new JCheckBox(weapons.get(i));
			weaponsPanel.add(checkBox);
		}
		for (int i = 0; i < num; i++) {
			peopleBox.addItem(players[i].getPlayerName());
			JCheckBox checkBox = new JCheckBox(players[i].getPlayerName());
			peoplePanel.add(checkBox);
		}
	}
	
}
