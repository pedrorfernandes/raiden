package game.gui;

import java.awt.event.KeyEvent;

public class GameKeys {
	static int upKey = KeyEvent.VK_W;
	static int leftKey = KeyEvent.VK_A;
	static int downKey = KeyEvent.VK_S;
	static int rightKey = KeyEvent.VK_D;
	static int eagleKey = KeyEvent.VK_E;
	static int surrenderKey = KeyEvent.VK_ESCAPE;
	
	public static void changeKey(String key, int newKeyCode){
		switch (key) {
		case "upKey":
			upKey = newKeyCode;
			break;
		case "leftKey":
			leftKey = newKeyCode;
			break;
		case "downKey":
			downKey = newKeyCode;
			break;
		case "rightKey":
			rightKey = newKeyCode;
			break;
		case "eagleKey":
			eagleKey = newKeyCode;
			break;
		case "surrenderKey":
			surrenderKey = newKeyCode;
			break;
		default:
			break;
		}
	}
}
