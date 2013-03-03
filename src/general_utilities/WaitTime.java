package general_utilities;

public class WaitTime {
	
	public static void waitFor (int n) { //Makes the system wait for n seconds

		long t0, t1;

		t0 =  System.currentTimeMillis();

		do{
			t1 = System.currentTimeMillis();
		}
		while ((t1 - t0) < (n * 1000));
	}

}