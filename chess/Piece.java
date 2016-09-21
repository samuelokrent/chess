package chess;

import java.util.List;

/**
 * Piece: A class representing a generic chess piece
 */

public abstract class Piece {

	// The color of this piece
	protected Chess.Color color;

	// Whether or not the piece is currently still in play
	protected boolean inPlay;

	// The current location of this piece
	protected Board.Spot spot;

	// The game the piece is in
	protected Game game;

	/**
	 * @param game The game context for this piece
	 * @param color This piece's color
	 * @param spot This piece's current spot on the board
	 */
	public Piece(Game game, Chess.Color color, Board.Spot spot) {
		this.game = game;
		this.color = color;
		this.spot = spot;
		spot.setPiece(this);

		this.inPlay = true;
	}

	public boolean isInPlay() {
		return this.inPlay;
	}

	/**
	 * Mark this piece as captured by the opposing team
	 */
	public void removeFromPlay() {
		if(spot.getPiece() == this)
			spot.setPiece(null);
		this.spot = null;
		this.inPlay = false;
	}

	public Chess.Color getColor() {
		return this.color;
	}

	public Board.Spot getSpot() {
		return this.spot;
	}

	/**
	 * @returns The spot that is the given offset away from this piece's current spot
	 */
	public Board.Spot getSpotWithOffset(int rowOffset, int colOffset) {
		return game.getBoard().getSpot(spot.getRow() + rowOffset, spot.getCol() + colOffset);
	}

	/**
	 * Update this piece's location on the board
	 */
	public void moveTo(Board.Spot spot) {
		// Clear reference to this piece from old spot
		this.spot.setPiece(null);

		this.spot = spot;
		spot.setPiece(this);
	}

	/**
	 * @returns Whether the given spot is available for this piece to move into
	 */
	public boolean isAvailableSpot(int row, int col, boolean regardlessOfKing) {
		return game.isAvailableSpotForPiece(this, row, col, regardlessOfKing);
	}

	/**
	 * Convenience method for isAvailableSpot(int, int)
	 * @returns Whether the given spot is available for this piece to move into
	 */
	public boolean isAvailableSpot(Board.Spot spot, boolean regardlessOfKing) {
		return isAvailableSpot(spot.getRow(), spot.getCol(), regardlessOfKing);
	}

	/**
	 * @returns A list of all spots this piece could currently move to,
	 * excluding those that would put the king into check
	 */
	public List<Board.Spot> getPossibleMoves() {
		return getPossibleMoves(false);
	}

	/**
	 * @returns A list of all spots this piece could currently move to,
	 * including those that would put the king into check
	 */
	public List<Board.Spot> getPossibleMovesRegardlessOfKing() {
		return getPossibleMoves(true);
	}

	/**
	 * Returns a list of all spots this piece could currently move to
	 * @param regardlessOfKing Whether or not to ignore if move puts king into check
	 */
	abstract List<Board.Spot> getPossibleMoves(boolean regardlessOfKing);

	/**
	 * A string representing the type of this piece
	 */
	public String getType() {
		return "Piece";
	}
	
	/**
	 * @returns The name of this piece's icon file
	 */
	public String getIconFile() {
		String fileName = color.toString() + getType() + ".png";
		return "icons/" + fileName;
	}
	
	public String toString() {
		return color.toString() + " " + getType();
	}
	
	public String displayString() {
		return formatString(toString());
	}

	/** 
	 * Pads the given string out to STRING_LENGTH
	 */
	public static String formatString(String s) {
		String result = s;
		for(int i = result.length(); i < STRING_LENGTH; i++) {
			result += " ";
		}
		return result;
	}
	
	// Length of strings returned by toString()
	public static final int STRING_LENGTH = 14;

}
