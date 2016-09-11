package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Bishop: A class represent a Bishop type chess piece
 */
public class Bishop extends Piece {

	public Bishop(Game game, Chess.Color color, Board.Spot spot) {
		super(game, color, spot);
	}

	public List<Board.Spot> getPossibleMoves(boolean regardlessOfKing) {
		List<Board.Spot> possibleMoves = new ArrayList<Board.Spot>();

		int[] verticalDirs = { 1, -1 };
		int[] horizontalDirs = { 1, -1 };

		// Explore outwards in all 4 possible directions
		for(int verticalDir : verticalDirs) {
			for(int horizontalDir : horizontalDirs) {

				int distance = 1;
				Board.Spot targetSpot = getSpotWithOffset(distance * verticalDir, distance * horizontalDir);

				while(isAvailableSpot(targetSpot, regardlessOfKing)) {

					possibleMoves.add(targetSpot);

					if(targetSpot.isOccupied()) {
						// If this spot is occupied, we can't move to a spot past it
						break;
					} else {
						distance++;
						targetSpot = getSpotWithOffset(distance * verticalDir, distance * horizontalDir);
					}

				} // END WHILE

			} // END FOR
		} // END FOR

		return possibleMoves;
	}

	@Override
	public String getType() { 
		return "Bishop";
	}

}
