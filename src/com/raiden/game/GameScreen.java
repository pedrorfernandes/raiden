package com.raiden.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.raiden.animation.Animation;
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

	public static Hero hero;
	private Image heroImage;
	public Animation heroAnimation, heroTurningLeftAnimation, heroTurningRightAnimation;
	private Image bulletImage;
	
	private Level level;

	// touch and input variables
	private Point dragPoint;

	// screen size variables
	public static Point screenSize;
	
	private static SoundController soundController;
	private static AnimationController animationController;
	private static EffectsController effectsController;
	private static MusicController musicController;

	private static ArrayList<Animation> specialEffects;
	private static final float NORMAL_SCALE = 1.0f;
	
	// enemy variables
	private Image enemyImage;
	private static final int MAX_ENEMIES = 20;
	public static Enemy[] enemies = new Enemy[MAX_ENEMIES];

	private static final float ANGLE_DOWN = 270.0f;
	private static final float ANGLE_UP = 90.0f;
	
	// power up variables
	private static final int MAX_POWER_UPS = 15;
	private static PowerUp[] powerUps = new PowerUp[MAX_POWER_UPS];

	// constants for animation
	private static final int ANIMATION_UPDATE = 1;
	
	// debug variables
	private static boolean HITBOXES_VISIBLE = false;
	private static Paint hitboxColor;

	// TODO random variables that must be deleted later!
	private Random random = new Random();
	private int counter = 0;

	// iterating variables
	private static int length;
	private static Bullet bullet;
	private static Enemy enemy;
	private static Image image;
	private static PowerUp powerUp;
	private static Animation specialEffect;

	public GameScreen(Game game) {
		super(game);
		
		screenSize = game.getSize();
		Collidable.setBounds(screenSize);
		Collidable.setGameScreen(this);

		// Initialize game objects here
		hero = new Hero();
		hero.setTargets(enemies);
		hero.setPowerUps(powerUps);
		
		heroAnimation = Assets.getHeroAnimation();
		heroTurningLeftAnimation = Assets.getHeroTurningLeftAnimation();
		heroTurningRightAnimation = Assets.getHeroTurningRightAnimation();
		
		heroImage = heroAnimation.getImage();
		
		// observers
		effectsController = new EffectsController(this);
		soundController = new SoundController(this);
		animationController = new AnimationController(this);
		musicController = new MusicController(this);

		hero.addObserver(effectsController);
		hero.addObserver(soundController);
		hero.addObserver(animationController);
		hero.addObserver(musicController);
		
		for (int i = 0; i < MAX_ENEMIES; i++)
		{
			enemies[i] = new Enemy(hero);
			enemies[i].addObserver(effectsController);
			enemies[i].addObserver(soundController);
		}
		
		for (int i = 0; i < MAX_POWER_UPS; i++) {
			powerUps[i] = new PowerUp();
		}
	
		specialEffects = new ArrayList<Animation>();
		
		level = new Level(this, RaidenGame.level1);

		dragPoint = new Point();

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		
		hitboxColor = new Paint();
		hitboxColor.setColor(Color.MAGENTA);

	}
	
	public Enemy spawnEnemy(int x, int y, float angle, Enemy.Type type, FlightPattern flightPattern, PowerUp.Type PowerUpDrop){
		for (int i = 0; i < MAX_ENEMIES; i++)
		{
			enemy = enemies[i];
			if ( !enemy.isInGame() ){
				enemy.spawn(x, y, angle, type, flightPattern, PowerUpDrop);
				return enemy;
			}
		}
		return null;
	}
	
	public PowerUp spawnPowerUp(int x, int y, PowerUp.Type type){
		for (int i = 0; i < MAX_POWER_UPS; i++)
		{
			powerUp = powerUps[i];
			if ( !powerUp.isVisible() ){
				powerUp.spawn(x, y, type);
				return powerUp;
			}
		}
		return null;
	}
	
	public void addSpecialEffect(Animation specialEffect){
		specialEffects.add(specialEffect);
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
		if (counter > 960*1){
			counter = 0;
			/*
			spawnPowerUp(100, 0, PowerUp.Type.HeavyBullets);
			spawnPowerUp(400, 0, PowerUp.Type.Machinegun);
			spawnPowerUp(600, 0, PowerUp.Type.ScatterShot);
			*/
			// TODO this is a flight pattern example, must be removed !!
			ArrayList<Integer> x = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0));
			ArrayList<Integer> y = new ArrayList<Integer>(Arrays.asList(100,200,300,400,500,600));
			for (int i = 0; i < x.size()-1; i++) {
				FlightPattern pattern = new FlightPattern();
				pattern.addMovement(  0, 800, Direction.Right);
				pattern.addMovement(270,  16, Direction.Right);
				pattern.addMovement(180,  16, Direction.Right);
				pattern.addMovement( 90,  16, Direction.Right);
				pattern.addMovement(  0, 300, Direction.Right);
				pattern.addMovement(270,  16, Direction.Right);
				pattern.addMovement(180,  16, Direction.Right);
				pattern.addMovement( 90,  16, Direction.Right);
				pattern.addMovement(  0, 800, Direction.Right);
				enemy = spawnEnemy(x.get(i), y.get(i), 0.0f, Enemy.Type.Fast, pattern, null);
				enemy = spawnEnemy(x.get(i+1), y.get(i+1), 0.0f, Enemy.Type.Normal, pattern, null);
				if (enemy == null) continue;
			}
			
		}

		
		// All touch input is handled here
		length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				dragPoint.x = event.x;
				dragPoint.y = event.y;
				hero.setAutoFire(true);
			}

			if (event.type == TouchEvent.TOUCH_UP) {
				hero.setAutoFire(false);
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
		
		level.update(deltaTime);

		// update the hero's bullets
		//length = Ship.shotsFired.size();
		for (int i = 0; i < hero.shots.length; i++) {
			bullet = hero.shots[i];
			bullet.update(deltaTime);
		}

		hero.update(deltaTime);

		// update the enemies  
		length = enemies.length;
		for (int i = 0; i < length; i++) {
			enemy = enemies[i];
			enemy.update(deltaTime);
			// update the enemy bullets
			for (int j = 0; j < enemy.shots.length; j++) {
				bullet = enemy.shots[j];
				bullet.update(deltaTime);
			}
		}
		
		// update power ups
		length = powerUps.length;
		for (int i = 0; i < length; i++) {
			powerUp = powerUps[i];
			powerUp.update(deltaTime);
		}
		
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

		g.clearScreen(Color.rgb(58, 86, 104));
		
		// hero drawing
		heroImage = animationController.getCurrenAnimation().getImage();
		
		g.drawImage(heroImage,
				hero.x - heroImage.halfWidth, 
				hero.y - heroImage.halfHeight);
		if (HITBOXES_VISIBLE) 
			g.drawCircle(hero.x, hero.y, hero.radius, hitboxColor);

		// hero shots drawing
		length = hero.shots.length;
		for (int i = 0; i < length; i++) {
			Bullet bullet = hero.shots[i];
			if (!bullet.visible) continue;
			bulletImage = bullet.type.image;
			if (bullet.angle == ANGLE_UP){
				g.drawImage(bulletImage,
						bullet.x-bulletImage.halfWidth, 
						bullet.y-bulletImage.halfHeight);
			} else {
				g.drawRotatedImage(bulletImage, 
						bullet.x-bulletImage.halfWidth, 
						bullet.y-bulletImage.halfHeight,
						bulletImage.width,
						bulletImage.height, 
						bullet.angle,
						ANGLE_UP);
			}
			if (HITBOXES_VISIBLE)
				g.drawCircle(bullet.x, bullet.y, bullet.radius, hitboxColor);
		}

		// enemies drawing
		length = enemies.length;
		for (int i = 0; i < length; i++) {
			Enemy enemy = enemies[i];
			
			// enemy bullets drawing
			for (int j = 0; j < enemy.shots.length; j++) {
				Bullet bullet = enemy.shots[j];
				if (!bullet.visible) continue;
				bulletImage = bullet.type.image;
				if (bullet.angle == ANGLE_UP){
					g.drawImage(bulletImage, 
							bullet.x-bulletImage.halfWidth, 
							bullet.y-bulletImage.halfHeight);
				} else {
					g.drawRotatedImage(bulletImage, 
							bullet.x-bulletImage.halfWidth, 
							bullet.y-bulletImage.halfHeight,
							bulletImage.width,
							bulletImage.height,
							bullet.angle,
							ANGLE_UP);
				}
				if (HITBOXES_VISIBLE)
					g.drawCircle(bullet.x, bullet.y, bullet.radius, hitboxColor);
			}
			
			// draw the enemy
			if (!enemy.visible) continue;
			enemyImage = enemy.type.image;
			if (enemy.angle == ANGLE_DOWN){
				g.drawImage(enemyImage, 
						enemy.x-enemyImage.halfWidth, 
						enemy.y-enemyImage.halfHeight);
			} else {
				g.drawRotatedImage(enemyImage, 
						enemy.x-enemyImage.halfWidth, 
						enemy.y-enemyImage.halfHeight,
						enemyImage.width,
						enemyImage.height, 
						enemy.angle,
						ANGLE_DOWN);
			}
			if (HITBOXES_VISIBLE)
				g.drawCircle(enemy.x, enemy.y, enemy.radius, hitboxColor);
		}

		// special effects drawing
		for (int i = 0; i < specialEffects.size(); i++) {
			specialEffect = specialEffects.get(i);
			if (specialEffect == null) continue;
			
			// clean up special effects
			if (!specialEffect.active){
				specialEffects.remove(i);
				i--;
			}
			
			image = specialEffect.getImage();
			if (specialEffect.scale == NORMAL_SCALE){
				g.drawImage(image, specialEffect.x - image.halfWidth, 
						           specialEffect.y - image.halfHeight);
			} else {
				g.drawScaledImage(image, 
						specialEffect.x - image.halfWidth, 
						specialEffect.y - image.halfHeight,
						specialEffect.x,
						specialEffect.y,
						specialEffect.scale);
			}
		}
		
		// power ups drawing
		length = powerUps.length;
		for (int i = 0; i < length; i++) {
			powerUp = powerUps[i];
			if (!powerUp.visible) continue;
			image = powerUp.type.image;
			g.drawImage(image, powerUp.x - image.halfWidth, 
                               powerUp.y - image.halfHeight);
		}
		
		animate(deltaTime);

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

	private void animate(float deltaTime) {
		heroAnimation.update(ANIMATION_UPDATE);
		heroTurningLeftAnimation.update(ANIMATION_UPDATE);
		heroTurningRightAnimation.update(ANIMATION_UPDATE);
		length = specialEffects.size();
		for (int i = 0; i < length; i++) {
			specialEffect = specialEffects.get(i);
			if (specialEffect.active)
				specialEffect.update(ANIMATION_UPDATE);
		}

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