package chess.tests;

import chess.*;
import static org.junit.Assert.*;

import java.util.List;

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

		Board.Spot[] expectedMoves = new Board.Spot[]{
				game.getBoard().getSpot(0, 0),
				game.getBoard().getSpot(1, 0),
				game.getBoard().getSpot(1, 1),
				game.getBoard().getSpot(1, 2),
				game.getBoard().getSpot(0, 2)
		};
		assertMovesEqual(king.getPossibleMoves(), expectedMoves);
		
		Board.Spot newSpot = king.getSpotWithOffset(1, 1);
		king.moveTo(newSpot);
		assertEquals(king.getSpot(), game.getBoard().getSpot(1,  2));
		assertEquals(king.getSpot().getPiece(), king);
	}
	
	@Test
	public void RemoveFromPlay() {
		setUp();
		
		Pawn pawn = (Pawn) game.addPiece(Pawn.class, Chess.Color.WHITE, game.getBoard().getSpot(0, 1));
		assertTrue(pawn.isInPlay());
		assertNotNull(pawn.getSpot());
		
		pawn.removeFromPlay();
		assertFalse(pawn.isInPlay());
		assertNull(pawn.getSpot());
	}
	
	@Test
	public void AvailableSpots() {
		setUp();
		
		Bishop wBishop1 = (Bishop) game.addPiece(Bishop.class, Chess.Color.WHITE, game.getBoard().getSpot(3, 3));
		Bishop wBishop2 = (Bishop) game.addPiece(Bishop.class, Chess.Color.WHITE, game.getBoard().getSpot(4, 4));
		Bishop bBishop = (Bishop) game.addPiece(Bishop.class, Chess.Color.BLACK, game.getBoard().getSpot(2, 2));
		King king = (King) game.addPiece(King.class, Chess.Color.BLACK, game.getBoard().getSpot(1, 1));
		
		// Check if an empty spot is available
		assertTrue(wBishop1.isAvailableSpot(2,  4, true));
		
		// Check if same-color occupied spot is available
		assertFalse(wBishop1.isAvailableSpot(wBishop2.getSpot(), true));
				
		// Check if opponent-color occupied spot is available
		assertTrue(wBishop1.isAvailableSpot(bBishop.getSpot(), true));
		
		// Check if an off-board spot is available
		assertFalse(wBishop1.isAvailableSpot(10,  10, true));
		
		// Check if a move that puts the black king in check is available for black piece
		assertFalse(bBishop.isAvailableSpot(1,  3, false));
	}
	
	public void assertMovesEqual(List<Board.Spot> moves, Board.Spot[] expectedMoves) {
		assertEquals(moves.size(), expectedMoves.length);
		for(Board.Spot spot : expectedMoves) {
			assertTrue(moves.contains(spot));
		}
	}

}
