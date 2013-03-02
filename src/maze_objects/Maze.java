package maze_objects;

public class Maze {

	/*** Private Attributes ***/

	//Maze attributes
	private int rows;
	private int columns;

	//Maze positions
	char[][] positions;

	/*** Public Methods ***/

	//Constructors

	public Maze(int numberOfRows, int numberOfColumns) {
		rows = numberOfRows;
		columns = numberOfColumns;
		positions = new char[numberOfRows][numberOfColumns];
	}
	

	//General Methods
	public char[][] getPositions() {
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
		if(positions[row][column] == MazeSymbol.wall)
			return true;
		else
			return false;
	}

	public boolean checkIfExit(int row, int column) {
		if(positions[row][column] == MazeSymbol.exit)
			return true;
		else
			return false;
	}

	public boolean checkIfEmpty(int row, int column) {
		if(positions[row][column] == MazeSymbol.empty)
			return true;
		else
			return false;
	}
}
