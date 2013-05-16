package com.raiden.test;

import com.raiden.game.Bullet;
import com.raiden.game.Collidable;
import com.raiden.game.Enemy;
import com.raiden.game.Ship;

import android.graphics.Point;
import android.test.AndroidTestCase;

public class EnemyTests extends AndroidTestCase {

	private static float scaleX = 1.0f;
	private static float scaleY = 1.0f;
	private static Point screenSize = new Point(720, 1280);
	private static final float TIMESLICE = 16;

	static {
		Collidable.setBounds(screenSize);
		Collidable.setScale(scaleX, scaleY);
	}
	
	public void testMovement() {
		/* This is going to test enemy ship movement and
		 * predefined trajectories
		 */
		
		// this enemy will shoot at nothing (null)
		// and will move down the screen
		Enemy enemy = new Enemy(null);
		int speed = 0;
		enemy.spawn(100, 100, 270.0f, speed);
		
		// check if the enemy stands still
		enemy.update(TIMESLICE);
		int x, y;
		x = enemy.getX(); y = enemy.getY();
		assertEquals(100, x);
		assertEquals(100, y);
		
		// check if he moves down
		speed = 5;
		enemy.setSpeed(speed);
		enemy.update(TIMESLICE);
		x = enemy.getX(); y = enemy.getY();
		assertEquals(100, x);
		assertEquals(100 + speed, y);
		
		// change direction left
		float angle = 180.0f;
		enemy.setDirection(angle);
		enemy.update(TIMESLICE);
		x = enemy.getX(); y = enemy.getY();
		assertEquals(100-speed, x);
		assertEquals(100+speed, y);
		
		// check if he goes off the screen
		for (int i = 0; i < 19; i++) {
			enemy.update(TIMESLICE);
		}
		x = enemy.getX(); y = enemy.getY();
		assertEquals(100-20*speed, x);
		assertEquals(100+speed, y);
		assertTrue(enemy.isVisible());
		enemy.update(TIMESLICE);
		assertTrue(!enemy.isVisible());
		
		// check if enemy goes off bounds
		assertTrue(!enemy.isOutOfRange());
		while (enemy.getX() >= -(Enemy.OFFSCREEN_LIMIT) ) {
			enemy.update(TIMESLICE);
		}
		assertTrue(enemy.isOutOfRange());
		
	}
	
	public void testShooting() {
		/* This is going to test enemy shooting and
		 * bullet trajectories
		 */
		
		// create a target and an enemy for that target
		Ship target = new Ship();
		target.moveTo(200, 100);
		while (target.getX() != 200 || target.getY() != 100) {
			target.update(TIMESLICE);
		}
		
		Enemy enemy = new Enemy(target);
		int speed = 0;
		enemy.spawn(100, 100, 270.0f, speed);
		enemy.shoot();
		Bullet[] bullets = enemy.getShotsFired();
		Bullet shotFired = bullets[0];
		
		assertTrue(shotFired.isVisible());
		assertEquals(0.0f, shotFired.getAngle());
		
		// move target to check a new firing angle
		target.moveTo(200, 200);
		while (target.getX() != 200 || target.getY() != 200) {
			target.update(TIMESLICE);
		}
		
		// check if reload time is working
		enemy.shoot();
		shotFired = bullets[1];
		assertTrue(!shotFired.isVisible());
		for (int timer = 0; timer < enemy.getTimeToReload(); timer += TIMESLICE) {
			enemy.update(TIMESLICE);
			assertTrue(!shotFired.isVisible());
		}
		// test automatic shooting through update()
		enemy.update(TIMESLICE);
		assertTrue(shotFired.isVisible());
		assertEquals(-45.0f, shotFired.getAngle());
		
	}
	
	public void testDamageAndDestruction() {
		/* This should test enemy ship receiving damage
		 * and being destroyed in the process
		 * */
		Ship target = new Ship();
		target.moveTo(100, 500);
		while (target.getX() != 100 || target.getY() != 500) {
			target.update(TIMESLICE);
		}
		
		Enemy enemy = new Enemy(target);
		int speed = 0;
		enemy.spawn(100, 100, 270.0f, speed);
		enemy.setArmor(8);
		enemy.setAutoFire(false);
		
		target.shoot();
		Bullet shotFired1 = target.getShotsFired()[0];
		Bullet shotFired2 = target.getShotsFired()[1];
		assertTrue(shotFired1.isVisible() && shotFired2.isVisible());
		shotFired1.setCollisionDamage(2); shotFired2.setCollisionDamage(2);
		
		int currentTime = 0;
		while (shotFired1.isVisible()){
			currentTime += TIMESLICE;
			enemy.update(TIMESLICE);
			target.update(TIMESLICE);
			shotFired1.update(TIMESLICE);
			shotFired2.update(TIMESLICE);
		}
		
		// enemy takes 2 bullets and had 8 armor
		assertEquals(4, enemy.getArmor());
		assertTrue(enemy.isAlive());
		assertTrue(!shotFired1.isVisible());
		assertTrue(!shotFired2.isVisible());

		
		// wait for enemy reload
		while (currentTime < target.getTimeToReload()){
			target.update(TIMESLICE);
		}
		
		target.shoot();
		while (shotFired1.isVisible()){
			currentTime += TIMESLICE;
			enemy.update(TIMESLICE);
			target.update(TIMESLICE);
			shotFired1.update(TIMESLICE);
			shotFired2.update(TIMESLICE);
		}
		
		// enemy died
		assertEquals(0, enemy.getArmor());
		assertTrue(!enemy.isAlive());
		assertTrue(!shotFired1.isVisible());
		assertTrue(!shotFired2.isVisible());
	}
	
}
