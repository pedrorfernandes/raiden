package com.raiden.game;

public interface Observer {
	public void update(int x, int y, Event event);
	public void update(Collidable c, Event event);
}
