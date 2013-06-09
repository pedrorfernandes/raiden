package com.raiden.game;

import java.util.ArrayList;

/**
 * A flight pattern is used by enemies can do complex movements.
 * It is a simple composition of basic movements and their duration.
 */
public class FlightPattern {
	private ArrayList<Movement> movements;
	private int currentMovement;
	private int currentTime;
	private int totalDuration;
	
	private boolean loop;
	private boolean active;

	/**
	 * Creates an empty flight pattern.
	 */
	public FlightPattern() {
		loop = true;
		active = true;

		movements = new ArrayList<Movement>();
		totalDuration = 0;

		currentTime = 0;
		currentMovement = 0;
	}
	
	/**
	 * Copy constructor.
	 * @param flightPattern The flight pattern to copy.
	 */
	public FlightPattern(FlightPattern flightPattern) {
		loop = flightPattern.loop;
		active = true;
		movements = new ArrayList<Movement>(flightPattern.movements);
		totalDuration = flightPattern.totalDuration;
		currentTime = 0;
		currentMovement = 0;
	}

	/**
	 * Adds a movement to a flight pattern.
	 * @param angle The angle of the movement.
	 * @param duration The duration of the movement
	 * @param turnDirection The direction to turn when entering the movement.
	 */
	public void addMovement(int angle, int duration, Direction turnDirection) {
		totalDuration += duration;
		movements.add(new Movement(angle, totalDuration, turnDirection));
	}
	
	/**
	 * Sets the movements to a new list of movements.
	 * @param newMovements The new list of movements.
	 * @param duration The total duration of the movements.
	 */
	public void setMovements(ArrayList<Movement> newMovements, int duration) {
		totalDuration = duration;
		this.movements = newMovements;
	}

	/**
	 * Updates the flight pattern, determining the current movement to take.
	 * @param elapsedTime The time passed since the last update.
	 */
	public void update(float elapsedTime) {
		if (movements.size() > 1) {
			currentTime += (int)elapsedTime;
			if (currentTime >= totalDuration) {
				if (loop){
					currentTime = currentTime % totalDuration;
					currentMovement = 0;
				} else {
					active = false;
				}

			}

			while (currentTime > getMovement(currentMovement).endTime && active) {
				currentMovement++;
			}
		}
	}

	/**
	 * @return The angle of the current movement.
	 */
	public int getCurrentAngle() {
		if (movements.size() == 0) {
			return 0;
		} else {
			return getMovement(currentMovement).angle;
		}
	}
	
	/**
	 * @return The direction to turn into the current movement.
	 */
	public Direction getCurrentDirection() {
		if (movements.size() == 0) {
			return null;
		} else {
			return getMovement(currentMovement).turnDirection;
		}
	}

	/**
	 * @param i Index of the movement.
	 * @return The corresponding movement.
	 */
	private Movement getMovement(int i) {
		return movements.get(i);
	}
	
	/**
	 * A movement is a angle and a time to execute that angle, plus the direction to turn into that angle.
	 */
	public class Movement {

		int angle;
		int endTime;
		Direction turnDirection;

		/**
		 * Creates a new movement.
		 * @param angle The angle of the movement.
		 * @param endTime Time when this movement ends.
		 * @param turnDirection Direction to turn into this movement.
		 */
		public Movement(int angle, int endTime, Direction turnDirection) {
			this.angle = angle;
			this.endTime = endTime;
			this.turnDirection = turnDirection;
		}
	}
}
