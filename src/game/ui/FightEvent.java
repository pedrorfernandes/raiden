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
		
		if(id.equals("lostFight"))
			message = "You lost the fight!\n";
		
		else if(id.equals("wonFight"))
			message = "WOW! You slayed the dragon!\n";
	}
	
}