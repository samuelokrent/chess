import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.lang.reflect.Constructor;

/**
 * Game: A class representing a chess game
 */

public class Game {

	// Keeps track of which side's turn it is
	private Chess.Color turnColor;

	// The game board
	private Board board;

	// A collection of all in-play game pieces that maps from color to a list of pieces
	private Map<Chess.Color, List<Piece>> pieces = new HashMap<Chess.Color, List<Piece>>();;

	/**
	 * Initializes a new chess game
	 */
	public Game() {
		this.board = new Board();
		this.pieces.put(Chess.Color.WHITE, new ArrayList<Piece>());
		this.pieces.put(Chess.Color.BLACK, new ArrayList<Piece>());

		// Initialize both sides' pieces
		for(Chess.RowConfiguration rowConfiguration : Chess.ROW_CONFIGURATIONS) {
			int row = rowConfiguration.row;
			Class[] pieceClasses = rowConfiguration.pieces;
			Chess.Color sideColor = rowConfiguration.sideColor;

			for(int col = 0; col < Chess.NUM_COLS; col++) {
				addPiece(pieceClasses[col], sideColor, board.getSpot(row, col));
			}
		}
	}

	public Board getBoard() {
		return this.board;
	}

	public Chess.Color getTurnColor() {
		return this.turnColor;
	}

	public void startGame() {
		startNewTurn();
	}

	public void startNewTurn() {
		if(turnColor == null) {
			// First turn, white side moves first
			turnColor = Chess.Color.WHITE;
		} else {
			turnColor = Chess.opponentColor(turnColor);
		}

		if(isInCheckmate(turnColor)) endGame(Chess.opponentColor(turnColor));
		else if(isInStalemate(turnColor)) endGame(null);
	}

	/**
	 * Does whatever is necessary to end the game
	 */
	public void endGame(Chess.Color winner) {
		
	}

	/**
	 * @returns Whether the side of the given color is in checkmate
	 */
	public boolean isInCheckmate(Chess.Color color) {
		if(!isInCheck(color)) return false;

		// Check if any piece has a move that doesn't leave the king in check
		for(Piece piece : pieces.get(color)) {
			if(!piece.getPossibleMoves().isEmpty()) return false;
		}

		return true;

	}

	/**
	 * @returns Whether the side of the given color is in stalemate
	 */
	public boolean isInStalemate(Chess.Color color) {
		if(isInCheck(color)) return false;

		// Check if any piece has a move
		for(Piece piece : pieces.get(color)) {
			if(!piece.getPossibleMoves().isEmpty()) return false;
		}

		return true;
	}

	/**
	 * Moves the specified piece to a new location
	 * @param piece The piece to move
	 * @param row The row to move to
	 * @param col The col to move to
	 * @returns The piece captured by the executed move, or null if the space was unoccupied
	 */
	public Piece movePieceTo(Piece piece, int row, int col) throws Chess.ChessException {
		// Check if move is played in turn, on an in-play piece, to a valid spot
		if(!(piece.isInPlay() && board.isValidSpot(row, col) &&
			(piece.getColor() == turnColor)))
				throw new Chess.ChessException("Attempt to move piece " + piece.toString() + " to (" + col + ", " + row + ") was invalid");

		Piece capturedPiece = board.getPieceAt(row, col);
		if(capturedPiece != null)
			removePieceFromPlay(capturedPiece);

		piece.moveTo(board.getSpot(row, col));

		return capturedPiece;
	}

	/**
	 * @returns Whether the given spot exists and is available
	 * for the given piece to move into
	 */
	public boolean isAvailableSpotForPiece(Piece piece, int row, int col, boolean regardlessOfKing) {
		if(!board.isValidSpot(row, col)) return false;

		if(board.isOccupied(row, col) && board.getPieceAt(row, col).getColor() == piece.getColor())
			return false;

		if(!regardlessOfKing && moveWouldPutKingInCheck(piece, board.getSpot(row, col))) return false;

		return true;
	}

	/**
	 * @returns A list of all pieces in a position to capture the given piece
	 * @param piece The piece being threatened
	 * @param regardlessOfKing Whether or not the existence of the threat
	 * should take into account the consequences for the opponent's king
	 */
	public List<Piece> getPiecesThreatening(Piece piece, boolean regardlessOfKing) {
		List<Piece> threateningPieces = new ArrayList<Piece>();
		for(Piece opponent : pieces.get(Chess.opponentColor(piece.getColor()))) {
			if(opponent.getPossibleMoves(regardlessOfKing).contains(piece.getSpot()))
				threateningPieces.add(opponent);
		}
		return threateningPieces;
	}

	/**
	 * @returns Whether or not the side of the given color is in check
	 */
	public boolean isInCheck(Chess.Color sideColor) {
		King king = getKing(sideColor);
		List<Piece> opponentPieces = pieces.get(Chess.opponentColor(sideColor));
		for(Piece opponentPiece : opponentPieces) {
			for(Board.Spot spot : opponentPiece.getPossibleMovesRegardlessOfKing()) {
				// Check if opponent piece can move to the king's spot
				if(king.getSpot().equals(spot)) return true;
			}
		}
		return false;
	}

	/**
	 * @returns Whether the king of the given piece's color would be in check,
	 * were it to move to the given spot
	 */
	public boolean moveWouldPutKingInCheck(Piece piece, Board.Spot spot) {
		Callable<Boolean> inCheckCondition = new Callable<Boolean>() {
			@Override
			public Boolean call() {
				return isInCheck(piece.getColor());
			}
		};

		return resultIfPieceMovedToSpot(piece, spot, inCheckCondition);
	}

	/**
	 * @returns Whether or not the given condition would be true
	 * if the given piece were to be moved to the given spot
	 */
	private boolean resultIfPieceMovedToSpot(Piece piece, Board.Spot spot, Callable<Boolean> condition) {
		// Save current board configuration
		Piece pieceInNewSpot = spot.getPiece();
		Board.Spot oldSpot = piece.getSpot();

		if(pieceInNewSpot != null) pieces.get(pieceInNewSpot.getColor()).remove(pieceInNewSpot);
		piece.moveTo(spot);

		Boolean result;
		try {
			result = condition.call();
		} catch(Exception e) {
			System.err.println("Exception checking condition: " + e.getMessage());
			result = false;
		}

		// Restore old board configuration
		piece.moveTo(oldSpot);
		if(pieceInNewSpot != null) {
			pieces.get(pieceInNewSpot.getColor()).add(pieceInNewSpot);
			spot.setPiece(pieceInNewSpot);
		}

		return result;
	} 

	/**
	 * @returns The king of the given color
	 */
	public King getKing(Chess.Color color) {
		for(Piece piece : pieces.get(color)) {
			if(piece instanceof King)
				return (King) piece;
		}
		return null;
	}

	/**
	 * Adds a piece to the board
	 * @param pieceClass The class of the piece to add
	 * @param sideColor The color of the piece to add
	 * @param spot The location to add the piece at
	 */
	public void addPiece(Class pieceClass, Chess.Color sideColor, Board.Spot spot) {
		try {
			Constructor pieceConstructor = pieceClass.getDeclaredConstructor(Game.class, Chess.Color.class, Board.Spot.class);
			Piece piece = (Piece) pieceConstructor.newInstance(this, sideColor, spot);
			pieces.get(sideColor).add(piece);
		} catch(Exception e) {
			System.err.println("Exception instantiating piece: " + e.getMessage());
		}
	}

	/**
	 * Removes the given piece from play
	 */
	private void removePieceFromPlay(Piece piece) {
		piece.removeFromPlay();
		pieces.get(piece.getColor()).remove(piece);
	}

}