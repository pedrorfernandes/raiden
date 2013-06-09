package com.raiden.animation;

import java.util.ArrayList;

import com.raiden.framework.Image;

/**
 * An animation contains a set of frames and a timer,
 * making it easy to animate a sequence of sprites in a timed fashion.
 */
public class Animation {

	protected ArrayList<AnimFrame> frames;
	protected int currentFrame;
	protected long animTime;
	protected long totalDuration;
	
	public boolean active;
	public int x, y, speedX, speedY;
	public float scale;
	protected boolean loop;

	/**
	 * Creates a new animation.
	 */
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

	/**
	 * Adds a frame to the current frame list.
	 * @param image The image of the frame.
	 * @param duration The duration of this frame.
	 */
	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	/**
	 * Sets the list of frames to a new list.
	 * @param newFrames The new frames list.
	 * @param duration The total duration of the new frames list.
	 */
	public synchronized void setFrames(ArrayList<AnimFrame> newFrames, long duration) {
		totalDuration = duration;
		this.frames = newFrames;
	}

	/**
	 * Updates the animation, determining the current frame of the animation.
	 * @param elapsedTime The time passed since the last update.
	 */
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

	/**
	 * @return The current image of the animation.
	 */
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

	/**
	 * @param i Index of the frame.
	 * @return The associated frame with that index.
	 */
	private AnimFrame getFrame(int i) {
		return (AnimFrame) frames.get(i);
	}
}
