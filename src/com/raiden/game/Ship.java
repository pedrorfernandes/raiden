package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

/**
 * A ship is a collidable that has armor and turrets that fire bullets.
 */
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
		// nothing happens
		return;
	}
	
	@Override
	public void visit(PowerUp powerUp) {
		// nothing happens
		return;
	}

	@Override
	public void accept(Collidable other) {
		other.visit(this);
	}

	/**
	 * Sets the armor (health)
	 * @param armor The new quantity of armor.
	 */
	public void setArmor(int armor){
		this.armor = armor;
	}

	/**
	 * @return The armor this ship has left.
	 */
	public int getArmor(){
		return armor;
	}
	
	public abstract boolean checkIfDestroyed();

	/**
	 * Take damage from a object that has hit this ship.
	 * @param collidable The object that collided.
	 */
	public void takeDamage(Collidable collidable){
		armor -= collidable.collisionDamage;
		this.checkIfDestroyed();
	}

	/**
	 * @return Bullet array of all the shots belonging to this ship.
	 */
	public Bullet[] getShotsFired(){
		return shots;
	}

	/**
	 * Orders each turret to fire.
	 * @return True if shots were fired, false otherwise.
	 */
	public boolean shoot() {
		if (readyToFire) {
			for (Turret turret: turrets) {
				turret.fire();
			}
			readyToFire = false;
			reloadTime = 0;
			return true;
		}
		return false;
	}

	/**
	 * Adds a new turret to the ship.
	 * @param firingAngle The angle of the shot (irrelevant if the ship has a target).
	 * @param bulletType The type of bullet to fire.
	 * @return True if turret was added successfully, false if there is no more space for turrets.
	 */
	public boolean addTurret(float firingAngle, Bullet.Type bulletType){
		if (emptyTurretPositions.size() == 0)
			return false;

		if (target == null)
			turrets.add(new Turret(this, emptyTurretPositions.get(0), firingAngle, bulletType));
		else
			turrets.add(new Turret(this, emptyTurretPositions.get(0), target, bulletType));
		emptyTurretPositions.remove(0);
		return true;
	}
	
	/**
	 * Adds a new turret to the ship.
	 * @param firingAngle The angle of the shot (irrelevant if the ship has a target).
	 * @return True if turret was added successfully, false if there is no more space for turrets.
	 */
	public boolean addTurret(float firingAngle){
		return addTurret(firingAngle, this.bulletType);
	}

	/**
	 * @return Waiting time for each reload.
	 */
	public int getTimeToReload(){
		return reloadDone;
	}
	
	/**
	 * @return If the ship is done reloading and ready to fire.
	 */
	public boolean isReadyToFire(){
		return readyToFire;
	}

	/**
	 * @return If the ship is alive.
	 */
	public boolean isAlive(){
		return alive;
	}
	
	/**
	 * Reloads the turrets for the next shot.
	 * When reload isn't done, the turrets cannot fire.
	 * @param deltaTime The time passed since the last update.
	 */
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
	
	/**
	 * Sets automatic firing when turrets are finished reloading.
	 * @param autofire The new auto fire setting.
	 */
	public void setAutoFire(boolean autofire){
		this.autofire = autofire;
	}
	
	/**
	 * @return If the ship is visible.
	 */
	public boolean isVisible(){
		return visible;
	}
		
	/**
	 * @return The turrets of this ship.
	 */
	public ArrayList<Turret> getTurrets(){
		return turrets;
	}
	
	/**
	 * Sets the ship armor to the maximum armor.
	 */
	public void repair(){
		this.armor = maxArmor;
	}

}
