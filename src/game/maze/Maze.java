package game.maze;

/**
 * A maze contains tiles: walls, empty spaces and exits.
 * The type of these tiles will determine where the
 * movable objects can go.
 */
public class Maze implements java.io.Serializable {

	private static final long serialVersionUID = -4816528368605071858L;

	/** The number of rows. */
	private int rows;
	
	/** The number of columns. */
	private int columns;

	/** The maze tiles. */
	Tile[][] positions;

	/**
	 * Instantiates a new maze with the number of rows and columns given
	 *
	 * @param numberOfRows the number of rows for the maze
	 * @param numberOfColumns the number of columns for the maze
	 */
	public Maze(int numberOfRows, int numberOfColumns) {
		rows = numberOfRows;
		columns = numberOfColumns;
		positions = new Tile[numberOfRows][numberOfColumns];
	}
	
	/**
	 * Instantiates a new maze with the number of rows and columns given, but with the option
	 * to create a maze constructed only with boundary walls
	 *
	 * @param numberOfRows the number of rows for the maze
	 * @param numberOfColumns the number of columns for the maze
	 * @param onlyWalls tells the constructor if it should construct only walls
	 */
	public Maze(int numberOfRows, int numberOfColumns, boolean onlyWalls) {
		this(numberOfRows, numberOfColumns);
		if(onlyWalls) {
			for(int i = 0; i < numberOfRows; i++)
				for(int j = 0; j < numberOfColumns; j++) {
					if(i == 0 || j == 0 || i == numberOfRows - 1 || j == numberOfColumns - 1)
						positions[i][j] = Tile.wall;
					else
						positions[i][j] = Tile.empty;
				}
		}
	}
	

	//General Methods
	public Tile[][] getPositions() {
		return positions;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	/**
	 * Check if there's a wall in the position given
	 *
	 * @param row the row to check
	 * @param column the column to check
	 * @return true, if it's a wall
	 */
	public boolean checkIfWall(int row, int column) {
		if(positions[row][column] == Tile.wall)
			return true;
		else
			return false;
	}

	/**
	 * Check if there's an exit in the position given
	 *
	 * @param row the row to check
	 * @param column the column to check
	 * @return true, if it's an exit
	 */
	public boolean checkIfExit(int row, int column) {
		if(positions[row][column] == Tile.exit)
			return true;
		else
			return false;
	}

	/**
	 * Check if there's an empty tile in the position given
	 *
	 * @param row the row to check
	 * @param column the column to check
	 * @return true, if it's an empty tile
	 */
	public boolean checkIfEmpty(int row, int column) {
		if(positions[row][column] == Tile.empty)
			return true;
		else
			return false;
	}
}
