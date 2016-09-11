package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Pawn: A class represent a Pawn type chess piece
 */
public class Pawn extends Piece {

	// Keeps track of whether or not the pawn has moved yet
	private boolean hasMoved = false;

	public Pawn(Game game, Chess.Color color, Board.Spot spot) {
		super(game, color, spot);
	}

	public List<Board.Spot> getPossibleMoves(boolean regardlessOfKing) {
		// White-side pawns move up the board, Black-side pawns move down
		int forward = (this.color == Chess.Color.WHITE) ? 1 : -1;

		List<Board.Spot> possibleMoves = new ArrayList<Board.Spot>();

		// One spot forward
		Board.Spot oneForward = getSpotWithOffset(forward, 0);
		if(isAvailableSpot(oneForward, regardlessOfKing) && !oneForward.isOccupied()) {
			possibleMoves.add(oneForward);
			
			// Two spots forward, only if this is the first move
			Board.Spot twoForward = getSpotWithOffset(2 * forward, 0);
			if(!hasMoved && isAvailableSpot(twoForward, regardlessOfKing) && !twoForward.isOccupied()) {
				possibleMoves.add(twoForward);
			}
			
		}

		// Diagonal attack moves
		int[] horizontalDirections = new int[]{ 1, -1 };
		for(int horizontalDirection : horizontalDirections) {
			Board.Spot attackSpot = getSpotWithOffset(forward, horizontalDirection);
			if(isAvailableSpot(attackSpot, regardlessOfKing) && attackSpot.isOccupied())
				possibleMoves.add(attackSpot);
		}

		return possibleMoves;
	}

	@Override
	public void moveTo(Board.Spot spot) {
		super.moveTo(spot);

		// Must update hasMoved if this is the first move
		if(!hasMoved) hasMoved = true;
	}

	@Override
	public String getType() {
		return "Pawn";
	}
}
