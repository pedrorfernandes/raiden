package com.raiden.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.raiden.framework.Graphics;
import com.raiden.framework.Image;

public class AndroidGraphics implements Graphics {
	AssetManager assets;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	Matrix matrix;

	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer, float scaleX, float scaleY) {
		this.assets = assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
		canvas.scale(scaleX, scaleY);
	}

	@Override
	public Image newImage(String fileName, ImageFormat format, float scaleX, float scaleY) {
		Config config = null;
		if (format == ImageFormat.RGB565)
			config = Config.RGB_565;
		else if (format == ImageFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;

		Options options = new Options();
		options.inPreferredConfig = config;


		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = assets.open(fileName);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			if (bitmap == null)
				throw new RuntimeException("Couldn't load bitmap from asset '"
						+ fileName + "'");
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset '"
					+ fileName + "'");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		
		if (scaleX != 1.0f || scaleY != 1.0f){
			int sizeX = bitmap.getWidth();
			int scaledX = (int)(sizeX * scaleX);
			
			int sizeY = bitmap.getHeight();
			int scaledY = (int)(sizeY * scaleY);
			
			bitmap = Bitmap.createScaledBitmap(bitmap, scaledX, scaledY, true);
		}

		if (bitmap.getConfig() == Config.RGB_565)
			format = ImageFormat.RGB565;
		else if (bitmap.getConfig() == Config.ARGB_4444)
			format = ImageFormat.ARGB4444;
		else
			format = ImageFormat.ARGB8888;

		return new AndroidImage(bitmap, format);
	}
	
	@Override
	public Image newImage(String fileName, ImageFormat format) {
		return newImage(fileName, format, 1.0f, 1.0f);
	}

	@Override
	public void clearScreen(int color) {
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
				(color & 0xff));
	}


	@Override
	public void drawLine(int x, int y, int x2, int y2, int color) {
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}

	@Override
	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
	}

	@Override
	public void drawARGB(int a, int r, int g, int b) {
		paint.setStyle(Style.FILL);
		canvas.drawARGB(a, r, g, b);
	}

	@Override
	public void drawString(String text, int x, int y, Paint paint){
		canvas.drawText(text, x, y, paint);
	}

	public void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth;
		dstRect.bottom = y + srcHeight;

		canvas.drawBitmap(image.bitmap, srcRect, dstRect, null);
	}

	@Override
	public void drawImage(Image Image, int x, int y) {
		canvas.drawBitmap(((AndroidImage)Image).bitmap, x, y, null);
	}

	public void drawScaledImage(Image image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight){
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + width;
		dstRect.bottom = y + height;
		
		canvas.drawBitmap(image.bitmap, srcRect, dstRect, null);
	}
	
	public void drawScaledImage(Image image, int x, int y, int pivotX, int pivotY, float scale){
		if (matrix == null){
			matrix = new Matrix();
		}
		matrix.reset();
		matrix.setTranslate(x, y);
		matrix.postScale(scale, scale, pivotX, pivotY);
		canvas.drawBitmap(image.bitmap, matrix, null);
	}
	
	public void drawRotatedImage(Image image, int x, int y, int width, int height, float angle, float startingAngle){
		if (matrix == null){
			matrix = new Matrix();
		}
		matrix.reset();
		matrix.setTranslate(x, y);
		matrix.postRotate(startingAngle - angle, x+width/2, y+height/2);
		canvas.drawBitmap(image.bitmap, matrix, null);
		/*
		canvas.save();
		canvas.rotate(startingAngle - angle, x + (width / 2), y + (height / 2));
		canvas.drawBitmap(image.bitmap, x, y, null);
		canvas.restore();
		*/
	}
	
	public void drawCircle(int x, int y, int radius, Paint paint){
		canvas.drawCircle(x, y, radius, paint);
	}

	@Override
	public int getWidth() {
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		return frameBuffer.getHeight();
	}
	
	// this might be useful if we want to pre cache every rotation
	private Bitmap rotate(Bitmap src, int Degrees) {
	    double width = src.getWidth();
	    double height = src.getHeight();

	    double sine = Math.sin(Degrees);
	    double cosine = Math.cos(Degrees);

	    double px1 = width * cosine;
	    double px2 = width * cosine - height * sine;
	    double px3 = -height * sine;
	    double py1 = height * cosine;
	    double py2 = height * cosine + width * sine;
	    double py3 = width * sine;

	    int minx = (int) Math.min(0, Math.min(px1, Math.min(px2, px3)));
	    int miny = (int) Math.min(0, Math.min(py1, Math.min(py2, py3)));
	    int maxx = (int) Math.max(px1, Math.max(px2, px3));
	    int maxy = (int) Math.max(py1, Math.max(py2, py3));

	    int dw = Math.abs(maxx) - minx;
	    int dh = Math.abs(maxy) - miny;

	    int w = 2;
	    int h = 2;
	    while (w < dw)
	        w *= 2;

	    while (h < dh)
	        h *= 2;

	    int xoff = 0;
	    int yoff = 0;

	    if (w > dw)
	        xoff = (int)((w - dw) / 2.0F + 0.5F);
	    if (h > dh)
	        yoff = (int)((h - dh) / 2.0F + 0.5F);

	    Bitmap map = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	    map.eraseColor(0x00000000);

	    for (int x = 0; x < dw; x++) {
	        for (int y = 0; y < dh; y++) {
	            int sx = (int) ((x + minx) * cosine + (y + miny) * sine);
	            int sy = (int) ((y + miny) * cosine - (x + minx) * sine);

	            if (sx < 0 || sx >= width || sy < 0 || sy >= height)
	                continue;

	            map.setPixel(x + xoff, y + yoff, src.getPixel(sx, sy));
	        }
	    }

	    return map;
	}
}