package main;

import java.util.Iterator;
import java.util.LinkedList;

import positionestimation.FingerprintingFunction;
import positionestimation.KNN;
import positionestimation.NN;
import positionestimation.WKNN;
import positiontracking.TrackingFunction;

public class Main {

	public static void main(String[] args) {
											//  1		2	  3		 4		5		6	  7		 8		9	   10
	    double[][] offline = new double[][] {{-50.0, -56.0, -77.0, -60.0, -68.0, -72.0, -90.0, -80.0, -80.0, -80.0},
	    									 {-58.0, -65.0, -71.0, -68.0, -70.0, -64.0, -90.0, -80.0, -80.0, -80.0},
	    									 {-68.0, -62.0, -72.0, -70.0, -67.0, -57.0, -90.0, -80.0, -80.0, -80.0},
	    									 {-55.0, -60.0, -55.0, -59.0, -66.0, -62.0, -90.0, -80.0, -80.0, -80.0},
	    									 {-60.0, -71.0, -62.0, -53.0, -69.0, -71.0, -90.0, -80.0, -80.0, -80.0},//5
	    									 {-70.0, -70.0, -67.0, -61.0, -61.0, -55.0, -90.0, -80.0, -80.0, -80.0},
	    									 {-60.0, -62.0, -56.0, -48.0, -73.0, -76.0, -90.0, -80.0, -80.0, -80.0},
	    									 {-64.0, -60.0, -70.0, -50.0, -64.0, -70.0, -90.0, -80.0, -80.0, -80.0},
	    									 {-61.0, -63.0, -67.0, -68.0, -50.0, -59.0, -90.0, -80.0, -80.0, -80.0},
	    									 {-85.0, -85.0, -85.0, -85.0, -75.0, -80.0, -53.0, -80.0, -75.0, -60.0},//10
	    									 {-85.0, -85.0, -85.0, -85.0, -85.0, -75.0, -56.0, -80.0, -75.0, -62.0},
	    									 {-85.0, -85.0, -85.0, -85.0, -85.0, -85.0, -60.0, -80.0, -75.0, -65.0},
	    									 {-75.0, -75.0, -75.0, -75.0, -75.0, -75.0, -72.0, -63.0, -68.0, -50.0},
	    									 {-80.0, -80.0, -80.0, -80.0, -80.0, -80.0, -79.0, -60.0, -60.0, -56.0},
	    									 {-80.0, -80.0, -80.0, -80.0, -80.0, -80.0, -76.0, -57.0, -52.0, -63.0},//15
	    									 {-85.0, -80.0, -80.0, -80.0, -80.0, -80.0, -76.0, -50.0, -60.0, -69.0},
	    									 {-65.0, -75.0, -75.0, -70.0, -70.0, -70.0, -78.0, -63.0, -65.0, -56.0},
	    									 {-65.0, -70.0, -70.0, -70.0, -70.0, -70.0, -76.0, -60.0, -56.0, -60.0},
	    									 {-80.0, -80.0, -80.0, -80.0, -80.0, -80.0, -78.0, -57.0, -48.0, -62.0},
	    									 {-85.0, -85.0, -85.0, -85.0, -85.0, -85.0, -80.0, -50.0, -56.0, -65.0}};//20
	    									 
	    double[][] positions_finger = new double[][]{{1.6, 10.2},
	    											 {3.0, 10.2},
	    											 {4.4, 10.2},
	    											 {1.6, 8.6},
	    											 {3.0, 8.6},//5
	    											 {4.4, 8.6},
	    											 {1.6, 7.0},
	    											 {3.0, 7.0},
	    											 {4.4, 7.0},
	    											 {3.0, 16.0},//10
	    											 {4.4, 16.0},
	    											 {5.4, 16.0},
	    											 {3.0, 13.8},
	    											 {4.4, 13.8},
	    											 {5.4, 13.8},//15
	    											 {7.2, 13.8},
	    											 {3.0, 12.6},
	    											 {4.4, 12.6},
	    											 {5.4, 12.6},
	    											 {7.2, 12.6}};//(x,y)
	    
	    double[] time1 = new double[]{-41.0, -74.0, 0.0, 0.0, -81.0};
	    double[] time2 = new double[]{-52.0, -74.0, 0.0, 0.0, -81.0};
	    double[] time3 = new double[]{-71.0, -74.0, 0.0, 0.0, -81.0};
	    double[] time4 = new double[]{-81.0, -74.0, 0.0, 0.0, -81.0};
	    
	    //Position estimation
	    FingerprintingFunction fingerprint = new FingerprintingFunction(positions_finger, null, offline);
	    WKNN wksolver = new WKNN(fingerprint, 3);
	    KNN ksolver = new KNN(fingerprint, 3);
	    NN solver = new NN(fingerprint);
	    //Postion Tracking
	    TrackingFunction tracking = new TrackingFunction();
	    
	    LinkedList<double[]> rssilist = new LinkedList<>();
	    rssilist.addLast(time1);
	    rssilist.addLast(time2);
	    rssilist.addLast(time3);
	    rssilist.addLast(time4);
	    
	    Iterator<double[]> rssireceived = rssilist.iterator();
	    while(rssireceived.hasNext()){
	    	double[] temp = rssireceived.next();
	    	//Fingerprinting
	        fingerprint.setRssi(temp);
	        double[] wkposition = wksolver.estimatePosition();
	        double[] kposition = ksolver.estimatePosition();
	        double[] nposition = solver.estimatePosition();
	        
	        //Kalman Filter
	        double[] kwkposition = tracking.calculatePosition(wkposition[0],wkposition[1]);
	        double[] kkposition = tracking.calculatePosition(kposition[0],kposition[1]);
	        double[] knposition = tracking.calculatePosition(nposition[0],nposition[1]);
	        
	        System.out.println("Fingerprinting");
	        System.out.println("WKNN:"+wkposition[0]+", "+wkposition[1]);
	        System.out.println("KNN:"+kposition[0]+", "+kposition[1]);
	        System.out.println("NN:"+nposition[0]+", "+nposition[1]);
	        System.out.println("Fingerprinting with Kalman filter");
	        System.out.println("KWKNN:"+kwkposition[0]+", "+kwkposition[1]);
	        System.out.println("KKNN:"+kkposition[0]+", "+kkposition[1]);
	        System.out.println("NN:"+knposition[0]+", "+knposition[1]);
	        System.out.println("-------------------------------------------");
	    }
	}

}
