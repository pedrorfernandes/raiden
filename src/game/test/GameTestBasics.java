package game.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import game.logic.Game;
import game.ui.GameOptions;
import game.ui.TestInterface;

import maze_objects.Dragon;
import maze_objects.Hero;

import org.junit.Test;

public class GameTestBasics extends GameTest {

	/*@Test
	public void testCustomSpawns() {
		Dragon d1 = new Dragon(1, 7, Dragon.SLEEPING);
		Dragon d2 = new Dragon(3, 1, Dragon.STATIC);
		Dragon d3 = new Dragon(8, 4, Dragon.NORMAL);
		ArrayList<Dragon> dragons = new ArrayList<Dragon>(3);
		dragons.add(d1);
		dragons.add(d2);
		dragons.add(d3);

		int hero_row = 1, hero_column = 1, sword_row = 7, sword_column = 8;

		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		Stack<Character> heroMoves = createMovesStack("ddddas");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

	}*/

	@Test
	public void testHeroToEmptySpace() {
		Dragon d1 = new Dragon(1,  7,  Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 1, sword_row = 7, sword_column = 8;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		//Tests moving right to an empty space
		Stack<Character> heroMoves = createHeroMoves("d");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(1, test.getGame().getHero().getRow());
		assertEquals(2, test.getGame().getHero().getColumn());

		//Tests moving down to an empty space
		d1 = new Dragon(1,  7,  Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		heroMoves = createHeroMoves("s");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(2, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());

		//Tests moving up to an empty space
		d1 = new Dragon(1,  7,  Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		hero_row = 8;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("w");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(7, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());

		//Tests moving left to an empty space
		d1 = new Dragon(1,  7,  Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		hero_row = 8;
		hero_column = 8;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("a");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(7, test.getGame().getHero().getColumn());

		//Tests a full sequence of moves through empty spaces
		d1 = new Dragon(1,  7,  Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		hero_row = 1;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("dddssssswddsssaddd");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());
	}
	
	@Test
	public void testHeroToWall() {
		Dragon d1 = new Dragon(1,  7,  Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 1, sword_row = 7, sword_column = 8;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		//Tests moving left against a wall
		Stack<Character> heroMoves = createHeroMoves("a");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(1, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());

		//Tests moving up against a wall
		d1 = new Dragon(1,  7,  Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		heroMoves = createHeroMoves("w");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(1, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());

		//Tests moving right against a wall
		d1 = new Dragon(1,  7,  Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		hero_row = 8;
		hero_column = 8;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("d");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());

		//Tests moving down against a wall
		d1 = new Dragon(1,  7,  Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		heroMoves = createHeroMoves("s");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());

		//Tests a full sequence of moves, mixed with going against walls
		d1 = new Dragon(1,  7,  Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		hero_row = 1;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("wawadddssdadassswdwswsdsdadassssssssadddsdsd");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());
	}
	
	@Test
	public void testHeroAgainstSleepingDragon() {
		Dragon d1 = new Dragon(5, 4, Dragon.STATIC);
		d1.setState(Dragon.ASLEEP); //Creates a permanently asleep dragon, due to it being a STATIC dragon
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 4, hero_column = 4, sword_row = 7, sword_column = 8;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		//Tests moving down on a sleeping dragon
		Stack<Character> heroMoves = createHeroMoves("s");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(Hero.IN_GAME, test.getGame().getHero().getState());
		assertEquals(4, test.getGame().getHero().getRow());
		assertEquals(4, test.getGame().getHero().getColumn());

		//Tests moving up on a sleeping dragon
		d1 = new Dragon(5, 4, Dragon.STATIC);
		d1.setState(Dragon.ASLEEP); //Creates a permanently asleep dragon, due to it being a STATIC dragon
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		hero_row = 6;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		
		heroMoves = createHeroMoves("w");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(Hero.IN_GAME, test.getGame().getHero().getState());
		assertEquals(6, test.getGame().getHero().getRow());
		assertEquals(4, test.getGame().getHero().getColumn());

		//Tests moving right on a sleeping dragon
		d1 = new Dragon(5, 4, Dragon.STATIC);
		d1.setState(Dragon.ASLEEP); //Creates a permanently asleep dragon, due to it being a STATIC dragon
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		hero_row = 5;
		hero_column = 3;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("d");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(Hero.IN_GAME, test.getGame().getHero().getState());
		assertEquals(5, test.getGame().getHero().getRow());
		assertEquals(3, test.getGame().getHero().getColumn());

		//Tests moving left on a sleeping dragon
		d1 = new Dragon(5, 4, Dragon.STATIC);
		d1.setState(Dragon.ASLEEP); //Creates a permanently asleep dragon, due to it being a STATIC dragon
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		hero_row = 5;
		hero_column = 5;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("a");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(Hero.IN_GAME, test.getGame().getHero().getState());
		assertEquals(5, test.getGame().getHero().getRow());
		assertEquals(5, test.getGame().getHero().getColumn());
	}
	
	///Tests if hero picks up sword, sword is registered as taken, and hero as armed.
	@Test
	public void testPickingUpSword() {
		Dragon d1 = new Dragon(1,  7,  Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 1, sword_row = 3, sword_column = 1;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		
		Stack<Character> heroMoves = createHeroMoves("ss");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(3, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());
		
		assertEquals(true, test.getGame().getSword().isTaken());
		assertEquals(Hero.ARMED, test.getGame().getHero().getState());
	}
	
	@Test
	public void testLosingAgainstDragon() {
		Dragon d1 = new Dragon(1, 3, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 1, sword_row = 3, sword_column = 1;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		
		Stack<Character> heroMoves = createHeroMoves("d");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(Hero.DEAD, test.getGame().getHero().getState());
	}
	
	@Test
	public void testKillingDragon() {
		Dragon d1 = new Dragon(1, 3, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 1, sword_row = 3, sword_column = 1;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		
		Stack<Character> heroMoves = createHeroMoves("sswwd");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Checks if the hero state remained armed
		assertEquals(Hero.ARMED, test.getGame().getHero().getState());
		
		//Checks if the dragon was killed and the number of remaining dragons dropped
		assertEquals(0, test.getGame().getRemainingDragons());
		assertEquals(Dragon.DEAD, test.getGame().getDragon(0).getState());
		
		/* Tests if the hero kills the dragon if the dragon is right next to the sword */
		d1 = new Dragon(1, 3, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		sword_row = 1;
		sword_column = 2;
		
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		
		heroMoves = createHeroMoves("d");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Checks if the hero state remained armed
		assertEquals(Hero.ARMED, test.getGame().getHero().getState());
		
		//Checks if the dragon was killed and the number of remaining dragons dropped
		assertEquals(Dragon.DEAD, test.getGame().getDragon(0).getState());
		assertEquals(0, test.getGame().getRemainingDragons());
	}
	
	@Test
	public void testSuccessfulExit() {
		Dragon d1 = new Dragon(1, 3, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 1, sword_row = 3, sword_column = 1;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		
		Stack<Character> heroMoves = createHeroMoves("sswwdddddddssssd");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Checks if the exit is open
		assertEquals(Game.EXIT_OPEN, test.getGame().getExitState());
		
		//Checks if the hero exited the maze
		assertEquals(Hero.EXITED_MAZE, test.getGame().getHero().getState());
		
		//Checks if the dragon was killed and there are no remaining dragons
		assertEquals(0, test.getGame().getRemainingDragons());
		assertEquals(Dragon.DEAD, test.getGame().getDragon(0).getState());
	}
	
	@Test
	public void testPrematureExit() {
		Dragon d1 = new Dragon(8, 1, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		/* Test exit attempt without sword or killing all the dragons */
		int hero_row = 4, hero_column = 8, sword_row = 6, sword_column = 8;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		
		Stack<Character> heroMoves = createHeroMoves("sd");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Checks if exit remains closed
		assertEquals(Game.EXIT_CLOSED, test.getGame().getExitState());
		
		//Checks if hero didn't change its state and remained in its place
		assertEquals(5, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());
		assertEquals(Hero.IN_GAME, test.getGame().getHero().getState());
		
		
		/* Test exit attempt with sword but without killing any dragons */
		d1 = new Dragon(8, 1, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		
		heroMoves = createHeroMoves("sswd");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Checks if exit remained closed
		assertEquals(Game.EXIT_CLOSED, test.getGame().getExitState());
		
		//Checks if hero didn't change its state and remained in its place
		assertEquals(5, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());
		assertEquals(Hero.ARMED, test.getGame().getHero().getState());
		
		
		/* Test exit attempt with sword and after killing a dragon, but not all */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		Dragon d2 = new Dragon(8, 1, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);
		dragons.add(d2);

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		
		heroMoves = createHeroMoves("sssswwwd");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Checks if exit remains closed
		assertEquals(Game.EXIT_CLOSED, test.getGame().getExitState());
		
		//Checks if hero didn't change its state and remained in its place
		assertEquals(5, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());
		assertEquals(Hero.ARMED, test.getGame().getHero().getState());
	}

}
