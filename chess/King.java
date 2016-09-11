package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * King: A class represent a King type chess piece
 */
public class King extends Piece {

	public King(Game game, Chess.Color color, Board.Spot spot) {
		super(game, color, spot);
	}

	public List<Board.Spot> getPossibleMoves(boolean regardlessOfKing) {
		List<Board.Spot> possibleMoves = new ArrayList<Board.Spot>();

		int[] verticalDirs = { 1, 0, -1 };
		int[] horizontalDirs = { 1, 0, -1 };

		// Explore 8 adjacent spots
		for(int verticalDir : verticalDirs) {
			for(int horizontalDir : horizontalDirs) {
				// Direction vector needs at least one non-zero to be valid
				if(horizontalDir == 0 && verticalDir == 0) continue;

				Board.Spot targetSpot = getSpotWithOffset(verticalDir, horizontalDir);
				if(isAvailableSpot(targetSpot, regardlessOfKing))
					possibleMoves.add(targetSpot);

			} // END FOR
		} // END FOR

		return possibleMoves;
	}

	@Override
	public String getType() {
		return "King";
	}
}
