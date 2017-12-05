package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random; 

public class ComputerPlayer extends Player {
	private static ArrayList<Card> unseenCards;
	private static Set<Card> unseenCardList;
	private char lastRoom;
	private static Set<Card> unseenWeapons;
	private static Set<Card> unseenPersons;

	public ComputerPlayer(String playerName, int row, int column, Color color, boolean human) {
		super(playerName, row, column, color, human);
		unseenPersons = new HashSet<Card>();
		unseenWeapons = new HashSet<Card>();
		unseenCardList = new HashSet<Card>();

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
	
	public static void setUnseenCards(ArrayList<Card> allCards, Set<Card> weapons, Set<Card> persons) {
		unseenCards = allCards;
		unseenWeapons = weapons;
		unseenPersons = persons;
		for (int i = 0; i < allCards.size(); i++) {
			unseenCardList.add(allCards.get(i));
		}
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

	public Solution createSuggestion(String room) {
		String weapon = " ";
		String person = " ";


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
	public Solution makeAccusation() {
		String roomGuess = new String();
		String weaponGuess = new String();
		String personGuess = new String();
		if (unseenCardList.size() == 3) {
			for (Card card: unseenCardList) {
				if (card.getCardName().equalsIgnoreCase(myCards.get(0).getCardName())) {
					unseenCardList.remove(card);
				}
				if (card.getCardName().equalsIgnoreCase(myCards.get(1).getCardName())) {
					unseenCardList.remove(card);
				}
				if (card.getCardName().equalsIgnoreCase(myCards.get(2).getCardName())) {
					unseenCardList.remove(card);
				}
			}
			for (Card card: unseenWeapons) {
				weaponGuess = card.getCardName();
				unseenCardList.remove(card);
			}
			for (Card card: unseenPersons) {
				personGuess = card.getCardName();
				unseenCardList.remove(card);
			}
			for (Card card: unseenCardList) {
				roomGuess = card.getCardName();
			}
			return new Solution(personGuess, weaponGuess, roomGuess);
		}
		return null;
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
	public static void removeUnseenCard(Card card) {
		unseenCardList.remove(card);
	}
	public static void removeUnseenWeapon(Card card) {
		unseenWeapons.remove(card);
	}

	public static void removeUnseenPerson(Card card) {
		unseenPersons.remove(card);
	}

}
