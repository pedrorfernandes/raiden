package maze_objects;

public class MazeSymbol { //Symbols used in the printing functions to identify the different objects. This makes reading easier.

	//Maze tiles related symbols
	public static final char sword = 'E';
	public static final char wall = 'X';
	public static final char exit = 'S';
	public static final char empty = ' ';
	public static final char space = ' ';

	//Maze tiles images
	public static final String wallPic = "images/wall.png";
	public static final String emptyPic = "images/empty.png";
	public static final String spacePic = "images/empty.png";
	public static final String exitPic = "images/exit.png";
	public static final String swordPic = "images/sword.png";

	//Hero related symbols
	public static final char hero = 'H';
	public static final char armedHero = 'A';

	//Hero related images
	public static final String heroPic = "images/hero.png";
	public static final String armedHeroPic = "images/armedHero.png";
	public static final String deadHeroPic = "images/deadHero.png";

	//Dragon related symbols
	public static final char dragon = 'D';
	public static final char sleepingDragon = 'd';

	//Dragon related images
	public static final String dragonPic = "images/dragon.png";
	public static final String sleepingDragonPic = "images/sleepingDragon.png";
	public static final String deadDragonPic = "images/deadDragon.png";

	//Sword related symbols
	public static final char guardedSword = 'F';
	public static final char sleepingGuardedSword = 'f';

	//Sword related images
	public static final String guardedSwordPic = "images/guardedSword.png";
	public static final String sleepingGuardedSwordPic = "images/sleepingGuardedSword.png";

	//Eagle related symbols
	public static final char eagle = '>';
	public static final char eagleOnHero = 'L';
	public static final char eagleWithSword = 'Y';
	public static final char eagleOnWall = 'u';
	public static final char eagleOnDragon = 'G';
	public static final char eagleOnSleepingDragon = 'g';

	//Eagle related images
	public static final String eaglePic = "images/eagle.png";
	public static final String eagleOnHeroPic = "images/eagleOnHero.png";
	public static final String eagleWithSwordPic = "images/eagleWithSword.png";
	public static final String eagleOnWallPic = "images/eagleOnWall.png";
	public static final String eagleOnDragonPic = "images/eagleOnDragon.png";
	public static final String eagleOnSleepingDragonPic = "images/eagleOnSleepingDragon.png";
}
