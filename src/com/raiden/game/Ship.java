package com.raiden.game;

import java.util.ArrayList;

import android.graphics.Point;

public class Ship extends Collidable {

	protected int armor;
	protected boolean alive;
	
	// empty turrets -> positions relative to centerX
	protected ArrayList<Point> emptyTurretPositions;
	protected ArrayList<Turret> turrets;
	public Bullet[] shots;

	protected boolean readyToFire;
	protected float reloadTime;
	protected int reloadDone;

	protected Ship target = null;

	protected ArrayList<Point> impacts;
	protected static final int IMPACT_INTERVAL = 500;
	protected int impactTimer = IMPACT_INTERVAL;

	@Override
	public void visit(Ship ship) {
		if (impactTimer == IMPACT_INTERVAL){
			int midPointX = (this.x + ship.x) / 2;
			int midPointY = (this.y + ship.y) / 2;
			ship.takeDamage(collisionDamage);
			this.takeDamage(ship.getCollisionDamage());
			ship.addImpact(midPointX, midPointY);
			impactTimer = 0;
		}
	}

	@Override
	public void visit(Bullet bullet) {
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

	public void takeDamage(int damage){
		armor -= damage;
		if (armor < 1){
			alive = false;
		}
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

	public boolean addTurret(float firingAngle){
		if (emptyTurretPositions.size() == 0)
			return false;

		if (target == null)
			// just fire downwards
			turrets.add(new Turret(this, emptyTurretPositions.get(0), firingAngle));
		else
			turrets.add(new Turret(this, emptyTurretPositions.get(0), target));
		emptyTurretPositions.remove(0);
		return true;
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

	public ArrayList<Point> getImpacts(){
		ArrayList<Point> impactsCopy = new ArrayList<Point>(impacts);
		impacts.clear();
		return impactsCopy;
	}

	public void addImpact(int x, int y){
		impacts.add(new Point(x,y));
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

}
