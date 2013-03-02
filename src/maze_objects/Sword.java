package maze_objects;

public class Sword extends Movable {
	
	/*** Private Attributes ***/
	private boolean taken; //Identifies whether the sword has been caught already or not
	
	/*** Public Methods ***/
	
	//Constructors
	public Sword() {
		taken = false;
	}
	
	public Sword(int r, int c) {
		row = r;
		column = c;
		taken = false;
	}
	
	//General methods
	public boolean getTaken() {
		return taken;
	}
	
	//Game methods
	public void takeSword() {
		taken = true;
	}
	
	public void dropSword() {
		taken = false;
	}

}
