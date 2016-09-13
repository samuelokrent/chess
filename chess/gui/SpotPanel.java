package chess.gui;

import chess.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * SpotPanel: A JPanel that displays a single spot on
 * the chess board
 */
public class SpotPanel extends JPanel {
	
	// The display length of the side of a SpotPanel
	public static int SIDE_LENGTH = 100;
	
	// The display colors of spots
	public static final Color BLACK = new Color(0.56f, 0.32f, 0.1f);
	public static final Color WHITE = new Color(0.95f, 0.9f, 0.83f);

	// The spot this SpotPanel represents
	private Board.Spot spot;
	
	public SpotPanel(Board.Spot spot) {
		super();
		this.spot = spot;
		setPreferredSize(new Dimension(SIDE_LENGTH, SIDE_LENGTH));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getColor());
		g.fillRect(0, 0, SIDE_LENGTH, SIDE_LENGTH);
		
		if(spot.isOccupied()) {
			String iconFile = spot.getPiece().getIconFile();
			try {
			    BufferedImage img = ImageIO.read(new File(iconFile));
			    int left = (getWidth() - img.getWidth()) / 2;
			    int right = left + img.getWidth();
			    int top = (getHeight() - img.getHeight()) / 2;
			    int bottom = top + img.getHeight();
			    
			    g.drawImage(img, left, top, right, bottom, 0, 0, img.getWidth(), img.getHeight(), null);
			    System.out.println("Drew image: " + iconFile);
			} catch (IOException e) {
				System.err.println("COULD NOT READ FILE: " + iconFile);
			}
		}
	}
	
	/**
	 * 
	 * @returns The display color of this SpotPanel
	 */
	public Color getColor() {
		if(spot.getColor() == Chess.Color.BLACK)
			return BLACK;
		else
			return WHITE;
	}
	
}
