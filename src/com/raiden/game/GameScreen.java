package com.raiden.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Image;
import com.raiden.framework.Screen;
import com.raiden.framework.Input.TouchEvent;

public class GameScreen extends Screen {
    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;

    // Variable Setup
    // You would create game objects here.

    int livesLeft = 1;
    Paint paint;
    
    private static Ship hero;
    private Image heroImage;
    private Animation heroAnimation, heroTurningLeftAnimation, heroTurningRightAnimation;
    
    private Point dragPoint;
    
    public static Point screenSize;
    
    public GameScreen(Game game) {
        super(game);
        screenSize = game.getSize();

        // Initialize game objects here
        hero = new Ship();
        heroAnimation = new Animation();
        heroAnimation.addFrame(Assets.hero1, 100);
        heroAnimation.addFrame(Assets.hero2, 100);
        heroTurningLeftAnimation = new Animation();
        heroTurningLeftAnimation.addFrame(Assets.heroLeft1, 100);
        heroTurningLeftAnimation.addFrame(Assets.heroLeft2, 100);
        heroTurningRightAnimation = new Animation();
        heroTurningRightAnimation.addFrame(Assets.heroRight1, 100);
        heroTurningRightAnimation.addFrame(Assets.heroRight2, 100);
        heroImage = heroAnimation.getImage();
        
        dragPoint = new Point();

        // Defining a paint object
        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        // We have four separate update methods in this example.
        // Depending on the state of the game, we call different update methods.
        // Refer to Unit 3's code. We did a similar thing without separating the
        // update methods.

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        
        // This example starts with a "Ready" screen.
        // When the user touches the screen, the game begins. 
        // state now becomes GameState.Running.
        // Now the updateRunning() method will be called!
        
        if (touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {        
        heroImage = heroAnimation.getImage();
    	
        // All touch input is handled here
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == MotionEvent.ACTION_DOWN) {
            	dragPoint.x = event.x;
            	dragPoint.y = event.y;
            	hero.shoot();
			}

			if (event.type == MotionEvent.ACTION_UP) {
				// don't move
			}
			
			if (event.type == MotionEvent.ACTION_MOVE) {
				hero.move(event.x - dragPoint.x, event.y - dragPoint.y);
				dragPoint.x = event.x; dragPoint.y = event.y;
				hero.shoot();
			}
            
        }
        
        // 2. Check miscellaneous events like death:
        
        if (livesLeft == 0) {
            state = GameState.GameOver;
        }
        
        // 3. Call individual update() methods here.
        // This is where all the game updates happen.
        if ( hero.isMovingLeft() )
	        heroImage = heroTurningLeftAnimation.getImage();
        else if ( hero.isMovingRight() )
	        heroImage = heroTurningRightAnimation.getImage();
        else 
	        heroImage = heroAnimation.getImage();

        hero.update(deltaTime);
        
        ArrayList<Bullet> heroShots = hero.getShotsFired();
        for (int i = 0; i < heroShots.size(); i++) {
			heroShots.get(i).update();
		}

        animate();
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 300 && event.x < 980 && event.y > 100
                        && event.y < 500) {
                    nullify();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        // First draw the game elements.

        g.clearScreen(0);
        
        // hero drawing
        g.drawImage(heroImage,
        		    hero.getX()-heroImage.getHalfWidth(), 
        		    hero.getY()-heroImage.getHalfHeight());
        
        // hero shots drawing
        ArrayList<Bullet> heroShots = hero.getShotsFired();
        for (int i = 0; i < heroShots.size(); i++) {
			Bullet bullet = heroShots.get(i);
			g.drawImage(Assets.heroBullet1, 
					    bullet.getX()-Assets.heroBullet1.getHalfWidth(), 
					    bullet.getY()-Assets.heroBullet1.getHalfHeight());
		}

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }
    
    private void animate() {
    	heroAnimation.update(10);
    	heroTurningLeftAnimation.update(10);
    	heroTurningRightAnimation.update(10);

    }

    private void nullify() {

        // Set all variables to null. You will be recreating them in the
        // constructor.
        paint = null;

        // Call garbage collector to clean up memory.
        System.gc();
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap each side of the screen to move in that direction.",
                640, 300, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 640, 300, paint);

    }

    @Override
    public void pause() {
        if (state == GameState.Running)
            state = GameState.Paused;

    }

    @Override
    public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }
}