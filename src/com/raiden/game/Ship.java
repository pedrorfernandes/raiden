package com.raiden.game;

public class Ship {

	// ship speed
	private int moveSpeed = 8;

	// initial starting point
	private int centerX = 100;
	private int centerY = 377;
	
	private boolean readyToFire = true;

	// position to move next
	private int newX = 100;
	private int newY = 377;
	
	// box which the player can move in
	private int minX = 0;
	private int minY = 0;
	private int maxX = GameScreen.screenSize.x - 1;
	private int maxY = GameScreen.screenSize.y - 1;
	
	private int halfSizeX = 81;
	private int halfSizeY = 63;
		
	public void update() {
		if (newX < centerX) {
			if ( (centerX - newX) < moveSpeed)
				centerX -= (centerX - newX);
			else
				centerX -= moveSpeed;
		} 
		else if (newX > centerX){
			if ( (newX - centerX) < moveSpeed)
				centerX += (newX - centerX);
			else
				centerX += moveSpeed;
		}
		
		if (newY < centerY) {
			if ( (centerY - newY) < moveSpeed)
				centerY -= (centerY - newY);
			else
				centerY -= moveSpeed;
		} 
		else if (newY > centerY) {
			if ( (newY - centerY) < moveSpeed)
				centerY += (newY - centerY);
			else
				centerY += moveSpeed;
		}
	}
	
	public void move(int x, int y){
		newX += x;
		newY += y;
		if (newX < minX)
			newX = minX;
		if (newY < minY)
			newY = minY;
		if (newX > maxX)
			newX = maxX;
		if (newY > maxY)
			newY = maxY;
	}
	
	public int getX(){
		return centerX-halfSizeX;
	}
	
	public int getY(){
		return centerY-halfSizeY;
	}
}
