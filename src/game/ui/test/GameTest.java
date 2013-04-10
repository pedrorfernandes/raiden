package game.ui.test;

import java.util.Stack;

public abstract class GameTest {
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
