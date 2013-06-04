package com.raiden.framework;

import android.graphics.Bitmap;

import com.raiden.framework.Graphics.ImageFormat;

public abstract class Image {
    public Bitmap bitmap;
    public ImageFormat format;
    public int width, halfWidth, height, halfHeight;
	
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract ImageFormat getFormat();
    public abstract void dispose();
    public abstract int getHalfWidth();
    public abstract int getHalfHeight();
}
 