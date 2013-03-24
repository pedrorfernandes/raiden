package game.test;

import static org.junit.Assert.assertEquals;
import game.logic.Game;
import game.ui.GameOptions;
import game.ui.GameOutput;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import maze_objects.Dragon;

import org.junit.Test;

public class GameTestSave extends GameTest {
	private int hero_row; private int hero_column; private int sword_row = 7; private int sword_column = 8;
	private Stack<Integer> moveNumbers;
	private Stack<Integer> sleepNumbers;
	private FakeRandom moves, sleeps;
	private ArrayList<Dragon>dragons;
	private Dragon d1; private Dragon d2;
	private GameOptions customOptions;
	private Stack<Character> heroMoves;
	private TestInterface test;
	
	@Test 
	public void serializeGame()
	{
		hero_row = 1; hero_column = 1; sword_row = 7; sword_column = 8;

		// In this game the dragon will move right
		// this will not run right now, it will be saved and loaded
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
		
		File file = new File("gameTest.save");
		GameOutput.save(test.getGame(), file);
		
		// And in this one it will move left
		moveNumbers = createDragonMoves("a");
		sleepNumbers = createDragonSleeps("2");
		moves = new FakeRandom(moveNumbers);
		sleeps = new FakeRandom(sleepNumbers);
		d2 = new Dragon(5,  4,  Dragon.NORMAL , moves, sleeps);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d2);
		heroMoves = createHeroMoves(" ");

		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);
		test = new TestInterface(customOptions, heroMoves);
		
		test.startGame();
		assertEquals(5, d2.getRow());
		assertEquals(3, d2.getColumn());
		
		heroMoves = createHeroMoves(" ");
		test = new TestInterface(customOptions, heroMoves);
		Game loadedGame = GameOutput.load(file);
		test.setGame(loadedGame);
		test.startGame();
		
		d1 = test.getGame().getDragon(0);

		assertEquals(5, d1.getRow());
		assertEquals(5, d1.getColumn());
		

		// Clean up the file
		file.delete();

	}
}
