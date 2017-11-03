package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
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
		assertEquals(13, testArr[0].getRow());
		assertEquals(18, testArr[0].getColumn());

		//Test Second Player
		assertEquals("Mrs. Gehrig White", testArr[1].getName());
		assertFalse(testArr[1].isHuman());
		assertEquals(Color.white, testArr[1].getColor());
		assertEquals(16, testArr[1].getRow());
		assertEquals(21, testArr[1].getColumn());
		
		//Test 4th Player
		assertEquals("Ms. Scarlet", testArr[3].getName());
		assertFalse(testArr[3].isHuman());
		assertEquals(Color.red, testArr[3].getColor());
		assertEquals(12, testArr[3].getRow());
		assertEquals(6, testArr[3].getColumn());
	
		//Test Last Player
		assertEquals("Sargent George", testArr[5].getName());
		assertFalse(testArr[5].isHuman());
		assertEquals(Color.orange, testArr[5].getColor());
		assertEquals(5, testArr[5].getRow());
		assertEquals(10, testArr[5].getColumn());
	}
	
	@Test
	public void testLoadCards() {
		Card testDeck[] = board.getDeck();
		assertEquals(DECK_SIZE, testDeck.length);
		int numRooms;
		int numWeapons;
		int numPlayers;
		boolean containsPlayer, containsWeapon, containsRoom = false;
		
		for (int i =  0; i < testDeck.length; i++) {
			if (testDeck[i].getType() == CardType.PERSON) {
				numPlayers++;
			}
			if (testDeck[i].getType() == CardType.WEAPON) {
				numWeapons++;
			}
			if (testDeck[i].getType() == CardType.ROOM) {
				numRooms++;
			}
			if (testDeck[i].getName() == "Sargent George") {
				containsPlayer = true;
			}
			if (testDeck[i].getName() == "Candlestick") {
				containsWeapon = true;
			}
			if (testDeck[i].getName() == "Kitchen") {
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
	
	@Test
	public void testDealCards() {
		board.dealCards();
		boolean testArr[] = new boolean[NUM_PLAYERS];
		Player playerArr[] = board.getPlayers();
		int numPantry, numScarlet, numPistol = 0;
		//test that everyone has enough cards
		for (int i = 0; i < playerArr.length; i++) {
			Card tempArr[] = playerArr.getCards();
			if (tempArr > 2) {
				testArr[i] = true;
			}
			//Test that card only shows up once
			for (int j = 0; j < tempArr.length; j++) {
				if (tempArr[j].getName.equalsIgnoresCase("Ms. Scarlet")) {
					numScarlet++;
				}
				if (tempArr[j].getName.equalsIgnoresCase("Pistol")) {
					numPistol++;
				}
				if (tempArr[j].getName.equalsIgnoresCase("Pantry")) {
					numPantry++;
				}
			}
			
		}
		for (int i = 0; i < testArr.length; i++) {
			assertTrue(testArr[i]);
		}
		Card tempDeck[] = board.getDeck();
		assertEquals(0, tempDeck.length);
		assertEquals(1, numPantry);
		assertEquals(1, numScarlet);
		assertEquals(1, numPistol);
	}
	
	
}
