package com.raiden.game;

public class ArmorObserver implements Observer {

	@Override
	public void update(int x, int y, Event event) {
		
	}

	@Override
	public void update(Collidable c, Event event) {
		this.update(c.x, c.y, event);		
	}

}
