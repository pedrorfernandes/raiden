package game.ui;

public class ResultEvent extends GameEvent {
	
	public ResultEvent(int id) { //ID 0 means the Hero was killed, ID 1 means the Hero exited the maze and won the game
		if(id == 0) {
			name = "lost";
			message = "Oh no, the dragon killed you!";
		}
		else if(id == 1) {
			name = "won";
			message = "Congratulations, you exited the maze!";
		}
	}
}