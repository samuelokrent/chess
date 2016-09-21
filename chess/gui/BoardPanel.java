package chess.gui;

import chess.*;
import chess.gui.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * BoardPanel: A JPanel class that displays a chess board
 */
public class BoardPanel extends JPanel {

	// The board this BoardPanel represents
	private Board board;
	
	private SpotPanel[][] spotPanels;
	
	public BoardPanel(MouseListener listener) {
		super();
		
		GridLayout layout = new GridLayout(Chess.NUM_ROWS, Chess.NUM_COLS);
		setLayout(layout);
		setBorder(BorderFactory.createMatteBorder(50, 50, 50, 50, SpotPanel.WHITE));
		spotPanels = new SpotPanel[Chess.NUM_ROWS][Chess.NUM_COLS];
		
		int height = SpotPanel.SIDE_LENGTH * Chess.NUM_ROWS;
		int width = SpotPanel.SIDE_LENGTH * Chess.NUM_COLS;
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());
		
		for(int row = Chess.NUM_ROWS - 1; row >= 0; row--) {
			for(int col = 0; col < Chess.NUM_COLS; col++) {
				spotPanels[row][col] = new SpotPanel(listener);
				add(spotPanels[row][col]);
			}
		}
	}
	
	public void setBoard(Board board) {
		this.board = board;
		for(int row = 0; row < Chess.NUM_ROWS; row++) {
			for(int col = 0; col < Chess.NUM_COLS; col++) {
				spotPanels[row][col].setSpot(board.getSpot(row, col));
			}
		}
		revalidate();
		repaint();
	}
	
}
