package maze_objects;

import java.util.Vector;
import java.util.Stack;
import java.util.Random;

public class RandomMaze extends MazeBuilder {

	public void buildMaze(int rows, int cols){ 
		createNewMaze(rows, cols);

		// fill the maze with walls
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				maze.positions[i][j] = MazeSymbol.wall;
			}
		}

		// create a CellStack (LIFO) to hold a list of cell locations
		Stack<Cell> cellStack = new Stack<Cell>();

		//choose random starting odd cell, call it currentCell
		Cell currentCell = new Cell();		
		Random r = new Random();
		currentCell.i = r.nextInt(cols-2)+1; // 1..cols-2, can't be a wall
		currentCell.j = r.nextInt(rows-2)+1;

		cellStack.push(currentCell);

		maze.positions[currentCell.i][currentCell.j] = MazeSymbol.empty;
		Vector<Cell> nearbyCells = new Vector<Cell>();

		while(true){
			// find all 4 neighbors of CurrentCell with all walls intact 
			nearbyCells.clear();
			nearbyCells = checkPossibilities(currentCell, rows, cols);

			//if one or more found 
			if (nearbyCells.size() > 0){
				// choose one at random  
				int selected = r.nextInt(nearbyCells.size());
				Cell next = nearbyCells.elementAt(selected);
				maze.positions[next.i][next.j] = MazeSymbol.empty;
				// push CurrentCell location on the CellStack
				cellStack.push(next);
				// make the new cell CurrentCell 
				currentCell = next;
			} else {
				if (cellStack.empty()){
					break;
				}
				currentCell = cellStack.peek();
				if ( (checkPossibilities(cellStack.peek(), rows, cols)).size() == 0){
					cellStack.pop();
				}
			}
		}

		generateNewPaths(rows, cols);
		
		generateExit(rows, cols);

	}

	private int checkWalls(int i, int j){
		int numberWalls = 0;
		if( (i-1) >= 0 && maze.positions[i-1][j] == MazeSymbol.wall)
			numberWalls++;
		if( (i+1) < maze.positions.length && maze.positions[i+1][j] == MazeSymbol.wall)
			numberWalls++;
		if( (j+1) < maze.positions[0].length && maze.positions[i][j+1] == MazeSymbol.wall)
			numberWalls++;
		if( (j-1) >= 0 && maze.positions[i][j-1] == MazeSymbol.wall)
			numberWalls++;
		return numberWalls;
	}

	private Vector<Cell> checkPossibilities(Cell currentCell, int rows, int cols){
		Vector<Cell> nearbyCells = new Vector<Cell>();

		// top									// A neighbour is valid if
		if(	currentCell.i-1 > 0 && currentCell.i-1 < rows // Not out of bounds
				&& maze.positions[currentCell.i-1][currentCell.j] == MazeSymbol.wall // Is a wall
				&& checkWalls(currentCell.i-1, currentCell.j) == 3  // Is surrounded by walls
				&& maze.positions[currentCell.i-2][currentCell.j-1] != MazeSymbol.empty // Diagonals don't have
				&& maze.positions[currentCell.i-2][currentCell.j+1] != MazeSymbol.empty //      empty spaces
				&& !(maze.positions[currentCell.i][currentCell.j+1] == MazeSymbol.empty && maze.positions[currentCell.i+1][currentCell.j+1] == MazeSymbol.empty)
				&& !(maze.positions[currentCell.i][currentCell.j-1] == MazeSymbol.empty && maze.positions[currentCell.i+1][currentCell.j-1] == MazeSymbol.empty)  
				) 
		{
			nearbyCells.add(new Cell(currentCell.i-1, currentCell.j));
		}

		// left
		if(currentCell.j-1 > 0 && currentCell.j-1 < cols 
				&& maze.positions[currentCell.i][currentCell.j-1] == MazeSymbol.wall 
				&& checkWalls(currentCell.i, currentCell.j-1) == 3
				&& maze.positions[currentCell.i-1][currentCell.j-2] != MazeSymbol.empty 
				&& maze.positions[currentCell.i+1][currentCell.j-2] != MazeSymbol.empty 
				&& !(maze.positions[currentCell.i+1][currentCell.j] == MazeSymbol.empty && maze.positions[currentCell.i+1][currentCell.j+1] == MazeSymbol.empty)
				&& !(maze.positions[currentCell.i-1][currentCell.j] == MazeSymbol.empty && maze.positions[currentCell.i-1][currentCell.j+1] == MazeSymbol.empty)
				) 
		{
			nearbyCells.add(new Cell(currentCell.i, currentCell.j-1));
		}


		// down
		if(currentCell.i+1 > 0 && currentCell.i+1 < rows 
				&& maze.positions[currentCell.i+1][currentCell.j] == MazeSymbol.wall
				&& checkWalls(currentCell.i+1, currentCell.j) == 3
				&& maze.positions[currentCell.i+2][currentCell.j-1] != MazeSymbol.empty 
				&& maze.positions[currentCell.i+2][currentCell.j+1] != MazeSymbol.empty 
				&& !(maze.positions[currentCell.i][currentCell.j+1] == MazeSymbol.empty && maze.positions[currentCell.i-1][currentCell.j+1] == MazeSymbol.empty)
				&& !(maze.positions[currentCell.i][currentCell.j-1] == MazeSymbol.empty && maze.positions[currentCell.i-1][currentCell.j-1] == MazeSymbol.empty)  
				) 
		{
			nearbyCells.add(new Cell(currentCell.i+1, currentCell.j));
		}
		// right
		if(currentCell.j+1 > 0 && currentCell.j+1 < cols 
				&& maze.positions[currentCell.i][currentCell.j+1] == MazeSymbol.wall 
				&& checkWalls(currentCell.i, currentCell.j+1) == 3
				&& maze.positions[currentCell.i-1][currentCell.j+2] != MazeSymbol.empty 
				&& maze.positions[currentCell.i+1][currentCell.j+2] != MazeSymbol.empty
				&& !(maze.positions[currentCell.i+1][currentCell.j] == MazeSymbol.empty && maze.positions[currentCell.i+1][currentCell.j-1] == MazeSymbol.empty)
				&& !(maze.positions[currentCell.i-1][currentCell.j] == MazeSymbol.empty && maze.positions[currentCell.i-1][currentCell.j-1] == MazeSymbol.empty)  
				) 
		{
			nearbyCells.add(new Cell(currentCell.i, currentCell.j+1));
		}
		return nearbyCells;
	}

	private void generateExit(int rows, int cols){
		boolean done = false;
		Cell exitCell = new Cell();
		Random r = new Random();

		while (!done){
			int side = r.nextInt(4);
			switch (side) {
			case 0: // left side exit
				exitCell.j = 0;
				exitCell.i = r.nextInt(rows-2)+1;
				if(maze.positions[exitCell.i][exitCell.j+1] != MazeSymbol.wall){
					done = true;
				}
				break;
			case 1: // right side exit
				exitCell.j = cols-1;
				exitCell.i = r.nextInt(rows-2)+1;
				if(maze.positions[exitCell.i][exitCell.j-1] != MazeSymbol.wall){
					done = true;
				}
				break;
			case 2: // top side exit
				exitCell.i = 0;
				exitCell.j = r.nextInt(cols-2)+1;
				if(maze.positions[exitCell.i+1][exitCell.j] != MazeSymbol.wall){
					done = true;
				}
				break;
			case 3: // down side exit
				exitCell.i = rows-1;
				exitCell.j = r.nextInt(rows-2)+1;
				if(maze.positions[exitCell.i-1][exitCell.j] != MazeSymbol.wall){
					done = true;
				}
				break;

			default:
				break;
			}
		}
		maze.positions[exitCell.i][exitCell.j] = MazeSymbol.exit;
	}

	private void generateNewPaths(int rows, int cols){
		int newPaths = 2;//r.nextInt(5);

		for (int i = 2; i < rows-2; i++) {
			for (int j = 1; j < cols-1; j++) {
				if (maze.positions[i][j] == MazeSymbol.wall
						&& maze.positions[i][j-1] == MazeSymbol.empty
						&& maze.positions[i][j+1] == MazeSymbol.empty
						&& maze.positions[i-1][j] == MazeSymbol.wall
						&& maze.positions[i+1][j] == MazeSymbol.wall){
					if (newPaths > 0){
						//maze.positions[i][j] = '.';
						maze.positions[i][j] = MazeSymbol.empty;
						newPaths--;
						break;
					} else {
						break;
					}
				}
			}
			if (newPaths == 0){
				break;
			}
		}

		newPaths = 2;

		for (int i = 1; i < rows-1; i++) {
			for (int j = 2; j < cols-2; j++) {
				if (maze.positions[i][j] == MazeSymbol.wall
						&& maze.positions[i][j-1] == MazeSymbol.wall
						&& maze.positions[i][j+1] == MazeSymbol.wall
						&& maze.positions[i-1][j] == MazeSymbol.empty
						&& maze.positions[i+1][j] == MazeSymbol.empty){
					if (newPaths > 0){
						//maze.positions[i][j] = '.';
						maze.positions[i][j] = MazeSymbol.empty;
						newPaths--;
						break;
					} else {
						break;
					}
				}
			}
			if (newPaths == 0){
				break;
			}
		}
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
