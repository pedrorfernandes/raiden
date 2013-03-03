package maze_objects;

public class Eagle extends Movable {

	/*** Private Attributes ***/

	private int place;         //Tells if eagle is on top of a wall or a dragon
	private boolean hasSword;  //Tells if eagle has the sword
	
	private boolean onRouteToSword;   //Tells if eagle is going after a sword;
	private boolean onRouteToHero;    //Tells if eagle is going back to hero;
	
	private boolean withHero;  //Tells if eagle is with hero
	
	private boolean onGroundWithSword;  //Tells if eagle is on top of sword place
	private boolean waitingForHero;     //Tells if eagle is waiting for hero, on ground
	
	private int onGroundCounter; //Counts the number of turns the eagle is grounded

	//Position of the sword the eagle is going after
	private int swordRow;
	private int swordColumn;

	//Position of the eagle when it took off
	private int startRow;
	private int startColumn;

	//Number of turns to be grounded on top of sword
	private static final int ON_GROUND_TURNS = 1;

	/*** Public Attributes ***/

	//Eagle States
	public static final int DEAD = 0;
	public static final int ALIVE = 1;

	//Eagle Positions
	public static final int ON_HERO = 0;
	public static final int ON_EMPTY = 1;
	public static final int ON_WALL = 2;
	public static final int ON_DRAGON = 3;
	public static final int ON_SLEEPING_DRAGON = 4;

	/*** Private Methods ***/
	private void makeMoveAtSword() {
		int dx = Math.abs(swordColumn - startColumn);
		int dy = Math.abs(swordRow - startRow);

		int sx = (startColumn < swordColumn) ? 1 : -1;
		int sy = (startRow < swordRow) ? 1 : -1;

		int err = dx - dy;


		int e2 = 2 * err;

		if (e2 > -dy) {
			err -= dy;
			column += sx;
		}

		if (e2 < dx) {
			err += dx;
			row += sy;
		}
	}

	private void makeMoveAtStart() {
		int dx = Math.abs(startColumn - swordColumn);
		int dy = Math.abs(startRow - swordRow);

		int sx = (swordColumn < startColumn) ? 1 : -1;
		int sy = (swordRow < startRow) ? 1 : -1;

		int err = dx - dy;


		int e2 = 2 * err;

		if (e2 > -dy) {
			err -= dy;
			column += sx;
		}

		if (e2 < dx) {
			err += dx;
			row += sy;
		}
	}

	/*** Public Methods ***/

	//Constructors
	public Eagle(int r, int c) {
		row = r;
		column = c;

		state = ALIVE;
		place = ON_HERO;
		hasSword = false;	
	}

	//General Methods
	public int getPlace() {
		return place;
	}

	public void setPlace(int p) {
		place = p;
	}

	public boolean eagleHasSword() {
		return hasSword;
	}

	public void catchSword() {
		hasSword = true;
	}

	public void dropSword() {
		hasSword = false;
	}

	public boolean isOnRouteToSword() {
		return onRouteToSword;
	}

	public void setOnRouteToSword() {
		onRouteToSword = true;
	}
	
	public void removeOnRouteToSword() {
		onRouteToSword = false;
	}

	public boolean isOnRouteToHero() {
		return onRouteToHero;
	}

	public void setOnRouteToHero() {
		onRouteToHero = true;
	}

	public void removeOnRouteToHero() {
		onRouteToHero = false;
	}

	public void setSwordRow(int r) {
		swordRow = r;
	}

	public void setSwordColumn(int c) {
		swordColumn = c;
	}

	//Game Methods
	public void moveEagle() {

		if(onGroundCounter < ON_GROUND_TURNS && onGroundWithSword)
			onGroundCounter++;
		else if(onGroundWithSword) {
			onGroundWithSword = false;
			onRouteToSword = false;
			onRouteToHero = true;
		}

		if(!onGroundWithSword || !waitingForHero || !withHero) {
			if(onRouteToSword && !hasSword)
				makeMoveAtSword();
			else if(onRouteToHero)
				makeMoveAtStart();
		
			if(row == swordRow && column == swordColumn) {
				onGroundWithSword = true;
				onGroundCounter = 0;
			}
			
			if(row == startRow && column == startColumn)
				waitingForHero = true;
		}
		

	}


}
