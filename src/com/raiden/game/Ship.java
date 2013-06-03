package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public abstract class Ship extends Collidable {

	protected int armor, maxArmor;
	protected boolean alive, visible;
	
	// empty turrets -> positions relative to centerX
	protected ArrayList<Point> emptyTurretPositions;
	protected ArrayList<Turret> turrets;
	public Bullet[] shots;

	protected boolean readyToFire;
	protected float reloadTime;
	protected int reloadDone;

	protected Ship target = null;

	protected static final int IMPACT_INTERVAL = 500;
	protected int impactTimer = IMPACT_INTERVAL;
	
	protected boolean autofire;
	
	public Bullet.Type bulletType;
	
	@Override
	public void visit(Ship ship) {
		if (impactTimer == IMPACT_INTERVAL){
			int midPointX = (this.x + ship.x) / 2;
			int midPointY = (this.y + ship.y) / 2;
			ship.takeDamage(this);
			this.takeDamage(ship);
			impactTimer = 0;
			notifyObservers(midPointX, midPointY, Event.Collision);
		}
	}

	@Override
	public void visit(Bullet bullet) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void visit(PowerUp powerUp) {
		// TODO Auto-generated method stub
	}

	@Override
	public void accept(Collidable other) {
		other.visit(this);
	}

	public void setArmor(int armor){
		this.armor = armor;
	}

	public int getArmor(){
		return armor;
	}
	
	public abstract boolean checkIfDestroyed();

	public void takeDamage(Collidable collidable){
		armor -= collidable.collisionDamage;
		this.checkIfDestroyed();
	}

	public Bullet[] getShotsFired(){
		return shots;
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

	public boolean addTurret(float firingAngle, Bullet.Type bulletType){
		if (emptyTurretPositions.size() == 0)
			return false;

		if (target == null)
			// just fire downwards
			turrets.add(new Turret(this, emptyTurretPositions.get(0), firingAngle, bulletType));
		else
			turrets.add(new Turret(this, emptyTurretPositions.get(0), target, bulletType));
		emptyTurretPositions.remove(0);
		return true;
	}
	
	public boolean addTurret(float firingAngle){
		return addTurret(firingAngle, this.bulletType);
	}

	public int getTimeToReload(){
		return reloadDone;
	}
	
	public boolean isReadyToFire(){
		return readyToFire;
	}

	public boolean isAlive(){
		return alive;
	}
	
	protected void reload(float deltaTime){
		if (!readyToFire){
			reloadTime += deltaTime;
		}
		
		// check if reload time is done
		if (reloadTime >= reloadDone){
			readyToFire = true;
		}
	}
	
	@Override
	public void addObserver(Observer observer){
		this.observers.add(observer);
		for (Bullet bullet : shots) {
			bullet.addObserver(observer);
		}
	}
	
	public void setAutoFire(boolean autofire){
		this.autofire = autofire;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public ArrayList<Turret> getTurrets(){
		return turrets;
	}
	
	public void repair(){
		this.armor = maxArmor;
	}

}
