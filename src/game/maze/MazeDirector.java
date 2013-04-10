package game.maze;

// TODO: Auto-generated Javadoc
/**
 * A maze director is in charge of selecting a
 * building pattern and to order its construction.
 */
public class MazeDirector {
	
	/** The maze builder. */
	private MazeBuilder mazeBuilder;
	
	/**
	 * Sets the maze builder.
	 *
	 * @param mb the new maze builder
	 */
	public void setMazeBuilder(MazeBuilder mb) { mazeBuilder = mb; }
	
	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public Maze getMaze() { return mazeBuilder.getMaze(); }
	
	/**
	 * Construct maze.
	 *
	 * @param rows the rows
	 * @param cols the cols
	 */
	public void constructMaze(int rows, int cols) {
		mazeBuilder.buildMaze(rows, cols);
	}

}
