import java.util.Random;
import java.util.List;

public class Test implements Game.GameEventListener {

	public void onGameEnded(Chess.Color winner) {
		if(winner == null) System.out.println("Stalemate");
		else System.out.println(winner.toString() + " wins");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.startGame();

		Test test = new Test();
		game.setGameEventListener(test);

		System.out.println(game.getBoard().toString());

		Random rand = new Random();	

		for(int i = 0; i < 1000000; i++) {
			boolean moved = false;
			List<Piece> pieces = game.getPieces(game.getTurnColor());
			while(!moved) {
				Piece piece = pieces.get(rand.nextInt(pieces.size()));
				List<Board.Spot> moves = piece.getPossibleMoves();
				if(!moves.isEmpty()) {
					Board.Spot move = moves.get(rand.nextInt(moves.size()));
					System.out.println("Move " + piece.toString() + " to " + move.toString());
					Piece captured = game.movePieceTo(piece, move);
					if(captured != null) System.out.println("Captured " + captured.toString());
					moved = true;
				}
			}
			System.out.println(game.getBoard().toString());
			game.startNewTurn();
		}
	}
	
}
