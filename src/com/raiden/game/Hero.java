package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public class Hero extends Ship {
	private static final int RADIUS = 30;
	private static final int SPEED = 15;
	
	// position to move next
	private int newX;
	private int newY;

	// to know when the ship is actually turning or not
	final int LOW_THRESHOLD = 0;
	final int HIGH_THRESHOLD = 8;
	private int turningThreshold = HIGH_THRESHOLD;

	private static final int MAX_BULLETS = 30;
	
	private static final int RELOAD_DONE = 200;
	
	private Enemy[] enemies;
		
	// iterating variables
	private static Enemy enemy;
	private static Bullet bullet;
	private static int length;
	
	public void setTargets(Enemy[] enemies){
		this.enemies = enemies;
	}

	public Hero() {
		armor = 9001; // http://bit.ly/jxMrWX
		radius = RADIUS;
		
		int halfSizeY = (radius * 2);
		
		// initial starting point
		x = maxX / 2;
		y = maxY - halfSizeY * 6;
		newX = x;
		newY = y;
		
		alive = true;
		speed = SPEED;
		visible = true;
		
		readyToFire = true;
		reloadDone = RELOAD_DONE;
		reloadTime = reloadDone;
				
		shots = new Bullet[MAX_BULLETS];
		
		for (int i = 0; i < MAX_BULLETS; i++)
		{
			shots[i] = new Bullet();
		}

		// fill the empty turret positions
		emptyTurretPositions = new ArrayList<Point>();
		emptyTurretPositions.add(new Point(-36, -halfSizeY));
		emptyTurretPositions.add(new Point( 36, -halfSizeY));
		emptyTurretPositions.add(new Point(  0, -halfSizeY));

		// create the starting turrets
		turrets = new ArrayList<Turret>();
		addTurret(90.0f, Bullet.Type.Hero);
		addTurret(90.0f, Bullet.Type.Hero);
		//addTurret(90.0f+15.0f);
		//addTurret(90.0f-15.0f);
	}

	public void update(float deltaTime) {
		// update ship X position
		if (newX < x) {
			if ( (x - newX) < speed)
				x -= (x - newX);
			else
				x -= speed;
		} 
		else if (newX > x){
			if ( (newX - x) < speed)
				x += (newX - x);
			else
				x += speed;
		}

		// update ship Y position
		if (newY < y) {
			if ( (y - newY) < speed)
				y -= (y - newY);
			else
				y -= speed;
		} 
		else if (newY > y) {
			if ( (newY - y) < speed)
				y += (newY - y);
			else
				y += speed;
		}
		
		// check collision with enemies and their bullets
		if (enemies != null)
			length = enemies.length;
		else
			length = 0;
		for (int i = 0; i < length; i++) {
			enemy = enemies[i];
			if ( enemy.isInGame() ){
				this.checkCollision(enemy);
			}
			for (int j = 0; j < enemy.shots.length; j++) {
				bullet = enemy.shots[j];
				if (bullet.visible)
					this.checkCollision(bullet);
			}
		}

		reload(deltaTime);
		
		checkTurning();
		
		if (autofire) shoot();
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

	public boolean isMoving(){
		return (x == newX && y == newY);
	}

	public void checkTurning(){
		// control the turning threshold to check if the ship will turn again later
		if ( x == newX && y == newY && turningThreshold < HIGH_THRESHOLD )
			turningThreshold += 2;
		
		if ( (newX - x) < 0 && Math.abs(newX - x) > turningThreshold ){
			turningThreshold = LOW_THRESHOLD;
			notifyObservers(Event.TurnLeft);
		} else if ( (newX - x) > 0 && Math.abs(newX - x) > turningThreshold ){
			turningThreshold = LOW_THRESHOLD;
			notifyObservers(Event.TurnRight);
		} else {
			notifyObservers(Event.StopTurning);
		}
	}
	
	public void moveTo(int x, int y){
		this.move(x - this.x, y - this.y);
	}
	
	@Override
	public void takeDamage(Collidable collidable){
		notifyObservers(collidable, Event.HeroHit);
		super.takeDamage(collidable);
	}
	
	@Override
	public void setAutoFire(boolean autofire){
		if (autofire == true)
			notifyObservers(Event.StartFiring);
		else {
			notifyObservers(Event.StopFiring);
		}
		super.setAutoFire(autofire);
	}
	
	public boolean checkIfDestroyed(){
		if (armor < 1){
			alive = false; visible = false;
			notifyObservers(Event.Explosion);
			// possibly respawn hero
			return true;
		} else {
			return false;
		}
	}
}
