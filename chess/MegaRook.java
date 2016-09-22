package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * MegaRook: A class representing a MegaRook type chess piece
 * MegaRooks move just like Rooks, except pieces that lie in their
 * way do not block their paths, so they can essentially jump other pieces.
 */
public class MegaRook extends Piece {

	public MegaRook(Game game, Chess.Color color, Board.Spot spot) {
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

			while(game.getBoard().isValidSpot(targetSpot.getRow(), targetSpot.getCol())) {

				if(isAvailableSpot(targetSpot, regardlessOfKing))
					possibleMoves.add(targetSpot);

				distance++;
				targetSpot = getSpotWithOffset(distance * verticalDir, distance * horizontalDir);

			} // END WHILE

		} // END FOR

		return possibleMoves;
	}

	@Override
	public String getType() {
		return "MegaRook";
	}
}