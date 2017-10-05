package expirament;

public class BoardCell {
	private int row;
	private int column;
	public BoardCell(int i, int j) {
		this.row = i;
		this.column = j;
	}

	public String toString() {
		return "[" + row + ", " + column + "]";
	}
	
	
}
