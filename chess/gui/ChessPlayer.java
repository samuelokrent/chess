package chess.gui;

import chess.*;
import chess.Chess.Color;
import chess.gui.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
 * ChessPlayer: The main GUI for a chess game
 */
public class ChessPlayer implements Game.GameEventListener, ActionListener, MouseListener {
	
	// The current chess game being played in this GUI
	private Game game;
	
	JFrame frame;
	
	private BoardPanel boardPanel;
	
	JButton startButton;
	JButton restartButton;
	JButton wForfeitButton;
	JButton bForfeitButton;
	
	JLabel turnLabel;
	JLabel wScoreLabel;
	JLabel bScoreLabel;
	
	SpotPanel selectedSpot;

	int wScore = 0;
	int bScore = 0;
	
	public ChessPlayer() {

        this.frame = new JFrame("ChessPlayer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GroupLayout layout = new GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(layout);
        
        this.game = new Game();
        game.setGameEventListener(this);
        
        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        
        restartButton = new JButton("Restart Game");
        restartButton.setEnabled(false);
        restartButton.addActionListener(this);
        
        wForfeitButton = new JButton("White Forfeit");
        wForfeitButton.setEnabled(false);
        wForfeitButton.addActionListener(this);
        
        bForfeitButton = new JButton("Black Forfeit");
        bForfeitButton.setEnabled(false);
        bForfeitButton.addActionListener(this);
        
        turnLabel = new JLabel();
        turnLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        wScoreLabel = new JLabel();
        wScoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        bScoreLabel = new JLabel();
        bScoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        this.boardPanel = new BoardPanel(this);
        boardPanel.setBoard(game.getBoard());
        //frame.setMaximumSize(boardPanel.getPreferredSize());
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        				.addComponent(startButton)
        				.addComponent(restartButton)
        				.addComponent(wForfeitButton)
        				.addComponent(bForfeitButton)
        				.addComponent(turnLabel)
        				.addComponent(wScoreLabel)
        				.addComponent(bScoreLabel)
        				)
        		.addComponent(boardPanel)
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        				.addComponent(startButton)
        				.addComponent(restartButton)
        				.addComponent(wForfeitButton)
        				.addComponent(bForfeitButton)
        				.addComponent(turnLabel)
        				.addComponent(wScoreLabel)
        				.addComponent(bScoreLabel)
        				)
        		.addComponent(boardPanel)
        );

        frame.pack();
        frame.setVisible(true);
	}

	public static void main(String[] args) {
		new ChessPlayer();
	}

	@Override
	public void onGameStarted() {
		
		startButton.setEnabled(false);
		restartButton.setEnabled(true);
		wForfeitButton.setEnabled(true);
		bForfeitButton.setEnabled(true);
		
		updateText();
		
		// Ensure boardPanel displays current board
		boardPanel.setBoard(game.getBoard());
	}

	@Override
	public void onMoveTaken(Piece captured) {
		
		boardPanel.revalidate();
		boardPanel.repaint();
		
		if(captured != null)
			JOptionPane.showMessageDialog(frame, captured.toString() + " Captured!");
		
		game.startNewTurn();
		
		updateText();
	}
	
	@Override
	public void onGameEnded(Chess.Color winner) {
		if(winner != null) {
			
			if(winner == Chess.Color.WHITE) wScore++;
			else if(winner == Chess.Color.BLACK) bScore++;
			
			updateText();
			JOptionPane.showMessageDialog(frame, winner.toString() + " Wins!");
		} else {
			
			JOptionPane.showMessageDialog(frame, "Draw!");
		}
		
		startButton.setEnabled(true);
		restartButton.setEnabled(false);
		wForfeitButton.setEnabled(false);
		bForfeitButton.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startButton) {
			
			// START GAME CLICK
			game.startGame();
			
		} else if(e.getSource() == restartButton) {
			
			// RESTART GAME CLICK
			game.restartGame();
			
		}
		else if(e.getSource() == wForfeitButton) {
			
			// WHITE FORFEIT CLICK
			game.endGame(Chess.Color.BLACK);
			
		} else if(e.getSource() == bForfeitButton) {
			
			// BLACK FORFEIT CLICK
			game.endGame(Chess.Color.WHITE);
			
		}
	}
	
	/**
	 * Updates labels to display current game information
	 */
	public void updateText() {
		if(game.getTurnColor() != null)
			turnLabel.setText("Turn: " + game.getTurnColor().toString());
		wScoreLabel.setText("White: " + wScore);
		bScoreLabel.setText("Black: " + bScore);
	}
	
	/**
	 * @param spotPanel The spot in question
	 * @return Whether or not the user can select this spot
	 */
	private boolean canSelect(SpotPanel spotPanel) {
		
		if(!game.isInPlay()) return false;
		Board.Spot spot = spotPanel.getSpot();
		
		if(selectedSpot == null) {
			return spot.isOccupied() && 
					spot.getPiece().getColor() == game.getTurnColor();
		} else {
			return selectedSpot == spotPanel ||
					selectedSpot.getSpot().getPiece()
					.getPossibleMoves().contains(spot);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		SpotPanel spotPanel = (SpotPanel) e.getSource();
		if(!canSelect(spotPanel)) return;
			
		if(selectedSpot == null) {
			
			selectedSpot = spotPanel;
			selectedSpot.setHighlighted(true);
			
		} else if(selectedSpot == spotPanel) {
			
			// Deselecting selected spot
			selectedSpot.setHighlighted(false);
			selectedSpot = null;
			
		} else {
			
			selectedSpot.setHighlighted(false);
			game.movePieceTo(selectedSpot.getSpot().getPiece(), spotPanel.getSpot());
			selectedSpot = null;
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		SpotPanel spotPanel = (SpotPanel) e.getSource();
		if(canSelect(spotPanel)) {
			spotPanel.setHighlighted(true);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		SpotPanel spotPanel = (SpotPanel) e.getSource();
		if(spotPanel != selectedSpot) spotPanel.setHighlighted(false);
	}

}