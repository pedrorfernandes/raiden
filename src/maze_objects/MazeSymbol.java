package maze_objects;

public class MazeSymbol { //Symbols used in the printing functions to identify the different objects. This makes reading easier.
	
	//Maze tiles related symbols
	public static char sword = 'E';
	public static char wall = 'X';
	public static char exit = 'S';
	public static char empty = ' ';
	public static char space = ' ';
	
	//Hero related symbols
	public static char hero = 'H';
	public static char armedHero = 'A';
	
	//Dragon related symbols
	public static char dragon = 'D';
	public static char sleepingDragon = 'd';
	
	//Sword related symbols
	public static char guardedSword = 'F';
	public static char sleepingGuardedSword = 'f';
	
	//Eagle related symbols
	public static char eagle = '>';
	public static char eagleWithSword = 'Y';
	public static char eagleOnWall = 'u';
	public static char eagleOnDragon = 'G';
	public static char eagleOnSleepingDragon = 'g';
}
