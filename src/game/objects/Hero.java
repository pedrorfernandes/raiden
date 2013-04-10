package game.objects;

import game.logic.Game;
import game.maze.Maze;
import game.ui.ExitEvent;

/**
 * The Hero class represents the main character that the player controls.
 * It has 4 states: starts alive, is armed after catching the sword or
 * is dead after an enemy catches him. If the hero slays all the enemies
 * and exits the maze, the final state is reached.
 */
public class Hero extends Movable implements java.io.Serializable {

	private static final long serialVersionUID = -5953402713235051361L;

	//Hero states
	public static final int ARMED = 1;
	public static final int DEAD = 2;
	public static final int EXITED_MAZE = 3;
	public static final int ALIVE = 0;

	/*** Private Variable Area ***/

	private void exitMaze(int r, int c) { //Moves the hero to the exit position and toggles his state to exited_maze
		row = r;
		column = c;
		state = EXITED_MAZE;
	}

	private void makeMove(int r, int c) { //Puts the hero symbol in the correct position after taking the move
		row = r;
		column = c;
	}

	/*** Public methods ***/
	
	/**
	 * Changes hero state to the armed state as he takes the sword
	 * @param r The row where the sword is
	 * @param c The column where the sword is
	 */
	public void armHero(int r, int c) {
		state = ARMED;
		row = r;
		column = c;
	}

	//Constructors
	public Hero() {
		row = 1;
		column = 1;
		state = ALIVE;
	}

	/**
	 * Create the hero with a predefined row and column
	 * @param r The predefined row
	 * @param c The predefined column
	 */
	public Hero(int r, int c) { 
		row = r;
		column = c;
		state = ALIVE;
	}

	//Game methods

	/**
	 * Possibly moves the hero to a new position, analyzing if it's able to and possible outcoming events
	 * @param rowMovement Direction to move in rows
	 * @param columnMovement Direction to move in columns
	 * @param g The game to move the hero in
	 * @return
	 */
	public boolean moveHero(int rowMovement, int columnMovement, Game g) { 
		Maze m = g.getMaze();

		int newRow = row + rowMovement;
		int newColumn = column + columnMovement;

		if(m.checkIfEmpty(newRow, newColumn) && !g.checkIfSword(newRow, newColumn) && !g.checkIfOnAliveDragon(newRow, newColumn)) {
			makeMove(newRow, newColumn);
		}
		else if(m.checkIfExit(newRow, newColumn)) {
			if(state == ARMED) {
				if(g.getExitState() == Game.EXIT_OPEN) {
					exitMaze(newRow, newColumn);
					return false;
				}
				else {
					ExitEvent ee = new ExitEvent(1);
					g.addEvent(ee);
				}
			}
			else {
				ExitEvent ee = new ExitEvent(0);
				g.addEvent(ee);
			}
		}
		else if(g.checkIfSword(newRow, newColumn) && !g.getSword().isTaken() && !g.getEagle().isOnGroundWithSword()) {
			armHero(newRow, newColumn);
			g.getSword().takeSword();
		}

		return true;
	}

}
