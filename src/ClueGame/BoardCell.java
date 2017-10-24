package clueGame;

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

	public String toString() {
		return "[" + row + ", " + column + "]";
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return isDoorway;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return this.doorDirection;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}
	public Boolean getIsDoorway() {
		return isDoorway;
	}
	public void setIsDoorway(Boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	
	
}
