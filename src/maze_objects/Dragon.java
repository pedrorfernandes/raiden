package maze_objects;

import game.logic.Game;

import java.util.Random;


public class Dragon extends Movable {
	
	/*** Private Attributes ***/
	
	private boolean hasSword;

	/*** Public Attributes ***/

	//Dragon States
	public static final int DEAD = 0;
	public static final int ALIVE = 1;

	/*** Private Method Area ***/

	private void makeMove(Game g, int new_row, int new_column) {
		
		/*if(m.positions[row][column] == MazeSymbol.guardedSword)
			m.positions[row][column] = MazeSymbol.sword;
		else
			m.positions[row][column] = MazeSymbol.empty;*/

		row = new_row;
		column = new_column;
		
		if(!g.checkIfSword(row, column))
			hasSword = false;
		else
			hasSword = true;

		/*if(m.positions[row][column] == MazeSymbol.sword)
			m.positions[row][column] = MazeSymbol.guardedSword;
		else
			m.positions[row][column] = MazeSymbol.dragon;*/
	}

	/*** Public Method Area ***/

	//Constructors
	public Dragon(int dragon_row, int dragon_column) {
		row = dragon_row;
		column = dragon_column;
		state = ALIVE;
		hasSword = false;
	}
	
	//General methods
	public boolean getHasSword() {
		return hasSword;
	}

	//Game methods
	public void moveDragon(Game g) {
		
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

		} while(g.getMaze().checkIfWall(new_row, new_column) || g.getMaze().checkIfExit(new_row, new_column) || new_row == 0 || new_column == 0);

		makeMove(g, new_row, new_column);
	}


}
