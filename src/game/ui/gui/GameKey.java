package game.ui.gui;

/**
 * The Class GameKey maps a given keycode with a character that the game interprets as an action.
 */
public class GameKey {
	
	public int keycode;
	public char character;
	
	public GameKey(int code, char charCode){
		keycode = code;
		character = charCode;
	}
	
	public int getKey(){
		return keycode;
	}
	
	public void changeKey(int newCode){
		keycode = newCode;
	}
	
	public char getChar(){
		return character;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof GameKey){
			if ( ((GameKey)obj).getKey() == this.keycode)
				return true;
		}
		return false;
	}
}