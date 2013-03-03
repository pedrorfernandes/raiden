package game.ui;

public class ExitEvent extends GameEvent {

	public ExitEvent(int id) { //ID = 0, tried to exit without a weapon, ID = 1, tried to exit without killing the enemies
		
		if(id == 0)
			message = "You are not armed, it's dangerous to go alone like that!\n";
		else if(id == 1)
			message = "The exit is closed! You have to kill the dragons first!\n";
		
	}
	
}
