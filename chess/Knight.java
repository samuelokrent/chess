package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Knight: A class represent a Knight type chess piece
 */
public class Knight extends Piece {

	public Knight(Game game, Chess.Color color, Board.Spot spot) {
		super(game, color, spot);
	}

	public List<Board.Spot> getPossibleMoves(boolean regardlessOfKing) {

		List<Board.Spot> possibleMoves = new ArrayList<Board.Spot>();
		
		int[] verticalDirs = { 1, -1 };
		int[] horizontalDirs = { 1, -1 };
		int[][]  multipliers = new int[][]{
			new int[]{ 1, 2 },
			new int[]{ 2, 1 }
		};

		// Explore all 8 possible 2-ahead, 1-over moves
		for(int verticalDir : verticalDirs) {
			for(int horizontalDir : horizontalDirs) {
				for(int[] multiplier : multipliers) {

					Board.Spot targetSpot = getSpotWithOffset(verticalDir * multiplier[0], horizontalDir * multiplier[1]);
					if(isAvailableSpot(targetSpot, regardlessOfKing))
						possibleMoves.add(targetSpot);

				} // END FOR
			} // END FOR
		} // END FOR

		return possibleMoves;
	}

	@Override
	public String getType() {
		return "Knight";
	}
}
