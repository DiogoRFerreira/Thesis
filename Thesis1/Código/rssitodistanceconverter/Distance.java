package rssitodistanceconverter;

public class Distance implements DistanceCalculator {
	
    public double calculator(double rssi, double txpower) {

        if (rssi == 56) {
            return 0.98; // if we cannot determine accuracy, return -1.
        }

        //0,98m = 56dB
        double ratio = (rssi + 56) / (-20);
        double distance = (0.98) * Math.pow(10, ratio);

        //ver na net o do txpower
        //double ratio1 = (txpower-rssi) / (-20);
        //double distance1 = Math.pow(10, ratio1);

        //ver com coeficientes

        return distance;
    }
}
