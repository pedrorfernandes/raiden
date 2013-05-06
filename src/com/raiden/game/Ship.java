package com.raiden.game;

import java.util.ArrayList;
import java.util.ListIterator;

import android.graphics.Point;

public class Ship {

	// ship speed
	private int moveSpeed = 15;
	
	// image sprite half size
	public final int halfSizeX = Assets.hero1.getWidth() / 2;
	public final int halfSizeY = Assets.hero1.getHeight() / 2;

	// initial starting point
	public int centerX = GameScreen.screenSize.x / 2;
	public int centerY = GameScreen.screenSize.y - halfSizeY * 6;
	
	// position to move next
	private int newX = centerX;
	private int newY = centerY;
	
	// box which the player can move in
	private int minX = 0;
	private int minY = 0;
	private int maxX = GameScreen.screenSize.x - 1;
	private int maxY = GameScreen.screenSize.y - 1;
	
	// to know when the ship is actually turning or not
    final int LOW_THRESHOLD = 0;
    final int HIGH_THRESHOLD = 8;
    private int turningThreshold = HIGH_THRESHOLD;
    
    private ArrayList<Point> emptyTurretPositions; // positions relative to centerX
    private ArrayList<Turret> turrets;
    private ArrayList<Bullet> shotsFired;
	private boolean readyToFire = true;
	private final int RELOAD_DONE = 20;
	private float reloadTime = RELOAD_DONE;
	
	public Ship() {
		// fill the empty turret positions
		emptyTurretPositions = new ArrayList<Point>();
		emptyTurretPositions.add(new Point( 0, -halfSizeY));
		emptyTurretPositions.add(new Point(-25, -halfSizeY));
		emptyTurretPositions.add(new Point( 25, -halfSizeY));
		
		turrets = new ArrayList<Turret>();
		addTurret(90.0);
		addTurret(90.0+15.0);
		addTurret(90.0-15.0);
		
		shotsFired = new ArrayList<Bullet>();
	}
	
	public boolean addTurret(double firingAngle){
		if (emptyTurretPositions.size() == 0)
			return false;
		
		turrets.add(new Turret(this, emptyTurretPositions.get(0), firingAngle));
		emptyTurretPositions.remove(0);
		return true;
	}
	
	public boolean shoot() {
		if (readyToFire) {
			for (Turret turret: turrets) {
				shotsFired.add(turret.fire());
			}
			readyToFire = false;
			reloadTime = 0;
			return true;
		}
		return false;
	}
	
	public ArrayList<Bullet> getShotsFired(){
		return shotsFired;
	}

	public void update(float deltaTime) {
		if (newX < centerX) {
			if ( (centerX - newX) < moveSpeed)
				centerX -= (centerX - newX);
			else
				centerX -= moveSpeed;
		} 
		else if (newX > centerX){
			if ( (newX - centerX) < moveSpeed)
				centerX += (newX - centerX);
			else
				centerX += moveSpeed;
		}
		
		if (newY < centerY) {
			if ( (centerY - newY) < moveSpeed)
				centerY -= (centerY - newY);
			else
				centerY -= moveSpeed;
		} 
		else if (newY > centerY) {
			if ( (newY - centerY) < moveSpeed)
				centerY += (newY - centerY);
			else
				centerY += moveSpeed;
		}
		
		if ( centerX == newX && centerY == newY && turningThreshold < HIGH_THRESHOLD )
			turningThreshold += 2;
		
		ListIterator<Bullet> bulletItr = shotsFired.listIterator();
		while(bulletItr.hasNext()){
			if ( ! bulletItr.next().isVisible() )
				bulletItr.remove();
		}
		
		if (reloadTime >= RELOAD_DONE){
			readyToFire = true;
		}
		
		if (!readyToFire){
			reloadTime += deltaTime;
		}
	}
	
	public void move(int x, int y){
		newX += x;
		newY += y;
		if (newX < minX)
			newX = minX;
		if (newY < minY)
			newY = minY;
		if (newX > maxX)
			newX = maxX;
		if (newY > maxY)
			newY = maxY;
	}
	
	public int getX(){
		return centerX;
	}
	
	public int getY(){
		return centerY;
	}
	
	public boolean isMoving(){
		return (centerX == newX && centerY == newY);
	}
	
	public boolean isMovingLeft(){
		if ( (newX - centerX) < 0 && Math.abs(newX - centerX) > turningThreshold ){
			turningThreshold = LOW_THRESHOLD;
			return true;
		}

		return false;
	}
	
	public boolean isMovingRight(){
		if ( (newX - centerX) > 0 && Math.abs(newX - centerX) > turningThreshold ){
			turningThreshold = LOW_THRESHOLD;
			return true;
		}
		return false;
	}
}
