package com.raiden.game;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

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
    private Animation heroAnimation, 
                      heroTurningLeftAnimation, 
                      heroTurningRightAnimation;
    
    private Point dragPoint;
    private boolean shooting = false;
    private boolean stoppedShooting = true;
    
    public static Point screenSize;
    
	private int volume = 100;
    private static final int FIRST_SHOT_FIRED = 8;
    
    public static ArrayList<Enemy> enemies;
    private Image enemyImage;
    
    private static final float ENEMY_ANGLE = 270.0f;
    private static final float HERO_ANGLE = 90.0f;
    
    private Random random = new Random();
    private int counter = 0;
    
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
        
        enemyImage = Assets.enemy1;
        enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(200, 0, 270.0f));
        enemies.add(new Enemy(500, 0, 225.0f));
        
		Assets.machinegun.setLooping(true);
        
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
    	
    	counter += deltaTime;
    	if (counter > 100){
    		enemies.add(new Enemy(random.nextInt(800), 0, ENEMY_ANGLE));
    		enemies.add(new Enemy(random.nextInt(800), 0, ENEMY_ANGLE));
    		enemies.add(new Enemy(random.nextInt(800), 0, ENEMY_ANGLE));
    		counter = 0;
    	}
    	
        // All touch input is handled here
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
            	dragPoint.x = event.x;
            	dragPoint.y = event.y;
            	shooting = true;
			}

			if (event.type == TouchEvent.TOUCH_UP) {
				shooting = false;
				// the hero isn't shooting anymore
				// but the gun sound hasn't stopped yet
				stoppedShooting = false;
			}
			
			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				hero.move(event.x - dragPoint.x, event.y - dragPoint.y);
				dragPoint.x = event.x; dragPoint.y = event.y;
			}
			
			if (event.type == TouchEvent.TOUCH_HOLD) {
				// nothing for now
			}
            
        }
        
        // 2. Check miscellaneous events like death:
        
        if (livesLeft == 0) {
            state = GameState.GameOver;
        }
        
        // 3. Call individual update() methods here.
        // This is where all the game updates happen.
        
        // check if the hero is shooting
        // and control the machine gun sound
        if (shooting){
			if ( !Assets.machinegun.isPlaying() ){
				Assets.machinegun.play();
			}
			hero.shoot();
        } else if (!stoppedShooting) {
        	if (Assets.machinegun.getCurrentPosition() > FIRST_SHOT_FIRED){
        		Assets.machinegun.seekBegin();
        		Assets.machinegun.stop();
        		stoppedShooting = true;
        	}
        }

        // check if the hero is turning and switch to the correct image
        if ( hero.isMovingLeft() )
	        heroImage = heroTurningLeftAnimation.getImage();
        else if ( hero.isMovingRight() )
	        heroImage = heroTurningRightAnimation.getImage();
        else 
	        heroImage = heroAnimation.getImage();

        hero.update(deltaTime);
        
        // update the hero's bullets
        ArrayList<Bullet> heroShots = hero.getShotsFired();
        for (int i = 0; i < heroShots.size(); i++) {
			heroShots.get(i).update();
		}

        // update the enemies  
        ListIterator<Enemy> enemyItr = enemies.listIterator();
		while( enemyItr.hasNext() ){
			Enemy enemy = enemyItr.next();
			enemy.update();
			if ( enemy.isOutOfRange() || !enemy.alive )
				enemyItr.remove();
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
			if (bullet.getAngle() == 90.0f){
				g.drawImage(Assets.heroBullet1, 
					    bullet.getX()-Assets.heroBullet1.getHalfWidth(), 
					    bullet.getY()-Assets.heroBullet1.getHalfHeight());
			} else {
				g.drawRotatedImage(Assets.heroBullet1, 
					    bullet.getX()-Assets.heroBullet1.getHalfWidth(), 
					    bullet.getY()-Assets.heroBullet1.getHalfHeight(),
					    Assets.heroBullet1.getWidth(),
					    Assets.heroBullet1.getHeight(), 
					    bullet.getAngle(),
					    HERO_ANGLE);
			}
		}
        
        // enemies drawing
        for (int i = 0; i < enemies.size(); i++) {
        	Enemy enemy = enemies.get(i);
			if (enemy.getAngle() == 270.0f){
				g.drawImage(enemyImage, 
						enemy.getX()-enemyImage.getHalfWidth(), 
						enemy.getY()-enemyImage.getHalfHeight());
			} else {
				g.drawRotatedImage(enemyImage, 
						enemy.getX()-enemyImage.getHalfWidth(), 
						enemy.getY()-enemyImage.getHalfHeight(),
						enemyImage.getWidth(),
						enemyImage.getHeight(), 
					    enemy.getAngle(),
					    ENEMY_ANGLE);
			}
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