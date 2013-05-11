package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public class Ship extends Collidable {

	// ship speed
	private int speed = (int) Math.ceil(15 * GameScreen.scaleX);

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
	final int HIGH_THRESHOLD = (int) (8 * GameScreen.scaleX);
	private int turningThreshold = HIGH_THRESHOLD;

	private ArrayList<Point> emptyTurretPositions; // positions relative to centerX
	private ArrayList<Turret> turrets;
	private static final int MAX_BULLETS = 30;
	public static Bullet[] shots = new Bullet[MAX_BULLETS];
	
	private ArrayList<Point> enemyImpacts = new ArrayList<Point>();
	
	//ListIterator<Bullet> bulletItr;
	private boolean readyToFire = true;
	private final int RELOAD_DONE = 200;
	private float reloadTime = RELOAD_DONE;
	
	// iterating variables
	private static Enemy enemy;
	private static Bullet bullet;
	private static int length;

	static
	{
		for (int i = 0; i < MAX_BULLETS; i++)
		{
			shots[i] = new Bullet();
		}
	}

	public Ship() {

		// initial starting point
		x = GameScreen.screenSize.x / 2;
		y = GameScreen.screenSize.y - halfSizeY * 6;
		newX = x;
		newY = y;
		this.radius = (int)(40 * GameScreen.scaleX);

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
				turret.fire(shots);
			}
			readyToFire = false;
			reloadTime = 0;
			return true;
		}
		return false;
	}

	/*
	public ArrayList<Bullet> getShotsFired(){
		return shots;
	}
	*/

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
		length = GameScreen.enemies.length;
		for (int i = 0; i < length; i++) {
			enemy = GameScreen.enemies[i];
			if ( enemy.isInGame() ){
				this.checkCollision(enemy);
			}
			for (int j = 0; j < enemy.shots.length; j++) {
				bullet = enemy.shots[j];
				if (bullet.visible)
					this.checkCollision(bullet);
			}
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
