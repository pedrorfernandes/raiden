package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public class Enemy extends Collidable {
	private int speed;
	public boolean visible, outOfRange, alive;
	public float angle;
	private double radians;
	
	private static int minX = 0;
	private static int minY = 0;
	private static int maxX = GameScreen.screenSize.x - 1;
	private static int maxY = GameScreen.screenSize.y - 1;
	
	private static final int BOUNDS = 100;
	private static int outMinX = - BOUNDS;
	private static int outMinY = - BOUNDS;
	private static int outMaxX = GameScreen.screenSize.x + BOUNDS;
	private static int outMaxY = GameScreen.screenSize.y + BOUNDS;
	
	private int moveX;
	private int moveY;
	
	public int health;
	
	private static Ship target = GameScreen.hero;
	
    private ArrayList<Point> emptyTurretPositions; // positions relative to centerX
    private ArrayList<Turret> turrets;
    public static ArrayList<Bullet> shots = new ArrayList<Bullet>();
	private static final int MAX_BULLETS = 60;
	
	private boolean readyToFire = true;
	private final int RELOAD_DONE = 500;
	private float reloadTime = RELOAD_DONE;
	
	// iterating variables
	private static Bullet bullet;
	private static int length;
    
	static
	{
		for (int i = 0; i < MAX_BULLETS; i++)
		{
			shots.add(new Bullet());
		}
	}
	
	public Enemy() {
		this.radius = 55;
		this.speed = 7;
		this.visible = false;
		this.outOfRange = true;
		this.alive = false;
		
		emptyTurretPositions = new ArrayList<Point>();
		emptyTurretPositions.add(new Point(0, 0));
		
		turrets = new ArrayList<Turret>();
		addTurret(0.0f);
	}
	
	public void spawn(int x, int y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = (float)angle;
		this.visible = true;
		this.outOfRange = false;
		this.radians = Math.toRadians(angle);
		this.moveX = (int)(speed * Math.cos(radians));
		this.moveY = (int)(speed * Math.sin(-radians));
		this.alive = true;
		this.health = 60;
	}
	
	public boolean addTurret(float firingAngle){
		if (emptyTurretPositions.size() == 0)
			return false;
		
		turrets.add(new Turret(this, emptyTurretPositions.get(0), target));
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
	
	public void update(float deltaTime){
		
		if (health <= 0) alive = false;
		
		if (outOfRange || !alive) return;
		
		x += moveX;
		y += moveY;

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;

		if ( !visible ){
			// if the enemy is out of bounds, it isn't coming back to the screen
			if (x < outMinX || y < outMinY || x > outMaxX || y > outMaxY){
				outOfRange = true;
				return;
			}
		}
		
		length = Ship.shots.size();
		for (int i = 0; i < length; i++) {
			bullet = Ship.shots.get(i);
			if (bullet.visible)
				this.checkCollision(bullet);
		}
		
		
		// check if reload time is done
		if (reloadTime >= RELOAD_DONE){
			readyToFire = true;
		}
		
		// reload weapons
		if (!readyToFire){
			reloadTime += deltaTime;
		} else {
			shoot();
		}
	}
	
	public boolean hasDied(){
		if (!this.alive && this.visible){
			this.visible = false;
			return true;
		}
		return false;
	}
	
	public boolean isInGame(){
		return (alive && !outOfRange);
	}
	
	public void turn(float degrees){
		// this will turn the ship
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public boolean isOutOfRange(){
		return outOfRange;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public float getAngle() {
		return angle;
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
