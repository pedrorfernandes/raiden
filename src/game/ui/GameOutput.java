package game.ui;


import game.logic.Game;

import java.util.ArrayList;
import java.util.LinkedList;

import maze_objects.Dragon;
import maze_objects.Eagle;
import maze_objects.Hero;
import maze_objects.MazeSymbol;
import maze_objects.Maze;
import maze_objects.Sword;
import maze_objects.Tile;

public class GameOutput {

	private static char[][] getMazeSymbols(Maze m) { //Returns an array with the symbols of the corresponding maze tiles
		char[][] mazePositions = new char[m.getRows()][m.getColumns()];

		for(int r = 0; r < m.getRows(); r++)
			for(int c = 0; c < m.getColumns(); c++) {
				Tile currentTile = m.getPositions()[r][c];
				switch(currentTile) {
				case wall:
					mazePositions[r][c] = MazeSymbol.wall;
					break;
				case empty:
					mazePositions[r][c] = MazeSymbol.empty;
					break;
				case exit:
					mazePositions[r][c] = MazeSymbol.exit;
					break;
				}
			}
		return mazePositions;
	}

	public static void printMaze(Maze m) {

		char[][] mazePositions = getMazeSymbols(m);

		for(int i = 0; i < 100; i++)
			System.out.println();
		for (int x = 0; x < m.getRows(); x++) {
			for (int y = 0; y < m.getColumns(); y++) {
				System.out.print(mazePositions[x][y]);
				System.out.print(MazeSymbol.space);
			}
			System.out.print('\n');
		}
	}

	public static void printGame(Game g) {

		Maze m = g.getMaze();
		Hero h = g.getHero();
		ArrayList<Dragon> d = g.getDragons();
		Sword s = g.getSword();
		Eagle e = g.getEagle();

		char[][] mazePositions = getMazeSymbols(m);

		if(h.getState() == Hero.ARMED || h.getState() == Hero.EXITED_MAZE)
			mazePositions[h.getRow()][h.getColumn()] = MazeSymbol.armedHero;
		else
			mazePositions[h.getRow()][h.getColumn()] = MazeSymbol.hero;

		for(int i = 0; i < g.getNumberOfDragons(); i++) {
			if(d.get(i).getState() == Dragon.ALIVE && d.get(i).getHasSword())
				mazePositions[d.get(i).getRow()][d.get(i).getColumn()] = MazeSymbol.guardedSword;
			else if(d.get(i).getState() == Dragon.ALIVE && !d.get(i).getHasSword())
				mazePositions[d.get(i).getRow()][d.get(i).getColumn()] = MazeSymbol.dragon;
			else if(d.get(i).getState () == Dragon.ASLEEP && d.get(i).getHasSword())
				mazePositions[d.get(i).getRow()][d.get(i).getColumn()] = MazeSymbol.sleepingGuardedSword;
			else if(d.get(i).getState() == Dragon.ASLEEP && !d.get(i).getHasSword())
				mazePositions[d.get(i).getRow()][d.get(i).getColumn()] = MazeSymbol.sleepingDragon;

			if(!s.isTaken())
				mazePositions[s.getRow()][s.getColumn()] = MazeSymbol.sword;
		}

		// eagle printing goes here
		if ( !e.isWithHero() && e.getState() != Eagle.DEAD){
			switch (mazePositions[ e.getRow() ][e.getColumn() ] ) {
			case MazeSymbol.hero:
				mazePositions[ e.getRow() ][e.getColumn() ] = MazeSymbol.eagleOnHero;
				break;
			case MazeSymbol.dragon:
				mazePositions[ e.getRow() ][e.getColumn() ] = MazeSymbol.eagleOnDragon;
				break;
			case MazeSymbol.wall:
				mazePositions[ e.getRow() ][e.getColumn() ] = MazeSymbol.eagleOnWall;
				break;
			case MazeSymbol.empty:
				mazePositions[ e.getRow() ][e.getColumn() ] = MazeSymbol.eagle;
				break;
			case MazeSymbol.sleepingDragon:
				mazePositions[ e.getRow() ][e.getColumn() ] = MazeSymbol.eagleOnSleepingDragon;
				break;
			case MazeSymbol.sword:
				mazePositions[ e.getRow() ][e.getColumn() ] = MazeSymbol.eagleWithSword;
				break;
			default:
				break;
			}
		}

		for(int i = 0; i < 100; i++)
			System.out.println();
		for (int x = 0; x < m.getRows(); x++) {
			for (int y = 0; y < m.getColumns(); y++) {
				System.out.print(mazePositions[x][y]);
				System.out.print(MazeSymbol.space);
			}
			System.out.print('\n');
		}

	}

	public static void printAskForMove() {
		System.out.print("Move your hero (WASD, only first input will be considered): ");
	}

	public static void printEventQueue(LinkedList<GameEvent> events) { //Prints the events on the event queue given
		while(!events.isEmpty())
			printEvent(events.removeFirst());
	}

	public static void printEvent(GameEvent ev) { //Prints the message field of the GameEvent given
		System.out.println(ev.getMessage());
	}

	public static void printStartMessage() { //Prints a welcome message
		System.out.println("Welcome, challenger.");
	}

	public static void printOptions(int n) { //Asks player for the maze options wanted. n = 0 to ask for maze type, n = 1 to ask for rows, n = 2 to ask for columns

		switch(n) {
		case 0:
			System.out.print("Give a specific maze size? (Y/N): ");
			break;
		case 1:
			System.out.println("Please specify the desired maze size.");
			System.out.print("Number of rows? ");
			break;
		case 2:
			System.out.print("Number of columns? ");
			break;
		}

	}

	public static void printMazeSizeError() {
		System.out.println("Invalid maze size given. Starting predefined maze!");
	}

	public static void printDragonOptions() { //Prints dragon type related options
		System.out.println("\nPlease specify the type of dragons you want on the maze.");
		System.out.println("0 for static dragons, 1 for an always awake dragons, 2 for randomly sleeping dragons.");
		System.out.print("Option: ");
	}

	public static void printMultipleDragonOptions() {
		System.out.println("\nWould you like to generate a number of dragons proportional to the size of the maze?");
		System.out.print("Otherwise, only one will be generated. (Y/N): ");
	}
}
