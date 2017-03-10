package thesis;

public class Signal {
	int rssi;
	int txPower;
	double distance;
	
	public Signal(int rssi, int txPower){
		this.rssi = rssi;
		this.txPower= txPower;
		this.distance = this.getDistance();
	}
	
	double getDistance() {
	    /*
	     * RSSI = TxPower - 10 * n * lg(d)
	     * n = 2 (in free space)
	     * 
	     * d = 10 ^ ((TxPower - RSSI) / (10 * n))
	     */
	    return Math.pow(10d, ((double) txPower - rssi) / (10 * 2));
	}
	//n = 4.2119, paper - Evaluation of the Reliability of RSSI for Indoor Localization, Qian Dong and Waltenegus Dargie
}
