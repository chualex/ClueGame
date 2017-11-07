package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.prism.paint.Color;

import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Board;
import clueGame.BoardCell;

public class gameActionTests {

	private static Board board;


	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoard.csv", "ClueLegend.txt", "PlayerFile.txt", "WeaponsFile.txt");
		board.initialize();
	}
	
	@Test
	public void testTargetSelection(){
		

	// Tests random selection
		ComputerPlayer player = new ComputerPlayer("Miss Scarlet", 9, 5, Color.RED);
		board.calcTargets(9, 5, 2);
		Set<BoardCell> testTargets = board.getTargets();
		boolean location1 = false;
		boolean location2 = false;
		boolean location3 = false;


		for (int i = 0; i < 100; i++) {
			BoardCell selection = player.pickLocation(testTargets);
			if (selection == board.getCellAt(8, 4)) {
				location1 = true;
			}
			if (selection == board.getCellAt(7, 5)) {
				location2 = true;
			}
			if (selection == board.getCellAt(8, 6)) {
				location3 = true;
			}
			assertTrue(location1);
			assertTrue(location2);
			assertTrue(location3);
		}
	//Tests that player goes to the room if it hasn't been visted and not last visited
		ComputerPlayer player1 = new ComputerPlayer("Miss Scarlet", 9, 5, Color.RED);
		board.calcTargets(9, 5, 2);
		testTargets = board.getTargets();
		BoardCell selection = player1.pickLocation(testTargets);
		board.calcTargets(selection.getRow(), selection.getColumn(), 3);
		testTargets = board.getTargets();
		assertFalse(testTargets.contains(board.getCellAt(9, 5)));
		assertFalse(testTargets.contains(board.getCellAt(9, 4)));

	//Tests random selection if no rooms in visited list
		ComputerPlayer player2 = new ComputerPlayer("Miss Scarlet", 9, 5, Color.RED);
		board.calcTargets(8, 5, 1);
		testTargets = board.getTargets();
		selection = player2.pickLocation(testTargets);
		assertEquals(selection, board.getCellAt(9, 5));
	}

	@Test
	public void testAccusation(){
		Solution solution = new Solution("Miss Scarlet", "Wrench", "Kitchen"); 
		Solution correct = new Solution("Miss Scarlet", "Wrench", "Kitchen");
		Solution badPerson = new Solution("Professor Swanson", "Wrench", "Kitchen");
		Solution badWeapon = new Solution("Miss Scarlet", "Knife", "Kitchen");
		Solution badRoom = new Solution("Miss Scarlet", "Wrench", "Dining Room");
								
	//Tests for correct solution
assertTrue(board.checkAccusation(correct));
	//Tests for solution with wrong person
assertFalse(board.checkAccusation(badPerson));
	//Tests for solution with wrong weapon
assertFalse(board.checkAccusation(badWeapon));
	//Tests for solution with wrong room
assertFalse(board.checkAccusation(badRoom));

	}
	
	@Test
	public void testCreateSuggestion(){
		ComputerPlayer player = new ComputerPlayer("Miss Scarlet", 9, 5, Color.RED, false);
		player.createSuggestion();
		//Tests that room matches player's current room
		assertEquals(player.getSuggestedRoom(), "Gallery");
		//Tests that last unsuggested weapon is suggested
		Set<Card> unseenWeapons = new HashSet<Card>();
		Card testWeapon = new Card("Knife", CardType.WEAPON);
		unseenWeapons.add(testWeapon);
		player.setUnseenWeapons(unseenWeapons);
		Solution suggestion = player.createSuggestion();
		assertEquals(suggestion.getWeapon(), "Knife");

		//Tests that last unsuggested person is suggested
		Set<Card> unseenPersons = new HashSet<Card>();
		Card testPerson = new Card("Professor Swanson", CardType.PERSON);
		unseenPersons.add(testPerson);
		player.setUnseenPersons(unseenPersons);
		suggestion = player.createSuggestion();
		assertEquals(suggestion.getPerson(), "Professor Swanson");
		
		//Tests that remaining unsuggested weapons are suggested randomly
		Card testWeapon2 = new Card("Wrench", CardType.WEAPON);
		Card testWeapon3 = new Card("Rope", CardType.WEAPON);
		unseenWeapons.add(testWeapon2);
		unseenWeapons.add(testWeapon3);
		player.setUnseenWeapons(unseenWeapons);
		boolean w1, w2, w3 = false;
		
		for (int i = 0; i < 100; i++) {
			suggestion = player.createSuggestion();
			if (suggestion.getWeapon() == "Knife") {
				w1 = true;
			}
			else if (suggestion.getWeapon() == "Wrench") {
				w2 = true;
			}
			else if (suggestion.getWeapon() == "Rope") {
				w3 = true;
			}
			else {
				fail("remaining weapons were not selected randomly");
			}
		}
		
		assertTrue(w1);
		assertTrue(w2);
		assertTrue(w3);
		
		//Tests that remaining unsuggested persons are suggested randomly
		
		Card testPerson2 = new Card("Mrs. Gehrig White", CardType.PERSON);
		Card testPerson3 = new Card("Colonel Mustard", CardType.PERSON);
		unseenPersons.add(testPerson2);
		unseenPersons.add(testPerson3);
		player.setUnseenPersons(unseenPersons);
		
		boolean p1, p2, p3 = false;
		
		for (int i = 0; i < 100; i++) {
			suggestion = player.createSuggestion();
			if (suggestion.getPerson() == "Professor Swanson") {
				p1 = true;
			}
			else if (suggestion.getPerson() == "Mrs. Gehrig White") {
				p2 = true;
			}
			else if (suggestion.getPerson() == "Colonel Mustard") {
				p3 = true;
			}
			else {
				fail("remaining persons were not selected randomly");
			}
		}
		
		assertTrue(p1);
		assertTrue(p2);
		assertTrue(p3);
		
	}

	@Test
	public void  testDisprovingSuggestion(){
		
		Solution testSuggestion = new Solution("Miss Scarlet", "Knife", "Kitchen");
		
		Card matchOne = new Card("Miss Scarlet", CardType.PERSON);
		Card matchTwo = new Card("Knife", CardType.WEAPON);
		Card matchThree = new Card("Kitchen", CardType.ROOM);
		
		Card diffOne = new Card("Professor Swanson", CardType.PERSON);
		Card diffTwo = new Card("Wrench", CardType.WEAPON);
		Card diffThree = new Card("Gallery", CardType.ROOM);

		ArrayList<Card> testCards = new ArrayList<Card>();
		ComputerPlayer testPlayer = new ComputerPlayer();
		
		//Tests that if player only has one matching card that it is shown
		
		testCards.add(matchOne);
		testCards.add(diffTwo);
		testCards.add(diffThree);
		
		testPlayer.setMyCards(testCards);
		String feedback = testPlayer.disproveSuggestion(testSuggestion).getCardName();
		assertEquals(feedback, matchOne.getCardName());
		
		testCards.clear();
		//Tests that a player with more than one matching card selects a random one to show
		
		testCards.add(matchOne);
		testCards.add(diffTwo);
		testCards.add(matchTwo);
		
		boolean one, two, three = false;
	
		String choice;

		for (int i = 0; i < 100; i++) {
			choice = testPlayer.disproveSuggestion(testSuggestion).getCardName();
			if (choice == matchOne.getCardName()) {
				one = true;
			}
			else if (choice == matchTwo.getCardName()) {
				two = true;
			}
			else if (choice == matchThree.getCardName()) {
				three = true;
			}
			else {
				fail("invalid card selected");
			}
		}
		assertTrue(one);
		assertTrue(two);
		assertTrue(three);
		
		testPlayer.setMyCards(testCards);
		feedback = testPlayer.disproveSuggestion(testSuggestion).getCardName();

		
		testCards.clear();
		//Tests that null is returned if player has no matching cards
		
		testCards.add(diffOne);
		testCards.add(diffTwo);
		testCards.add(diffThree);
		
		testPlayer.setMyCards(testCards);
		feedback = testPlayer.disproveSuggestion(testSuggestion).getCardName();
		assertEquals(null, feedback);
		
		testCards.clear();

	}

	@Test
	public void testHandlingSuggestion(){
		
		Player players[] = new Player[3];
		
		HumanPlayer human = new HumanPlayer("Madame Young Jon", 13, 8, Color.PINK, true);
		ComputerPlayer compOne = new ComputerPlayer("Miss Scarlet", 9, 5, Color.RED, false);
		ComputerPlayer compTwo = new ComputerPlayer("Professor Swanson", Color.GREEN, 16, 12, false);
		
		Card one = new Card("Miss Scarlet", CardType.PERSON);
		Card two = new Card("Knife", CardType.WEAPON);
		Card three = new Card("Kitchen", CardType.ROOM);
		Card four = new Card("Professor Swanson", CardType.PERSON);
		Card five = new Card("Wrench", CardType.WEAPON);
		Card six = new Card("Gallery", CardType.ROOM);
		Card seven = new Card("Mrs. Gehrig White", CardType.PERSON);
		Card eight = new Card("Rope", CardType.WEAPON);
		Card nine = new Card("Office", CardType.ROOM);
		
		ArrayList<Card> humanCards = new ArrayList<Card>();
		ArrayList<Card> compOneCards = new ArrayList<Card>();
		ArrayList<Card> compTwoCards = new ArrayList<Card>();
		
		humanCards.add(one);
		humanCards.add(two);
		humanCards.add(three);
		
		compOneCards.add(four);
		compOneCards.add(five);
		compOneCards.add(six);
		
		compTwoCards.add(seven);
		compTwoCards.add(eight);
		compTwoCards.add(nine);
		
		human.setMyCards(humanCards);
		compOne.setMyCards(compOneCards);
		compTwo.setMyCards(compTwoCards);
		
		players[0] = human;
		players[1] = compOne;
		players[2] = compTwo;
		
		
		Solution testSolOne = Solution("Colonel Mustard", "Candlestick", "Library");
		Solution testSolTwo = Solution("Professor Swanson", "Wrench", "Gallery");
		Solution testSolThree = Solution("Miss Scarlet", "Knife", "Kitchen");
		Solution testSolFour = Solution("Professor Swanson", "Candlestick", "Office");
		Solution testSolFive = Solution("Miss Scarlet", "Candlestick", "Gallery");
		
		//Tests that a suggestion nobody can disprove returns a null
		for (int i = 0; i < players.length; i++) {
			assertTrue(players[i].disproveSuggestion(testSolOne) == null);
		}
		
		//Tests that a suggestion that only the accuser can disprove (should return null)
		assertTrue(players[1].disproveSuggestion(testSolTwo) == null);
		
		//Tests suggestion that only the human player can disprove (should return card)
		
		boolean cardOne, cardTwo, cardThree = false;
		String choice;
		for (int i = 0; i < 100; i++) {
			choice = players[0].disproveSuggestion(testSolThree).getCardName();
			if (choice == "Miss Scarlet") {
				cardOne = true;
			}
			else if (choice == "Knife") {
				cardTwo = true;
			}
			else if (choice == "Kitchen") {
				cardThree = true;
			}
			else {
				fail("Player did not properly disprove");
			}
		}
		assertTrue(cardOne);
		assertTrue(cardTwo);
		assertTrue(cardThree);
		
		//Tests suggestion that only human can disprove w/ human as accuser (should return null)
		assertTrue(players[0].disproveSuggestion(testSolThree) == null);
		
		//Tests suggestion that 2 players can disprove and ensures that one answer is returned
		
			choice = players[1].disproveSuggestion(testSolFour).getCardName();
			assertTrue(choice == "Professor Swanson");
			assertFalse(players[2].disproveSuggestion(testSolFour).getCardName() == "Office");
		

		//Tests a suggestion that both a human and computer can disprove (computer should show card)
		
			assertTrue(players[0].disproveSuggestion(testSolFive) == null);
			assertTrue(players[1].disproveSuggestion(testSolFive).getCardName() == "Gallery");

	}

}
