package com.raiden.test;

import com.raiden.game.Collidable;
import com.raiden.game.Enemy;

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
	}
	
	public void testDamageAndDestruction() {
		/* This should test enemy ship receiving damage
		 * and being destroyed in the process
		 * */
	}
	
}
