package chess.gui;

import chess.*;
import chess.gui.*;
import javax.swing.*;
import java.awt.*;

/**
 * BoardPanel: A JPanel class that displays a chess board
 */
public class BoardPanel extends JPanel {

	// The board this BoardPanel represents
	private Board board;
	
	public BoardPanel(Board board) {
		super();
		this.board = board;
		
		GridLayout layout = new GridLayout(Chess.NUM_ROWS, Chess.NUM_COLS);
		setLayout(layout);
		
		int height = SpotPanel.SIDE_LENGTH * Chess.NUM_ROWS;
		int width = SpotPanel.SIDE_LENGTH * Chess.NUM_COLS;
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());
		
		for(int row = Chess.NUM_ROWS - 1; row >= 0; row--) {
			for(int col = 0; col < Chess.NUM_COLS; col++) {
				add(new SpotPanel(board.getSpot(row,  col)));
			}
		}
	}
	
}
