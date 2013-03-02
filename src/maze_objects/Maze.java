package maze_objects;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

public class Maze {

	/*** Private Attributes ***/

	//Parameters for default maze 
	private final int DEFAULT_ROW_SIZE = 10;
	private final int DEFAULT_COLUMN_SIZE = 10;

	//Maze attributes
	private int rows;
	private int columns;

	//Maze positions
	char[][] positions = new char[DEFAULT_ROW_SIZE][DEFAULT_COLUMN_SIZE];

	/*** Private Methods ***/

	//Maze Generators
	private void startPredefinedMaze(){ //Starts the predefined maze

		for (int row = 0; row < DEFAULT_ROW_SIZE; row++) {
			for (int column = 0; column < DEFAULT_COLUMN_SIZE; column++) {
				if ( row == 0 || row == DEFAULT_ROW_SIZE-1 || column == 0 || column == DEFAULT_COLUMN_SIZE-1){
					positions[row][column] = MazeSymbol.wall;
				}
				else if((row == 2 || row == 3 || row == 4 || row == 6 || row == 7 || row == 8) &&
						(column == 2 || column == 3 || column == 5 || column == 7)){
					positions[row][column] = MazeSymbol.wall;
				}
				else {
					positions[row][column] = MazeSymbol.empty;
				}
			}
		}

		positions[1][2] = MazeSymbol.empty;
		positions[1][3] = MazeSymbol.empty;
		positions[1][5] = MazeSymbol.empty;
		positions[1][7] = MazeSymbol.empty;
		positions[5][2] = MazeSymbol.empty;
		positions[5][3] = MazeSymbol.empty;
		positions[5][5] = MazeSymbol.empty;
		positions[5][7] = MazeSymbol.wall;
		positions[8][5] = MazeSymbol.empty;
		positions[8][7] = MazeSymbol.empty;
		positions[5][9] = MazeSymbol.exit;

	}

	private char[][] generateMaze(int rows, int cols){	
		// fill the maze with walls
		char[][] maze = new char [rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				maze[i][j] = MazeSymbol.wall;
			}
		}

		// create a CellStack (LIFO) to hold a list of cell locations
		Stack<Cell> cellStack = new Stack<Cell>();

		//choose random starting odd cell, call it currentCell
		Cell currentCell = new Cell();
		Random r = new Random();
		currentCell.i = r.nextInt(cols-2)+1; // 1..cols-2, can't be a wall
		currentCell.j = r.nextInt(rows-2)+1;

		maze[currentCell.i][currentCell.j] = MazeSymbol.empty;
		Vector<Cell> nearbyCells = new Vector<Cell>();

		while(true){
			// find all 4 neighbors of CurrentCell with all walls intact 
			nearbyCells.clear();
			// top									// A neighbour is valid if
			if(	currentCell.i-1 > 0 && currentCell.i-1 < rows // Not out of bounds
					&& maze[currentCell.i-1][currentCell.j] == MazeSymbol.wall // Is a wall
					&& checkWalls(currentCell.i-1, currentCell.j, maze) == 3  // Is surrounded by walls
					&& maze[currentCell.i-2][currentCell.j-1] != MazeSymbol.empty // Diagonals don't have
					&& maze[currentCell.i-2][currentCell.j+1] != MazeSymbol.empty )  //      empty spaces
			{
				nearbyCells.add(new Cell(currentCell.i-1, currentCell.j));
			}

			// left
			if(currentCell.j-1 > 0 && currentCell.j-1 < cols 
					&& maze[currentCell.i][currentCell.j-1] == MazeSymbol.wall 
					&& checkWalls(currentCell.i, currentCell.j-1, maze) == 3
					&& maze[currentCell.i-1][currentCell.j-2] != MazeSymbol.empty 
					&& maze[currentCell.i+1][currentCell.j-2] != MazeSymbol.empty ) 
			{
				nearbyCells.add(new Cell(currentCell.i, currentCell.j-1));
			}


			// down
			if(currentCell.i+1 > 0 && currentCell.i+1 < rows 
					&& maze[currentCell.i+1][currentCell.j] == MazeSymbol.wall
					&& checkWalls(currentCell.i+1, currentCell.j, maze) == 3
					&& maze[currentCell.i+2][currentCell.j-1] != MazeSymbol.empty 
					&& maze[currentCell.i+2][currentCell.j+1] != MazeSymbol.empty ) 
			{
				nearbyCells.add(new Cell(currentCell.i+1, currentCell.j));
			}
			// right
			if(currentCell.j+1 > 0 && currentCell.j+1 < cols 
					&& maze[currentCell.i][currentCell.j+1] == MazeSymbol.wall 
					&& checkWalls(currentCell.i, currentCell.j+1, maze) == 3
					&& maze[currentCell.i-1][currentCell.j+2] != MazeSymbol.empty 
					&& maze[currentCell.i+1][currentCell.j+2] != MazeSymbol.empty) 
			{
				nearbyCells.add(new Cell(currentCell.i, currentCell.j+1));
			}


			//if one or more found 
			if (nearbyCells.size() > 0){
				// choose one at random  
				int selected = r.nextInt(nearbyCells.size());
				Cell next = nearbyCells.elementAt(selected);
				maze[next.i][next.j] = MazeSymbol.empty;
				// push CurrentCell location on the CellStack
				cellStack.push(next);
				// make the new cell CurrentCell 
				currentCell = next;
			} else {
				if (cellStack.empty()){
					break;
				}
				currentCell = cellStack.pop();
			}

		}

		// generate an exit
		boolean done = false;
		Cell exitCell = new Cell();
		while (!done){
			int side = r.nextInt(4);
			switch (side) {
			case 0: // left side exit
				exitCell.j = 0;
				exitCell.i = r.nextInt(rows-2)+1;
				if(maze[exitCell.i][exitCell.j+1] != MazeSymbol.wall){
					done = true;
				}
				break;
			case 1: // right side exit
				exitCell.j = cols-1;
				exitCell.i = r.nextInt(rows-2)+1;
				if(maze[exitCell.i][exitCell.j-1] != MazeSymbol.wall){
					done = true;
				}
				break;
			case 2: // top side exit
				exitCell.i = 0;
				exitCell.j = r.nextInt(cols-2)+1;
				if(maze[exitCell.i+1][exitCell.j] != MazeSymbol.wall){
					done = true;
				}
				break;
			case 3: // down side exit
				exitCell.i = rows-1;
				exitCell.j = r.nextInt(rows-2)+1;
				if(maze[exitCell.i-1][exitCell.j] != MazeSymbol.wall){
					done = true;
				}
				break;

			default:
				break;
			}
		}
		maze[exitCell.i][exitCell.j] = MazeSymbol.exit;

		return maze;
	}

	/*** Public Methods ***/

	//Constructors
	public Maze() {
		rows = DEFAULT_ROW_SIZE;
		columns = DEFAULT_COLUMN_SIZE;

		startPredefinedMaze();
	}

	public Maze(int numberOfRows, int numberOfColumns) {
		rows = numberOfRows;
		columns = numberOfColumns;
		positions = generateMaze(rows, columns);
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

	static public int checkWalls(int i, int j, char[][] map){
		int numberWalls = 0;
		if( (i-1) >= 0 && map[i-1][j] == MazeSymbol.wall)
			numberWalls++;
		if( (i+1) < map.length && map[i+1][j] == MazeSymbol.wall)
			numberWalls++;
		if( (j+1) < map[0].length && map[i][j+1] == MazeSymbol.wall)
			numberWalls++;
		if( (j-1) >= 0 && map[i][j-1] == MazeSymbol.wall)
			numberWalls++;
		return numberWalls;
	}
}

class Cell {
	public int i;
	public int j;

	public Cell(){
		i = 0;
		j = 0;
	}

	public Cell(int row, int col){
		i = row;
		j = col;
	}
}
