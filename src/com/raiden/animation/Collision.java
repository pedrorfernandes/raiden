package com.raiden.animation;

import java.util.ArrayList;

import com.raiden.game.Assets;
import com.raiden.framework.Image;

/**
 * A collision is an animation that holds frames for ship on ship collision.
 * These frames are stored statically for less memory usage, causing less impact 
 * and easy instantiation of new collision objects.
 */
public class Collision extends Animation {
	
	/**
	 * Creates a new collision animation.
	 * @param x The X coordinate of the animation.
	 * @param y The Y coordinate of the animation.
	 * @param scale The scale of the animation (0.0 to 1.0)
	 * @param speedX The speed of the animation in X.
	 * @param speedY The speed of the animation in Y.
	 */
	public Collision(int x, int y, float scale, int speedX, int speedY) {
		loop = false;
		
		synchronized (this) {
			frames = collisionFrames;
			totalDuration = collisionDuration;
			animTime = 0;
			currentFrame = 0;
		}
		
		this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.scale = scale;
        active = true;
	}
	
	private static final int COLLISION_DURATION = 8;
	private static ArrayList<AnimFrame> collisionFrames;
	private static long collisionDuration;
	
	static {
		collisionFrames = new ArrayList<AnimFrame>();
		collisionDuration = 0;
		ArrayList<Image> collisionImages = Assets.getCollisionImages();
		for (Image image : collisionImages) {
			collisionDuration += COLLISION_DURATION;
			collisionFrames.add(new AnimFrame(image, collisionDuration));
		}
	}
}
