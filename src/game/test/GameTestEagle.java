package game.test;

import static org.junit.Assert.assertEquals;
import game.ui.GameOptions;
import game.ui.TestInterface;

import java.util.ArrayList;
import java.util.Stack;

import maze_objects.Dragon;

import org.junit.Test;

public class GameTestEagle {
	
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
	
	@Test
	public void testEagleGetSword() {
		
		
		/* Test sending eagle from a direct line above the sword */
		Dragon d1 = new Dragon(8, 8, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 3, hero_column = 4, sword_row = 5, sword_column = 4;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		Stack<Character> heroMoves = createMovesStack("ez");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();
		
		//Check if the eagle is on the original sword position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());
		
		//Check if the eagle is on ground
		assertEquals(true, test.getGame().getEagle().isOnGroundWithSword());
		
		
		
		/* Test sending eagle from a direct line under the sword */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 7;
		hero_column = 4;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("ez");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();
		
		//Check if the eagle is on the original sword position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());
		
		//Check if the eagle is on ground
		assertEquals(true, test.getGame().getEagle().isOnGroundWithSword());
		
		
		
		/* Test sending eagle from a direct line right from the sword */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 5;
		hero_column = 2;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("ez");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();
		
		//Check if the eagle is on the original sword position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());
		
		//Check if the eagle is on ground
		assertEquals(true, test.getGame().getEagle().isOnGroundWithSword());
		
		
		/* Test sending eagle from a direct line left from the sword */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 5;
		hero_column = 6;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("ez");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();
		
		//Check if the eagle is on the original sword position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());
		
		//Check if the eagle is on ground
		assertEquals(true, test.getGame().getEagle().isOnGroundWithSword());
		
		
		/* Test sending the eagle from a diagonal position in relation to the sword */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 1;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createMovesStack("e  z");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();
		
		//Check if the eagle is on the original sword position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());
		
		//Check if the eagle is on ground
		assertEquals(true, test.getGame().getEagle().isOnGroundWithSword());
	}

}
