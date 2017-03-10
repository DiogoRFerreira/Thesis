package diogoferreira.myapplication;

import java.io.Serializable;
//import java.security.Timestamp;

/**
 * Created by diogoferreira on 25/02/17.
 */
public class Objectsent implements Serializable {

    private static final long serialVersionUID = 1L;
    public Beacon beaconinfo;
    public Long time;
    public Double distance;

    public Objectsent(Beacon beacon,Long timestamp, Double distance){
        this.beaconinfo=beacon;
        this.distance=distance;
        this.time=timestamp;
    }

    @Override
    public String toString() {
        return "Objectsent [beaconinfo=" + beaconinfo + ", time=" + time + ", distance=" + distance + "]";
    }
}
