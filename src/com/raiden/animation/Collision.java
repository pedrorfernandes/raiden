package com.raiden.animation;

import java.util.ArrayList;

import com.raiden.game.Assets;

public class Collision extends Animation {
	
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
		collisionDuration += COLLISION_DURATION;
		collisionFrames.add(new AnimFrame(Assets.heroCollision1, collisionDuration));
		collisionDuration += COLLISION_DURATION;
		collisionFrames.add(new AnimFrame(Assets.heroCollision2, collisionDuration));
		collisionDuration += COLLISION_DURATION;
		collisionFrames.add(new AnimFrame(Assets.heroCollision3, collisionDuration));
	}
}
