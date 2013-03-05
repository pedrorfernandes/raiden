package game.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import game.logic.Game;
import game.ui.GameOptions;

import maze_objects.Dragon;

import org.junit.Test;

public class GameTest {

	@Test
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
		Game g = new Game(customOptions);
		g.play();
	}

}
