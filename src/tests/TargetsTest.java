package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class TargetsTest {

		private static Board board;
		@BeforeClass
		public static void setUp() {
			// Board is singleton, get the only instance and initialize it		
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ClueGameBoard.csv", "ClueLegend.txt");		
			board.initialize();
			board.calcAdjacencies();
		}
		

		// Ensure that player does not move around within room
		// These cells are ORANGE on the planning spreadsheet
		@Test
		public void testAdjacenciesInsideRooms()
		{
			// Test a corner
			Set<BoardCell> testList = board.getAdjList(0, 0);
			assertEquals(0, testList.size());
			// Test one that has walkway above
			testList = board.getAdjList(7, 19);
			assertEquals(0, testList.size());
			// Test one that is in middle of room
			testList = board.getAdjList(20, 3);
			assertEquals(0, testList.size());
			// Test one beside a door
			testList = board.getAdjList(18, 11);
			assertEquals(0, testList.size());
			// Test one in a edge of board
			testList = board.getAdjList(11, 24);
			assertEquals(0, testList.size());
		}

		// Ensure that the adjacency list from a doorway is only the
		// walkway. NOTE: This test could be merged with door 
		// direction test. 
		// These tests are PURPLE on the planning spreadsheet
		@Test
		public void testAdjacencyRoomExit()
		{
			// TEST DOORWAY RIGHT 
			Set<BoardCell> testList = board.getAdjList(18, 17);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(18, 18)));
			// TEST DOORWAY LEFT 
			testList = board.getAdjList(4, 14);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(4, 13)));
			//TEST DOORWAY DOWN
			testList = board.getAdjList(5, 3);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(6, 3)));
			//TEST DOORWAY UP
			testList = board.getAdjList(18, 12);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(17, 12)));
		}
		
		// Test adjacency at entrance to rooms
		// These tests are GREEN in planning spreadsheet
		@Test
		public void testAdjacencyDoorways()
		{
			// Test beside a door direction RIGHT
			Set<BoardCell> testList = board.getAdjList(18, 18);
			assertTrue(testList.contains(board.getCellAt(18, 17)));
			assertTrue(testList.contains(board.getCellAt(19, 18)));
			assertTrue(testList.contains(board.getCellAt(18, 19)));
			assertTrue(testList.contains(board.getCellAt(17, 18)));
			assertEquals(4, testList.size());
			// Test beside a door direction DOWN
			testList = board.getAdjList(5, 17);
			assertTrue(testList.contains(board.getCellAt(6, 17)));
			assertTrue(testList.contains(board.getCellAt(4, 17)));
			assertTrue(testList.contains(board.getCellAt(5, 16)));
			assertTrue(testList.contains(board.getCellAt(5, 18)));
			assertEquals(4, testList.size());
			// Test beside a door direction LEFT
			testList = board.getAdjList(11, 18);
			assertTrue(testList.contains(board.getCellAt(11, 19)));
			assertTrue(testList.contains(board.getCellAt(11, 17)));
			assertTrue(testList.contains(board.getCellAt(10, 18)));
			assertTrue(testList.contains(board.getCellAt(12, 18)));
			assertEquals(4, testList.size());
			// Test beside a door direction UP
			testList = board.getAdjList(8, 4);
			assertTrue(testList.contains(board.getCellAt(8, 5)));
			assertTrue(testList.contains(board.getCellAt(8, 3)));
			assertTrue(testList.contains(board.getCellAt(7, 4)));
			assertTrue(testList.contains(board.getCellAt(9, 4)));
			assertEquals(4, testList.size());
		}

		// Test a variety of walkway scenarios
		// These tests are LIGHT PURPLE on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// Test on top edge of board, just one walkway piece
			Set<BoardCell> testList = board.getAdjList(22, 9);
			assertTrue(testList.contains(board.getCellAt(21, 9)));
			assertEquals(1, testList.size());
			
			// Test left edge of board, edge of room
			testList = board.getAdjList(16, 0);
			assertTrue(testList.contains(board.getCellAt(17, 0)));
			assertTrue(testList.contains(board.getCellAt(16, 1)));
			assertEquals(2, testList.size());
			
			// Test on left edge of board, three walkway pieces
			testList = board.getAdjList(6, 0);
			assertTrue(testList.contains(board.getCellAt(5, 0)));
			assertTrue(testList.contains(board.getCellAt(6, 1)));
			assertTrue(testList.contains(board.getCellAt(7, 0)));
			assertEquals(3, testList.size());

			// Test surrounded by 4 walkways
			testList = board.getAdjList(16,19);
			assertTrue(testList.contains(board.getCellAt(15, 19)));
			assertTrue(testList.contains(board.getCellAt(17, 19)));
			assertTrue(testList.contains(board.getCellAt(16, 20)));
			assertTrue(testList.contains(board.getCellAt(16, 18)));
			assertEquals(4, testList.size());
			
			// Test on bottom edge of board, next to 1 room piece
			testList = board.getAdjList(22, 15);
			assertTrue(testList.contains(board.getCellAt(21, 15)));
			assertTrue(testList.contains(board.getCellAt(22, 16)));
			assertEquals(2, testList.size());
			

			// Test on walkway next to  door that is not in the needed
			// direction to enter
			testList = board.getAdjList(9, 6);
			assertTrue(testList.contains(board.getCellAt(8, 6)));
			assertTrue(testList.contains(board.getCellAt(9, 7)));
			assertTrue(testList.contains(board.getCellAt(10, 6)));
			assertEquals(3, testList.size());
		}
		
		
		// Tests of just walkways, 1 step, includes on edge of board
		// and beside room
		// Have already tested adjacency lists on all four edges, will
		// only test two edges here
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsOneStep() {
			board.calcTargets(7, 6, 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCellAt(7, 7)));
			assertTrue(targets.contains(board.getCellAt(7, 5)));	
			assertTrue(targets.contains(board.getCellAt(6, 6)));	
			assertTrue(targets.contains(board.getCellAt(8, 6)));	

			
			board.calcTargets(14, 24, 1);
			targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCellAt(15, 24)));
			assertTrue(targets.contains(board.getCellAt(14, 23)));			
		}
		
		// Tests of just walkways, 2 steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsTwoSteps() {
			board.calcTargets(7, 6, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCellAt(5, 6)));
			assertTrue(targets.contains(board.getCellAt(6, 7)));
			assertTrue(targets.contains(board.getCellAt(6, 5)));
			assertTrue(targets.contains(board.getCellAt(8, 7)));
			assertTrue(targets.contains(board.getCellAt(8, 5)));
			assertTrue(targets.contains(board.getCellAt(9, 6)));
			assertTrue(targets.contains(board.getCellAt(7, 4)));
			board.calcTargets(14, 24, 2);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(14, 22)));
			assertTrue(targets.contains(board.getCellAt(15, 23)));	
			assertTrue(targets.contains(board.getCellAt(16, 24)));			
		}
		
		// Tests of just walkways, 4 steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsFourSteps() {
			board.calcTargets(14, 24, 4);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCellAt(14, 20)));
			assertTrue(targets.contains(board.getCellAt(15, 21)));
			assertTrue(targets.contains(board.getCellAt(16, 22)));
			assertTrue(targets.contains(board.getCellAt(17, 23)));			
			assertTrue(targets.contains(board.getCellAt(15, 23)));
			assertTrue(targets.contains(board.getCellAt(16, 24)));			
			assertTrue(targets.contains(board.getCellAt(14, 22)));
		}	
		
		
		// Test getting into a room
		// These are LIGHT BLUE on the planning spreadsheet

		@Test 
		public void testTargetsIntoRoom()
		{
			// One room is exactly 2 away
			board.calcTargets(5, 24, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCellAt(3, 24)));
			assertTrue(targets.contains(board.getCellAt(6, 23)));
			assertTrue(targets.contains(board.getCellAt(5, 22)));
			assertTrue(targets.contains(board.getCellAt(4, 23)));
		}
		
		// Test getting into room, doesn't require all steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsIntoRoomShortcut() 
		{
			board.calcTargets(9, 18, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCellAt(9, 19)));
			assertTrue(targets.contains(board.getCellAt(10, 19)));
			assertTrue(targets.contains(board.getCellAt(11, 18)));
			assertTrue(targets.contains(board.getCellAt(10, 17)));
			assertTrue(targets.contains(board.getCellAt(8, 17)));
			assertTrue(targets.contains(board.getCellAt(7, 18)));
		}

		// Test getting out of a room
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testRoomExit()
		{
			// Take one step, essentially just the adj list
			board.calcTargets(18, 21, 1);
			Set<BoardCell> targets= board.getTargets();
			// Ensure doesn't exit through the wall
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCellAt(17, 21)));
			// Take two steps
			board.calcTargets(18, 21, 2);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(17, 22)));
			assertTrue(targets.contains(board.getCellAt(16, 21)));
			assertTrue(targets.contains(board.getCellAt(17, 20)));
		}

	}



