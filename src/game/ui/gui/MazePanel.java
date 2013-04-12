package game.ui.gui;

import game.logic.Game;
import game.ui.GameOutput;
import game.ui.MazePictures;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This class, extending JPanel, serves the purpose of drawing the game on the main game window in the
 * Graphical user interface. It overrides the paintComponent method to use the printGame function of
 * the GameOutput class.
 */
public class MazePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/** The game to be painted on the panel */
	Game game;
	
	/** The pictures to be used on the drawing. */
	MazePictures pictures;

	/**
	 * Instantiates a new MazePanel with the given game variable, images to be used and dimensions
	 * for the panel.
	 *
	 * @param game the game variable
	 * @param pictures the image files
	 * @param preferred the preferred size for the JPanel
	 * @param maximum the maximum size for the JPanel
	 */
	public MazePanel(Game game, MazePictures pictures, Dimension preferred, Dimension maximum) {
		setBackground(null);
		setFocusable(true);
		setMaximumSize(maximum);
		setMinimumSize(GUInterface.getFormattedPreferredDimension(preferred, maximum));
		setPreferredSize(GUInterface.getFormattedPreferredDimension(preferred, maximum));

		this.game = game;
		this.pictures = pictures;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		GameOutput.printGame(game, g, pictures, size);
	}
}

