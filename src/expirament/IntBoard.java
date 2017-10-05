package expirament;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjCells;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	int rows;
	int columns;
	public IntBoard(int i, int j) {
		rows = i;
		columns = j;
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[i][j];
		setUpBoard();
	}
	/*
	 * Allocates the space for the board
	 */
	public void setUpBoard() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
	}
	/*
	 * Gets adjacent cell
	 */
	public Set<BoardCell> getAdjList(BoardCell Cell){
		return this.adjCells.get(Cell);
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

	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {

	}
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}

	
	
	
	
}
