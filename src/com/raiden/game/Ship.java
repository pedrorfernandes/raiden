package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public class Ship extends Collidable {
	private static final int RADIUS = 30;
	private static final int SPEED = 15;
	
	// position to move next
	private int newX;
	private int newY;

	// to know when the ship is actually turning or not
	final int LOW_THRESHOLD = 0;
	final int HIGH_THRESHOLD = (int) (8 * scaleX);
	private int turningThreshold = HIGH_THRESHOLD;

	private ArrayList<Point> emptyTurretPositions; // positions relative to centerX
	private ArrayList<Turret> turrets;
	private static final int MAX_BULLETS = 30;
	public Bullet[] shots = new Bullet[MAX_BULLETS];
	
	private Enemy[] enemies;
	
	private ArrayList<Point> enemyImpacts = new ArrayList<Point>();
	
	private boolean readyToFire = true;
	private int reloadDone = 200;
	private float reloadTime = reloadDone;
	
	// iterating variables
	private static Enemy enemy;
	private static Bullet bullet;
	private static int length;
	
	private boolean alive;
	
	public int armor;
	
	public void setTargets(Enemy[] enemies){
		this.enemies = enemies;
	}

	public Ship() {
		radius = (int)(RADIUS * scaleX);
		int halfSizeY = (int)(radius * 2);
		
		// initial starting point
		x = maxX / 2;
		y = maxY - halfSizeY * 6;
		newX = x;
		newY = y;
		
		alive = true;
		speed = (int) Math.ceil(SPEED * scaleX);
		
		for (int i = 0; i < MAX_BULLETS; i++)
		{
			shots[i] = new Bullet();
		}

		// fill the empty turret positions
		emptyTurretPositions = new ArrayList<Point>();
		emptyTurretPositions.add(new Point(-(int)(36*scaleX), -halfSizeY));
		emptyTurretPositions.add(new Point( (int)(36*scaleX), -halfSizeY));
		emptyTurretPositions.add(new Point(  0              , -halfSizeY));

		// create the starting turrets
		turrets = new ArrayList<Turret>();
		addTurret(90.0f);
		addTurret(90.0f);
		//addTurret(90.0f+15.0f);
		//addTurret(90.0f-15.0f);
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
				turret.fire(shots);
			}
			readyToFire = false;
			reloadTime = 0;
			return true;
		}
		return false;
	}

	public Bullet[] getShotsFired(){
		return shots;
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

		// control the turning threshold to check if the ship will turn again later
		if ( x == newX && y == newY && turningThreshold < HIGH_THRESHOLD )
			turningThreshold += 2;
		
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

		// reload weapons
		if (!readyToFire){
			reloadTime += deltaTime;
			// check if reload time is done
			if (reloadTime >= reloadDone){
				readyToFire = true;
			}
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
	
	public ArrayList<Point> getEnemyImpacts(){
		ArrayList<Point> impacts = new ArrayList<Point>(enemyImpacts);
		enemyImpacts.clear();
		return impacts;
	}
	
	public void addEnemyImpact(int x, int y){
		enemyImpacts.add(new Point(x,y));
	}
	
	public void accept(Collidable other) {
		other.visit(this);
	}
	
	public void moveTo(int x, int y){
		this.move(x - this.x, y - this.y);
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
	
	public int getTimeToReload(){
		return reloadDone;
	}
	
	public void setArmor(int armor){
		this.armor = armor;
	}
	
	public int getArmor(){
		return armor;
	}
	
	public void takeDamage(int damage){
		armor -= damage;
		if (armor < 1){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
}
