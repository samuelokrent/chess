package chess;

/**
 * Board: A class representing a chess board
 */

public class Board {

	/**
	 * Spot: A class representing a single square on a chess board
	 */
	
	public class Spot {

		public static final String ANSI_BLACK = "\u001B[40m\u001B[37m";
		public static final String ANSI_WHITE = "\u001B[47m\u001B[30m";
		public static final String ANSI_RESET = "\u001B[0m";

		// Coordinates (0, 0) lie in bottom-left corner
		private int col, row;

		// The piece occupying this spot
		private Piece piece;

		public Spot(int row, int col) { 
			this.row = row;
			this.col = col;
		}

		public Piece getPiece() { return piece; }
		public void setPiece(Piece piece) { this.piece = piece; }

		public int getRow() { return this.row; }
		public int getCol() { return this.col; }

		/**
		 * Returns the color of this spot
		 * Bottom-left corner (0,0) is black
		 */
		public Chess.Color getColor() {
			if((row + col) % 2 == 0) return Chess.Color.BLACK;
			else return Chess.Color.WHITE;
		}

		/**
		 * @returns Whether a piece lies in this spot
		 */
		public boolean isOccupied() {
			return this.piece != null;
		}

		@Override
		public boolean equals(Object other) {
			return (other instanceof Spot) && 
				((Spot) other).row == this.row && ((Spot) other).col == this.col;
		}

		/**
		 * @returns A formatted, colored display string representing this spot
		 */
		public String displayString() {
			String ansiColor = (getColor() == Chess.Color.WHITE) ? ANSI_WHITE : ANSI_BLACK;
			if(isOccupied()) {
				return ansiColor + piece.toString() + ANSI_RESET;
			} else {
				return ansiColor + Piece.formatString("") + ANSI_RESET;
			}
		}

		public String toString() {
			return "(" + row + ", " + col + ")";
		}
	}

	private final Spot[][] spots = new Spot[Chess.NUM_ROWS][Chess.NUM_COLS];

	/**
	 * Initializes an empty chess board
	 */
	public Board() {
		for(int row = 0; row < Chess.NUM_ROWS; row++) {
			for(int col = 0; col < Chess.NUM_COLS; col++) {
				spots[row][col] = new Spot(row, col);
			}
		}
	}

	/**
	 * @returns The spot at the given coordinates,
	 * or a dummy spot if the coordinates do not lie on the board
	 */
	public Spot getSpot(int row, int col) {
		if(isValidSpot(row, col))
			return spots[row][col];
		else 
			return new Spot(row, col);
	}

	/**
	 * @returns Whether the given coordinates lie on the board
	 */
	public boolean isValidSpot(int row, int col) {
		return ((0 <= row && row < Chess.NUM_ROWS) &&
				(0 <= col && col < Chess.NUM_COLS));
	}

	/**
	 * @returns the piece at the given coordinates, or null if none exists
	 */
	public Piece getPieceAt(int row, int col) {
		if(!isValidSpot(row, col)) return null;
		return spots[row][col].getPiece();
	}

	/**
	 * @returns Whether a piece lies in the given spot
	 */
	public boolean isOccupied(int row, int col) {
		return getSpot(row, col).isOccupied();
	}

	public String toString() {
		String out = "\n";
		for(int rowIdx = Chess.NUM_ROWS - 1; rowIdx >= 0; rowIdx--) {
			out += rowIdx + " ";
			Spot[] row = spots[rowIdx];
			for(int col = 0; col < Chess.NUM_COLS; col++) {
				out += row[col].displayString();
			}
			out += "\n";
		}

		out += "  ";
		for(int col = 0; col < Chess.NUM_COLS; col++) {
			out += Piece.formatString("" + col);
		}
		return out + "\n\n";
	}

}
