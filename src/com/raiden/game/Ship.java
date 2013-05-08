package com.raiden.game;

import java.util.ArrayList;
import java.util.ListIterator;

import android.graphics.Point;

public class Ship extends Collidable {

	// ship speed
	private int moveSpeed = 15;
	
	// image sprite half size
	// TODO fix these, they should be the radius
	public final int halfSizeX = Assets.hero1.getWidth() / 2;
	public final int halfSizeY = Assets.hero1.getHeight() / 2;
	
	// position to move next
	private int newX = x;
	private int newY = y;
	
	// box which the player can move in
	private static int minX = 0;
	private static int minY = 0;
	private static int maxX = GameScreen.screenSize.x - 1;
	private static int maxY = GameScreen.screenSize.y - 1;
	
	// to know when the ship is actually turning or not
    final int LOW_THRESHOLD = 0;
    final int HIGH_THRESHOLD = 8;
    private int turningThreshold = HIGH_THRESHOLD;
    
    private ArrayList<Point> emptyTurretPositions; // positions relative to centerX
    private ArrayList<Turret> turrets;
    public static ArrayList<Bullet> shotsFired = new ArrayList<Bullet>();
    ListIterator<Bullet> bulletItr;
	private boolean readyToFire = true;
	private final int RELOAD_DONE = 20;
	private float reloadTime = RELOAD_DONE;
	
	public Ship() {
		
		// initial starting point
		x = GameScreen.screenSize.x / 2;
		y = GameScreen.screenSize.y - halfSizeY * 6;
		newX = x;
		newY = y;
		this.radius = 50;
		
		// fill the empty turret positions
		emptyTurretPositions = new ArrayList<Point>();
		emptyTurretPositions.add(new Point(  0, -halfSizeY));
		emptyTurretPositions.add(new Point(-25, -halfSizeY));
		emptyTurretPositions.add(new Point( 25, -halfSizeY));
		
		// create the starting turrets
		turrets = new ArrayList<Turret>();
		addTurret(90.0f);
		addTurret(90.0f+15.0f);
		addTurret(90.0f-15.0f);
	}
	
	public boolean addTurret(float firingAngle){
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
		// update ship X position
		if (newX < x) {
			if ( (x - newX) < moveSpeed)
				x -= (x - newX);
			else
				x -= moveSpeed;
		} 
		else if (newX > x){
			if ( (newX - x) < moveSpeed)
				x += (newX - x);
			else
				x += moveSpeed;
		}
		
		// update ship Y position
		if (newY < y) {
			if ( (y - newY) < moveSpeed)
				y -= (y - newY);
			else
				y -= moveSpeed;
		} 
		else if (newY > y) {
			if ( (newY - y) < moveSpeed)
				y += (newY - y);
			else
				y += moveSpeed;
		}
		
		// control the turning threshold to check if the ship will turn again later
		if ( x == newX && y == newY && turningThreshold < HIGH_THRESHOLD )
			turningThreshold += 2;
		
		// remove the bullets that are no longer in the screen
		bulletItr = shotsFired.listIterator();
		while(bulletItr.hasNext()){
			if ( ! bulletItr.next().isVisible() )
				bulletItr.remove();
		}
		
		for (Bullet bullet : Enemy.shotsFired) {
			this.checkCollision(bullet);
		}
		
		// check if reload time is done
		if (reloadTime >= RELOAD_DONE){
			readyToFire = true;
		}
		
		// reload weapons
		if (!readyToFire){
			reloadTime += deltaTime;
		}
	}
	
	public void move(int x, int y){
		newX += x;
		newY += y;
		
		// check to see if ship is
		// not out of bounds
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
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean isMoving(){
		return (x == newX && y == newY);
	}
	
	public boolean isMovingLeft(){
		if ( (newX - x) < 0 && Math.abs(newX - x) > turningThreshold ){
			turningThreshold = LOW_THRESHOLD;
			return true;
		}

		return false;
	}
	
	public boolean isMovingRight(){
		if ( (newX - x) > 0 && Math.abs(newX - x) > turningThreshold ){
			turningThreshold = LOW_THRESHOLD;
			return true;
		}
		return false;
	}

	public void accept(Collidable other) {
		other.visit(this);
	}
	
	@Override
	public void visit(Ship ship) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Bullet bullet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Enemy enemy) {
		// TODO Auto-generated method stub
		
	}
}
