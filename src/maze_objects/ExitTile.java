package maze_objects;

public class ExitTile extends Tile {
	
	//Exit state constants
	public static final int OPEN = 1;
	public static final int CLOSED = 0;
	
	/*** Private Attributes ***/
	
	//Exit State
	private int state;
	

	/*** Public Methods ***/
	//Constructors
	public ExitTile() {
		type = "exit";
		state = CLOSED;
	}
	
	//General Methods
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
	
}
