package com.raiden.game;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Screen;
import com.raiden.framework.Graphics.ImageFormat;


public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }


    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("menu.png", ImageFormat.RGB565);
        Assets.click = game.getAudio().createSound("explode.wav");
        Assets.hero1 = g.newImage("hero1.png", ImageFormat.ARGB4444);
        Assets.hero2 = g.newImage("hero2.png", ImageFormat.ARGB4444);
        Assets.heroLeft1 = g.newImage("heroLeft1.png", ImageFormat.ARGB4444);
        Assets.heroLeft2 = g.newImage("heroLeft2.png", ImageFormat.ARGB4444);
        Assets.heroRight1 = g.newImage("heroRight1.png", ImageFormat.ARGB4444);
        Assets.heroRight2 = g.newImage("heroRight2.png", ImageFormat.ARGB4444);
        Assets.heroBullet1 = g.newImage("heroBullet1.png", ImageFormat.ARGB4444);

        game.setScreen(new MainMenuScreen(game));

    }


    @Override
    public void paint(float deltaTime) {


    }


    @Override
    public void pause() {


    }


    @Override
    public void resume() {


    }


    @Override
    public void dispose() {


    }


    @Override
    public void backButton() {


    }
}