package maze_objects;

import java.util.Random;


public class Dragon {
	/*** Private Variable Area ***/

	//Dragon State Variables
	int row;
	int column;
	int state;

	/*** Public Variable Area ***/

	//Dragon States
	public static final int ALIVE = 1;
	public static final int DEAD = 0;

	/*** Private Method Area ***/

	private void makeMove(Maze m, int new_row, int new_column) {
		if(m.positions[row][column] == MazeSymbol.guardedSword)
			m.positions[row][column] = MazeSymbol.sword;
		else
			m.positions[row][column] = MazeSymbol.empty;

		row = new_row;
		column = new_column;

		if(m.positions[row][column] == MazeSymbol.sword)
			m.positions[row][column] = MazeSymbol.guardedSword;
		else
			m.positions[row][column] = MazeSymbol.dragon;
	}

	/*** Public Method Area ***/

	//Constructors
	Dragon(int dragon_row, int dragon_column) {
		row = dragon_row;
		column = dragon_column;
		state = ALIVE;
	}

	//General Methods
	int getRow() {
		return row;
	}

	int getColumn() {
		return column;
	}

	int getState() {
		return state;
	}
	
	void setState(int st) {
		state = st;
	}


	//Game methods
	void moveDragon(Maze m) {
		
		if(state == DEAD)
			return;
		
		Random random = new Random();
		int move = 0;
		int new_row = 0;
		int new_column = 0;
		do {
			move = random.nextInt(5);

			switch(move) {
			case 0:
				break;
			case 1:
				new_row = row + 1;
				new_column = column;
				break;
			case 2:
				new_row = row - 1;
				new_column = column;
				break;
			case 3:
				new_column = column + 1;
				new_row = row;
				break;
			case 4:
				new_column = column - 1;
				new_row = row;
				break;
			}

		} while(m.checkIfWall(new_row, new_column) || m.checkIfExit(new_row, new_column) || new_row == 0 || new_column == 0);

		makeMove(m, new_row, new_column);
	}


}
