package game.ui;

public class EagleEvent extends GameEvent {
	
	/**
	 * Instantiates a new eagle event.
	 *
	 * @param id ID "killed" means the eagle was killed, ID "gotSword" means the eagle caught the sword. ID "isWaiting" means the eagle is grounded, waiting for the hero, the rest is self explanatory
	 */
	public EagleEvent(String id) {
		name = id;
		
		if(id.equals("killed"))
			message = "Oh no! Your eagle was killed!\n";
		
		else if(id.equals("gotSword"))
			message = "Your eagle caught the sword!\n";
		
		else if(id.equals("isWaiting"))
			message = "Your eagle has arrived and is waiting for you!\n";
		
		else if(id.equals("cantSendDead"))
			message = "Your eagle is dead!";
		else if(id.equals("cantSendOnRoute"))
			message = "Your eagle is not with you!";
		else if(id.equals("noSword"))
			message = "There is no sword for the eagle to catch!";
		else if(id.equals("eagleReturned"))
			message = "The eagle returned safely to you!";
		else
			message = "Something happened to your eagle..."; //In case an inexistent id is given
	}

}
