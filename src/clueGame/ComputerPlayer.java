package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random; 

public class ComputerPlayer extends Player {
private ArrayList<Card> unseenCards;
private char lastRoom;
private Set<Card> unseenWeapons;
private Set<Card> unseenPersons;

public ComputerPlayer(String playerName, int row, int column, Color color, boolean human) {
	super(playerName, row, column, color, human);
	unseenCards = new ArrayList<Card>();
	unseenPersons = new HashSet<Card>();
	unseenWeapons = new HashSet<Card>();
}

public BoardCell pickLocation(Set<BoardCell> targets) {
	BoardCell cell = null;
	for (BoardCell check : targets) {
		if (check.getInitial() != 'X' && check.getInitial() != 'W' && lastRoom != check.getInitial()) {
			lastRoom = check.getInitial();
			return check;
		}
	}
	Random rand = new Random();
	int pick = rand.nextInt(targets.size());
	int count = 0;
	for (BoardCell randomCell : targets) {
		if (count == pick) {
			if (randomCell.getInitial() != 'X' && randomCell.getInitial() != 'W') {
				lastRoom = randomCell.getInitial();
			}
			return randomCell;
		}
		count++;
	}
	
	return cell;
}

public Solution createSuggestion(Board board) {
	String room = " ";
	String weapon = " ";
	String person = " ";
	
	BoardCell cellAt = board.getCellAt(row, column);
	room = board.getLegend().get(cellAt.getInitial());
	
	if (unseenWeapons.size() > 0) {
		Random rand = new Random();
		int pick = rand.nextInt(unseenWeapons.size());
		int count = 0;
		for (Card card: unseenWeapons) {
			if (pick == count) {
				weapon = card.getCardName();
			}
			count++;
		}
	}
	if (unseenPersons.size() > 0) {
		Random rand = new Random();
		int pick = rand.nextInt(unseenPersons.size());
		int count = 0;
		for (Card card: unseenPersons) {
			if (pick == count) {
				person = card.getCardName();
			}
			count++;
		}
	}
	Solution output = new Solution(person, weapon, room);
	suggestion = output;
	return suggestion;
	
}

public void makeAccusation() {
	
}

public ArrayList<Card> getUnseenCards() {
	return unseenCards;
}

public void setUnseenCards(ArrayList<Card> unseenCards) {
	this.unseenCards = unseenCards;
}

public char getLastRoom() {
	return lastRoom;
}

public void setLastRoom(char lastRoom) {
	this.lastRoom = lastRoom;
}


public void setUnseenWeapons(Set<Card> a) {
	this.unseenWeapons = a;
	
}

public void setUnseenPersons(Set<Card> a) {
	this.unseenPersons = a;
}


}
