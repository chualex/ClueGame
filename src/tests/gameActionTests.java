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
	
	public static Card missScarlet;
	public static Card knife;
	public static Card kitchen;
	public static Card profSwanson;
	public static Card wrench;
	public static Card gallery;
	public static Card mrsGehrigWhite;
	public static Card rope;
	public static Card office; 
	public static Card colonelMustard;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoard.csv", "ClueLegend.txt", "PlayerFile.txt", "WeaponsFile.txt");
		board.initialize();
		
		missScarlet = new Card("Miss Scarlet", CardType.PERSON);
		knife = new Card("Knife", CardType.WEAPON);
		kitchen = new Card("Kitchen", CardType.ROOM);
		profSwanson = new Card("Professor Swanson", CardType.PERSON);
		wrench = new Card("Wrench", CardType.WEAPON);
		gallery = new Card("Gallery", CardType.ROOM);
		mrsGehrigWhite = new Card("Mrs. Gehrig White", CardType.PERSON);
		rope = new Card("Rope", CardType.WEAPON);
		office = new Card("Office", CardType.ROOM);
		colonelMustard = new Card("Colonel Mustard", CardType.PERSON);
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
	
		unseenWeapons.add(knife);
		player.setUnseenWeapons(unseenWeapons);
		Solution suggestion = player.createSuggestion();
		assertEquals(suggestion.getWeapon(), "Knife");

		//Tests that last unsuggested person is suggested
		Set<Card> unseenPersons = new HashSet<Card>();
	
		unseenPersons.add(profSwanson);
		player.setUnseenPersons(unseenPersons);
		suggestion = player.createSuggestion();
		assertEquals(suggestion.getPerson(), "Professor Swanson");
		
		//Tests that remaining unsuggested weapons are suggested randomly
		unseenWeapons.add(wrench);
		unseenWeapons.add(rope);
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
		unseenPersons.add(mrsGehrigWhite);
		unseenPersons.add(colonelMustard);
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
		ArrayList<Card> testCards = new ArrayList<Card>();
		ComputerPlayer testPlayer = new ComputerPlayer();
		
		//Tests that if player only has one matching card that it is shown
		testCards.add(missScarlet);
		testCards.add(wrench);
		testCards.add(gallery);
		testPlayer.setMyCards(testCards);
		String feedback = testPlayer.disproveSuggestion(testSuggestion).getCardName();
		assertEquals(feedback, missScarlet.getCardName());
		
		testCards.clear();
		
		//Tests that a player with more than one matching card selects a random one to show
		testCards.add(missScarlet);
		testCards.add(wrench);
		testCards.add(kitchen);
		testPlayer.setMyCards(testCards);
		
		boolean one, two, three = false;
	
	for (int i = 0; i < 100; i++) {
			feedback = testPlayer.disproveSuggestion(testSuggestion).getCardName();
			if (feedback == missScarlet.getCardName()) {
				one = true;
			}
			else if (feedback == knife.getCardName()) {
				two = true;
			}
			else if (feedback == kitchen.getCardName()) {
				three = true;
			}
			else {
				fail("invalid card selected");
			}
		}
		assertTrue(one);
		assertTrue(two);
		assertTrue(three);
		testCards.clear();
		
		//Tests that null is returned if player has no matching cards
		testCards.add(profSwanson);
		testCards.add(wrench);
		testCards.add(gallery);
		
		testPlayer.setMyCards(testCards);
		feedback = testPlayer.disproveSuggestion(testSuggestion).getCardName();
		assertEquals(null, feedback);
		
		testCards.clear();

	}

	@Test
	public void testHandlingSuggestion(){
		 
		Player testPlayers[] = new Player[3];
		
		
		HumanPlayer human = new HumanPlayer("Madame Young Jon", 13, 8, Color.PINK, true);
		ComputerPlayer compOne = new ComputerPlayer("Miss Scarlet", 9, 5, Color.RED, false);
		ComputerPlayer compTwo = new ComputerPlayer("Professor Swanson", Color.GREEN, 16, 12, false);
		
		ArrayList<Card> humanCards = new ArrayList<Card>();
		ArrayList<Card> compOneCards = new ArrayList<Card>();
		ArrayList<Card> compTwoCards = new ArrayList<Card>();
		
		humanCards.add(missScarlet);
		humanCards.add(knife);
		humanCards.add(kitchen);
		
		compOneCards.add(profSwanson);
		compOneCards.add(wrench);
		compOneCards.add(gallery);
		
		compTwoCards.add(mrsGehrigWhite);
		compTwoCards.add(rope);
		compTwoCards.add(office);
		
		human.setMyCards(humanCards);
		compOne.setMyCards(compOneCards);
		compTwo.setMyCards(compTwoCards);
		
		testPlayers[0] = human;
		testPlayers[1] = compOne;
		testPlayers[2] = compTwo;
		
		board.setPlayers(testPlayers);
		
		
		Solution testSolOne = Solution("Colonel Mustard", "Candlestick", "Library");
		Solution testSolTwo = Solution("Professor Swanson", "Wrench", "Gallery");
		Solution testSolThree = Solution("Miss Scarlet", "Knife", "Kitchen");
		Solution testSolFour = Solution("Professor Swanson", "Candlestick", "Office");
		Solution testSolFive = Solution("Miss Scarlet", "Candlestick", "Gallery");
		
		//Tests that a suggestion nobody can disprove returns a null
		for (int i = 0; i < testPlayers.length; i++) {
			assertTrue(board.handleSuggestion(testSolOne) == null);
		}
		
		//Tests that a suggestion that only the accuser can disprove (should return null)
		assertTrue(board.handleSuggestion(testSolTwo) == null);
		
		//Tests suggestion that only the human player can disprove (should return card)
		boolean cardOne, cardTwo, cardThree = false;
		String choice;
		for (int i = 0; i < 100; i++) {
			choice = board.handleSuggestion(testSolThree).getCardName();
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
		assertTrue(board.handleSuggestion(testSolThree) == null);
		
		//Tests suggestion that 2 players can disprove and ensures that one answer is returned
			choice = board.handleSuggestion(testSolFour).getCardName();
			assertTrue(choice == "Professor Swanson");
			assertFalse(board.handleSuggestion(testSolFour).getCardName() == "Office");
		
		//Tests a suggestion that both a human and computer can disprove (computer should show card)
		    assertTrue(board.handleSuggestion(testSolFive) == null);
			assertTrue(board.handleSuggestion(testSolFive).getCardName() == "Gallery");
	}
}
