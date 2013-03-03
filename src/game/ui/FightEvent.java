package game.ui;

public class FightEvent extends GameEvent {
	
	public FightEvent(String id) { //ID lostFight means the Hero lost the fight, ID wonFight means the Hero won the fight
		name = id;
		
		if(id.equals("lostFight"))
			message = "You lost the fight!\n";
		
		else if(id.equals("wonFight"))
			message = "WOW! You slayed the dragon!\n";
	}
	
}