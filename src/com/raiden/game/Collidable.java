package com.raiden.game;

public abstract class Collidable implements Visitor {
	public int hitX;
	public int hitY;
	public int radius;
	
	public void checkCollision(Collidable c){
	    int dx = c.hitX - this.hitX;
	    int dy = c.hitY - this.hitY;
	    int radii = c.radius + this.radius;
	    if ( ( dx * dx )  + ( dy * dy ) < radii * radii ) {
	        this.accept(c);
	    } else {
	        return;
	    }
	}

	public abstract void accept(Collidable other);
}
