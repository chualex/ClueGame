package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	protected String playerName;
	protected int row;
	protected int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;
	boolean isHuman;
	protected Solution suggestion;
	public static final int CELL_SIZE = 20;

	
	public Player(String playerName, int row, int column, Color color, boolean human) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		this.isHuman = human;
		myCards = new ArrayList<Card>();
		suggestion = new Solution(" ", " ", " ");
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
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		if (suggested.compareTo(this.suggestion) == false){
			for (Card card: myCards) {
				if (card.getCardName().equalsIgnoreCase(suggested.getPerson())) {
					matchingCards.add(card);
				}
				if (card.getCardName().equalsIgnoreCase(suggested.getWeapon())) {
					matchingCards.add(card);
				}
				if (card.getCardName().equalsIgnoreCase(suggested.getRoom())) {
					matchingCards.add(card);
				}
			}
		}
		if (matchingCards.size() > 0) { 
			Random rand = new Random();
			int pick = rand.nextInt(matchingCards.size());
			return matchingCards.get(pick);
		}
		return null;
	}
	public abstract BoardCell pickLocation(Set<BoardCell> targets);

	public void setSuggestion(Solution solution) {
		suggestion = solution;
	}
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
		g.drawOval(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}
}
