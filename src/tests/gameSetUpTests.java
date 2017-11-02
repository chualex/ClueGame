package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class gameSetUpTests {
	

	public static final int DECK_SIZE = 21;
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_WEAPONS = 6;
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueGameBoard.csv", "ClueLegend.txt", "PlayerFile.txt", "WeaponsFile.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	
	
	@Test
	public void testLoadPlayers() {
		Player testArr[] = board.getPlayers();
		assertEquals(NUM_PLAYERS, testArr.length);
		
		//Test First player
		assertEquals("Madame Young Jon", testArr[0].getName());
		assertTrue(testArr[0].isHuman());
		assertEquals(Color.pink, testArr[0].getColor());
		
		//Test Second Player
		assertEquals("Mrs. Gehrig White", testArr[1].getName());
		assertFalse(testArr[1].isHuman());
		assertEquals(Color.white, testArr[1].getColor());
		
		//Test 4th Player
		assertEquals("Ms. Scarlet", testArr[3].getName());
		assertFalse(testArr[3].isHuman());
		assertEquals(Color.red, testArr[3].getColor());
		
		//Test Last Player
		assertEquals("Sargent George", testArr[5].getName());
		assertFalse(testArr[5].isHuman());
		assertEquals(Color.orange, testArr[5].getColor());
	}
	
	@Test
	public void testLoadCards() {
		Card testDeck[] = board.getDeck();
		assertEquals(DECK_SIZE, testDeck.length);
		int numRooms;
		int numWeapons;
		int numPlayers;
		boolean containsPlayer, containsWeapon, containsRoom = false;
		
		for (int i =  0; i < testArr.length; i++) {
			if (testArr[i].getType() == CardType.PERSON) {
				numPlayers++;
			}
			if (testArr[i].getType() == CardType.WEAPON) {
				numWeapons++;
			}
			if (testArr[i].getType() == CardType.ROOM) {
				numRooms++;
			}
			if (testArr[i].getName == "Sargent George") {
				containsPlayer = true;
			}
			if (testArr[i].getName == "Candlestick") {
				containsWeapon = true;
			}
			if (testArr[i].getName == "Kitchen") {
				containsRoom = true;
			}
		}
		
		assertEquals(NUM_ROOMS, numRooms);
		assertEquals(NUM_WEAPONS, numWeapons);
		assertEquals(NUM_PLAYERS, numPlayers);
		assertTrue(containsRoom);
		assertTrue(containsPlayer);
		assertTrue(containsWeapon);
	}

}
