package com.raiden.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
	AndroidGame game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	Canvas canvas;
	volatile boolean running = false;
	private static long deltaTime;
	private static long simulationTime;
	private static long startTime;
	private static long realTime;
	private static final float updateInterval = 16.0f;
	
	// for frame counting
	private static long currentTime;
	private static long lastTime;
	private static int frames = 0, fps;
	private static Paint fpsPaint;

	public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
		super(game);
		this.game = game;
		this.framebuffer = framebuffer;
		this.holder = getHolder();
		
		fpsPaint = new Paint();
		fpsPaint.setColor(Color.WHITE);
		fpsPaint.setTextSize(50);

	}

	public void resume() { 
		running = true;
		renderThread = new Thread(this);
		renderThread.start();   

	}      

	public void run() {
		Rect dstRect = new Rect();
		startTime = System.currentTimeMillis();
		lastTime = startTime;
		simulationTime = 0;
		while(running) {  
			if(!holder.getSurface().isValid())
				continue;           

			realTime = System.currentTimeMillis() - startTime;

			deltaTime = 0;
			while (simulationTime < realTime) {
				simulationTime += updateInterval;
				deltaTime += updateInterval;
				game.getCurrentScreen().update(updateInterval);
			}
			game.getCurrentScreen().paint(deltaTime);

			canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(framebuffer, null, dstRect, null);
			frames++;
			// count the fps
			currentTime = System.currentTimeMillis();
			if ( (currentTime - lastTime) >= 1000)
			{
			    fps = frames;
				frames = 0;
				lastTime = currentTime;
			}
			
			canvas.drawText(Long.toString(fps), 30, 50, fpsPaint);
			
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {                        
		running = false;                        
		while(true) {
			try {
				renderThread.join();
				break;
			} catch (InterruptedException e) {
				// retry
			}

		}
	}     


}