package game.ui;

public class EagleEvent extends GameEvent {
	
	public EagleEvent(String id) { //ID "killed" means the eagle was killed, ID "gotSword" means the eagle caught the sword
		name = id;                 //ID "isWaiting" means the eagle is grounded, waiting for the hero
		
		if(id.equals("killed"))
			message = "Oh no! Your eagle was killed!\n";
		
		else if(id.equals("gotSword"))
			message = "Your eagle caught the sword!\n";
		
		else if(id.equals("isWaiting"))
			message = "Your eagle has arrived and is waiting for you!\n";
	}

}
