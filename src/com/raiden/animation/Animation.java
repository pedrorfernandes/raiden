package com.raiden.animation;

import java.util.ArrayList;

import com.raiden.framework.Image;


public class Animation {

	protected ArrayList<AnimFrame> frames;
	protected int currentFrame;
	protected long animTime;
	protected long totalDuration;
	
	public boolean active;
	public int x, y, speedX, speedY;
	public float scale;
	protected boolean loop;

	public Animation() {
		loop = true;
		active = true;
		
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;

		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
		speedX = 0;
		speedY = 0;
	}

	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	public synchronized void setFrames(ArrayList<AnimFrame> newFrames, long duration) {
		totalDuration = duration;
		this.frames = newFrames;
	}

	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;
			if (animTime >= totalDuration) {
				if (loop){
					animTime = animTime % totalDuration;
					currentFrame = 0;
				} else {
					active = false;
				}

			}

			while (animTime > getFrame(currentFrame).endTime && active) {
				currentFrame++;

			}
		}
		x += speedX;
		y += speedY;
	}

	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

	private AnimFrame getFrame(int i) {
		return (AnimFrame) frames.get(i);
	}
}
