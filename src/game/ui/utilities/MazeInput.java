package game.ui.utilities;

import java.io.IOException;
import java.util.Scanner;

/* Utilities to receive player input */
public class MazeInput {

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

	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		
		return true;
	}

}
