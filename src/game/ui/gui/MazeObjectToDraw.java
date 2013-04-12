package game.ui.gui;

import game.maze.Tile;
import game.objects.Movable;

/**
 * The Class MazeObjectToDraw is an utilitary class to be used with MazeEditor.
 * It stores the current object to be drawn on screen in the MazeEditor panel, be it a Movable
 * or Tile object. This class implements methods to retrieve and handle those objects.
 */
public class MazeObjectToDraw {
	
	boolean isMovable;
	
	Tile tile;
	
	Movable movable;
	
	/**
	 * Instantiates a new MazeObjectToDraw, setting it to an empty tile, by default.
	 */
	public MazeObjectToDraw() {
		set(Tile.empty);
	}
	
	/**
	 * Sets the object to indicate it's a movable.
	 */
	public void setMovable() {isMovable = true;}
	
	/**
	 * Sets the object to indicate it's not a movable.
	 */
	public void setTile() {isMovable = false;}
	
	/**
	 * Sets the object to be drawn to a particular tile, changing the isMovable variable
	 * to false, indicating it's a tile and not a movable.
	 *
	 * @param tile the tile to be drawn
	 */
	public void set(Tile tile) {
		this.tile = tile;
		setTile();
	}
	
	/**
	 * Sets the object to be drawn to a particular movable, changing the isMovable variable
	 * to true, indicating it's a movable and not a tile.
	 *
	 * @param movable the movable to be drawn
	 */
	public void set(Movable movable) {
		this.movable = movable;
		setMovable();
	}

}
