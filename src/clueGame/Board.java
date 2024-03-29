package clueGame;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * Board Class - Sets up Board and finds adjacencies and targets.
 * 
 * @author Alexander Chu
 * @author Joseph O'Brien
 * 
 *
 */

public class Board extends JPanel implements MouseListener {
	// Array to store the game board
	private BoardCell[][] gameBoard;
	// Number of rows in game board
	private int numRows;
	// Number of columns in game board
	private int numColumns;
	// Map to store the legend
	private Map<Character, String> legend;
	// Map to store all adjacent cells
	private Map<BoardCell, Set<BoardCell>> adjCells;
	// set to store visited cells 
	private Set<BoardCell> visited;
	// set to store targets
	private Set<BoardCell> targets;
	// Creates only instance of the board
	private static Board theInstance = new Board();
	// Board input file
	private String boardFile;
	// Legend input file name
	private String legendFile;
	// Player input file name
	private String playerFile;
	// Weapons file name
	private String weaponsFile;
	// Array for players
	private Player players[];
	// Number of players
	private int numPlayers;
	// Index of current player
	private int currentPlayerIndex;
	// current player 
	Player currentPlayer;
	// Array for deck
	private ArrayList<Card> deck;
	// Solution for the game
	private Solution solution;
	// array of rooms
	private Set<Card> rooms;
	// array of weapons
	private Set<Card> weapons;
	// number for die 
	private int die;
	// boolean for mouse listener
	private boolean mouseInput;
	// Players list
	private Set<Card> playerList;
	private boolean hasMoved;

	public Board() {
		legend = new HashMap<Character, String>();
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		deck = new ArrayList<Card>();
		weapons = new HashSet<Card>();
		rooms = new HashSet<Card>();
		playerList = new HashSet<Card>();
		currentPlayerIndex = 0;
		mouseInput = false;
		hasMoved = false;
		addMouseListener(this);
	}

	/**
	 * Sets the config file names
	 * @param string The name of the Board file
	 * @param string2 The name of the Legend file
	 */
	public void setConfigFiles(String string, String string2) {
		boardFile = string;
		legendFile = string2;
		playerFile = "PlayerFile.txt";
		weaponsFile = "WeaponsFile.txt";
		
	}
	/**
	 * 
	 * @param string
	 * @param string2
	 * @param string3
	 * @param string4
	 */
	public void setConfigFiles(String string, String string2, String string3, String string4) {
		boardFile = string;
		legendFile = string2;
		playerFile = string3;
		weaponsFile = string4;
		
	}
	
	/**
	 * Calls config functions to read in the elemments of the game board and legend
	 */
	public void initialize() {

		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		loadPlayerConfig();
		loadWeaponConfig();
		calcAdjacencies();
		ComputerPlayer.setUnseenCards(deck, weapons, playerList);
		makeSolution();
		dealCards();
	}



	/**
	 * Getter for legend
	 * 
	 * @return The map that contains the legend
	 */

	public Map<Character, String> getLegend() {
		return legend;
	}

	/**
	 * Getter for numRows
	 * 
	 * @return Number of rows on game board
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * Getter for numColumns
	 * 
	 * @return Number of columns on game board
	 */
	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int i, int j) {
		return gameBoard[i][j];
	}

	/**
	 * Getter for the instance of the board
	 * 
	 * @return The instance of the game board
	 */
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * Loads in the legend from text file. Puts the elements in a map to represent the legend. 
	 * 
	 * @throws BadConfigFormatException If the format of the legend is not correct 
	 */
	public void loadRoomConfig() throws BadConfigFormatException {
		//opens file
		FileReader iFS;
		Scanner scan = null;
		try {
			iFS = new FileReader(legendFile);
			scan = new Scanner(iFS);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//reads in the lines of the file
		while (scan.hasNextLine()) {
			String input = scan.nextLine();
			String[] split = input.split(", ");
			//throws exception if not all contents are available
			if (split.length < 3) {
				throw new BadConfigFormatException("There are not 3 entries for this line" + split[0]);
			}
			String str = split[2];
			// throws exception if room is not of type card or other
			if (!str.equalsIgnoreCase("Card")) {
				if (!str.equalsIgnoreCase("Other")) {
					throw new BadConfigFormatException("Type set Error: " + split[1] + " " + split[2]);
				}
			}
			if (split[2].equalsIgnoreCase("Card")) {
				Card room = new Card(split[1], CardType.ROOM);
				deck.add(room);
				rooms.add(room);
			}
			// puts legend together
			legend.put(split[0].charAt(0), split[1]);
		}
		scan.close();

	}
	
	/**
	 * Loads in the game board and sets up the game board. Creates an array of BoardCell the represents the game board. 
	 * Determines if the cell is a door and defines the direction. 
	 * 
	 * @throws BadConfigFormatException If the board is not formated correctly
	 */
	public void loadBoardConfig() throws BadConfigFormatException {
		// opens file
		FileReader iFS;
		ArrayList<String> input = new ArrayList<String>();
		try {
			iFS = new FileReader(boardFile);
			Scanner scan = new Scanner(iFS);
			// read in rows
			while (scan.hasNextLine()) {
				input.add(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//set rows
		numRows = input.size();
		gameBoard = new BoardCell[input.size()][];
		//allocate columns
		for (int i = 0; i < input.size(); i++) {
			String[] split = input.get(i).split(",");
			gameBoard[i] = new BoardCell[split.length];
			if (i == 0) {
				numColumns = split.length;
			}
			//throws exception if columns are not consistent
			if (i > 0) {
				if (split.length != numColumns) {
					throw new BadConfigFormatException("Row " + i + " doesnt have the same number of columns.");
				}
			}
			// puts board together
			for (int j = 0; j < split.length; j++) {
				if (legend.containsKey(split[j].charAt(0))){
					gameBoard[i][j] = new BoardCell(i,j,split[j].charAt(0));
				}
				else {
					throw new BadConfigFormatException("The letter " + split[j].charAt(0) + " is not in the legend");
				}
				// determines if location is a door
				if (split[j].length() > 1) {
					if (split[j].charAt(1) == 'R' || split[j].charAt(1) == 'U' || split[j].charAt(1) == 'L' || split[j].charAt(1) == 'D') {
						gameBoard[i][j].setIsDoorway(true);
						switch(split[j].charAt(1)) {
						case 'D':
							gameBoard[i][j].setDoorDirection(DoorDirection.DOWN);
							break;
						case 'U':
							gameBoard[i][j].setDoorDirection(DoorDirection.UP);
							break;
						case 'L':
							gameBoard[i][j].setDoorDirection(DoorDirection.LEFT);
							break;
						case 'R':
							gameBoard[i][j].setDoorDirection(DoorDirection.RIGHT);
							break;
						}

					}
				}
			}
		}


	}
	
	public boolean isMouseInput() {
		return mouseInput;
	}
	/**
	 * 
	 */

	public void loadPlayerConfig() {
		FileReader iFS;
		ArrayList<String> input = new ArrayList<String>();
		try {
			iFS = new FileReader(playerFile);
			Scanner scan = new Scanner(iFS);
			// read in rows
			while (scan.hasNextLine()) {
				input.add(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Allocate player array
		numPlayers = input.size();
		players = new Player[numPlayers];
		
		// Fill player array
		for (int i = 0; i < input.size(); i++) {
			String[] split = input.get(i).split(", ");
			String playerName = split[0];
			Color color = convertColor(split[1]);
			boolean isHuman = false;
			if (split[2].equalsIgnoreCase("Player")) {
				isHuman = true;
			}
			
			int row = intConverter(split[3]);
			int column = intConverter(split[4]);
			if (isHuman) {
				players[i] = new HumanPlayer(playerName, row, column, color, isHuman);
			}
			else {
				players[i] = new ComputerPlayer(playerName, row, column, color, isHuman);
			}
			Card player = new Card(split[0], CardType.PERSON);
			deck.add(player);
			playerList.add(player);
		}
	}
	
	public void loadWeaponConfig() {
		FileReader iFS;
		try {
			iFS = new FileReader(weaponsFile);
			Scanner scan = new Scanner(iFS);
			// read in rows
			while (scan.hasNextLine()) {
				String input = scan.nextLine();
				Card weapon = new Card(input, CardType.WEAPON);
				deck.add(weapon);
				weapons.add(weapon);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param strInt
	 * @return
	 */
	public int intConverter(String strInt) {
		int output;
		try {
			output = Integer.parseInt(strInt);
		} catch (NumberFormatException e) {
			output = 0;
		}
		return output;
		
	}
	/**
	 * from http://stackoverflow.com/questions/2854043/converting-a-string-to-color-in-java
	 * @param strColor
	 * @return
	 */
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}
	
	/**
	 * Getter for adjacent cells 
	 *  
	 * @param a Row number
	 * @param b Column number
	 * @return all adjacent cells for a given cell
	 */
	public Set<BoardCell> getAdjList(int a, int b){
		return adjCells.get(gameBoard[a][b]);
	}

	/**
	 * Getter for targets
	 * @return the cells that the can be moved to. 
	 */
	public Set<BoardCell> getTargets(){
		return targets;
	}

	/**
	 * Calculates the adjacencies for the whole game board. Recognizes the cannot move within room, Only move in one direction from door,
	 *  and can only enter rooms through a door. 
	 */
	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				Set<BoardCell> Cells = new HashSet<BoardCell>(); 
				if (getCellAt(i,j).getInitial() == 'W') {
					if (i - 1 >= 0 && (getCellAt(i - 1, j).getDoorDirection() == DoorDirection.DOWN || getCellAt(i - 1,j).getInitial() == 'W')) {
						Cells.add(gameBoard[i - 1][j]);
					}
					if (j - 1 >= 0 && (getCellAt(i, j - 1).getDoorDirection() == DoorDirection.RIGHT || getCellAt(i,j - 1).getInitial() == 'W')) {
						Cells.add(gameBoard[i][j - 1]);
					}
					if (i + 1 < numRows && (getCellAt(i + 1, j).getDoorDirection() == DoorDirection.UP || getCellAt(i + 1,j).getInitial() == 'W')) {
						Cells.add(gameBoard[i + 1][j]);
					}
					if (j + 1 < numColumns && (getCellAt(i, j + 1).getDoorDirection() == DoorDirection.LEFT || getCellAt(i,j + 1).getInitial() == 'W')) {
						Cells.add(gameBoard[i][j + 1]);
					}
				}
				if (getCellAt(i,j).isDoorway() == true) {
					switch (getCellAt(i,j).getDoorDirection()) {
					case DOWN:
						Cells.add(gameBoard[i + 1][j]);
						break;
					case LEFT:
						Cells.add(gameBoard[i][j - 1]);
						break;
					case RIGHT:
						Cells.add(gameBoard[i][j + 1]);
						break;
					case UP:
						Cells.add(gameBoard[i - 1][j]);
						break;	
					}
				}
				adjCells.put(gameBoard[i][j], Cells);
			}
		}
	}

	/**
	 * Calls calcTargets for a certain location on the game board.
	 * 
	 * @param a row number
	 * @param b column number 
	 * @param pathLength number of steps
	 */
	public void calcTargets(int a, int b, int pathLength) {
		targets.clear();
		visited.clear();
		BoardCell startCell = getCellAt(a, b);
		findAllTargets(startCell, pathLength);
	}

	/**
	 * Calculates all targets for a given cell and certain distance. 
	 * 
	 * @param startCell starting position
	 * @param pathLength number of steps
	 */
	public void findAllTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		for (BoardCell Cell: adjCells.get(startCell)) {
			if (visited.contains(Cell)) {
				continue;
			}
			visited.add(Cell);
			if (pathLength == 1 || Cell.isDoorway() == true) {
				targets.add(Cell);
			}
			else {
				findAllTargets(Cell, pathLength - 1);
			}
			visited.remove(Cell);
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void dealCards() {
		
		int size = deck.size();
		int count = 0;
		while (!deck.isEmpty()) {
			Random rand = new Random();
			int nextNum = rand.nextInt(deck.size());
			if (count < numPlayers) {
			ArrayList<Card> cards = new ArrayList<Card>();
			cards = players[count].getMyCards();
			cards.add(deck.get(nextNum));
			players[count].setMyCards(cards);
			deck.remove(nextNum);
			count++;
			}
			else {
				count = 0;
			}
		}
		
	}

	public Card handleSuggestion(Solution testSolOne) {
		ArrayList<Card> matchingCardsHuman = new ArrayList<Card>();
		ArrayList<Card> matchingCardsComputer = new ArrayList<Card>();
		for (int i = 0; i < numPlayers; i++) {
			Card currentCard = players[i].disproveSuggestion(testSolOne);
			if (currentCard != null) {
				if(players[i].isHuman) {
					matchingCardsHuman.add(currentCard);
				}
				else {
					matchingCardsComputer.add(currentCard);
				}
			}
		}
		if (matchingCardsComputer.size() > 0) {
			Random rand = new Random();
			int pick = rand.nextInt(matchingCardsComputer.size());
			return matchingCardsComputer.get(pick);
		}
		else if (matchingCardsHuman.size() > 0) {
			Random rand = new Random();
			int pick = rand.nextInt(matchingCardsHuman.size());
			return matchingCardsHuman.get(pick);
		}
		return null;
	}

	public boolean checkAccusation(Solution accusation) {
		if (solution.compareTo(accusation) == true) {
			return true;
		}
		return false;
	}

	public void setPlayers(Player[] testPlayers) {
		this.players = testPlayers;
		numPlayers = 3;
	}

	public void setSolution(Solution a) {
		this.solution = a;
	}

	public Set<Card> getRooms() {
		return rooms;
	}

	public Set<Card> getWeapons() {
		return weapons;
	}

	public int getNumPlayers() {
		return numPlayers;
	}
	
	public void paintComponent(Graphics g) {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				gameBoard[i][j].draw(g);
			}
		}
		for (BoardCell cell: targets) {
			cell.drawTarget(g);
		}
		for (int i = 0; i < players.length; i++) {
		players[i].draw(g);	
		}
	}
	
	public void makeSolution() {
		// gets random number to find solution cards
		Random rand = new Random();
		int playerNum = rand.nextInt(players.length);
		int weaponsNum = rand.nextInt(weapons.size());
		int roomNum = rand.nextInt(rooms.size());
		
		// finds random cards for solution
		String playerCard = players[playerNum].getPlayerName();
		int count = 0;
		String weaponsCard = new String();
		for (Card card: weapons) {
			if (count == weaponsNum) {
				weaponsCard = card.getCardName();
			}
			count++;
		}
		
		String roomCard = new String();
		count = 0;
		for (Card card: rooms) {
			if (count == roomNum) {
				roomCard = card.getCardName();
			}
			count++;
		}
		
		// Index of solution cards
		int playerIndex = 0;
		int weaponIndex = 0;
		int roomIndex = 0;
		
		// Finds solution cards in deck and removes them
		for (int i = 0; i < deck.size(); i++) {
			if (deck.get(i).getCardName().equalsIgnoreCase(playerCard)) {
				deck.remove(i);	
			}
			else if (deck.get(i).getCardName().equalsIgnoreCase(weaponsCard)) {
				deck.remove(i);	
			}
			else if (deck.get(i).getCardName().equalsIgnoreCase(roomCard)) {
				deck.remove(i);			
				}
		}
	
		// creates solution
		solution = new Solution(playerCard, weaponsCard, roomCard);
	}

	public void nextPlayer(int dieRoll) {
		die = dieRoll;
		currentPlayer = players[currentPlayerIndex];
		if (currentPlayer.isHuman) {
			humanStep();
			
		}
		else {
			computerStep();
		}
	}
	
	public void humanStep() {
		hasMoved = false;
		calcTargets(currentPlayer.getRow(), currentPlayer.getColumn(), die);
		repaint();
		mouseInput = true;
		
	}
	
	public void computerStep() {
		Solution guess = ((ComputerPlayer) currentPlayer).makeAccusation();
		if (guess != null) {
			if(solution.compareTo(guess)) {
				JOptionPane splash = new JOptionPane();
				splash.showMessageDialog(null, "The solution is: " + solution.getPerson() + " with the " +  solution.getWeapon() + " in the " + solution.getRoom(), currentPlayer.getPlayerName() + " Won!", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
		calcTargets(currentPlayer.getRow(), currentPlayer.getColumn(), die);
		BoardCell newLocation = players[currentPlayerIndex].pickLocation(targets);
		players[currentPlayerIndex].setLocation(newLocation.getRow(), newLocation.getColumn());
		targets.clear();
		if (gameBoard[currentPlayer.getRow()][currentPlayer.getColumn()].isDoorway()) {
			String room = legend.get(gameBoard[currentPlayer.getRow()][currentPlayer.getColumn()].getInitial());
			Solution suggestion = ((ComputerPlayer) currentPlayer).createSuggestion(room);
			ControlPanel.setGuess(suggestion);
			Card card = handleSuggestion(suggestion);
			if (card != null) {
				ControlPanel.setResponse(card.getCardName());
				makeCardSeen(card);
			}
			else {
				ControlPanel.setResponse("No card returned");
			}
			
		}
		repaint();
	}
	
	public static void makeCardSeen(Card seenCard) {
		switch(seenCard.getCardType()) {
		case WEAPON:
			ComputerPlayer.removeUnseenWeapon(seenCard);
			ComputerPlayer.removeUnseenCard(seenCard);
			break;
		case PERSON:
			ComputerPlayer.removeUnseenPerson(seenCard);
			ComputerPlayer.removeUnseenCard(seenCard);
			break;
		case ROOM:
			ComputerPlayer.removeUnseenCard(seenCard);
			break;
		}
	}
	public boolean hasMoved() {
		return hasMoved;
	}
	public Solution getSolution() {
		return solution;
	}
	public void setCurrentPlayer(int input) {
		currentPlayerIndex = input;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) { 
		// waits for the input from user
		boolean displayError = true;
		if (mouseInput) {
			for (BoardCell cell: targets) {
				// checks if the click is inside the targets
				if (cell.containsClick(e.getX(), e.getY())) {
					// moves player
					players[currentPlayerIndex].setLocation(cell.getRow(), cell.getColumn());
					targets.clear();
					repaint();
					mouseInput = false;
					displayError = false;
					hasMoved = true;
					if (gameBoard[currentPlayer.getRow()][currentPlayer.getColumn()].isDoorway()) {
						String room = legend.get(gameBoard[currentPlayer.getRow()][currentPlayer.getColumn()].getInitial());
						// show suggestion window
						SuggestionWindow suggestionWindow = new SuggestionWindow(room);
					}
				break;
				}
			}
				
		}
		// display error
		if (displayError) {
			JOptionPane splash = new JOptionPane();
			splash.showMessageDialog(null, "This is not a valid move" , "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
	

}
