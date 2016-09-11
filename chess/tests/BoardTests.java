package chess.tests;

import chess.*;
import chess.Board.Spot;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTests {
	
	@Test
	public void CreateNewBoard() {
		Board board = new Board();
		
		for(int row = 0; row < Chess.NUM_ROWS; row++) {
			for(int col = 0; col < Chess.NUM_COLS; col++) {
				assertEquals(board.getSpot(row, col).getRow(), row);
				assertEquals(board.getSpot(row, col).getCol(), col);
			}
		}
	}

}
