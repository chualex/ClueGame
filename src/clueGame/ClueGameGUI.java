package clueGame;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGameGUI extends JFrame{
	private DetectiveNotesDialog notesDialog;
	private static Board board;
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

	public static void main(String[] args) {
		String splashMessage = "You are Madame Young Jon, press Next Player to begin to play";
		ClueGameGUI frame = new ClueGameGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);	
		ControlPanel controlPanel = new ControlPanel();
		frame.add(controlPanel, BorderLayout.SOUTH);
		myCardsPanel = new MyCardsPanel();
		//Creates and displays splash window to start game
		JOptionPane splash = new JOptionPane();
		splash.showMessageDialog(frame, splashMessage, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		frame.add(myCardsPanel, BorderLayout.EAST);
		frame.setVisible(true);
		frame.add(board, BorderLayout.CENTER);

	}
}
