package clueGame;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGameGUI extends JFrame{
	private DetectiveNotesDialog notesDialog;
	private static Board board;
	static JButton nextPlayer;
	static JButton makeAccusation;
	
	private int currentTurn;
	private int humanIndex;
	public static MyCardsPanel myCardsPanel;
	public ControlPanel controlPanel;
	private int numPlayers;
	public ClueGameGUI() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoard.csv", "ClueLegend.txt", "PlayerFile.txt", "WeaponsFile.txt");
		board.initialize();
		numPlayers = board.getNumPlayers();
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createFileShowNotesItem());
		return menu;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createFileShowNotesItem() {
		JMenuItem item = new JMenuItem("Show Notes");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				notesDialog = new DetectiveNotesDialog();
				notesDialog.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	public void doOneRound() {
		Player currentPlayer = board.getPlayer(currentTurn);
		if (currentPlayer.isHuman) {
			controlPanel.setTurn(currentPlayer.getPlayerName());
		}
	}
	
	private static JPanel createNextPlayerButton() {
		//Creates the button for moving to the next player
		nextPlayer = new JButton("Next Player");
		JPanel panel = new JPanel();
		ButtonListener listener = new ButtonListener();  // create a button listener
		nextPlayer.addActionListener(listener);  // add the listener to the button
		panel.setLayout(new GridLayout(1,1));
		panel.add(nextPlayer);
		return panel;
		
	}
	
	private static JPanel createMakeAccusationButton() {
		//Creates the button for making an accusation
		makeAccusation = new JButton("Make an Accusation");
		JPanel panel = new JPanel();
		ButtonListener listener = new ButtonListener();  // create a button listener
		makeAccusation.addActionListener(listener);  // add the listener to the button
		panel.setLayout(new GridLayout(1,1));
		panel.add(makeAccusation);
		return panel;
	}
	
	private static class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nextPlayer) {
				
			}
			else if (e.getSource() == makeAccusation) {
				System.out.println("Accusation");
			}
		}
	}
	public static void main(String[] args) {
		String splashMessage = "You are Madame Young Jon, press Next Player to begin to play";
		ClueGameGUI frame = new ClueGameGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1100, 800);	
		ControlPanel controlPanel = new ControlPanel();
		frame.add(controlPanel, BorderLayout.SOUTH);
		myCardsPanel = new MyCardsPanel();
		//Creates and displays splash window to start game
		JOptionPane splash = new JOptionPane();
		splash.showMessageDialog(frame, splashMessage, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		frame.add(myCardsPanel, BorderLayout.EAST);
		frame.setVisible(true);
		frame.add(board, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.add(createNextPlayerButton());
		panel.add(createMakeAccusationButton());
		frame.add(panel, BorderLayout.NORTH);

		

		
	}
}
