package com.raiden.framework;

import android.graphics.Paint;

public interface Graphics {
    public static enum ImageFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public Image newImage(String fileName, ImageFormat format, float scaleX, float scaleY);
    
    public Image newImage(String fileName, ImageFormat format);

    public void clearScreen(int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);

    public void drawImage(Image Image, int x, int y);
    
	public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight);
	
	public void drawScaledImage(Image image, int x, int y, int pivotX, int pivotY, float scale);
	
	public void drawRotatedImage(Image image, int x, int y, int width, int height, float angle, float startingAngle);

    public void drawString(String text, int x, int y, Paint paint);
    
    public void drawRotatedString(String text, int x, int y, float angle, Paint paint);
    
    public void drawText(String text, int x, int y, int maxWidth, Paint paint);
	
    public void drawCircle(int x, int y, int radius, Paint paint);
	
    public int getWidth();

    public int getHeight();

    public void drawARGB(int i, int j, int k, int l);

}