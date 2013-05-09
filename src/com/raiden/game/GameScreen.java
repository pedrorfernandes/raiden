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
import com.raiden.framework.Sound;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;

	// Variable Setup
	// You would create game objects here.

	int livesLeft = 1;
	Paint paint;

	public static Ship hero;
	private Image heroImage;
	private Animation heroAnimation, 
	heroTurningLeftAnimation, 
	heroTurningRightAnimation;

	// touch and input variables
	private Point dragPoint;
	private boolean shooting = false;
	private boolean stoppedShooting = true;

	public static Point screenSize;

	// sound variables
	private int volume = 100;
	private static final int FIRST_SHOT_FIRED = 8;
	private static ArrayList<Sound> hitSounds;
	private static int currentHitSound = 0;
	private static ArrayList<Sound> explosionSounds;
	private static int currentExplosionSound = 0;

	public static ArrayList<Explosion> explosions;
	private static final int NUMBER_OF_EXPLOSIONS = 20;
	private static final float BIG_EXPLOSION = 1.5f;
	private static final float NORMAL_EXPLOSION = 1.0f;
	private static final float SMALL_EXPLOSION = 0.4f;

	// enemy variables
	private Image enemyImage;
	private static final int MAX_ENEMIES = 20;
	public static Enemy[] enemies = new Enemy[MAX_ENEMIES];

	private static final float ANGLE_DOWN = 270.0f;
	private static final float ANGLE_UP = 90.0f;

	// constants for animation
	private static final int EXPLOSION_DURATION = 3;
	private static final int HERO_ANIMATION_DURATION = 10;
	private static final int ANIMATION_UPDATE = 1;
	
	// debug variables
	private static boolean HITBOXES_VISIBLE = false;
	private static Paint hitboxColor;

	// TODO random variables that must be deleted later!
	private Random random = new Random();
	private int counter = 0;

	// iterating variables
	private int length;
	Bullet bullet;
	Enemy enemy;
	Image image;
	Explosion explosion;
	ListIterator<Enemy> enemyItr;

	public GameScreen(Game game) {
		super(game);
		screenSize = game.getSize();

		// Initialize game objects here
		hero = new Ship();
		heroAnimation = new Animation();
		heroAnimation.addFrame(Assets.hero1, HERO_ANIMATION_DURATION);
		heroAnimation.addFrame(Assets.hero2, HERO_ANIMATION_DURATION);
		heroTurningLeftAnimation = new Animation();
		heroTurningLeftAnimation.addFrame(Assets.heroLeft1, HERO_ANIMATION_DURATION);
		heroTurningLeftAnimation.addFrame(Assets.heroLeft2, HERO_ANIMATION_DURATION);
		heroTurningRightAnimation = new Animation();
		heroTurningRightAnimation.addFrame(Assets.heroRight1, HERO_ANIMATION_DURATION);
		heroTurningRightAnimation.addFrame(Assets.heroRight2, HERO_ANIMATION_DURATION);
		heroImage = heroAnimation.getImage();

		enemyImage = Assets.enemy1;
		for (int i = 0; i < MAX_ENEMIES; i++)
		{
			enemies[i] = new Enemy();
		}
		
		spawnEnemy(200, 200, 295.0f);
		spawnEnemy(500, 500, 225.0f);

		Assets.machinegun.setLooping(true);
		hitSounds = new ArrayList<Sound>();
		hitSounds.add(Assets.hit1); hitSounds.add(Assets.hit2); 
		hitSounds.add(Assets.hit3); hitSounds.add(Assets.hit4); 
		hitSounds.add(Assets.hit5); 

		explosionSounds = new ArrayList<Sound>();
		explosionSounds.add(Assets.explosionSound1); explosionSounds.add(Assets.explosionSound2);
		explosionSounds.add(Assets.explosionSound3); explosionSounds.add(Assets.explosionSound4);
		explosionSounds.add(Assets.explosionSound5); explosionSounds.add(Assets.explosionSound6);
		explosionSounds.add(Assets.explosionSound7); explosionSounds.add(Assets.explosionSound8);
		explosionSounds.add(Assets.explosionSound9); explosionSounds.add(Assets.explosionSound10);
		explosionSounds.add(Assets.explosionSound11);

		explosions = new ArrayList<Explosion>();
		Explosion.addFrame(Assets.explosion1, EXPLOSION_DURATION);
		Explosion.addFrame(Assets.explosion2, EXPLOSION_DURATION);
		Explosion.addFrame(Assets.explosion3, EXPLOSION_DURATION);
		Explosion.addFrame(Assets.explosion4, EXPLOSION_DURATION);
		Explosion.addFrame(Assets.explosion5, EXPLOSION_DURATION);
		Explosion.addFrame(Assets.explosion6, EXPLOSION_DURATION);

		// create a number of explosions
		for (int i = 0; i < NUMBER_OF_EXPLOSIONS; i++) {
			explosions.add(new Explosion());
		}

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
	
	public void spawnEnemy(int x, int y, float angle){
		for (int i = 0; i < MAX_ENEMIES; i++)
		{
			enemy = enemies[i];
			if ( !enemy.isInGame() ){
				enemy.spawn(x, y, angle);
				return;
			}
		}
	}

	public void setExplosion(int x, int y, float scale){
		length = explosions.size();
		for (int i = 0; i < length; i++) {
			if (!explosions.get(i).active){
				explosions.get(i).set(x, y, scale);
				return;
			}
		}
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
			//spawnEnemy(random.nextInt(800), 0, random.nextFloat()*60 + 240);
			//spawnEnemy(random.nextInt(800), 0, random.nextFloat()*60 + 240);
			spawnEnemy(random.nextInt(800), 0, ANGLE_DOWN);
			spawnEnemy(random.nextInt(800), 0, ANGLE_DOWN);
			spawnEnemy(random.nextInt(800), 0, ANGLE_DOWN);
			counter = 0;
		}

		// All touch input is handled here
		length = touchEvents.size();
		for (int i = 0; i < length; i++) {
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

		// update the hero's bullets
		//length = Ship.shotsFired.size();
		for (int i = 0; i < Ship.shots.length; i++) {
			bullet = Ship.shots[i];
			//if (!bullet.visible) continue;
			bullet.update();
			if ( bullet.checkHit() ){
				hitSounds.get(currentHitSound).play(volume);
				currentHitSound++;
				if (currentHitSound >= hitSounds.size() )
					currentHitSound = 0;
				setExplosion(bullet.x, bullet.y, SMALL_EXPLOSION);
			}
		}

		hero.update(deltaTime);

		// update the enemies  
		//enemyItr = enemies.listIterator();
		length = enemies.length;
		for (int i = 0; i < length; i++) {
			enemy = enemies[i];
			enemy.update(deltaTime);
			if ( enemy.hasDied() ){
				// play an explosion
				explosionSounds.get(currentExplosionSound).play(volume);
				currentExplosionSound++;
				if (currentExplosionSound >= explosionSounds.size() )
					currentExplosionSound = 0;
				setExplosion(enemy.x, enemy.y, BIG_EXPLOSION);

			}
		}
		/*
		while( enemyItr.hasNext() ){
			enemy = enemyItr.next();
			enemy.update(deltaTime);
			if ( enemy.hasDied() ){
				// play an explosion
				explosionSounds.get(currentExplosionSound).play(volume);
				currentExplosionSound++;
				if (currentExplosionSound >= explosionSounds.size() )
					currentExplosionSound = 0;
				setExplosion(enemy.x, enemy.y, BIG_EXPLOSION);

			}
		}
		*/
		
		// update the enemy bullets
		for (int i = 0; i < Enemy.shots.length; i++) {
			bullet = Enemy.shots[i];
			//if (!bullet.visible) continue;
			bullet.update();
			if ( bullet.checkHit()  ){
				Assets.heroHit.play(volume);
				setExplosion(bullet.x, bullet.y, SMALL_EXPLOSION);
			}
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
				hero.x - heroImage.getHalfWidth(), 
				hero.y - heroImage.getHalfHeight());
		if (HITBOXES_VISIBLE) 
			g.drawCircle(hero.x, hero.y, hero.radius, hitboxColor);

		// hero shots drawing
		length = Ship.shots.length;
		for (int i = 0; i < length; i++) {
			Bullet bullet = Ship.shots[i];
			if (!bullet.visible) continue;
			if (bullet.angle == ANGLE_UP){
				g.drawImage(Assets.heroBullet1, 
						bullet.x-Assets.heroBullet1.getHalfWidth(), 
						bullet.y-Assets.heroBullet1.getHalfHeight());
			} else {
				g.drawRotatedImage(Assets.heroBullet1, 
						bullet.x-Assets.heroBullet1.getHalfWidth(), 
						bullet.y-Assets.heroBullet1.getHalfHeight(),
						Assets.heroBullet1.getWidth(),
						Assets.heroBullet1.getHeight(), 
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
			if (!enemy.visible) continue;
			if (enemy.angle == ANGLE_DOWN){
				g.drawImage(enemyImage, 
						enemy.x-enemyImage.getHalfWidth(), 
						enemy.y-enemyImage.getHalfHeight());
			} else {
				g.drawRotatedImage(enemyImage, 
						enemy.x-enemyImage.getHalfWidth(), 
						enemy.y-enemyImage.getHalfHeight(),
						enemyImage.getWidth(),
						enemyImage.getHeight(), 
						enemy.angle,
						ANGLE_DOWN);
			}
			if (HITBOXES_VISIBLE)
				g.drawCircle(enemy.x, enemy.y, enemy.radius, hitboxColor);
		}
		
		// enemy bullets drawing
		length = Enemy.shots.length;
		for (int i = 0; i < length; i++) {
			Bullet bullet = Enemy.shots[i];
			if (!bullet.visible) continue;
			if (bullet.angle == ANGLE_UP){
				g.drawImage(Assets.enemyBullet1, 
						bullet.x-Assets.enemyBullet1.getHalfWidth(), 
						bullet.y-Assets.enemyBullet1.getHalfHeight());
			} else {
				g.drawRotatedImage(Assets.enemyBullet1, 
						bullet.x-Assets.enemyBullet1.getHalfWidth(), 
						bullet.y-Assets.enemyBullet1.getHalfHeight(),
						Assets.enemyBullet1.getWidth(),
						Assets.enemyBullet1.getHeight(), 
						bullet.angle,
						ANGLE_UP);
			}
			if (HITBOXES_VISIBLE)
				g.drawCircle(bullet.x, bullet.y, bullet.radius, hitboxColor);
		}

		// explosions drawing
		length = explosions.size();
		for (int i = 0; i < length; i++) {
			explosion = explosions.get(i);
			if (!explosion.active) continue;
			image = explosion.getImage();
			if (explosion.scale == NORMAL_EXPLOSION){
				g.drawImage(image, explosion.x - image.getHalfWidth(), 
                                   explosion.y - image.getHalfHeight());
			} else {
				g.drawScaledImage(image, 
                            explosion.x - image.getHalfWidth(), 
                            explosion.y - image.getHalfHeight(),
                            explosion.x,
                            explosion.y,
                            explosion.scale);
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
		heroAnimation.update(ANIMATION_UPDATE);
		heroTurningLeftAnimation.update(ANIMATION_UPDATE);
		heroTurningRightAnimation.update(ANIMATION_UPDATE);
		length = explosions.size();
		for (int i = 0; i < length; i++) {
			explosion = explosions.get(i);
			if (explosion.active)
				explosion.update(ANIMATION_UPDATE);
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