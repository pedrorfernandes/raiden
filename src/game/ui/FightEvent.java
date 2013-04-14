package game.ui;

/**
 * The Class FightEvent stores messages regarding fights against dragons and the hero
 */
public class FightEvent extends GameEvent {
	
	/**
	 * Instantiates a new fight event.
	 * @param id ID lostFight means the Hero lost the fight, ID wonFight means the Hero won the fight
	 */
	public FightEvent(String id) {
		name = id;
		
		if(id.equals("lostFight")) //$NON-NLS-1$
			message = Messages.getString("FightEvent.1"); //$NON-NLS-1$
		
		else if(id.equals("wonFight")) //$NON-NLS-1$
			message = Messages.getString("FightEvent.3"); //$NON-NLS-1$
	}
	
}