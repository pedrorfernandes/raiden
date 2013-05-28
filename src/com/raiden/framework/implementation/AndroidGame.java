package com.raiden.framework.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.raiden.framework.Audio;
import com.raiden.framework.FileIO;
import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Input;
import com.raiden.framework.Screen;

public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    private final static int GAMESIZE_X = 800;
    private final static int GAMESIZE_Y = 1280;
    
    Point screenSize, gameSize;
    private float scaleX, scaleY;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        
        screenSize = new Point();
        // getWindowManager().getDefaultDisplay().getSize(size); // API level 13 only
        screenSize.x = getWindowManager().getDefaultDisplay().getWidth();
        screenSize.y = getWindowManager().getDefaultDisplay().getHeight();
        gameSize = new Point(GAMESIZE_X, GAMESIZE_Y);
        
        int frameBufferWidth = isPortrait ? screenSize.x : screenSize.y;
        int frameBufferHeight = isPortrait ? screenSize.y : screenSize.x;
        
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        scaleX = (float) screenSize.x / GAMESIZE_X;
        scaleY = (float) screenSize.y / GAMESIZE_Y;

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer, scaleX, scaleY);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, 1/scaleX, 1/scaleY);
        //input = new AndroidInput(this, renderView, 1.0f, 1.0f);
        screen = getInitScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {

        return screen;
    }
    
    public Point getSize() {
    	return gameSize;
    }
    
    public float getScaleX(){
    	return scaleX;
    }
    
    public float getScaleY(){
    	return scaleY;
    }
}