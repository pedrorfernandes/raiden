package game.ui.cli;

import game.logic.Game;
import game.ui.GameInterface;
import game.ui.GameOptions;
import game.ui.GameOutput;
import game.ui.MazeInput;
import game.ui.WaitTime;

/**
 * The Class CLInterface handles a game played in the command line.
 */
public class CLInterface extends GameInterface {

	/**
	 * Gets the game options from the user, starts the game and prints it in the command line.
	 */
	public void startGame() {
		GameOptions options = new GameOptions();
		game = new Game(options);
		GameOutput.clearScreen();          
		GameOutput.printGame(game);
		mainLoop();
	}

	/**
	 * The main loop in a command line interface game.
	 * Asks the player for a move, calls it's turn and the enemies turn
	 * and prints the game while it isn't over.
	 */
	private void mainLoop() {

		boolean goOn = true;
		char input = ' ';

		while(goOn){

			try {
				GameOutput.printAskForMove();
				input = MazeInput.getChar();
			}
			catch(Exception e) {
				System.err.println("Problem reading user input!");
			}

			goOn = game.heroTurn(input);

			GameOutput.clearScreen();
			GameOutput.printGame(game);
			WaitTime.wait(250);

			goOn = game.dragonTurn(goOn);
			GameOutput.clearScreen();
			GameOutput.printGame(game);
			WaitTime.wait(250);

			goOn = game.checkState(goOn);

			GameOutput.clearScreen();
			GameOutput.printEventQueue(game.getEvents() );
			GameOutput.printGame(game);
			WaitTime.wait(250);

		}
	}

}
