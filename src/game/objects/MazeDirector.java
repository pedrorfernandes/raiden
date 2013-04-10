package game.objects;

/**
 * A maze director is in charge of selecting a
 * building pattern and to order its construction
 */
public class MazeDirector {
	private MazeBuilder mazeBuilder;
	
	public void setMazeBuilder(MazeBuilder mb) { mazeBuilder = mb; }
	public Maze getMaze() { return mazeBuilder.getMaze(); }
	
	public void constructMaze(int rows, int cols) {
		mazeBuilder.buildMaze(rows, cols);
	}

}
