package game.ui;

public class FightEvent extends GameEvent {
	
	public FightEvent(String id) { //ID 0 means the Hero lost the fight, ID 1 means the Hero won the fight
		name = id;
		
		if(id.equals("lostFight"))
			message = "You lost the fight!\n";
		
		else if(id.equals("wonFight"))
			message = "WOW! You slayed the dragon! Exit is now opened!\n";
	}
	
}