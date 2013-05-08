package com.raiden.game;

public abstract class Collidable implements Visitor {
	public int x;
	public int y;
	public int radius;
	
	public void checkCollision(Collidable c){
	    int dx = c.x - this.x;
	    int dy = c.y - this.y;
	    int radii = c.radius + this.radius;
	    if ( ( dx * dx )  + ( dy * dy ) < radii * radii ) {
	        this.accept(c);
	    } else {
	        return;
	    }
	}

	public abstract void accept(Collidable other);
}
