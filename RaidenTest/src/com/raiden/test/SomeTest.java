package com.raiden.test;

import com.raiden.game.Bullet;
import com.raiden.game.Collidable;
import com.raiden.game.Enemy;
import com.raiden.game.Ship;

import junit.framework.Assert;
import android.graphics.Point;
import android.test.AndroidTestCase;


public class SomeTest extends AndroidTestCase {

	private static float scaleX = 1.0f;
	private static float scaleY = 1.0f;
	private static Point screenSize = new Point(720, 1280);

	static {
		Collidable.setBounds(screenSize);
		Collidable.setScale(scaleX, scaleY);
	}

	public void testSomething() throws Throwable {
		Ship hero = new Ship();
		Enemy[] enemies = new Enemy[2];
		hero.setTargets(enemies);
		hero.shoot();
		
		Assert.assertEquals(hero.getShotsFired().length, 30);
		int i;
		for (i = 0; i < 3; i++) {
			Bullet bullet = hero.getShotsFired()[i];
			assertTrue(bullet.visible);
		}
		for(; i < hero.getShotsFired().length; i++){
			Bullet bullet = hero.getShotsFired()[i];
			assertFalse(bullet.visible);
		}
	}

	public void testSomethingElse() throws Throwable {
		Assert.assertTrue(1 + 1 == 2);
	}
}