package chess.tests;

import chess.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTests {
	
	private Game game;
	
	public void setUp() {
		game = new Game();
	}

	@Test
	public void CreateNewPiece() {
		setUp();
		
		Piece piece = new Pawn(game, Chess.Color.BLACK, game.getBoard().getSpot(2, 3));
		
		assertTrue(piece.isInPlay());
		assertEquals(piece.getColor(), Chess.Color.BLACK);
		assertEquals(piece.getSpot(), game.getBoard().getSpot(2,  3));
	}
	
	@Test
	public void Movement() {
		setUp();
		
		King king = (King) game.addPiece(King.class, Chess.Color.WHITE, game.getBoard().getSpot(0, 1));
		assertEquals(king.getPossibleMoves().size(), 5);
		
		Board.Spot newSpot = king.getSpotWithOffset(1, 1);
		king.moveTo(newSpot);
		assertEquals(king.getSpot(), game.getBoard().getSpot(1,  2));
	}

}
