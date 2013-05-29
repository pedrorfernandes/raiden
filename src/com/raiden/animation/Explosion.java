package com.raiden.animation;

import java.util.ArrayList;

import com.raiden.game.Assets;
import com.raiden.framework.Image;

public class Explosion extends Animation {
	
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
