package chess.tests;

import java.util.List;
import chess.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class GameTests {
	
	private Game game;
	private Bishop wBishop;
	private Rook wRook1;
	private Rook wRook2;
	private Rook bRook;
	private King wKing;
	private King bKing;
	
	public void setUp() {
		game = new Game();
		wKing = (King) game.addPiece(King.class, Chess.Color.WHITE, game.getBoard().getSpot(0, 0));
		bKing = (King) game.addPiece(King.class, Chess.Color.BLACK, game.getBoard().getSpot(7, 7));
		wBishop = (Bishop) game.addPiece(Bishop.class, Chess.Color.WHITE, game.getBoard().getSpot(2, 3));
		wRook1 = (Rook) game.addPiece(Rook.class, Chess.Color.WHITE, game.getBoard().getSpot(4, 5));
		wRook2 = (Rook) game.addPiece(Rook.class, Chess.Color.WHITE, game.getBoard().getSpot(2, 6));
		bRook = (Rook) game.addPiece(Rook.class, Chess.Color.BLACK, game.getBoard().getSpot(5, 3));
	}

	@Test
	public void NewGameConditions() {
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
		
		assertEquals(game.getPieces(Chess.Color.WHITE).size(), 16);
		assertEquals(game.getPieces(Chess.Color.BLACK).size(), 16);
	}
	
	@Test
	public void Check() {
		setUp();
		
		assertFalse(game.isInCheck(Chess.Color.BLACK));
		
		wRook1.moveTo(game.getBoard().getSpot(7,  6));
		
		assertTrue(game.isInCheck(Chess.Color.BLACK));
	}
	
	@Test
	public void Checkmate() {
		setUp();
		
		assertFalse(game.isInCheckmate(Chess.Color.BLACK));
		
		wRook1.moveTo(game.getBoard().getSpot(7,  5));
		wRook2.moveTo(game.getBoard().getSpot(6,  5));
		
		assertTrue(game.isInCheckmate(Chess.Color.BLACK));
	}
	
	@Test
	public void Stalemate() {
		setUp();
		
		assertFalse(game.isInStalemate(Chess.Color.BLACK));
		
		game.removePieceFromPlay(bRook);
		wRook1.moveTo(game.getBoard().getSpot(6,  5));
		wRook2.moveTo(game.getBoard().getSpot(5,  6));
		
		assertTrue(game.isInStalemate(Chess.Color.BLACK));
	}
	
	@Test
	public void CapturePiece() {
		setUp();
		
		game.startGame();
		Board.Spot rookSpot = bRook.getSpot();
		Piece capturedPiece = game.movePieceTo(wBishop, rookSpot);
		
		assertEquals(capturedPiece, bRook);
		assertFalse(bRook.isInPlay());
		assertNull(bRook.getSpot());
		assertEquals(wBishop.getSpot(), rookSpot);
		assertEquals(wBishop.getSpot().getPiece(), wBishop);
	}
	
	@Test
	public void GetPiecesThreatening() {
		setUp();
		
		assertEquals(game.getPiecesThreatening(bKing, true).size(), 0);
		
		wRook1.moveTo(game.getBoard().getSpot(7, 6));
		wRook2.moveTo(game.getBoard().getSpot(6, 7));
		wBishop.moveTo(game.getBoard().getSpot(6, 6));
		
		List<Piece> threateningPieces = game.getPiecesThreatening(bKing, true);
		
		assertEquals(threateningPieces.size(), 3);
		assertTrue(threateningPieces.contains(wRook1));
		assertTrue(threateningPieces.contains(wRook2));
		assertTrue(threateningPieces.contains(wBishop));
	}
	
	@Test
	public void MoveWouldPutKingInCheck() {
		setUp();
		
		bRook.moveTo(game.getBoard().getSpot(7, 6));
		assertFalse(game.moveWouldPutKingInCheck(bRook, game.getBoard().getSpot(6, 6)));
		
		wRook1.moveTo(game.getBoard().getSpot(7, 5));
		assertTrue(game.moveWouldPutKingInCheck(bRook, game.getBoard().getSpot(6, 6)));
	}

}
