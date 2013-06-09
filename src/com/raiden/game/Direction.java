package com.raiden.game;

/**
 * The directions which a ship can turn.
 */
public enum Direction{
	Left("Left"), Right("Right");
	
	String id;
	
	/**
	 * Creates a direction.
	 * @param id The id string of the direction.
	 */
	private Direction(String id){
		this.id = id;
	}
	
	/**
	 * @param id The direction id.
	 * @return The direction associated with the id, null if id isn't valid.
	 */
	public static Direction getDirection(String id){
		Direction[] directions = Direction.values();
		for (Direction direction : directions){
			if ( direction.id.equals(id) )
				return direction;
		}
		return null;
	}
	
	/**
	 * @param angle1 The starting angle.
	 * @param angle2 The angle to turn.
	 * @return The new angle after turning, specified by this direction.
	 */
	public float turn(float angle1, float angle2){
		if (this == Left)
			return angle1 + angle2;
		else
			return angle1 - angle2;
	}
};
