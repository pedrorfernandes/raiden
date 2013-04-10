package game.ui.utilities;

public class WaitTime {
	
	public static void wait (int n) { //Makes the system wait for n milliseconds
		try {
			int time = n;
			Thread.sleep(time);
		} catch (Exception e) {
			// handle exception
		}
	}
}
