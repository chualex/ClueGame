package clueGame;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ClueGameGUI extends JFrame{
	private DetectiveNotesDialog notesDialog;
	private static Board board;
	public ClueGameGUI() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoard.csv", "ClueLegend.txt", "PlayerFile.txt", "WeaponsFile.txt");
		board.initialize();
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
		ClueGameGUI frame = new ClueGameGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1300, 1000);	
		ControlPanel controlPanel = new ControlPanel();
		frame.add(controlPanel, BorderLayout.SOUTH);
		MyCardsPanel myCardsPanel = new MyCardsPanel();
		frame.add(myCardsPanel, BorderLayout.EAST);
		frame.setVisible(true);
		frame.add(board, BorderLayout.CENTER);
	}
}