package maze_objects;

public class Movable {

	/*** Private/Protected Attributes ***/

	//State Attributes
	protected int row;
	protected int column;
	protected int state;

	/*** Public Methods ***/

	//General Methods
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getState() {
		return state;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public void setColumn(int c) {
		column = c;
	}

	public void setState(int st) {
		state = st;
	}
}
