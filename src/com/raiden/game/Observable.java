package com.raiden.game;

public interface Observable {
	public void notifyObservers(int x, int y, Event event);
	public void notifyObservers(Collidable collidable, Event event);
	public void notifyObservers(Event event);
	public void addObserver(Observer observer);
	public void removeObserver(Observer observer);
}
