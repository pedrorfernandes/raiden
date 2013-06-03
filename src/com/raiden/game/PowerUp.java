package com.raiden.game;

import java.util.ArrayList;

import com.raiden.framework.Image;

public class PowerUp extends Collidable {

	public Type type;
	public boolean visible;
	private int moveX, moveY;
	
	public enum Type {		
		HeavyBullets(6, Assets.powerUp1, "HeavyBullets"){
			@Override
			public void powerUp(Ship ship){
				ArrayList<Turret> turrets = ship.getTurrets();
				for (Turret turret : turrets) {
					turret.setBulletType(Bullet.Type.HeroHeavy);
				}
			}
		};

		public int speed;
		public Image image;
		public String id;
		
		Type(int speed, Image image, String id){
			this.speed = speed;
			this.image = image;
			this.id = id;
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
		type.powerUp(ship);
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
