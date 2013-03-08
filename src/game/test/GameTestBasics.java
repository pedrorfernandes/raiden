package game.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import game.logic.Game;
import game.ui.GameOptions;
import game.ui.TestInterface;

import maze_objects.Dragon;

import org.junit.Test;

public class GameTestBasics {

	private Stack<Character> createMovesStack(String moves){
		Stack<Character> movesStack = new Stack<Character>();
		char[] chars = moves.toCharArray();
		int i = chars.length-1;
		while (i >= 0){
			movesStack.push(chars[i]);
			i--;
		}

		return movesStack;
	}

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
		Stack<Character> heroMoves = createMovesStack("d");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(1, test.getGame().getHero().getRow());
		assertEquals(2, test.getGame().getHero().getColumn());

		//Tests moving down to an empty space
		heroMoves = createMovesStack("s");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(2, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());

		//Tests moving up to an empty space
		hero_row = 8;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("w");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(7, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());

		//Tests moving left to an empty space
		hero_row = 8;
		hero_column = 8;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("a");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(7, test.getGame().getHero().getColumn());

		//Tests a full sequence of moves through empty spaces
		hero_row = 1;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("dddssssswddsssaddd");

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
		Stack<Character> heroMoves = createMovesStack("a");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(1, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());

		//Tests moving up against a wall
		heroMoves = createMovesStack("w");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(1, test.getGame().getHero().getRow());
		assertEquals(1, test.getGame().getHero().getColumn());

		//Tests moving right against a wall
		hero_row = 8;
		hero_column = 8;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("d");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());

		//Tests moving down against a wall
		heroMoves = createMovesStack("s");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());

		//Tests a full sequence of moves, mixed with going against walls
		hero_row = 1;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("wawadddssdadassswdwswsdsdadassssssssadddsdsd");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		assertEquals(8, test.getGame().getHero().getRow());
		assertEquals(8, test.getGame().getHero().getColumn());
	}

}
