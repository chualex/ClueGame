package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public abstract class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;
	boolean isHuman;
	

	
	public Player(String playerName, int row, int column, Color color, boolean human) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		this.isHuman = human;
		myCards = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public Color getColor() {
		return color;
	}

	public boolean isHuman() {
		return isHuman;
	}
	
	public Card disproveSuggestion(Solution suggested) {
		Card choice = new Card("Miss scarlet", CardType.PERSON);
		
		return choice;
	}
	public BoardCell pickLocation(Set<BoardCell> targets);

}
