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

	public ClueGameGUI() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

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
				//TODO: need to add notes 
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	

	public static void main(String[] args) {
		ClueGameGUI frame = new ClueGameGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 150);	
		ControlPanel controlPanel = new ControlPanel();
		frame.add(controlPanel, BorderLayout.SOUTH);
		MyCardsPanel myCardsPanel = new MyCardsPanel();
		frame.add(myCardsPanel, BorderLayout.EAST);
		frame.setVisible(true);
	}
	
}
