package game.ui.gui;

import game.objects.Movable;
import game.objects.Tile;

public class MazeObjectToDraw {
	
	boolean isMovable;
	Tile tile;
	Movable movable;
	
	public MazeObjectToDraw() {
		set(Tile.empty);
	}
	
	public void setMovable() {isMovable = true;}
	public void setTile() {isMovable = false;}
	
	public void set(Tile tile) {
		this.tile = tile;
		setTile();
	}
	
	public void set(Movable movable) {
		this.movable = movable;
		setMovable();
	}

}
