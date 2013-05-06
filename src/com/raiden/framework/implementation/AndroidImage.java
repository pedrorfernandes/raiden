package com.raiden.framework.implementation;

import android.graphics.Bitmap;

import com.raiden.framework.Image;
import com.raiden.framework.Graphics.ImageFormat;

public class AndroidImage implements Image {
    Bitmap bitmap;
    ImageFormat format;
    public int halfWidth;
    public int halfHeight;
    
    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
        this.halfWidth = getWidth() / 2;
        this.halfHeight = getHeight() / 2;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
    
    @Override
    public int getHalfWidth(){
    	return halfWidth;
    }
    
    @Override
    public int getHalfHeight(){
    	return halfHeight;
    }
}