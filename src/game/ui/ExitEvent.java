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
			message = Messages.getString("ExitEvent.0"); //$NON-NLS-1$
		else if(id == 1)
			message = Messages.getString("ExitEvent.1"); //$NON-NLS-1$
		
	}
	
}
