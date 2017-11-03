package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/**
 * Board Class - Sets up Board and finds adjacencies and targets.
 * 
 * @author Alexander Chu
 * @author Joseph O'Brien
 * 
 *
 */

public class Board {
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
	// Array for deck
	private Card deck[];
	
	public Board() {
		legend = new HashMap<Character, String>();
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		players = new Player[1];
		deck = new Card[1];
	}

	/**
	 * Sets the config file names
	 * @param string The name of the Board file
	 * @param string2 The name of the Legend file
	 */
	public void setConfigFiles(String string, String string2) {
		boardFile = string;
		legendFile = string2;
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
		calcAdjacencies();
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
		// TODO Auto-generated method stub
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

	public Card[] getDeck() {
		return deck;
	}

	public void dealCards() {
		// TODO Auto-generated method stub
		
	}

	




}
