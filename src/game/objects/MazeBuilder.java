package game.objects;

/**
 * The mazebuilder abstract class
 * The derived classes must implement a building pattern for a maze
 */
abstract public class MazeBuilder {
	protected Maze maze;
	
	public Maze getMaze() { return maze; }
	
	public void createNewMaze(int rows, int cols) { maze = new Maze(rows, cols); }
	
	public abstract void buildMaze(int rows, int cols);
	
}