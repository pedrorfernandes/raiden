package game.objects;

/**
 * The sword that the hero must pick up to win the game
 */
public class Sword extends Movable implements java.io.Serializable {
	
	private static final long serialVersionUID = 6107957989260469574L;
	
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
	public boolean isTaken() {
		return taken;
	}
	
	//Game methods
	public void takeSword() {
		taken = true;
	}
	
	public void dropSword() {
		taken = false;
	}
	
	public void dropSword(int r, int c) {
		taken = false;
		row = r;
		column = c;
	}

}
