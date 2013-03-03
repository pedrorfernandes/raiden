package general_utilities;

public class WaitTime {
	
	public static void wait (int n) { //Makes the system wait for n seconds
		try {
			int time = n * 1000;
			Thread.sleep(time);
		} catch (Exception e) {
			// handle exception
		}
	}
}
