package game.ui.test;

import static org.junit.Assert.assertEquals;
import game.objects.Dragon;
import game.objects.Eagle;
import game.objects.Hero;
import game.ui.GameOptions;

import java.util.ArrayList;
import java.util.Stack;


import org.junit.Test;

/**
 * A series of tests to determine if the eagle is working as expected.
 */
public class GameTestEagle extends GameTest {

	@Test
	public void testMoveWithHero() {
		Dragon d1 = new Dragon(8, 8, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 1, sword_row = 5, sword_column = 4;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		Stack<Character> heroMoves = createHeroMoves("wdddw");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle moved to the right position
		assertEquals(1, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());

		//Check if the eagle moved with the hero
		assertEquals(test.getGame().getHero().getRow(), test.getGame().getEagle().getRow());
		assertEquals(test.getGame().getHero().getColumn(), test.getGame().getEagle().getColumn());

		//Check if the eagle is with hero
		assertEquals(true, test.getGame().getEagle().isWithHero());
	}

	@Test
	public void testSendingEagle() {
		Dragon d1 = new Dragon(8, 8, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 3, hero_column = 4, sword_row = 5, sword_column = 4;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		Stack<Character> heroMoves = createHeroMoves("e");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle moved one position
		assertEquals(4, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());

		//Check if the eagle is not with hero
		assertEquals(false, test.getGame().getEagle().isWithHero());

		//Check if the eagle is on route to the sword
		assertEquals(true, test.getGame().getEagle().isOnRouteToSword());
	}

	@Test
	public void testEagleGetSword() {
		/* Test sending eagle from a direct line above the sword */
		Dragon d1 = new Dragon(8, 8, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 3, hero_column = 4, sword_row = 5, sword_column = 4;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		Stack<Character> heroMoves = createHeroMoves("ez");

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

		heroMoves = createHeroMoves("ez");

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

		heroMoves = createHeroMoves("ez");

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

		heroMoves = createHeroMoves("ez");

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

		heroMoves = createHeroMoves("e  z");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the original sword position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());

		//Check if the eagle is on ground
		assertEquals(true, test.getGame().getEagle().isOnGroundWithSword());
	}

	@Test
	public void testEagleReturn() {

		/* Test returning eagle from a direct line above the sword */
		Dragon d1 = new Dragon(8, 8, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 4, sword_row = 5, sword_column = 4;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		Stack<Character> heroMoves = createHeroMoves("ea       z");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the original hero position
		assertEquals(1, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());

		//Check if the eagle is waiting
		assertEquals(true, test.getGame().getEagle().isWaitingForHero());



		/* Test returning eagle from a direct line under the sword */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 8;
		hero_column = 4;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("ed        z");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the original hero position
		assertEquals(8, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());

		//Check if the eagle is waiting
		assertEquals(true, test.getGame().getEagle().isWaitingForHero());



		/* Test returning eagle from a direct line right from the sword */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 5;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("ew    z");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the original hero position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(1, test.getGame().getEagle().getColumn());

		//Check if the eagle is waiting
		assertEquals(true, test.getGame().getEagle().isWaitingForHero());


		/* Test returning eagle from a direct line left from the sword */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 5;
		hero_column = 6;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("ew  z");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the original hero position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(6, test.getGame().getEagle().getColumn());

		//Check if the eagle is waiting
		assertEquals(true, test.getGame().getEagle().isWaitingForHero());


		/* Test returning the eagle from a diagonal position in relation to the sword */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 1;
		hero_column = 1;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("es      z");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the original hero position
		assertEquals(1, test.getGame().getEagle().getRow());
		assertEquals(1, test.getGame().getEagle().getColumn());

		//Check if the eagle is waiting
		assertEquals(true, test.getGame().getEagle().isWaitingForHero());
	}

	@Test
	public void testRetrievingEagle() {
		
		/* Test retrieving eagle when it flies up to hero's position */
		Dragon d1 = new Dragon(8, 8, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 4, sword_row = 5, sword_column = 4;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		Stack<Character> heroMoves = createHeroMoves("e       z");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the original hero position
		assertEquals(1, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());

		//Check if the eagle is caught
		assertEquals(true, test.getGame().getEagle().isWithHero());
		
		//Check if the hero gets the sword
		assertEquals(Hero.ARMED, test.getGame().getHero().getState());
		
		//Check if the eagle is not on route to sword nor to hero
		assertEquals(false, test.getGame().getEagle().isOnRouteToHero());
		assertEquals(false, test.getGame().getEagle().isOnRouteToSword());
		
		//Check if the eagle is not grounded and dropped sword
		assertEquals(false, test.getGame().getEagle().isOnGroundWithSword());
		assertEquals(false, test.getGame().getEagle().isWaitingForHero());
		assertEquals(false, test.getGame().getEagle().eagleHasSword());
		
		
		
		/* Test retrieving eagle when the hero goes on top of it */
		d1 = new Dragon(8, 8, Dragon.STATIC);
		dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		hero_row = 1;
		hero_column = 4;
		customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		heroMoves = createHeroMoves("es      wz");

		test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the original hero position
		assertEquals(1, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());

		//Check if the eagle is caught
		assertEquals(true, test.getGame().getEagle().isWithHero());
		
		//Check if the hero gets the sword
		assertEquals(Hero.ARMED, test.getGame().getHero().getState());
		
		//Check if the eagle is not on route to sword nor to hero
		assertEquals(false, test.getGame().getEagle().isOnRouteToHero());
		assertEquals(false, test.getGame().getEagle().isOnRouteToSword());
		
		//Check if the eagle is not grounded and dropped sword
		assertEquals(false, test.getGame().getEagle().isOnGroundWithSword());
		assertEquals(false, test.getGame().getEagle().isWaitingForHero());
		assertEquals(false, test.getGame().getEagle().eagleHasSword());
	}
	
	@Test
	public void testEagleDies() {
		
		Dragon d1 = new Dragon(5, 5, Dragon.STATIC);
		ArrayList<Dragon>dragons = new ArrayList<Dragon>(1);
		dragons.add(d1);

		int hero_row = 1, hero_column = 4, sword_row = 5, sword_column = 4;
		GameOptions customOptions = new GameOptions(0, false, hero_row, hero_column, sword_row, sword_column, dragons);

		Stack<Character> heroMoves = createHeroMoves("e   z");

		TestInterface test = new TestInterface(customOptions, heroMoves);
		test.startGame();

		//Check if the eagle is on the sword position
		assertEquals(5, test.getGame().getEagle().getRow());
		assertEquals(4, test.getGame().getEagle().getColumn());

		//Check if the eagle is dead
		assertEquals(Eagle.DEAD, test.getGame().getEagle().getState());
		
		//Check if the eagle dropped the sword
		assertEquals(false, test.getGame().getEagle().eagleHasSword());
		
		//Check if the sword is registered as not taken
		assertEquals(false, test.getGame().getSword().isTaken());
	}

}
