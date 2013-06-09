package com.raiden.game;

/**
 * An observable object must hold a list of all his observers
 * and notify them when a certain event occurs.
 */
public interface Observable {
	
	/**
	 * Notify observers of an event and an associated position with it.
	 * @param x The X coordinate of the position.
	 * @param y The Y coordinate of the position.
	 * @param event The event to notify.
	 */
	public void notifyObservers(int x, int y, Event event);
	
	/**
	 * Notify observers of an event and an associated collidable object with it.
	 * @param collidable The collidable object associated with the event.
	 * @param event The event to notify.
	 */
	public void notifyObservers(Collidable collidable, Event event);
	
	/**
	 * Notifies every observer of an event.
	 * @param event The event to notify.
	 */
	public void notifyObservers(Event event);
	
	/**
	 * Adds a new observer for this object.
	 * @param observer The new observer.
	 */
	public void addObserver(Observer observer);
	
	/**
	 * Removes a observer from this object.
	 * @param observer The observer to remove.
	 */
	public void removeObserver(Observer observer);
}
