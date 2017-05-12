package beacon;


public class Beacon{

	private int major;
    private int minor;
    private String uuid;
    private double txpower;
    /*
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

    public double getTxpower() {
        return Txpower;
    }

    public void setTxpower(double txpower) {
        Txpower = txpower;
    }
*/
    //Constructor
    public Beacon(int major, int minor, String uuid, double txpower){
        this.major = major;
        this.minor = minor;
        this.txpower = txpower;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Major: "+major+", Minor: "+minor+", TXPower: "+txpower+", UUID: "+uuid;
    }
    
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}


}
