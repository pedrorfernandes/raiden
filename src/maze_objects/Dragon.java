package maze_objects;

import game.logic.Game;

import java.util.Random;


public class Dragon extends Movable {

	/*** Private Attributes ***/

	private int type;
	private boolean hasSword;
	private int dragonSleepTurns;

	/*** Public Attributes ***/

	public static final int POSSIBLE_MOVES = 5; //This tells the number of moves a dragon may take.
	//At the moment, 0 does not move, 1 move down, 2 move up, 3 move right, 4 move left

	public static final int CHANCE_TO_SLEEP = 15; //Dragon has a 1/CHANCE_TO_SLEEP probability of sleeping
	public static final int MAX_SLEEP_TURNS = 3; //Maximum number of turns the dragon may sleep for

	//Dragon States
	public static final int DEAD = 0;
	public static final int ALIVE = 1;
	public static final int ASLEEP = 2;

	//Dragon Types
	public static final int STATIC = 0;
	public static final int NORMAL = 1;
	public static final int SLEEPING = 2;

	/*** Private Method Area ***/

	private void makeMove(Game g, int new_row, int new_column) {
		row = new_row;
		column = new_column;

		if(!g.checkIfSword(row, column)) {
			if(g.getSword().isTaken() && hasSword)
				g.getSword().dropSword();
			
			hasSword = false;
		}
		else {
			hasSword = true;
			g.getSword().takeSword();
		}
	}

	/*** Public Method Area ***/

	//Constructors
	public Dragon(int dragon_row, int dragon_column, int dragon_type) {
		row = dragon_row;
		column = dragon_column;
		state = ALIVE;
		type = dragon_type;
		hasSword = false;
	}

	//General methods
	public int getType() {
		return type;
	}

	public boolean getHasSword() {
		return hasSword;
	}

	//Game methods
	public void moveDragon(Game g) {

		if(type == STATIC)
			return;

		if(state == DEAD)
			return;

		if(type == SLEEPING) {
			if(state == ALIVE) {
				Random random = new Random();
				int n  = random.nextInt(CHANCE_TO_SLEEP);
				if(n == 1) {
					state = ASLEEP;
					dragonSleepTurns = (random.nextInt(MAX_SLEEP_TURNS) + 1); //Sleeps from 1 to max_sleep_turns
					return;
				}
			}
			else if(state == ASLEEP) {
				
				if(dragonSleepTurns == 0)
					state = ALIVE;
				else {
					dragonSleepTurns--;
				}

				return;
				
			}
		}

		Random random = new Random();
		int move = 0;
		int new_row = 0;
		int new_column = 0;
		do {
			move = random.nextInt(POSSIBLE_MOVES);

			switch(move) {
			case 0:
				new_row = row;
				new_column = column;
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

		} while(g.getMaze().checkIfWall(new_row, new_column) || g.isOnDragon(new_row, new_column) || g.getMaze().checkIfExit(new_row, new_column) || new_row == 0 || new_column == 0);

		makeMove(g, new_row, new_column);
	}

}
