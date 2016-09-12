package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Rook: A class represent a Rook type chess piece
 */
public class Rook extends Piece {

	public Rook(Game game, Chess.Color color, Board.Spot spot) {
		super(game, color, spot);
	}

	public List<Board.Spot> getPossibleMoves(boolean regardlessOfKing) {
		List<Board.Spot> possibleMoves = new ArrayList<Board.Spot>();

		int[][] dirVectors = new int[][]{ 
			new int[]{ 1, 0 },
			new int[]{ -1, 0 },
			new int[]{ 0, 1 },
			new int[]{ 0, -1 }
		};

		// Explore outwards in all 4 possible directions
		for(int[] dirVector : dirVectors) {
			int verticalDir = dirVector[0];
			int horizontalDir = dirVector[1];
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

		return possibleMoves;
	}

	@Override
	public String getType() {
		return "Rook";
	}
}
