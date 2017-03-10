package diogoferreira.myapplication;

import java.io.Serializable;

public class Beacon implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Major;
    private int Minor;
    private String Uuid;
    private double Rssi;
    private double Txpower;

    //Setters and Getters

    public int getMajor() {
        return Major;
    }

    public void setMajor(int major) {
        Major = major;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }

    public int getMinor() {
        return Minor;
    }

    public void setMinor(int minor) {
        Minor = minor;
    }

    public double getRssi() {
        return Rssi;
    }

    public void setRssi(double rssi) {
        Rssi = rssi;
    }

    public double getTxpower() {
        return Txpower;
    }

    public void setTxpower(double txpower) {
        Txpower = txpower;
    }

    //Constructor
    public Beacon(int major, int minor, String uuid, double rssi, double txpower){
        this.Major = major;
        this.Minor = minor;
        this.Rssi = rssi;
        this.Txpower = txpower;
        this.Uuid = uuid;
    }

    @Override
    public String toString() {
        return "Major: "+Major+", Minor: "+Minor+", RSSI: "+Rssi+", TXPower: "+Txpower+", UUID: "+Uuid;
    }


}
