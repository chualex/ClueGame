package expirament;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

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
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Set<BoardCell> Cells = new HashSet<BoardCell>(); 
				if (i - 1 >= 0) {
					Cells.add(grid[i - 1][j]);
				}
				if (j - 1 >= 0) {
					Cells.add(grid[i][j - 1]);
				}
				if (i + 1 < rows) {
					Cells.add(grid[i + 1][j]);
				}
				if (j + 1 < columns) {
					Cells.add(grid[i][j + 1]);
				}
				adjCells.put(grid[i][j], Cells);
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		for (BoardCell Cell: adjCells.get(startCell)) {
			if (visited.contains(Cell)) {
				continue;
			}
			visited.add(Cell);
			if (pathLength == 1) {
				targets.add(Cell);
			}
			else {
				calcTargets(Cell, pathLength - 1);
			}
			visited.remove(Cell);
		}
	}
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}


	
	
}
