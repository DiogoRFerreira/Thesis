package diogoferreira.positioningsystem;

import android.app.Application;
import java.util.LinkedList;
import diogoferreira.positioningmethods.FingerprintingFunction;
import diogoferreira.positioningmethods.WKNN;
import diogoferreira.positiontracking.TrackingFunction;


public class Database extends Application{

    //Fingerprinting
    private static double[][] offline = new double[][] {{-48.0, -73.0, -85.0,-82.0,-80.0},{-60.0, -58.0, -70.0,-85.0,-74.0},{-49.0, -69.0, -83.0,-70.0,-76.0},{-79.0,-76.0,-56.0,-79.0,-71.0},{-81.0,-73.0,-57.0,-84.0,-79.0},{-80.0,-76.0,-70.0,-73.0,-54.0},{-89.0,-75.0,-87.0,-70.0,-58.0},{-85.0,-85.0,-80.0,-47.0,-73.0},{-65.0,-67.0,-90.0,-58.0,-71.0}};
    private static double[][] positions_finger = new double[][]{{1.05,0.80},{3.15, 0.8},{1.05,2.4},{4.775,3.2},{5.925,3.2},{3.15,3.95},{3.15,5.45},{1.05,5.45},{1.05,3.95}};//(x,y)

    //Save BLE Beacons Values
    protected final static int number_beacons = 10;
    protected static double[] latestrssireceived = new double[number_beacons];
    protected static double[] timerssireceived = new double[number_beacons];
    protected static int[] timenumberbeacons = new int[number_beacons];

    //Latest version number
    protected static String recent_version_number;

    //Save User Positions
    protected static LinkedList<double[]> positionhistory= new LinkedList<>();

    //Save User RSSI - Calculate position after
    protected static LinkedList<double[]> rssihistory= new LinkedList<>();

    //Notifications
    protected static int item;//Item to appear in Notification intent
    protected static double[] position = new double[2];//Notification Text

    //Draw position on Custom Map
    protected static boolean positioncalculated = false;

    //Position Estimation
    protected static FingerprintingFunction fingerprint = new FingerprintingFunction(positions_finger, timerssireceived, offline);
    protected static WKNN ksolver = new WKNN(fingerprint, 3);

    //Postion Tracking
    protected static TrackingFunction tracking = new TrackingFunction();
}