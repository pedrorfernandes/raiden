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
	private static String PROMPT = "> ";

	public static char[][] getMazeSymbols(Maze m) { //Returns an array with the symbols of the corresponding maze tiles
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

		// hero printing
		if(h.getState() == Hero.ARMED || h.getState() == Hero.EXITED_MAZE)
			mazePositions[h.getRow()][h.getColumn()] = MazeSymbol.armedHero;
		else
			mazePositions[h.getRow()][h.getColumn()] = MazeSymbol.hero;

		// dragon printing
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

		// eagle printing
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

		for (int x = 0; x < m.getRows(); x++) {
			for (int y = 0; y < m.getColumns(); y++) {
				System.out.print(mazePositions[x][y]);
				System.out.print(MazeSymbol.space);
			}
			System.out.println();
		}
		System.out.println();

	}

	public static void printAskForMove() {
		System.out.print("Move your hero (WASD, only first input will be considered): ");
		//System.out.print(PROMPT);
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
			System.out.println("Give a specific maze size? (Y/N): ");
			System.out.print(PROMPT);
			break;
		case 1:
			System.out.println("Please specify the desired maze size.");
			System.out.println("Number of rows? ");
			System.out.print(PROMPT);
			break;
		case 2:
			System.out.println("Number of columns? ");
			System.out.print(PROMPT);
			break;
		}

	}

	public static void printMazeSizeError() {
		System.out.println("Invalid maze size given. Starting predefined maze!");
	}

	public static void printDragonOptions() { //Prints dragon type related options
		System.out.println("\nPlease specify the type of dragons you want on the maze.");
		System.out.println("0 for static dragons, 1 for an always awake dragons, 2 for randomly sleeping dragons.");
		System.out.println("Choose your option ");
		System.out.print(PROMPT);
	}

	public static void printMultipleDragonOptions() {
		System.out.println("\nWould you like to generate a number of dragons proportional to the size of the maze?");
		System.out.println("Otherwise, only one will be generated. (Y/N): ");
		System.out.print(PROMPT);
	}
	
	public static void clearScreen(){
		for(int i = 0; i < 100; i++)
			System.out.println();
	}
}
