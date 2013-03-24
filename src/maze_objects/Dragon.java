package maze_objects;

import game.logic.Game;

import java.util.Random;


public class Dragon extends Movable implements java.io.Serializable {

	private static final long serialVersionUID = 7619209920273918432L;
	
	/*** Private Attributes ***/

	private int type;
	private boolean hasSword;
	private int dragonSleepTurns;
	private Random randomMove;
	private Random randomSleep;

	/*** Public Attributes ***/

	public static final int POSSIBLE_MOVES = 5; //This tells the number of moves a dragon may take.
	                                            //At the moment, 0 does not move, 1 move down, 2 move up, 3 move right, 4 move left

	public static final int CHANCE_TO_SLEEP = 15; //Dragon has a 1/CHANCE_TO_SLEEP probability of sleeping
	public static final int MAX_SLEEP_TURNS = 3;  //Maximum number of turns the dragon may sleep for

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
	
	private boolean validMoveTo(Game g, int new_row, int new_column) {
		return !g.getMaze().checkIfWall(new_row, new_column) 
				&& !g.isOnAliveDragon(new_row, new_column)
				&& !g.getMaze().checkIfExit(new_row, new_column);
	}

	/*** Public Method Area ***/

	//Constructors
	public Dragon(int dragon_row, int dragon_column, int dragon_type) {
		row = dragon_row;
		column = dragon_column;
		state = ALIVE;
		type = dragon_type;
		hasSword = false;
		randomMove = new Random();
		randomSleep = new Random();
	}
	
	public Dragon(int dragon_row, int dragon_column, int dragon_type, Random move, Random sleep) {
		row = dragon_row;
		column = dragon_column;
		state = ALIVE;
		type = dragon_type;
		hasSword = false;
		randomMove = move;
		randomSleep = sleep;
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
				if(randomSleep.nextInt(CHANCE_TO_SLEEP) == 1) {
					state = ASLEEP;
					
					//Sleeps from 1 to max_sleep_turns
					dragonSleepTurns = (randomSleep.nextInt(MAX_SLEEP_TURNS) + 1); 
					return;
				}
			}
			else if(state == ASLEEP) {
				dragonSleepTurns--;
				if(dragonSleepTurns == 0)
					state = ALIVE;
				return;
			}
		}

		int move = 0;
		int new_row = 0;
		int new_column = 0;
		
		do {
			move = randomMove.nextInt(POSSIBLE_MOVES);
			switch(move) {
			case 0: // stand still
				return;
			case 1: // down
				new_row = row + 1;
				new_column = column;
				break;
			case 2: // up
				new_row = row - 1;
				new_column = column;
				break;
			case 3: // right
				new_column = column + 1;
				new_row = row;
				break;
			case 4: // left
				new_column = column - 1;
				new_row = row;
				break;
			}

		} while(!validMoveTo(g, new_row, new_column));

		makeMove(g, new_row, new_column);
	}

}
