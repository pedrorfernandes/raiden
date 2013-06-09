package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

/**
 * The collidable class is the main class for the actors in the game (ships, bullets, power ups, etc).
 * It implements the visitor pattern to easily determine the consequences of a collision
 * between the various subclasses of collidable.
 * It also implements the observer pattern to easily control results of certain events
 * (for example, a bullet collision displays an explosion and plays a "boom" sound).
 */
public abstract class Collidable implements Visitor, Observable {
	public int x;
	public int y;
	public int radius;
	protected int speed;
	public int score;
	
	// box which every object can move in
	protected static int minX;
	protected static int minY;
	protected static int maxX;
	protected static int maxY;
	
	public static final int OFFSCREEN_LIMIT = 400;
	protected static int outMinX = - OFFSCREEN_LIMIT;
	protected static int outMinY = - OFFSCREEN_LIMIT;
	protected static int outMaxX;
	protected static int outMaxY;
			
	private static final int DEFAULT_DAMAGE = 1;
	protected int collisionDamage = DEFAULT_DAMAGE;
	
	protected ArrayList<Observer> observers = new ArrayList<Observer>();
	
	protected static GameScreen gameScreen;
	
	/**
	 * Sets all collidable objects to a gamescreen.
	 * @param gameScreen The current gamescreen.
	 */
	public static void setGameScreen(GameScreen gameScreen){
		Collidable.gameScreen = gameScreen;
	}
	
	/**
	 * Sets the bounds for all collidable objects.
	 * @param screenSize The point (x,y) that contains the screen size.
	 */
	public static void setBounds(Point screenSize){
		minX = 0; minY = 0;
		maxX = screenSize.x - 1;
		maxY = screenSize.y - 1;
		outMaxX = screenSize.x + OFFSCREEN_LIMIT;
		outMaxY = screenSize.y + OFFSCREEN_LIMIT;
	}
	
	/**
	 * Checks the collision between this collidable and another.
	 * If a collision is verified, this object will accept the other,
	 * which will determine the consequences of the collision.
	 * @param c The other collidable.
	 */
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

	/**
	 * Accept another collidable (as a visitor).
	 * @param other The other collidable.
	 */
	public abstract void accept(Collidable other);
	
	/**
	 * @return The x coordinate of the object.
	 */
	public int getX(){
		return x;
	}

	/**
	 * @return The y coordinate of the object.
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * @return The radius of the object.
	 */
	public int getRadius(){
		return radius;
	}
	
	/**
	 * @return The speed of the object.
	 */
	public int getSpeed(){
		return speed;
	}
	
	/**
	 * Sets a new speed for the object.
	 * @param speed The new speed.
	 */
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	/**
	 * Sets the collision damage for the object.
	 * @param damage The new collision damage.
	 */
	public void setCollisionDamage(int damage){
		this.collisionDamage = damage;
	}
	
	/**
	 * @return The damage given when a collision occurs.
	 */
	public int getCollisionDamage(){
		return collisionDamage;
	}
	
	public void notifyObservers(Event event){
		for (Observer observer : observers) {
			observer.update(this, event);
		}
	}
	
	public void addObserver(Observer observer){
		this.observers.add(observer);
	}
	
	public void removeObserver(Observer observer){
		this.observers.remove(observer);
	}
	
	public void notifyObservers(Collidable collidable, Event event){
		for (Observer observer : observers) {
			observer.update(collidable, event);
		}
	}
	
	public void notifyObservers(int x, int y, Event event){
		for (Observer observer : observers) {
			observer.update(x, y, event);
		}
	}
}
