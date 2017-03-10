package positioningmethods;

import org.junit.Test;

public class FingerprintingTest {
	@Test
	public void fingerprintingtest() throws Exception {
		double[][] positions = new double[][] { { 1.0, 4.0 }, { 2.0, 4.0 }, { 3.0, 4.0 } };
		double[] rssi = new double[] { -45.0, -48.0, -70.0, -78.0 };
		double[][] offline = new double[][] {{-45.0, -49.0, -69.0, -75.0 },{-47.0, -50.0, -74.0, -70.0 },{-49.0, -48.0, -70.0, -73.0 }};
		double[][] markov = new double[][]{{1,1,1},{1,-1,-1},{-1,1,1}};
		FingerprintingFunction fingerprint = new FingerprintingFunction(positions,rssi,offline,markov);
		double[] position = fingerprint.calculatePosition();
		System.out.println("Position: ("+position[0]+", "+position[1]+")");
		
		System.out.println("---------------------------------");	
		double[] rssi2 = new double[] { -50.0, -60.0, -75.0, -72.0 };
		fingerprint.setRssi(rssi2);
		System.out.println("Last position: "+(fingerprint.lastposition));	
		position = fingerprint.calculatePosition();	
		System.out.println("Position: ("+position[0]+", "+position[1]+")");
		System.out.println("---------------------------------");		
		double[] rssi3 = new double[] {-49.0, -48.0, -70.0, -73.0 };
		fingerprint.setRssi(rssi3);
		System.out.println("Last position: "+(fingerprint.lastposition));	
		position = fingerprint.calculatePosition();	
		System.out.println("Position: ("+position[0]+", "+position[1]+")");
		System.out.println("Last position: "+(fingerprint.lastposition));	
	}
}
