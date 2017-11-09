package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random; 

public class ComputerPlayer extends Player {
private ArrayList<Card> unseenCards;
private char lastRoom;

public ComputerPlayer(String playerName, int row, int column, Color color, boolean human) {
	super(playerName, row, column, color, human);
	unseenCards = new ArrayList<Card>();
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

public Solution createSuggestion(BoardCell cell) {
	return null;
	
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

public String getSuggestedRoom() {
	// TODO Auto-generated method stub
	return " ";
}

public void setUnseenWeapons(Set<Card> unseenWeapons) {
	// TODO Auto-generated method stub
	
}

public void setUnseenPersons(Set<Card> unseenPersons) {
	// TODO Auto-generated method stub
	
}


}
