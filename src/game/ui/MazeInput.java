package game.ui;

import java.io.IOException;
import java.util.Scanner;

/**
 * The Class MazeInput implements utilities to receive and parse the user's input
 */
public class MazeInput {

	/**
	 * Receives a char from user input
	 *
	 * @return First char in user input
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static char getChar() throws IOException {
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		char c;

		String s = in.nextLine();
		if(!s.isEmpty())
			c = s.charAt(0);
		else
			c = ' ';

		return c;
	}

	/**
	 * Checks if the string given is an integer
	 *
	 * @param s The string to analyze
	 * @return true, if the string represents an integer
	 */
	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		
		return true;
	}

}
