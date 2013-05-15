package com.raiden.game;

import android.graphics.Point;

public abstract class Collidable implements Visitor {
	public int x;
	public int y;
	public int radius;
	protected int speed;
	
	// box which every object can move in
	protected static int minX;
	protected static int minY;
	protected static int maxX;
	protected static int maxY;
	
	protected static float scaleX;
	protected static float scaleY;
		
	private static Point bounds;
	
	public static void setBounds(Point screenSize){
		bounds = screenSize;
		minX = 0; minY = 0;
		maxX = bounds.x - 1;
		maxY = bounds.y - 1;
	}
	
	public static void setScale(float x, float y){
		scaleX = x;
		scaleY = y;
	}
	
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
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
}
