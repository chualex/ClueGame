package clueGame;
/**
 * BoardCell Class - class for a single cell of the board.
 * 
 * @author Alexander Chu
 * @author Joseph O'Brien
 * 
 *
 */

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private Boolean isDoorway;
	private DoorDirection doorDirection;
	public BoardCell(int i, int j) {
		this.row = i;
		this.column = j;
		isDoorway = false;
	}
	public BoardCell(int i, int j, char a) {
		this.row = i;
		this.column = j;
		this.initial = a;
		isDoorway = false;
	}

	
	/**
	 * Getter for if the cell is a doorway
	 * @return true if the cell is a door otherwise false
	 */
	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return isDoorway;
	}
/**
 * Getter for DoorDirection
 * 
 * @return The direction of the door. 
 */
	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return this.doorDirection;
	}
	/**
	 * Getter for the type of cell
	 * 
	 * @return The char representing the type of cell
	 */
	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}
	/**
	 * Setter for doorways
	 * @param isDoorway true if the cell is a doorway
	 */
	public void setIsDoorway(Boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	
	/**
	 * setter for the door direction
	 * @param doorDirection The door direction for that cell. UP, DOWN, LEFT, RIGHT
	 */
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	
	
}
