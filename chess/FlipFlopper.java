package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * FlipFlopper: A class represent a FlipFlopper type chess piece
 * A FlipFlopper moves like a Queen on its first move, like a King on its second,
 * and continues alternating between the two every other move.
 */
public class FlipFlopper extends Piece {
	
	// The parity of the number of moves this piece has taken
	private int moveParity;

	public FlipFlopper(Game game, Chess.Color color, Board.Spot spot) {
		super(game, color, spot);
		moveParity = 0;
	}

	public List<Board.Spot> getPossibleMoves(boolean regardlessOfKing) {
		List<Board.Spot> possibleMoves = new ArrayList<Board.Spot>();

		int[] verticalDirs = { 1, 0, -1 };
		int[] horizontalDirs = { 1, 0, -1 };

		// Explore outwards in all 8 possible directions
		for(int verticalDir : verticalDirs) {
			for(int horizontalDir : horizontalDirs) {

				// Direction vector needs at least one non-zero to be valid
				if(horizontalDir == 0 && verticalDir == 0) continue;

				int distance = 1;
				Board.Spot targetSpot = getSpotWithOffset(distance * verticalDir, distance * horizontalDir);
				
				while(isAvailableSpot(targetSpot, regardlessOfKing)) {

					possibleMoves.add(targetSpot);
					
					if(moveParity == 1) {
						// If this is a King-like move, only explore 1 spot out
						break;
					} else if(targetSpot.isOccupied()) {
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
	public void moveTo(Board.Spot spot) {
		super.moveTo(spot);
		moveParity = (moveParity + 1) % 2;
	}

	@Override
	public String getType() { 
		return "FlipFlopper";
	}
}
