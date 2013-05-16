package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public class Enemy extends Collidable {
	private static final int RADIUS = 50;
	private static final int SPEED = 6;
	private static final int ARMOR = 4;
	
	public boolean visible, outOfRange, alive;
	public float angle;
	private float radians;
	
	private int moveX;
	private int moveY;

	private int armor;

	private Ship target;

	private ArrayList<Point> emptyTurretPositions; // positions relative to centerX
	private ArrayList<Turret> turrets;
	private static final int MAX_BULLETS = 10;
	public Bullet[] shots = new Bullet[MAX_BULLETS];

	private boolean readyToFire = true;
	private int reloadDone = 700;
	private float reloadTime = reloadDone;

	private static final int IMPACT_INTERVAL = 500;
	private int impactTimer = IMPACT_INTERVAL;

	// iterating variables
	private static Bullet bullet;
	private static int length;
	
	public void setTarget(Ship target){
		this.target = target;
		for (Turret turret: turrets) {
			turret.setTarget(target);
		}
	}
	
	public Bullet[] getShotsFired(){
		return shots;
	}

	public Enemy(Ship target) {
		this.radius =(int) (RADIUS * scaleX);
		this.speed = (int) Math.ceil(SPEED * scaleX);
		this.visible = false;
		this.outOfRange = true;
		this.alive = false;
		
		this.target = target;

		emptyTurretPositions = new ArrayList<Point>();
		emptyTurretPositions.add(new Point(0, 0));

		turrets = new ArrayList<Turret>();
		addTurret(0.0f);
		
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

	public boolean addTurret(float firingAngle){
		if (emptyTurretPositions.size() == 0)
			return false;

		if (target == null)
			// just fire downwards
			turrets.add(new Turret(this, emptyTurretPositions.get(0), 0.0f));
		else
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

		if (armor < 1) 
			alive = false;

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

		// reload weapons
		if (!readyToFire){
			reloadTime += deltaTime;
			// check if reload time is done
			if (reloadTime >= reloadDone){
				readyToFire = true;
			}
		} else if (target != null) {
			shoot();
		}

		if (impactTimer < IMPACT_INTERVAL)
			impactTimer += deltaTime;
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
	
	public boolean isAlive(){
		return alive;
	}

	public float getAngle() {
		return angle;
	}

	public void accept(Collidable other) {
		other.visit(this);
	}

	@Override
	public void visit(Ship ship) {
		if (impactTimer == IMPACT_INTERVAL){
			int midPointX = (this.x + ship.x) / 2;
			int midPointY = (this.y + ship.y) / 2;
			ship.addEnemyImpact(midPointX, midPointY);
			impactTimer = 0;
		}
	}

	@Override
	public void visit(Bullet bullet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Enemy enemy) {
		// TODO Auto-generated method stub

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
}
