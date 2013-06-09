package com.raiden.game;

/**
 * The observer must deal with a certain event (that might interest it)
 * as soon as it receives an update.
 */
public interface Observer {
	
	/**
	 * Performs an action according to the event and position received.
	 * @param x The X coordinate of the position.
	 * @param y The Y coordinate of the position.
	 * @param event The event that occurred.
	 */
	public void update(int x, int y, Event event);
	
	/**
	 * Performs an action according to the event and the collidable object received.
	 * @param c The collidable object.
	 * @param event The event that occurred.
	 */
	public void update(Collidable c, Event event);
}
