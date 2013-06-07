package com.raiden.framework;

import com.raiden.game.Level;
import com.raiden.game.MusicController;

import android.content.res.AssetManager;
import android.graphics.Point;

public interface Game {

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();
    
    public Point getSize();
    
    public float getScaleX();

    public float getScaleY();
    
    public boolean moveTaskToBack(boolean nonRoot);
    
    public AssetManager getAssets();

	public void saveHighscores();

	public void loadHighscores();

	public Level getLevel(int i);
	
	public MusicController getMusicController();
    
}
