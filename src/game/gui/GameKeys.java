package game.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class GameKeys {
	static GameKey upKey = new GameKey(KeyEvent.VK_W, 'w');
	static GameKey leftKey = new GameKey(KeyEvent.VK_A, 'a');
	static GameKey downKey = new GameKey(KeyEvent.VK_S, 's');
	static GameKey rightKey = new GameKey(KeyEvent.VK_D, 'd');
	static GameKey eagleKey = new GameKey(KeyEvent.VK_E, 'e');
	static GameKey surrenderKey = new GameKey(KeyEvent.VK_ESCAPE, 'z');
	static GameKey waitKey = new GameKey(KeyEvent.VK_ENTER, ' ');
	
	static ArrayList<GameKey> keyList = new ArrayList<GameKey>(
			Arrays.asList(upKey, leftKey, downKey, rightKey, eagleKey, surrenderKey, waitKey));
}
