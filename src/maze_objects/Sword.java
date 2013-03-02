package maze_objects;

public class Sword extends Movable {
	
	private boolean taken;
	
	public Sword() {
		taken = false;
	}
	
	public Sword(int r, int c) {
		row = r;
		column = c;
		taken = false;
	}
	
	public void takeSword() {
		taken = true;
	}
	
	public void dropSword() {
		taken = false;
	}
	
	public boolean getTaken() {
		return taken;
	}

}
