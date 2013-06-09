package com.raiden.game;

/**
 * A visitor must define what is the results of the 
 * interaction when visiting a certain object.
 */
public interface Visitor {
	/**
	 * Specifies what happens when this object visits a ship.
	 * @param ship The visited ship.
	 */
	public void visit(Ship ship);
	
	/**
	 * Specifies what happens when this object visits a bullet.
	 * @param bullet The visited bullet.
	 */
	public void visit(Bullet bullet);
	
	/**
	 * Specifies what happens when this object visits a power up.
	 * @param powerUp The visited power up.
	 */
	public void visit(PowerUp powerUp);
}