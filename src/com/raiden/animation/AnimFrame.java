package com.raiden.animation;

import com.raiden.framework.Image;

/**
 * A animation frame is a simple image with a 
 * timing associated with it.
 */
public class AnimFrame {

	Image image;
	long endTime;

	/**
	 * Constructor for a new animation frame.
	 * @param image The image of this frame.
	 * @param endTime Time when the frame ends.
	 */
	public AnimFrame(Image image, long endTime) {
		this.image = image;
		this.endTime = endTime;
	}
}