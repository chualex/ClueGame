package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.prism.paint.Color;

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
		ComputerPlayer player = new computerPlayer("Miss Scarlet", 9, 5, Color.RED);
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
			assertTrue(lacation3);
		}
	//Tests that player goes to the room if it hasn't been visted and not last visited
		ComputerPlayer player1 = new computerPlayer("Miss Scarlet", 9, 5, Color.RED);
		board.calcTargets(9, 5, 2);
		testTargets = board.getTargets();
		BoardCell selection = player1.pickLocation(testTargets);
		board.calcTargets(selection.getRow, selection.getColumn, 3);
		testTargets = board.getTargets();
		assertFalse(testTargets.contains(board.getCellAt(9, 5)));
		assertFalse(testTargets.contains(board.getCellAt(9, 4)));

	//Tests random selection if no rooms in visited list
		ComputerPlayer player2 = new computerPlayer("Miss Scarlet", 9, 5, Color.RED);
		board.calcTargets(8, 5, 1);
		testTargets = board.getTargets();
		selection = player2.pickLocation(testTargets);
		assertEquals(selection, board.getCellAt(9, 5));
	}

	@Test
	public void testAccusation(){
		

	}
	
	@Test
	public void testCreateSuggestion(){

		//Tests that room matches player's current room

		//Tests that last unsuggested weapon is suggested

		//Tests that last unsuggested person is suggested

		//Tests that remaining unsuggested weapons are suggested randomly

		//Tests that remaining unsuggested persons are suggested randomly


	}

	@Test
	public void  testDisprovingSuggestion(){

		//Tests that if player only has one matching card that it is shown

		//Tests that a player with more than one matching card selects a random one to show

		//Tests that null is returned if player has no matching cards


	}

	@Test
	public void testHandlingSuggestion(){

		//Tests that a suggestion nobody can disprove returns a null

		//Tests that a suggestion that only the accuser can disprove (should return null)

		//Tests suggestion that only the human player can disprove (should return card)

		//Tests suggestion that only human can disprove w/ human as accuser (should return null)

		//Tests suggestion that 2 players can disprove and ensures that one answer is returned

		//Tests a suggestion that both a human and computer can disprove (computer should show card)


	}

}
