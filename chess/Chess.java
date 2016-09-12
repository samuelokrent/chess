package chess;

import java.lang.Exception;

/**
 * Chess: A class containing utilities and constants relating to chess
 */
public class Chess {

	/**
	 * ChessError: An error meant to indicate a chess logic error has occured
	 */
	public static class ChessError extends Error {
	
		public ChessError(String message) {
			super(message);
		}

	}

	/**
	 * The possible colors of chess pieces and spots
	 */	
	public static enum Color {
		WHITE, BLACK;
	
		public String toString() {
			switch(this) {
				case WHITE:
					return "White";
				case BLACK:
					return "Black";
			}
			return null;
		}
	}

	/**
	 * Returns the color that is not passed as an argument
	 */
	public static Color opponentColor(Color color) {
		if(color == Color.WHITE) return Color.BLACK;
		else return Color.WHITE;
	}


	/** BOARD CONFIGURATION SECTION **/

	/**
	 * The dimensions of the chess board
	 */
	public static final int NUM_ROWS = 8;
	public static final int NUM_COLS = 8;

	/*
	 * BACK_ROW and FRONT_ROW represent starting piece configurations
	 */
	public static final Class[] BACK_ROW = new Class[] {
		Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class
	};

	public static final Class[] FRONT_ROW = new Class[] {
		Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class, Pawn.class
	};

	/**
	 * RowConfiguration: A class representing a starting configuration for a row of spots on the board
	 */
	public static class RowConfiguration {

		// The color of this row's pieces
		public Chess.Color sideColor;

		// The row number this configuration is meant for
		public int row;

		// The pieces, in order, that should populate this row
		public Class[] pieces;

		public RowConfiguration(Chess.Color sideColor, int row, Class[] pieces) {
			this.sideColor = sideColor;
			this.row = row;
			this.pieces = pieces;
		}
	}

	public static final RowConfiguration[] ROW_CONFIGURATIONS = {

		new RowConfiguration(Chess.Color.BLACK, 7, BACK_ROW),
		new RowConfiguration(Chess.Color.BLACK, 6, FRONT_ROW),

		new RowConfiguration(Chess.Color.WHITE, 1, FRONT_ROW),
		new RowConfiguration(Chess.Color.WHITE, 0, BACK_ROW)
	};

	/** END BOARD CONFIGURATION SECTION **/

}
