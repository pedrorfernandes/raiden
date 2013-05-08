package com.raiden.game;

import java.util.ArrayList;

import com.raiden.framework.Image;

public class Explosion {

	private static ArrayList<AnimFrame> frames = new ArrayList<AnimFrame>();
	
	private int currentFrame;
	private long animTime;
	private static long totalDuration = 0;

	public boolean active;
	public int x, y;
	public float scale;
	
	public Explosion() {
		
		//totalDuration = 0;
		active = false;

		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}
	
	public void set(int x, int y, float scale){
		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
		this.x = x;
		this.y = y;
		this.scale = scale;
		active = true;
	}

	public static synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}

	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;
			if (animTime >= totalDuration) {
				//animTime = animTime % totalDuration;
				//currentFrame = 0;
				active = false;
			}

			while (animTime > getFrame(currentFrame).endTime && active) {
				currentFrame++;
			}
		}
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

	private static class AnimFrame {

		Image image;
		long endTime;

		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}
