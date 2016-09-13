package chess.gui;

import chess.*;
import chess.gui.*;
import javax.swing.*;

/**
 * ChessPlayer: The main GUI for a chess game
 */
public class ChessPlayer implements Game.GameEventListener {
	
	// The game this GUI displays
	private Game game;
	
	public ChessPlayer() {

        JFrame frame = new JFrame("ChessPlayer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game = new Game();
        game.setGameEventListener(this);
        game.startGame();
        BoardPanel boardPanel = new BoardPanel(game.getBoard());
        frame.getContentPane().add(boardPanel);
        frame.setMaximumSize(boardPanel.getPreferredSize());

        frame.pack();
        frame.setVisible(true);
	}

	public static void main(String[] args) {
		new ChessPlayer();
	}
	
	/**
	 * Part of the GameEventListener interface
	 * Receives game end events
	 */
	public void onGameEnded(Chess.Color winner) {
		
	}
}