package com.raiden.test;

import com.raiden.game.Bullet;
import com.raiden.game.Collidable;
import com.raiden.game.Enemy;
import com.raiden.game.Hero;

import android.graphics.Point;
import android.test.AndroidTestCase;

public class CollisionTests extends AndroidTestCase {
	
	private static Point screenSize = new Point(720, 1280);
	private static final float TIMESLICE = 16;

	static {
		Collidable.setBounds(screenSize);
	}
	
	public void testEnemyOnPlayerCollision() {
		/* This is going to test collisions of an enemy ship with the player ship.
		 * It should check if the enemy receives damage as well as the player.
		 */
		Hero hero = new Hero();
		hero.moveTo(100, 100);
		while (hero.getX() != 100 || hero.getY() != 100) {
			hero.update(TIMESLICE);
		}
		hero.setSpeed(5);
		hero.setArmor(10);
		hero.setCollisionDamage(5);
		
		Enemy[] enemies = new Enemy[1];
		Enemy enemy = new Enemy(hero);
		enemies[0] = enemy;
		int speed = 5;
		enemy.spawn(500, 100, 180.0f, speed);
		enemy.setArmor(10);
		enemy.setAutoFire(false);
		enemy.setCollisionDamage(5);
		
		// make the hero and enemy move toward each other
		hero.setTargets(enemies);
		hero.moveTo(500, 100);
		
		while (hero.getX() != 500){
			enemy.update(TIMESLICE);
			hero.update(TIMESLICE);
		}
		
		assertEquals(5, enemy.getArmor());
		assertEquals(5, hero.getArmor());
		
	}
	
	public void testEnemyBulletOnPlayerShipCollision() {
		/* This is going to test collisions of enemy bullets against the player ship.
		 * It should check if the player receives damage from those bullets.
		 */
		
		Hero hero = new Hero();
		hero.moveTo(300, 300);
		while (hero.getX() != 300 || hero.getY() != 300) {
			hero.update(TIMESLICE);
		}
		hero.setArmor(10);
		
		Enemy[] enemies = new Enemy[1];
		Enemy enemy = new Enemy(hero);
		enemies[0] = enemy;
		int speed = 5;
		enemy.spawn(200, 200, 0.0f, speed);
		enemy.setAutoFire(false);
		hero.setTargets(enemies);

		// the enemy will fire at the hero
		enemy.shoot();
		Bullet shotFired = enemy.getShotsFired()[0];
		shotFired.setCollisionDamage(5);
		
		assertEquals(10, hero.getArmor());
		
		while (shotFired.isVisible()){
			enemy.update(TIMESLICE);
			hero.update(TIMESLICE);
			shotFired.update(TIMESLICE);
		}
		
		assertEquals(5, hero.getArmor());
	}
	
	public void testPlayerBulletOnEnemyShipCollision() {
		/* This is going to test collisions of player bullets against the enemy ships.
		 * It should check if the enemy receives damage from those bullets.
		 */
		
		Hero hero = new Hero();
		hero.moveTo(200, 600);
		while (hero.getX() != 200 || hero.getY() != 600) {
			hero.update(TIMESLICE);
		}
		
		Enemy[] enemies = new Enemy[1];
		Enemy enemy = new Enemy(hero);
		enemies[0] = enemy;
		int speed = 0;
		enemy.spawn(200, 200, 0.0f, speed);
		enemy.setAutoFire(false);
		enemy.setArmor(10);
		
		hero.setTargets(enemies);

		// the hero will fire at the enemy
		hero.shoot();
		Bullet shotFired = hero.getShotsFired()[0];
		shotFired.setCollisionDamage(5);
		
		assertEquals(10, enemy.getArmor());
				
		while (shotFired.isVisible()){
			enemy.update(TIMESLICE);
			hero.update(TIMESLICE);
			shotFired.update(TIMESLICE);
		}
		
		assertEquals(5, enemy.getArmor());
	}

}
