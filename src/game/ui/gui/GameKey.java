package game.ui.gui;

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