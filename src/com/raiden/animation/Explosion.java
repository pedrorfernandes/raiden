package com.raiden.animation;

import java.util.ArrayList;

import com.raiden.game.Assets;
import com.raiden.framework.Image;

/**
 * An explosion is an animation that holds explosion frames.
 * These frames are stored statically for less memory usage, causing less impact 
 * and easy instantiation of new explosion objects.
 */
public class Explosion extends Animation {
	
	/**
	 * Creates a new explosion animation.
	 * @param x The X coordinate.
	 * @param y The Y coordinate
	 * @param scale The scale of the explosion (0.0 to 1.0)
	 */
	public Explosion(int x, int y, float scale) {
		loop = false;
		
		synchronized (this) {
			frames = explosionFrames;
			totalDuration = explosionDuration;
			animTime = 0;
			currentFrame = 0;
		}
		
		this.x = x;
        this.y = y;
        this.scale = scale;
        active = true;
	}
	
	private static final int EXPLOSION_DURATION = 5;
	private static ArrayList<AnimFrame> explosionFrames;
	private static long explosionDuration;
	
	static {
		explosionFrames = new ArrayList<AnimFrame>();
		explosionDuration = 0;
		ArrayList<Image> explosionImages = Assets.getExplosionImages();
		for (Image image : explosionImages) {
			explosionDuration += EXPLOSION_DURATION;
			explosionFrames.add(new AnimFrame(image, explosionDuration));
		}
	}
	
}
