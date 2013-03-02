package maze_objects;

import game.logic.Game;
import game.ui.ExitEvent;
import game.ui.FightEvent;

public class Hero extends Movable {

	//Hero states
	public static final int ARMED = 1;
	public static final int DEAD = 2;
	public static final int EXITED_MAZE = 3;
	public static final int IN_GAME = 0;

	/*** Private Variable Area ***/

	private void armHero(int r, int c, Maze m) { //Changes hero symbol to the armed hero symbol as he takes the sword
		state = ARMED;
		//m.positions[row][column] = MazeSymbol.empty;
		//m.positions[r][c] = MazeSymbol.armedHero;
		row = r;
		column = c;
	}

	private void exitMaze(int r, int c, Maze m) { //Moves the hero to the exit position and toggles his state to exited_maze
		//m.positions[row][column] = MazeSymbol.empty;
		//m.positions[r][c] = MazeSymbol.armedHero;
		row = r;
		column = c;
		state = EXITED_MAZE;
	}

	private void makeMove(int r, int c, Maze m) { //Puts the hero symbol in the correct position after taking the move
		//m.positions[row][column] = MazeSymbol.empty;
		row = r;
		column = c;
		/*if(state == IN_GAME)
			m.positions[r][c] = MazeSymbol.hero;
		else if(state == ARMED)
			m.positions[r][c] = MazeSymbol.armedHero;*/
	}

	/*** Public methods ***/

	//Constructors
	public Hero() {
		row = 1;
		column = 1;
		state = IN_GAME;
	}

	public Hero(int r, int c) { //Create the hero with a predefined row and column
		row = r;
		column = c;
		state = IN_GAME;
	}

	//Game methods

	public boolean moveHero(int possibleRow, int possibleColumn, Game g) { //Possibly moves the hero to a new position, analyzing if it's able to and possible outcoming events
		Maze m = g.getMaze();

		if(m.checkIfEmpty(possibleRow, possibleColumn) && !g.checkIfSword(possibleRow, possibleColumn) && !g.checkIfDragon(possibleRow, possibleColumn)) {
			makeMove(possibleRow, possibleColumn, m);
		}
		else if(m.checkIfExit(possibleRow, possibleColumn)) {
			if(state == ARMED) {
				if(g.getExitState() == Game.OPEN) {
					exitMaze(possibleRow, possibleColumn, m);
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
		else if(g.checkIfSword(possibleRow, possibleColumn) && !g.getSword().getTaken()) {
			armHero(possibleRow, possibleColumn, m);
			g.getSword().takeSword();
		}
		else if(g.checkIfDragon(possibleRow, possibleColumn) && g.getDragon().getState() == Dragon.ALIVE) {
			if(g.fightDragon()) {
				FightEvent fe = new FightEvent("wonFight");
				g.addEvent(fe);
			}
			else
				return false;
		}
		
		return true;
	}

}
