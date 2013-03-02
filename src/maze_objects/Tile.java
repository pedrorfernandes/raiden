package maze_objects;

public abstract class Tile {
	
	/*** Private/Protected Attributes ***/
	
	protected String type; //A tile may have different types that affect how they are printed and how the objects interact on it
	                       //Currently, we have Empty, Wall and Exit tiles
	
	/*** Public Methods ***/
	
	//General methods
	public String getType() {
		return type;
	}
	
	public void setType(String t) {
		type = t;
	}
}
