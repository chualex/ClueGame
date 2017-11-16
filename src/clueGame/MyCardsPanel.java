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
	
	public MyCardsPanel() {
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
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel label = new JLabel("Weapons:");
		weapons = new JTextField(20);
		weapons.setEditable(false);
		panel.add(label);
		panel.add(weapons);
		return panel;
	}
	
	private JPanel createPeoplePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel label = new JLabel("People:");
		people = new JTextField(20);
		people.setEditable(false);
		panel.add(label);
		panel.add(people);
		return panel;
	}
	
	
	private JPanel createRoomsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		JLabel label = new JLabel("Rooms:");
		rooms = new JTextField(20);
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
