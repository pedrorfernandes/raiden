package game.ui;

/**
 * The Class ExitEvent stores an event if the hero tries to reach the exit.
 */
public class ExitEvent extends GameEvent {

	/**
	 * Instantiates a new exit event.
	 * @param id ID = 0, tried to exit without a weapon, ID = 1, tried to exit without killing the enemies
	 */
	public ExitEvent(int id) {
		
		if(id == 0)
			message = "You are not armed, it's dangerous to go alone like that!\n";
		else if(id == 1)
			message = "The exit is closed! You have to kill the dragons first!\n";
		
	}
	
}
