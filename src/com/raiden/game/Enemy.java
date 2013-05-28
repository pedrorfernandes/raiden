package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public class Enemy extends Ship {
	private static final int RADIUS = 50;
	private static final int SPEED = 6;
	private static final int ARMOR = 4;
	
	public boolean outOfRange;
	public float angle;
	private float radians;
	
	private int moveX;
	private int moveY;

	private static final int MAX_BULLETS = 10;

	private static final int RELOAD_DONE = 1400;

	// iterating variables
	private static Bullet bullet;
	private static int length;
	
	public void setTarget(Ship target){
		this.target = target;
		for (Turret turret: turrets) {
			turret.setTarget(target);
		}
	}

	public Enemy(Ship target) {
		this.radius = RADIUS;
		this.speed = SPEED;
		this.visible = false;
		this.outOfRange = true;
		this.alive = false;
		this.autofire = true;
		this.target = target;

		readyToFire = true;
		reloadDone = RELOAD_DONE;
		reloadTime = reloadDone;

		emptyTurretPositions = new ArrayList<Point>();
		emptyTurretPositions.add(new Point(0, 0));

		turrets = new ArrayList<Turret>();
		addTurret(0.0f);
		
		shots = new Bullet[MAX_BULLETS];
		
		for (int i = 0; i < MAX_BULLETS; i++)
		{
			shots[i] = new Bullet();
		}
	}

	public void spawn(int x, int y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.visible = true;
		this.outOfRange = false;
		this.radians = (float) Math.toRadians(angle);
		this.moveX = (int) (speed * FastMath.cos(radians));
		this.moveY = (int) (speed * FastMath.sin(-radians));
		this.alive = true;
		this.armor = ARMOR;
		this.impactTimer = IMPACT_INTERVAL;
	}
	
	public void spawn(int x, int y, float angle, int speed) {
		setSpeed(speed);
		spawn(x, y, angle);
	}

	public void update(float deltaTime){

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
		
		if (target != null)
			length = target.shots.length;
		else 
			length = 0;
		for (int i = 0; i < length; i++) {
			bullet = target.shots[i];
			if (bullet.visible)
				this.checkCollision(bullet);
		}

		reload(deltaTime);

		if (target != null && autofire) {
			shoot();
		}

		if (impactTimer < IMPACT_INTERVAL)
			impactTimer += deltaTime;
	}

	public boolean isInGame(){
		return (alive && !outOfRange);
	}

	public void turn(float degrees){
		// this will turn the ship
	}

	public boolean isOutOfRange(){
		return outOfRange;
	}
	
	public boolean isAlive(){
		return alive;
	}

	public float getAngle() {
		return angle;
	}
	
	@Override
	public void setSpeed(int speed){
		this.speed = speed;
		this.moveX = (int) (speed * FastMath.cos(radians));
		this.moveY = (int) (speed * FastMath.sin(-radians));
	}
	
	public void setDirection(float angle){
		this.angle = angle;
		this.radians = (float) Math.toRadians(angle);
		this.moveX = (int) (speed * FastMath.cos(radians));
		this.moveY = (int) (speed * FastMath.sin(-radians));
	}
	
	@Override
	public void takeDamage(Collidable collidable){
		notifyObservers(collidable, Event.EnemyHit);
		super.takeDamage(collidable);
	}
}
