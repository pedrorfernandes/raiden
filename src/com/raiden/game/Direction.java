package com.raiden.game;

public enum Direction{
	Left("Left"), Right("Right");
	
	String id;
	
	private Direction(String id){
		this.id = id;
	}
	
	public static Direction getDirection(String id){
		Direction[] directions = Direction.values();
		for (Direction direction : directions){
			if ( direction.id.equals(id) )
				return direction;
		}
		return null;
	}
	
	public float turn(float angle1, float angle2){
		if (this == Left)
			return angle1 + angle2;
		else
			return angle1 - angle2;
	}
};
