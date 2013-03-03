package maze_objects;

public class MazeSymbol { //Symbols used in the printing functions to identify the different objects. This makes reading easier.
	
	//Maze tiles related symbols
	public static final char sword = 'E';
	public static final char wall = 'X';
	public static final char exit = 'S';
	public static final char empty = ' ';
	public static final char space = ' ';
	
	//Hero related symbols
	public static final char hero = 'H';
	public static final char armedHero = 'A';
	
	//Dragon related symbols
	public static final char dragon = 'D';
	public static final char sleepingDragon = 'd';
	
	//Sword related symbols
	public static final char guardedSword = 'F';
	public static final char sleepingGuardedSword = 'f';
	
	//Eagle related symbols
	public static final char eagle = '>';
	public static final char eagleOnHero = 'L';
	public static final char eagleWithSword = 'Y';
	public static final char eagleOnWall = 'u';
	public static final char eagleOnDragon = 'G';
	public static final char eagleOnSleepingDragon = 'g';
}
