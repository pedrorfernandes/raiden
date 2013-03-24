package maze_objects;

public class Maze implements java.io.Serializable {

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
