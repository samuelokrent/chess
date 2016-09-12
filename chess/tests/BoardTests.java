package chess.tests;

import chess.*;
import chess.Board.Spot;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTests {
	
	private Board board;
	
	public void setUp() {
		board = new Board();
	}
	
	@Test
	public void CreateNewBoard() {
		setUp();
		
		for(int row = 0; row < Chess.NUM_ROWS; row++) {
			for(int col = 0; col < Chess.NUM_COLS; col++) {
				assertEquals(board.getSpot(row, col).getRow(), row);
				assertEquals(board.getSpot(row, col).getCol(), col);
			}
		}
	}
	
	@Test
	public void SpotColors() {
		setUp();
		
		assertEquals(board.getSpot(0, 0).getColor(), Chess.Color.BLACK);
		assertEquals(board.getSpot(1, 0).getColor(), Chess.Color.WHITE);
		assertEquals(board.getSpot(0, 1).getColor(), Chess.Color.WHITE);
		assertEquals(board.getSpot(1, 1).getColor(), Chess.Color.BLACK);
	}
	
	@Test
	public void ValidSpots() {
		setUp();
		
		assertTrue(board.isValidSpot(0, 0));
		assertTrue(board.isValidSpot(7, 7));
		assertFalse(board.isValidSpot(8, 7));
		assertFalse(board.isValidSpot(7, 8));
		assertFalse(board.isValidSpot(-1, 0));
	}
	
	@Test
	public void OccupiedSpots() {
		setUp();
		
		Game game = new Game();
		Pawn pawn = new Pawn(game, Chess.Color.BLACK, board.getSpot(0, 0));
		
		assertFalse(board.isOccupied(1, 1));
		assertTrue(board.isOccupied(0, 0));
		
		assertNull(board.getPieceAt(1,  1));
		assertEquals(board.getPieceAt(0,  0), pawn);
	}

}
