package clueGame;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Board {
	private BoardCell[][] gameBoard;
	private int numRows;
	private int numColumns;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjCells;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private static Board theInstance = new Board();
	private String boardFile;
	private String legendFile;
	
	public Board() {
		legend = new HashMap<Character, String>();
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		}
/*
 * Sets the config file names
 */
	public void setConfigFiles(String string, String string2) {
		boardFile = string;
		legendFile = string2;
	}
/*
 * Initializes game board -- calls config functions
 */
	public void initialize() {
		
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			}
		
	}
	/*
	 * Getter for legend
	 */

	public Map<Character, String> getLegend() {
		return legend;
	}
/*
 * Getter for numRows
 */
	public int getNumRows() {
		return numRows;
	}
	/*
	 * Getter for numColumns
	 */
	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int i, int j) {
		
		return gameBoard[i][j];
	}
	/*
	 * Getter for the instance of the board
	 */
	public static Board getInstance() {
		// TODO Auto-generated method stub
		return theInstance;
	}
	/*
	 * Loads in the legend from text file 
	 */
	public void loadRoomConfig() throws BadConfigFormatException {
		//opens file
		FileReader iFS;
		Scanner scan = null;
		try {
			iFS = new FileReader(legendFile);
			scan = new Scanner(iFS);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
	/*
	 * Loads in the game board and sets up the game board
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
			// TODO Auto-generated catch block
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
	/*
	 * Gets adjacent cell
	 */
	public Set<BoardCell> getAdjList(int a, int b){
		return adjCells.get(gameBoard[a][b]);
	}
	/*
	 * Getter for targets
	 */
	public Set<BoardCell> getTargets(){
		return targets;
	}
	/*
	 * Calculates adjacencies
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
	
	public void calcTargets(int a, int b, int pathLength) {
		
	}
	public static void main(String[] args) {
		Board board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
		board.initialize();
		board.calcAdjacencies();

	}

}
