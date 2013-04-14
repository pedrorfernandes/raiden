package game.ui;

public class EagleEvent extends GameEvent {
	
	/**
	 * Instantiates a new eagle event.
	 *
	 * @param id ID "killed" means the eagle was killed, ID "gotSword" means the eagle caught the sword. ID "isWaiting" means the eagle is grounded, waiting for the hero, the rest is self explanatory
	 */
	public EagleEvent(String id) {
		name = id;
		
		if(id.equals("killed")) //$NON-NLS-1$
			message = Messages.getString("EagleEvent.1"); //$NON-NLS-1$
		
		else if(id.equals("gotSword")) //$NON-NLS-1$
			message = Messages.getString("EagleEvent.3"); //$NON-NLS-1$
		
		else if(id.equals("isWaiting")) //$NON-NLS-1$
			message = Messages.getString("EagleEvent.5"); //$NON-NLS-1$
		
		else if(id.equals("cantSendDead")) //$NON-NLS-1$
			message = Messages.getString("EagleEvent.7"); //$NON-NLS-1$
		else if(id.equals("cantSendOnRoute")) //$NON-NLS-1$
			message = Messages.getString("EagleEvent.9"); //$NON-NLS-1$
		else if(id.equals("noSword")) //$NON-NLS-1$
			message = Messages.getString("EagleEvent.11"); //$NON-NLS-1$
		else if(id.equals("eagleReturned")) //$NON-NLS-1$
			message = Messages.getString("EagleEvent.13"); //$NON-NLS-1$
		else
			message = Messages.getString("EagleEvent.14"); //In case an inexistent id is given //$NON-NLS-1$
	}

}
