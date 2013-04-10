package game.maze;

/**
 * A maze director is in charge of selecting a
 * building pattern and to order its construction.
 */
public class MazeDirector {
	
	/** The maze builder. */
	private MazeBuilder mazeBuilder;
	
	public void setMazeBuilder(MazeBuilder mb) { mazeBuilder = mb; }
	
	public Maze getMaze() { return mazeBuilder.getMaze(); }
	
	/**
	 * Construct the maze.
	 *
	 * @param rows the maze number of rows
	 * @param cols the maze number of columns
	 */
	public void constructMaze(int rows, int cols) {
		mazeBuilder.buildMaze(rows, cols);
	}

}
