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
        
        Assets.explosionSound1 = game.getAudio().createSound("explosion1.wav");
        Assets.explosionSound2 = game.getAudio().createSound("explosion2.wav");
        Assets.explosionSound3 = game.getAudio().createSound("explosion3.wav");
        Assets.explosionSound4 = game.getAudio().createSound("explosion4.wav");
        Assets.explosionSound5 = game.getAudio().createSound("explosion5.wav");
        Assets.explosionSound6 = game.getAudio().createSound("explosion6.wav");
        Assets.explosionSound7 = game.getAudio().createSound("explosion7.wav");
        Assets.explosionSound8 = game.getAudio().createSound("explosion8.wav");
        Assets.explosionSound9 = game.getAudio().createSound("explosion9.wav");
        Assets.explosionSound10 = game.getAudio().createSound("explosion10.wav");
        Assets.explosionSound11 = game.getAudio().createSound("explosion11.wav");
        Assets.machinegun = game.getAudio().createMusic("machinegun.wav");
        Assets.hit1 = game.getAudio().createSound("hit1.wav");
        Assets.hit2 = game.getAudio().createSound("hit2.wav");
        Assets.hit3 = game.getAudio().createSound("hit3.wav");
        Assets.hit4 = game.getAudio().createSound("hit4.wav");
        Assets.hit5 = game.getAudio().createSound("hit5.wav");

        
        Assets.hero1 = g.newImage("hero1.png", ImageFormat.ARGB4444);
        Assets.hero2 = g.newImage("hero2.png", ImageFormat.ARGB4444);
        Assets.heroLeft1 = g.newImage("heroLeft1.png", ImageFormat.ARGB4444);
        Assets.heroLeft2 = g.newImage("heroLeft2.png", ImageFormat.ARGB4444);
        Assets.heroRight1 = g.newImage("heroRight1.png", ImageFormat.ARGB4444);
        Assets.heroRight2 = g.newImage("heroRight2.png", ImageFormat.ARGB4444);
        Assets.heroBullet1 = g.newImage("heroBullet1.png", ImageFormat.ARGB4444);
        
        Assets.explosion1 = g.newImage("explosion1.png", ImageFormat.ARGB4444);
        Assets.explosion2 = g.newImage("explosion2.png", ImageFormat.ARGB4444);
        Assets.explosion3 = g.newImage("explosion3.png", ImageFormat.ARGB4444);
        Assets.explosion4 = g.newImage("explosion4.png", ImageFormat.ARGB4444);
        Assets.explosion5 = g.newImage("explosion5.png", ImageFormat.ARGB4444);
        Assets.explosion6 = g.newImage("explosion6.png", ImageFormat.ARGB4444);

        
        Assets.enemy1 = g.newImage("enemy1.png", ImageFormat.ARGB4444);

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