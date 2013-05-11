package com.raiden.animation;

import com.raiden.framework.Image;

public class AnimFrame {

	Image image;
	long endTime;

	public AnimFrame(Image image, long endTime) {
		this.image = image;
		this.endTime = endTime;
	}
}