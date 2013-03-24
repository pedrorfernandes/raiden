package maze_objects;

//Dynamic objects that move along the maze
abstract class Movable implements java.io.Serializable { 

	private static final long serialVersionUID = -113368199479679000L;
	
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
