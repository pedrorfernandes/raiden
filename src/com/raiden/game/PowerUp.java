package com.raiden.game;

import java.util.ArrayList;

import com.raiden.framework.Image;
import com.raiden.framework.Sound;

public class PowerUp extends Collidable {

	public Type type;
	public boolean visible;
	private int moveX, moveY;
	
	public enum Type {		
		HeavyBullets (6, Assets.powerUp1, Assets.powerUpSound1, "HeavyBullets")
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
		
		Machinegun (6, Assets.powerUp2, Assets.powerUpSound2, "Machinegun")
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
		
		ScatterShot (6, Assets.powerUp3, Assets.powerUpSound3, "ScatterShot")
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
		
		Repair (6, Assets.powerUp4, Assets.powerUpSound4, "Repair")
		{
			@Override
			public void powerUp(Ship ship){
				ship.repair();
				notifyPowerUp(ship);
			}
		};

		public int speed;
		public Image image;
		public Sound sound;
		public String id;
		
		Type(int speed, Image image, Sound sound, String id){
			this.speed = speed;
			this.image = image;
			this.sound = sound;
			this.id = id;
		}
		
		public void notifyPowerUp(Ship ship){
			Event.PowerUp.setPowerUpType(this);
			ship.notifyObservers(Event.PowerUp);
		}
		
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
	
	public PowerUp(){
		this.visible = false;
	}
	
	public void spawn(int x, int y, Type type){
		this.type = type;
		this.setSpeed(type.speed);
		this.visible = true;
		this.x = x;
		this.y = y;
		this.radius = 70;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
		this.moveX = 0;
		this.moveY = speed;
	}
	
	public void update(float deltaTime){

		if (!visible) return;
		
		x += moveX;
		y += moveY;

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;
		else
			visible = true;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	@Override
	public void visit(Ship ship) {
		this.type.powerUp(ship);
		this.visible = false;
	}

	@Override
	public void visit(Bullet bullet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept(Collidable other) {
		other.visit(this);
	}

	@Override
	public void visit(PowerUp powerUp) {
		// TODO Auto-generated method stub
	}

	
}
