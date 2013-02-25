package maze_objects;

public class Hero {

	//Hero states
	public static final int ARMED = 1;
	public static final int DEAD = 2;
	public static final int EXITED_MAZE = 3;
	public static final int IN_GAME = 0;

	/*** Private Variable Area ***/

	//Hero state variables
	private int hero_row;
	private int hero_column;
	private int hero_state;

	/*** Private Methods ***/

	/*private void fightDragon(int row, int column, Maze m) {
		if(hero_state == IN_GAME) {
			hero_state = DEAD;
			m.positions[hero_row][hero_column] = MazeSymbol.empty;
		}
		else if(hero_state == ARMED) {
			m.dragon_state = 1;
			m.positions[hero_row][hero_column] = MazeSymbol.empty;
			m.positions[row][column] = MazeSymbol.armedHero;
			hero_row = row;
			hero_column = column;
		}
	}*/

	private void armHero(int row, int column, Maze m) {
		hero_state = ARMED;
		m.positions[hero_row][hero_column] = MazeSymbol.empty;
		m.positions[row][column] = MazeSymbol.armedHero;
		hero_row = row;
		hero_column = column;
	}

	private void exitMaze(int row, int column, Maze m) {
		m.positions[hero_row][hero_column] = MazeSymbol.empty;
		m.positions[row][column] = MazeSymbol.armedHero;
		hero_state = EXITED_MAZE;
	}

	private void makeMove(int row, int column, Maze m) {
		m.positions[hero_row][hero_column] = MazeSymbol.empty;
		hero_row = row;
		hero_column = column;
		if(hero_state == IN_GAME)
			m.positions[row][column] = MazeSymbol.hero;
		else if(hero_state == ARMED)
			m.positions[row][column] = MazeSymbol.armedHero;
	}

	/*** Public methods ***/

	//Constructors
	Hero() {
		hero_row = 1;
		hero_column = 1;
		hero_state = IN_GAME;
	}

	Hero(int row, int column) {
		hero_row = row;
		hero_column = column;
		hero_state = IN_GAME;
	}

	//General methods
	public int getRow() {
		return hero_row;
	}
	public int getColumn() {
		return hero_column;
	}
	public int getState() {
		return hero_state;
	}

	public void setState(int st) {
		hero_state = st;
	}

	//Game methods
	public boolean moveHero(int row, int column, Maze m) {
		if(m.positions[row][column] == MazeSymbol.empty) {
			makeMove(row, column, m);
			return true;
		}
		else if(m.positions[row][column] == MazeSymbol.exit) {
			if(hero_state == ARMED) {
				if(m.getExitState() == Maze.OPEN) {
					exitMaze(row, column, m);
					return false;
				}
				else
					System.out.println("The exit is closed! You have to kill the dragon first!");
			}
			else
				System.out.println("You are not armed, it's dangerous to go alone like that!");
		}
		else if(m.positions[row][column] == MazeSymbol.sword) {
			armHero(row, column, m);
		}
		else if(m.positions[row][column] == MazeSymbol.dragon) {
			m.fightDragon();
		}
		return true;
	}

}
