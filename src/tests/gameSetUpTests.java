package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
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
		
	}
	
	
	
	@Test
	public void testLoadPlayers() {
		board.initialize();
		Player testArr[] = board.getPlayers();
		assertEquals(NUM_PLAYERS, testArr.length);
		
		//Test First player
		assertEquals("Madame Young Jon", testArr[0].getPlayerName());
		assertTrue(testArr[0].isHuman());
		assertEquals(Color.pink, testArr[0].getColor());
		assertEquals(13, testArr[0].getRow());
		assertEquals(18, testArr[0].getColumn());

		//Test Second Player
		assertEquals("Mrs. Gehrig White", testArr[1].getPlayerName());
		assertFalse(testArr[1].isHuman());
		assertEquals(Color.white, testArr[1].getColor());
		assertEquals(16, testArr[1].getRow());
		assertEquals(21, testArr[1].getColumn());
		
		//Test 4th Player
		assertEquals("Ms. Scarlet", testArr[3].getPlayerName());
		assertFalse(testArr[3].isHuman());
		assertEquals(Color.red, testArr[3].getColor());
		assertEquals(12, testArr[3].getRow());
		assertEquals(6, testArr[3].getColumn());
	
		//Test Last Player
		assertEquals("Sargent George", testArr[5].getPlayerName());
		assertFalse(testArr[5].isHuman());
		assertEquals(Color.orange, testArr[5].getColor());
		assertEquals(5, testArr[5].getRow());
		assertEquals(10, testArr[5].getColumn());
	}
	
	@Test
	public void testLoadCards() {
		// Initialize Test
		try {
			board.loadRoomConfig();
		} catch (BadConfigFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			board.loadBoardConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.loadPlayerConfig();
		board.loadWeaponConfig();
		
		
		ArrayList<Card> testDeck = board.getDeck();
		assertEquals(DECK_SIZE, testDeck.size());
		int numRooms = 0;
		int numWeapons = 0;
		int numPlayers = 0;
		boolean containsPlayer = false;
		boolean containsWeapon = false;
		boolean containsRoom = false;
		// Test number of rooms, weapons, and players dealt
		for (int i =  0; i < testDeck.size(); i++) {
			if (testDeck.get(i).getCardType() == CardType.PERSON) {
				numPlayers++;
			}
			if (testDeck.get(i).getCardType() == CardType.WEAPON) {
				numWeapons++;
			}
			if (testDeck.get(i).getCardType() == CardType.ROOM) {
				numRooms++;
			}
			if (testDeck.get(i).getCardName().equalsIgnoreCase("Sargent George")) {
				containsPlayer = true;
			}
			if (testDeck.get(i).getCardName().equalsIgnoreCase("Candlestick")) {
				containsWeapon = true;
			}
			if (testDeck.get(i).getCardName().equalsIgnoreCase("Kitchen")) {
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
		board.initialize();
		boolean testArr[] = new boolean[NUM_PLAYERS];
		Player playerArr[] = board.getPlayers();
		int numPantry = 0;
		int numScarlet = 0;
		int numPistol = 0;
		
		//test that everyone has enough cards
		for (int i = 0; i < playerArr.length; i++) {
			ArrayList<Card> tempArr = playerArr[i].getMyCards();
			if (tempArr.size() > 2) {
				testArr[i] = true;
			}
			//Test that card only shows up once
			for (int j = 0; j < tempArr.size(); j++) {
				if (tempArr.get(j).getCardName().equalsIgnoreCase("Ms. Scarlet")) {
					numScarlet++;
				}
				if (tempArr.get(j).getCardName().equalsIgnoreCase("Pistol")) {
					numPistol++;
				}
				if (tempArr.get(j).getCardName().equalsIgnoreCase("Pantry")) {
					numPantry++;
				}
			}
			
		}
		for (int i = 0; i < testArr.length; i++) {
			assertTrue(testArr[i]);
		}

		assertEquals(1, numPantry);
		assertEquals(1, numScarlet);
		assertEquals(1, numPistol);
	}
	
	
}
