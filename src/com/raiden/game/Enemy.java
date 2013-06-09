package com.raiden.game;

import java.util.ArrayList;

import com.raiden.framework.Image;

import android.graphics.Point;

/**
 * The enemy is a ship that targets the hero ship (or any other ship for that matter).
 * Every enemy will move by default in the starting direction and shoot at the targeted hero.
 * Enemies can also have flight patterns which allow them to make special maneuvers,
 * such as turns and spins.
 */
public class Enemy extends Ship {
	public boolean outOfRange;
	public float angle, nextAngle;
	private float radians;
	
	private int moveX;
	private int moveY;

	private static final int MAX_BULLETS = 10;
	
	private int turnSpeed;
	
	private FlightPattern flightPattern;
	
	public Type type;
	public PowerUp.Type powerUpTypeDrop;
	
	// iterating variables
	private static Bullet bullet;
	private static int length;
	
	/**
	 * The various types of enemies.
	 */
	public static enum Type{
		Normal(6 , 2, 4, 10, 50, 1400, Bullet.Type.Enemy,      Assets.enemy1, "Normal"),
		Fast  (10, 4, 2, 20, 50, 2000, Bullet.Type.EnemyHeavy, Assets.enemy2, "Fast"),
		TankBoss (1, 1, 300, 1000, 100, 500, Bullet.Type.EnemyHeavy, Assets.tankBoss, "TankBoss"),
		TechBoss (4, 5, 200, 2000, 70, 700, Bullet.Type.EnemyHeavy, Assets.techBoss, "TechBoss");
		
		public int speed, turnSpeed, armor, score, radius, reloadDone;
		public Image image;
		public Bullet.Type bulletType;
		public String id;
		
		/**
		 * @param id The id string of an enemy type
		 * @return The enemy type associated with the given id, null if id doesn't exist.
		 */
		public static Type getType(String id){
			Type[] types = Type.values();
			for (Type type : types){
				if ( type.id.equals(id) )
					return type;
			}
			return null;
		}
		
		/**
		 * Constructor for a new enemy type.
		 * @param speed Speed of the enemy.
		 * @param turnSpeed Angle of enemy delta turn (bigger -> faster turn).
		 * @param armor The armor of the enemy.
		 * @param score Score that is given if the enemy is destroyed.
		 * @param radius Radius of the enemy.
		 * @param reloadDone Time taken to reload between each shot.
		 * @param bulletType The bullet type that this enemy fires.
		 * @param image The enemy representation.
		 * @param id String id of this enemy type.
		 */
		Type(int speed, int turnSpeed, int armor, int score, 
				int radius, int reloadDone, Bullet.Type bulletType, Image image, String id){
			this.speed = speed;
			this.turnSpeed = turnSpeed;
			this.armor = armor;
			this.score = score;
			this.radius = radius;
			this.reloadDone = reloadDone;
			this.bulletType = bulletType;
			this.image = image;
			this.id = id;
		}
	}
	
	/**
	 * Drops the power up (if is holding any).
	 */
	public void dropPowerUp(){
		if (this.powerUpTypeDrop != null) {
			gameScreen.spawnPowerUp(this.x, this.y, powerUpTypeDrop);
			powerUpTypeDrop = null;
		}
	}
	
	/**
	 * Sets the current target to a new ship.
	 * @param target The new ship to target.
	 */
	public void setTarget(Ship target){
		this.target = target;
		for (Turret turret: turrets) {
			turret.setTarget(target);
		}
	}

	/**
	 * Creates a new enemy. 
	 * This will not make the enemy appear in game, for that purpose check enemy.spawn()
	 * @param target The ship to target.
	 */
	public Enemy(Ship target) {
		this.visible = false;
		this.outOfRange = true;
		this.alive = false;
		this.autofire = true;
		this.target = target;
		this.type = Type.Normal;

		emptyTurretPositions = new ArrayList<Point>();
		emptyTurretPositions.add(new Point(0, 0));

		turrets = new ArrayList<Turret>();
		addTurret(0.0f, this.type.bulletType);
		
		shots = new Bullet[MAX_BULLETS];
		
		for (int i = 0; i < MAX_BULLETS; i++)
		{
			shots[i] = new Bullet();
		}
	}
	
	/**
	 * Sets the current enmy type to a new type.
	 * @param type The new enemy type.
	 */
	public void setType(Type type){
		this.type = type;
		this.setSpeed(type.speed);
		this.turnSpeed = type.turnSpeed;
		this.bulletType = type.bulletType;
		this.armor = type.armor;
		this.score = type.score;
		this.radius = type.radius;
		this.reloadDone = type.reloadDone;
		for (Turret turret : turrets) {
			turret.setBulletType(this.type.bulletType);
		}
	}

	/**
	 * Spawns an enemy in the game, on a given position.
	 * @param x The X coordinate of the starting position.
	 * @param y The Y coordinate of the starting position.
	 * @param angle The starting angle of the movement.
	 * @param type The type of enemy.
	 * @param flightPattern The flight pattern the enemy will execute (null if enemy just goes forward).
	 * @param powerUpDrop The type of power up that the enemy will drop on destruction (null if none).
	 */
	public void spawn(int x, int y, float angle, Type type, FlightPattern flightPattern, PowerUp.Type powerUpDrop) {
		this.x = x;
		this.y = y;
		this.angle = angle; this.nextAngle = angle;
		this.visible = true;
		this.outOfRange = false;
		this.radians = (float) Math.toRadians(angle);
		this.setType(type);
		this.alive = true;
		this.impactTimer = IMPACT_INTERVAL;
		this.reloadTime = reloadDone;
		this.readyToFire = true;
		this.powerUpTypeDrop = powerUpDrop;
		if (flightPattern != null)
			this.flightPattern = new FlightPattern(flightPattern);
	}

	/**
	 * Updates the enemy (if in game).
	 * @param deltaTime The time passed since the last update.
	 */
	public void update(float deltaTime){

		if (outOfRange || !alive) return;
		
		x += moveX;
		y += moveY;

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;
		else
			visible = true;

		if ( !visible ){
			// if the enemy is out of bounds, it isn't coming back to the screen
			if (x < outMinX || y < outMinY || x > outMaxX || y > outMaxY){
				outOfRange = true;
				flightPattern = null;
				return;
			}
		}
		
		if (target != null)
			length = target.shots.length;
		else 
			length = 0;
		for (int i = 0; i < length; i++) {
			bullet = target.shots[i];
			if (bullet.visible)
				this.checkCollision(bullet);
		}

		reload(deltaTime);
		
		if (Float.compare(angle, nextAngle) != 0 && flightPattern != null){
			adjustAngle(flightPattern.getCurrentDirection());
		} else if (flightPattern != null) {
			flightPattern.update(deltaTime);
			nextAngle = flightPattern.getCurrentAngle();
		}

		if (target != null && autofire) {
			shoot();
		}

		if (impactTimer < IMPACT_INTERVAL)
			impactTimer += deltaTime;
	}

	/**
	 * @return If this enemy is in game.
	 */
	public boolean isInGame(){
		return (alive && !outOfRange);
	}

	/**
	 * Sets a new flight pattern.
	 * @param flightPattern The new flight pattern.
	 */
	public void setFlightPattern(FlightPattern flightPattern){
		this.flightPattern = flightPattern;
	}

	/**
	 * @return If the enemy is out of the game limits.
	 */
	public boolean isOutOfRange(){
		return outOfRange;
	}

	/**
	 * @return The current angle of movement.
	 */
	public float getAngle() {
		return angle;
	}
	
	/**
	 * Sets the speed for an enemy (and associated delta movements in X and Y).
	 * @param speed The new speed.
	 */
	@Override
	public void setSpeed(int speed){
		this.speed = speed;
		this.moveX = Math.round(speed * FastMath.cos( radians));
		this.moveY = Math.round(speed * FastMath.sin(-radians));
	}
	
	/**
	 * Sets the angle of movement to a new angle.
	 * @param angle The new angle.
	 */
	public void setAngle(float angle){
		this.angle = angle;
		
		if (this.angle > 360.0f)
			this.angle -= 360.0f;
		else if (this.angle < 0.0f)
			this.angle += 360.0f;
		
		this.radians = (float) Math.toRadians(angle);
		this.setSpeed(this.speed);
	}
	
	/**
	 * Sets the next angle of movement.
	 * @param angle The next angle.
	 */
	public void setNextAngle(float angle){
		this.nextAngle = angle;
	}
	
	/**
	 * Adjusts the current angle of the ship closer to the new angle.
	 * The amount of turning angle added is determined by the turnSpeed.
	 * @param direction The direction to turn.
	 */
	public void adjustAngle(Direction direction){
		if (Float.compare(angle, nextAngle) != 0){
			float turningAngle =  Math.abs(this.angle - nextAngle);
			if ( turningAngle > turnSpeed){
				setAngle(direction.turn(this.angle,turnSpeed));
			} else {
				setAngle(direction.turn(this.angle, turningAngle));
			}
		}
	}
	
	@Override
	public void takeDamage(Collidable collidable){
		notifyObservers(collidable, Event.EnemyHit);
		super.takeDamage(collidable);
	}
	
	/**
	 * If true, sets the enemy to dead and notifies observers.
	 * @return True if the enemy was destroyed.
	 */
	public boolean checkIfDestroyed(){
		if (armor < 1){
			alive = false; visible = false; flightPattern = null;
			notifyObservers(Event.Explosion);
			notifyObservers(this, Event.ScoreUp);
			dropPowerUp();
			return true;
		} else {
			return false;
		}
	}
}
