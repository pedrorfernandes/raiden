package game.ui.test;

import java.util.Stack;

/**
 * The Class GameTest is simply a group of JUnit tests
 */
public abstract class GameTest {
	
	/**
	 * Creates a stack with all the specified moves for the hero to execute
	 * @param moves A string containing all the hero moves. For example: "wasd".
	 * @return The resulting stack of movements
	 */
	public Stack<Character> createHeroMoves(String moves){
		Stack<Character> movesStack = new Stack<Character>();
		char[] chars = moves.toCharArray();
		int i = chars.length-1;
		while (i >= 0){
			movesStack.push(chars[i]);
			i--;
		}

		return movesStack;
	}
	
	/**
	 * Creates a stack with all the moves for a single dragon to execute.
	 * @param moves The string containing the moves. For example "wasd".
	 * @return The resulting stack of movements.
	 */
	public Stack<Integer> createDragonMoves(String moves){
		Stack<Integer> movesStack = new Stack<Integer>();
		char[] chars = moves.toCharArray();
		int i = chars.length-1;
		while (i >= 0){
			switch(chars[i]) {
			case ' ': // stand still
				movesStack.push(0);
				break;
			case 's': // down
				movesStack.push(1);
				break;
			case 'w': // up
				movesStack.push(2);
				break;
			case 'd': // right
				movesStack.push(3);
				break;
			case 'a': // left
				movesStack.push(4);
				break;
			}
			i--;
		}
		return movesStack;
	}
	
	/**
	 * Creates the dragon sleeping patterns.1
	 * @param moves The string containing the pattern. For example: "2221322". 2 means don't sleep, 1 means sleep and after the 1 you must specify the number of sleeping turns.
	 * @return the stack
	 */
	public Stack<Integer> createDragonSleeps(String moves){
		Stack<Integer> movesStack = new Stack<Integer>();
		char[] chars = moves.toCharArray();
		int i = chars.length-1;
		while (i >= 0){
			movesStack.push( Character.getNumericValue(chars[i]) );
			i--;
		}

		return movesStack;
	}
}
