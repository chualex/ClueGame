package clueGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Font;

/**
 * BoardCell Class - class for a single cell of the board.
 * 
 * @author Alexander Chu
 * @author Joseph O'Brien
 * 
 *
 */

public class BoardCell {
	// Row position in game board
	private int row;
	// Column position in game board
	private int column;
	// Letter identifier
	private char initial;
	// Bool for if the cell is a doorway
	private Boolean isDoorway;
	// Door direction
	private DoorDirection doorDirection;
	// Size of cell
	public static final int CELL_SIZE = 50;
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
		return isDoorway;
	}
	/**
	 * Getter for DoorDirection
	 * 
	 * @return The direction of the door. 
	 */
	public DoorDirection getDoorDirection() {
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

	public int getRow() {
		// TODO Auto-generated method stub
		return row;
	}

	public int getColumn() {
		// TODO Auto-generated method stub
		return column;
	}
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		if (initial == 'W') {
			g2.setColor(Color.yellow);
			g2.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			g2.setColor(Color.BLACK);
			g2.drawRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
		}
		else if (initial == 'X') {
			g2.setColor(Color.red);
			g2.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			g2.setColor(Color.BLACK);
			g2.drawRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
		}
		else {
			g2.setColor(Color.gray);
			g2.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			g2.setColor(Color.BLACK);
			g2.drawRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);

			
			
			
			
		}
		if (isDoorway) {
			g2.setColor(Color.blue);
			g2.setStroke(new BasicStroke(6));
			switch (doorDirection) {
			case DOWN:
				g2.drawLine(column*CELL_SIZE, row*CELL_SIZE + CELL_SIZE,  column*CELL_SIZE + CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
				break;
			case UP:
				g2.drawLine(column*CELL_SIZE, row*CELL_SIZE,column*CELL_SIZE + CELL_SIZE, row*CELL_SIZE);
				break;
			case RIGHT:
				g2.drawLine(column*CELL_SIZE + CELL_SIZE, row*CELL_SIZE, column*CELL_SIZE + CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
				break;
			case LEFT:
				g2.drawLine(column*CELL_SIZE, row*CELL_SIZE, column*CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
				break;
			}
		}
		Font f = new Font("Comic Sans MS", Font.BOLD, 30);
		g.setFont(f);
		g.drawString("Kitchen", 2 * CELL_SIZE, 2 * CELL_SIZE);
		g.drawString("Gallery", 1 * CELL_SIZE, 12 * CELL_SIZE);
	
		g.drawString("Theater", 2 * CELL_SIZE, 20 * CELL_SIZE);
	
		g.drawString("Pantry", 8 * CELL_SIZE, 2 * CELL_SIZE);
	
		g.drawString("Library",14 * CELL_SIZE, 4 * CELL_SIZE);
	
		g.drawString("Dining Room",11 * CELL_SIZE, 20 * CELL_SIZE);
	
		g.drawString("Living Room",19 * CELL_SIZE, 2 * CELL_SIZE);
	
		g.drawString("Bedroom",20 * CELL_SIZE, 10 * CELL_SIZE);
	
		g.drawString("Office", 20 * CELL_SIZE, 20 * CELL_SIZE);
	}

		
	}


