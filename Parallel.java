import java.lang.Thread;
import java.lang.Exception;
import java.math.BigInteger;

public class Parallel {

	static boolean sleepNeeded = true;
	static BigInteger squares [] = new BigInteger[2];
	static BigInteger x = new BigInteger("40").pow(1900000);

	static void sqr(BigInteger z, int i) throws Exception {
		if (sleepNeeded) {
			Thread.sleep(2000); // <- uiktau kerek bolsa adeyi 2 sekund uiktatamyz.
		}
		squares[i] = z.multiply(z);
	}

	static BigInteger plus(BigInteger f, BigInteger b) throws Exception {
		return f.add(b);
	}

	static float sequential() throws Exception {
		long startTime = System.currentTimeMillis();
		
		// Kvardarttardy brinien son birin tizbektei eseptep alamyz.
		sqr(x, 0);
		sqr(x, 1);

		// Sosyn kosyndyny esepteimiz.
		plus(squares[0], squares[1]);

		long endTime = System.currentTimeMillis();
		
		return (float)(endTime - startTime)/1000;
		
	}

	static float parallel() throws Exception {
		long startTime = System.currentTimeMillis();

		// Eki kvardartty eki jeke threadtin ishinde esepteimiz.
		Thread th1 = new Thread() {
			@Override
			public void run() {
				try {
					sqr(x, 0);
				} catch (Exception e) {
					e.printStackTrace();		
				}
			}
		};
		th1.start();	

		Thread th2 = new Thread() {
			@Override
			public void run() {
				try {
					sqr(x, 1);
				} catch (Exception e) {
					e.printStackTrace();		
				}
			}
		};
		th2.start();

		// Eki threadting de bitkenin kutemiz.
		th1.join();
		th2.join();

		// Kosyndyny esepteimiz
		plus(squares[0], squares[1]);

		long endTime = System.currentTimeMillis();

		return (float)(endTime - startTime)/1000;
	}

	public static void main (String [] args) throws Exception {
		if (java.util.Arrays.asList(args).contains("demalma")) {
			sleepNeeded = false;
		}
		System.out.println("Java JDK " + System.getProperty("java.version"));
		System.out.println("\tTizbektei esepteuge "+sequential() +" sekund ketti");
		System.out.println("\tParallel esepteuge "+parallel()+ " sekund ketti");
	}
}
