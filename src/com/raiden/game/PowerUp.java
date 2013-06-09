package com.raiden.game;

import java.util.ArrayList;

import com.raiden.framework.Image;
import com.raiden.framework.Sound;

/**
 * A power up alters a ship in a beneficial way, when the ship collides with it.
 */
public class PowerUp extends Collidable {

	public Type type;
	public boolean visible;
	private int moveX, moveY;
	
	/**
	 * The various types of power ups.
	 * Each type must define what it does to a ship.
	 */
	public enum Type {		
		HeavyBullets (6, 50, Assets.powerUp1, Assets.powerUpSound1, "HeavyBullets")
		{
			@Override
			public void powerUp(Ship ship){
				ArrayList<Turret> turrets = ship.getTurrets();
				for (Turret turret : turrets) {
					turret.setBulletType(Bullet.Type.HeroHeavy);
					this.notifyPowerUp(ship);
				}
			}
		},
		
		Machinegun (6, 50, Assets.powerUp2, Assets.powerUpSound2, "Machinegun")
		{
			@Override
			public void powerUp(Ship ship){
				ArrayList<Turret> turrets = ship.getTurrets();
				for (Turret turret : turrets) {
					turret.setAngle(90.0f);
				}
				ship.addTurret(90.0f);
				ship.addTurret(90.0f);
				this.notifyPowerUp(ship);
			}
		},
		
		ScatterShot (6, 50, Assets.powerUp3, Assets.powerUpSound3, "ScatterShot")
		{
			@Override
			public void powerUp(Ship ship){
				ArrayList<Turret> turrets = ship.getTurrets();
				float scatterAngle = 5.0f;
				int numberOfTurrets = turrets.size();
				for (int i = 1; i < numberOfTurrets-1; i += 2) {
					turrets.get(  i).setAngle(90.0f + scatterAngle);
					turrets.get(i+1).setAngle(90.0f - scatterAngle);
					scatterAngle += scatterAngle;
				}
				
				this.notifyPowerUp(ship);
			}
		},
		
		Repair (6, 50, Assets.powerUp4, Assets.powerUpSound4, "Repair")
		{
			@Override
			public void powerUp(Ship ship){
				ship.repair();
				notifyPowerUp(ship);
			}
		};

		public int speed;
		public int score;
		public Image image;
		public Sound sound;
		public String id;
		
		/**
		 * Constructor for a power up type.
		 * @param speed The speed of the power up.
		 * @param score The score that the power up gives.
		 * @param image Representation of the power up.
		 * @param sound The sound played when the power up is picked up.
		 * @param id The id string of the power up.
		 */
		Type(int speed, int score, Image image, Sound sound, String id){
			this.speed = speed;
			this.score = score;
			this.image = image;
			this.sound = sound;
			this.id = id;
		}
		
		/**
		 * Notifies the ship that a power up has been picked up.
		 * @param ship The ship that picked this power up.
		 */
		public void notifyPowerUp(Ship ship){
			Event.PowerUp.setPowerUpType(this);
			ship.notifyObservers(Event.PowerUp);
		}
		
		/**
		 * @param id String id of the power up.
		 * @return The power up type associated with the string id. Null if the string isn't associated with any type.
		 */
		public static Type getType(String id){
			Type[] types = Type.values();
			for (Type type : types){
				if ( type.id.equals(id) )
					return type;
			}
			return null;
		}
		
		public abstract void powerUp(Ship ship);
	}
	
	/**
	 * Creates a new power up.
	 * To make the power up appear in game, check powerUp.spawn()
	 */
	public PowerUp(){
		this.visible = false;
	}
	
	/**
	 * Spawns a power up.
	 * @param x The X starting position.
	 * @param y The Y starting position.
	 * @param type The type of power up to spawn.
	 */
	public void spawn(int x, int y, Type type){
		this.type = type;
		this.setSpeed(type.speed);
		this.score = type.score;
		this.visible = true;
		this.x = x;
		this.y = y;
		this.radius = 70;
	}
	
	@Override
	public void setSpeed(int speed){
		this.speed = speed;
		this.moveX = 0;
		this.moveY = speed;
	}
	
	/**
	 * Updates a power up position and visibility in the game.
	 * @param deltaTime The time passed since the last update.
	 */
	public void update(float deltaTime){

		if (!visible) return;
		
		x += moveX;
		y += moveY;

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;
		else
			visible = true;
	}
	
	/**
	 * @return If the power up is visible.
	 */
	public boolean isVisible(){
		return visible;
	}
	
	@Override
	public void visit(Ship ship) {
		this.type.powerUp(ship);
		notifyObservers(this, Event.ScoreUp);
		this.visible = false;
	}

	@Override
	public void visit(Bullet bullet) {
		// nothing happens
		return;
	}

	@Override
	public void accept(Collidable other) {
		other.visit(this);
	}

	@Override
	public void visit(PowerUp powerUp) {
		// nothing happens
		return;	
	}
}
