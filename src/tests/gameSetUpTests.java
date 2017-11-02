package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class gameSetUpTests {
	
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 25;
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
		Player testArr[] = new Player[6];
		testArr = board.getPlayers();
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

}
