package beacon;

import java.io.Serializable;

/**
 * Created by diogoferreira on 25/02/17.
 */
public class BeaconDetected implements Serializable {

    private static final long serialVersionUID = 1L;
    protected Beacon beacon;
    protected Long timestamp;
	protected double rssi;

    public BeaconDetected(Beacon beacon,Long timestamp,double rssi){
        this.beacon=beacon;
		this.rssi = rssi;
        this.timestamp=timestamp;
    }
/*
    public Beacon getBeacon() {
		return beacon;
	}

	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public double getRssi() {
		return Rssi;
	}

	public void setRssi(double rssi) {
		Rssi = rssi;
	}
*/
	@Override
    public String toString() {
        return "Beacon detected [beaconinfo=" + beacon + ", time=" + timestamp+ ", rssi:"+rssi+"]";
    }
}
