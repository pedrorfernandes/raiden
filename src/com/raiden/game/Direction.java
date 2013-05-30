package com.raiden.game;

public enum Direction{
	Left, Right;
	
	public float turn(float angle1, float angle2){
		if (this == Left)
			return angle1 + angle2;
		else
			return angle1 - angle2;
	}
};
