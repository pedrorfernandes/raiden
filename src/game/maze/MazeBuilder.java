package game.maze;

/**
 * The mazebuilder abstract class
 * The derived classes must implement a building pattern for a maze.
 */
abstract public class MazeBuilder {
	
	protected Maze maze;
	
	public Maze getMaze() { return maze; }
	
	/**
	 * Creates a new maze with the given number of rows and columns
	 *
	 * @param rows the row number
	 * @param cols the column number
	 */
	public void createNewMaze(int rows, int cols) { maze = new Maze(rows, cols); }
	
	/**
	 * Builds the maze.
	 *
	 * @param rows the maze row number
	 * @param cols the maze column number
	 */
	public abstract void buildMaze(int rows, int cols);
	
}