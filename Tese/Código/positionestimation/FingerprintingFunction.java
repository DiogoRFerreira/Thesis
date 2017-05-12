package positionestimation;

public class FingerprintingFunction {

	/**
	 * Known positions of static nodes
	 */
	double positions[][];
	/**
	 * Offline Fingerprinting - RSSI received offline
	 */
	double offlinerssi[][];
	/**
	 * RSSI received online
	 */
	double rssi[];


	public FingerprintingFunction(double positions[][], double rssi[], double offlinerssi[][]) {

		if(positions.length < 1) {
			throw new IllegalArgumentException("Need at least one position.");
		}
		
		int positionDimension = positions[0].length;
		for (int i = 1; i < positions.length; i++) {
			if(positionDimension != positions[i].length) {
				throw new IllegalArgumentException("The dimension of all positions should be the same.");
			}
		}
		
		this.positions = positions;
		this.rssi = rssi;
		this.offlinerssi = offlinerssi;
	}
	
	public void setRssi(double[] rssi){
		this.rssi=rssi;
	}
	
}
