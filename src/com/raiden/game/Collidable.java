package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public abstract class Collidable implements Visitor, Observable {
	public int x;
	public int y;
	public int radius;
	protected int speed;
	
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
		
	private static Point bounds;
	
	private static final int DEFAULT_DAMAGE = 1;
	protected int collisionDamage = DEFAULT_DAMAGE;
	
	protected ArrayList<Observer> observers = new ArrayList<Observer>();
	
	protected static GameScreen gameScreen;
	
	public static void setGameScreen(GameScreen gameScreen){
		Collidable.gameScreen = gameScreen;
	}
	
	public static void setBounds(Point screenSize){
		bounds = screenSize;
		minX = 0; minY = 0;
		maxX = bounds.x - 1;
		maxY = bounds.y - 1;
		outMaxX = bounds.x + OFFSCREEN_LIMIT;
		outMaxY = bounds.y + OFFSCREEN_LIMIT;
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
	
	public void setCollisionDamage(int damage){
		this.collisionDamage = damage;
	}
	
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
