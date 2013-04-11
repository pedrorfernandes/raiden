package game.ui;

/**
 * The Class ResultEvent stores game over messages for winning and losing.
 */
public class ResultEvent extends GameEvent {

	/**
	 * Instantiates a new result event.
	 * @param id ID 0 means the exit has opened, ID 1 means the Hero was killed, ID 2 means the Hero exited the maze and won the game
	 */
	public ResultEvent(int id) {
		if(id == 0) {
			name = "exitOpened";
			message = "You slayed all the dragons! The exit is open!\n";
		}
		else if(id == 1) {
			name = "lost";
			message = "Oh no, the dragon killed you!\n";
		}
		else if(id == 2) {
			name = "won";
			message = "Congratulations, you exited the maze!\n";
		}
	}
}