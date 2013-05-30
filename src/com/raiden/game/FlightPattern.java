package com.raiden.game;

import java.util.ArrayList;

public class FlightPattern {
	protected ArrayList<Movement> movements;
	protected int currentAngle;
	protected int currentTime;
	protected int totalDuration;
	
	protected boolean loop;
	public boolean active;

	public FlightPattern() {
		loop = true;
		active = true;
		
		movements = new ArrayList<Movement>();
		totalDuration = 0;

		synchronized (this) {
			currentTime = 0;
			currentAngle = 0;
		}
	}

	public void addMovement(int angle, int duration, Direction turnDirection) {
		totalDuration += duration;
		movements.add(new Movement(angle, totalDuration, turnDirection));
	}
	
	
	public void setMovements(ArrayList<Movement> newMovements, int duration) {
		totalDuration = duration;
		this.movements = newMovements;
	}

	public void update(float elapsedTime) {
		if (movements.size() > 1) {
			currentTime += (int)elapsedTime;
			if (currentTime >= totalDuration) {
				if (loop){
					currentTime = currentTime % totalDuration;
					currentAngle = 0;
				} else {
					active = false;
				}

			}

			while (currentTime > getMovement(currentAngle).endTime && active) {
				currentAngle++;
			}
		}
	}

	public int getCurrentAngle() {
		if (movements.size() == 0) {
			return 0;
		} else {
			return getMovement(currentAngle).angle;
		}
	}
	
	public Direction getCurrentDirection() {
		if (movements.size() == 0) {
			return null;
		} else {
			return getMovement(currentAngle).turnDirection;
		}
	}

	private Movement getMovement(int i) {
		return movements.get(i);
	}
	
	public class Movement {

		int angle;
		int endTime;
		Direction turnDirection;

		public Movement(int angle, int endTime, Direction nextTurn) {
			this.angle = angle;
			this.endTime = endTime;
			this.turnDirection = nextTurn;
		}
	}
}
