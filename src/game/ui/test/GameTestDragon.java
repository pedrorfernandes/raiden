package game.ui.test;

import static org.junit.Assert.assertEquals;
import game.objects.Dragon;
import game.objects.Hero;
import game.ui.GameOptions;

import java.util.ArrayList;
import java.util.Stack;


import org.junit.Test;

/**
 * A series of tests to check advanced dragon behaviours, such as sleeping and interaction with various dragons.
 */
public class GameTestDragon extends GameTest {

	private int hero_row; private int hero_column; private int sword_row = 7; private int sword_column = 8;
	private Stack<Integer> moveNumbers;
	private Stack<Integer> sleepNumbers;
	private FakeRandom moves, sleeps;
	private ArrayList<Dragon>dragons;
	private Dragon d1; private Dragon d2; private Dragon d3;
	private GameOptions customOptions;
	private Stack<Character> heroMoves;
	private TestInterface test;
	
	/*
	@Test
	public void testDragon() {
		// moves of the dragon when he is not sleeping
		// after this stack empties, the numbers will be RANDOM
		Stack<Integer> moveNumbers = createDragonMoves("aswdds");
		// 2 -> doesnt sleep (any other int different than 1 works as well) / 1 ->sleeps
		// imediatelly after 1 you must specify the number of turns the dragon sleeps
		// For example, "2213222". In this case he will sleep 3+1 turns
		Stack<Integer> sleepNumbers = createDragonSleeps("2213222");

		FakeRandom moves = new FakeRandom(moveNumbers);
		FakeRandom sleeps = new FakeRandom(sleepNumbers);

		Dragon d1 = new Dragon(5,  4,  Dragon.NORMAL , moves, sleeps);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 1, sword_row = 7, sword_column = 8;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		//Tests moving right to an empty space
		Stack<Character> heroMoves = createHeroMoves(" ");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();
	}
	 */

	@Test
	public void testDragonMoves() {
		hero_row = 1; hero_column = 1; sword_row = 7; sword_column = 8;

		//Tests moving right to an empty space
		moveNumbers = createDragonMoves("d");
		sleepNumbers = createDragonSleeps("2");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d1 = new Dragon(5,  4,  Dragon.NORMAL , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		heroMoves = createHeroMoves(" ");

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(5, d1.getRow());
		assertEquals(5, d1.getColumn());


		//Tests moving down to an empty space
		moveNumbers = createDragonMoves("s");
		sleepNumbers = createDragonSleeps("2");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d1 = new Dragon(5,  4, Dragon.NORMAL , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		heroMoves = createHeroMoves(" ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(6, d1.getRow());
		assertEquals(4, d1.getColumn());


		//Tests moving up to an empty space
		moveNumbers = createDragonMoves("w");
		sleepNumbers = createDragonSleeps("2");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d1 = new Dragon(5,  4, Dragon.NORMAL , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		heroMoves = createHeroMoves(" ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(4, d1.getRow());
		assertEquals(4, d1.getColumn());


		//Tests moving left to an empty space
		moveNumbers = createDragonMoves("a");
		sleepNumbers = createDragonSleeps("2");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d1 = new Dragon(5,  4, Dragon.NORMAL , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		heroMoves = createHeroMoves(" ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(5, d1.getRow());
		assertEquals(3, d1.getColumn());

		//Tests a full sequence of moves through empty spaces
		moveNumbers = createDragonMoves("dddssssswddsssaddd");
		sleepNumbers = createDragonSleeps("2");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d1 = new Dragon(1,  1, Dragon.NORMAL , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_column = 1; 
		hero_row = 7; 
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		heroMoves = createHeroMoves("                  ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, d1.getRow());
		assertEquals(8, d1.getColumn());

	}

	@Test
	public void testDragonSleeps() {
		hero_row = 1; hero_column = 1; sword_row = 7; sword_column = 8;

		// Test if dragon is sleeping when game ends
		moveNumbers = createDragonMoves("wwwwwww");
		sleepNumbers = createDragonSleeps("2221322");

		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);

		d1 = new Dragon(8,  4,  Dragon.SLEEPING , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 1; hero_column = 1; sword_row = 7; sword_column = 8;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("       ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(5, d1.getRow());
		assertEquals(4, d1.getColumn());
		assertEquals(Dragon.SLEEPING, d1.getState() );
	}

	@Test
	public void testDragonKillsHero() {
		hero_row = 1; hero_column = 1; sword_row = 1; sword_column = 8;
		
		// Test if dragon gets killed
		moveNumbers = createDragonMoves("aa");
		sleepNumbers = createDragonSleeps("22");

		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);

		d1 = new Dragon(1,  4,  Dragon.NORMAL , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("  ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(Dragon.ALIVE, d1.getState() );
		assertEquals(Hero.DEAD, test.getGame().getHero().getState() );

	}

	@Test
	public void testDragonGetsKilled() {
		hero_row = 1; hero_column = 1; sword_row = 1; sword_column = 1;

		// Test if dragon gets killed
		moveNumbers = createDragonMoves("aa");
		sleepNumbers = createDragonSleeps("22");

		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);

		d1 = new Dragon(1,  4,  Dragon.NORMAL , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("  ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(Dragon.DEAD, d1.getState() );
		assertEquals(Hero.ARMED, test.getGame().getHero().getState());
	}

	@Test
	public void testMultipleDragons() {
		hero_row = 1; hero_column = 1; sword_row = 7; sword_column = 8;
		dragons = new ArrayList<Dragon>(1);

		// Test if dragon can't move into other dragons
		moveNumbers = createDragonMoves("aawwddss d");
		sleepNumbers = createDragonSleeps("22");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d1 = new Dragon(5,  3,  Dragon.NORMAL , moves, sleeps);
		dragons.add(d1);
		
		moveNumbers = createDragonMoves("a    ");
		sleepNumbers = createDragonSleeps("22");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d2 = new Dragon(5,  2,  Dragon.NORMAL , moves, sleeps);
		dragons.add(d2);
		
		moveNumbers = createDragonMoves("w     ");
		sleepNumbers = createDragonSleeps("22");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d3 = new Dragon(5,  4,  Dragon.NORMAL , moves, sleeps);
		dragons.add(d3);

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("  ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(5, d1.getRow());
		assertEquals(4, d1.getColumn());
		assertEquals(5, d2.getRow());
		assertEquals(1, d2.getColumn());
		assertEquals(4, d3.getRow());
		assertEquals(4, d3.getColumn());
	}

	//@Test
	public void testDragonWakesUp() {
		hero_row = 1; hero_column = 1; sword_row = 7; sword_column = 8;		
		// Test if dragon is awake when game ends
		moveNumbers = createDragonMoves("wwwwwww");
		// sleeps for 4 turns then 1 turn wakes up, only after then dragon will move
		sleepNumbers = createDragonSleeps("22213222222");

		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);

		d1 = new Dragon(8,  4,  Dragon.SLEEPING , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 1; hero_column = 1; sword_row = 7; sword_column = 8;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("            ");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(1, d1.getRow());
		assertEquals(4, d1.getColumn());
		assertEquals(Dragon.ALIVE, d1.getState() );
	}
}
