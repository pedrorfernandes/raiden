package game.ui.test;

import java.util.Stack;

import game.logic.Game;
import game.ui.GameInterface;
import game.ui.GameOptions;

/**
 * The Class TestInterface is the interface used for generating JUnit tests.
 * It generates a game and goes through a whole game loop for each test but doesn't print anything.
 */
public class TestInterface extends GameInterface {
	
	/** The stack that contains all of the hero movements for a given test. */
	private Stack<Character> heroMoves;
	
	public TestInterface(GameOptions options, Stack<Character> heroMoves){
		this.heroMoves = heroMoves;
		game = new Game(options);
	}
	
	public void startGame() {
		mainLoop();
	}

	/**
	 * The main loop in a test ends when the hero finishes all it's movements or when any game over condition is verified.
	 */
	private void mainLoop() {

		boolean goOn = true;
		char input = ' ';

		while( !heroMoves.empty() && goOn ){

			try {
				input = heroMoves.pop();
			}
			catch(Exception e) {
				System.err.println("Problem reading user input!");
			}

			goOn = game.heroTurn(input);

			//GameOutput.clearScreen();
			//System.out.println("Hero's turn");
			//GameOutput.printGame(game);
			//WaitTime.wait(250);

			goOn = game.dragonTurn(goOn);
			//GameOutput.clearScreen();
			//System.out.println("Dragons' turn");
			//GameOutput.printGame(game);
			//WaitTime.wait(250);

			goOn = game.checkState(goOn);

			//GameOutput.clearScreen();
			//GameOutput.printEventQueue(game.getEvents() );
			//GameOutput.printGame(game);
			//WaitTime.wait(250);
		}
	}
}
