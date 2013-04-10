package maze_objects;

/**
 * A maze contains tiles: walls, empty spaces and exits.
 * The type of these tiles will determine where the
 * movable objects can go.
 */
public class Maze implements java.io.Serializable {

	private static final long serialVersionUID = -4816528368605071858L;
	
	/*** Private Attributes ***/

	//Maze attributes
	private int rows;
	private int columns;

	//Maze positions
	Tile[][] positions;

	/*** Public Methods ***/

	//Constructors
	public Maze(int numberOfRows, int numberOfColumns) {
		rows = numberOfRows;
		columns = numberOfColumns;
		positions = new Tile[numberOfRows][numberOfColumns];
	}
	
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

	//Maze checking functions
	public boolean checkIfWall(int row, int column) {
		if(positions[row][column] == Tile.wall)
			return true;
		else
			return false;
	}

	public boolean checkIfExit(int row, int column) {
		if(positions[row][column] == Tile.exit)
			return true;
		else
			return false;
	}

	public boolean checkIfEmpty(int row, int column) {
		if(positions[row][column] == Tile.empty)
			return true;
		else
			return false;
	}
}
