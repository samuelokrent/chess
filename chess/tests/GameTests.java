package chess.tests;

import chess.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class GameTests {

	@Test
	public void CheckNewGameConditions() {
		Game game = new Game();
		assertNotNull(game.getBoard());
		
		game.startGame();
		assertEquals(game.getTurnColor(), Chess.Color.WHITE);
		
		for(Chess.RowConfiguration rowConfiguration : Chess.ROW_CONFIGURATIONS) {
			int row = rowConfiguration.row;
			Class[] pieceClasses = rowConfiguration.pieces;
			Chess.Color sideColor = rowConfiguration.sideColor;

			// Assert that a piece of the right color and type is in each spot
			for(int col = 0; col < Chess.NUM_COLS; col++) {
				Piece piece = game.getBoard().getPieceAt(row, col);
				assertTrue(pieceClasses[col].isInstance(piece));
				assertEquals(piece.getColor(), sideColor);
			}
		}
	}

}
