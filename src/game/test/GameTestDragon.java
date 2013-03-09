package game.test;

import static org.junit.Assert.assertEquals;
import game.ui.GameOptions;
import game.ui.TestInterface;

import java.util.ArrayList;
import java.util.Stack;

import maze_objects.Dragon;

import org.junit.Test;

public class GameTestDragon extends GameTest {

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
		int hero_row = 1, hero_column = 1, sword_row = 7, sword_column = 8;
		Stack<Integer> moveNumbers;
		Stack<Integer> sleepNumbers;
		FakeRandom moves, sleeps;
		ArrayList<Dragon>dragons;
		Dragon d1;
		GameOptions customOptions;
		Stack<Character> heroMoves;
		TestInterface test;
		
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

		/*
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
		*/
	}
	
	@Test
	public void testDragonSleeps() {
		
	}

	@Test
	public void testDragonKillsHero() {
		
	}
	
	@Test
	public void testDragonGetsKilled() {
		
	}
	
	@Test
	public void testMultipleDragons() {
		
	}
	
	@Test
	public void testDragonWakesUp() {
		
	}
}
