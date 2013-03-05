package game.ui;

public class GameOptions {
	public int rows;
	public int columns;

	public boolean randomMaze;

	public int dragonType;

	public boolean multipleDragons;

	private void askOptions() {

		int size[] = {rows, columns};

		//Get Maze options from user
		GameOutput.printStartMessage();
		randomMaze = GameInput.receiveMazeOptions(size);

		rows = size[0];
		columns = size[1];

		//Get Dragon options from user
		dragonType = GameInput.receiveDragonOptions();

		//Get Multiple dragon options
		multipleDragons = GameInput.receiveMultipleDragonOptions();

	}

	public GameOptions() {
		askOptions();
	}

	//Constructor for a random maze
	public GameOptions(int rows, int columns, int dragonType, boolean multipleDragons) { 

		this.rows = rows;
		this.columns = columns;
		randomMaze = true;

		this.dragonType = dragonType;

		this.multipleDragons = multipleDragons;
	}

	//Constructor for a predefined maze
	public GameOptions(int dragonType, boolean multipleDragons) {

		this.rows = 0;
		this.columns = 0;
		randomMaze = false;

		this.dragonType = dragonType;

		this.multipleDragons = multipleDragons;
	}


}
