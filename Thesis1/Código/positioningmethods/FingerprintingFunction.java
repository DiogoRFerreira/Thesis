package positioningmethods;


public class FingerprintingFunction {

	protected int lastposition;
	/**
	 * Known positions of static nodes
	 */
	protected double positions[][];
	/**
	 * Offline Fingerprinting - RSSI received offline
	 */
	protected double offlinerssi[][];
	/**
	 * RSSI received online
	 */
	protected double rssi[];
	
	
	protected double markovchain[][];

	public FingerprintingFunction(double positions[][], double rssi[], double offlinerssi[][], double markov[][]) {

		if(positions.length < 1) {
			throw new IllegalArgumentException("Need at least one position.");
		}
		
		int positionDimension = positions[0].length;
		for (int i = 1; i < positions.length; i++) {
			if(positionDimension != positions[i].length) {
				throw new IllegalArgumentException("The dimension of all positions should be the same.");
			}
		}
		
		this.lastposition = -1;
		this.markovchain = markov;
		this.positions = positions;
		this.rssi = rssi;
		this.offlinerssi = offlinerssi;
	}
	
	public void setRssi(double[] rssi){
		this.rssi=rssi;
	}
	
	public double[] getRssis() {
		return rssi;
	}

	public double[][] getPositions() {
		return positions;
	}
	
	public double[] calculatePosition(){
		double[] position = null;
		double[] vector = new double[offlinerssi.length];
		
		//Euclidean distance vector
		for(int i=0;i<offlinerssi.length;i++){
			double sum = 0;
			for(int j=0;j<rssi.length;j++){
				sum += Math.pow(rssi[j]-offlinerssi[i][j],2); 
			}
			vector[i] = Math.sqrt(sum);
		}
		
		System.out.println(vector[0]+","+vector[1]+","+vector[2]);
		
		//Markov + calculation of the closest 
		//1 possibility , -1 impossible
		if(lastposition!=-1){
			double[] vector2 = new double[markovchain[lastposition].length];
			for(int i=0;i<markovchain[lastposition].length;i++){
				if(vector[i]==0){//euclidean distance = 0
					if(markovchain[lastposition][i]<0){//impossible to go, -1*0 ou 1*0 = 0
						vector2[i] = -1;
					}else{//possible
						vector2[i] = markovchain[lastposition][i]*vector[i];
					}
				}else{//euclidean distance !=0
					vector2[i] = markovchain[lastposition][i]*vector[i];
				}
			}
			
			double aux = 9999.99;
			for (int i=0;i<vector2.length;i++){
				if(aux>=vector2[i] && vector2[i]>=0){
					position = positions[i];
					aux = vector2[i];
					lastposition = i;
				}
			}
		}else{//First time
			double aux = 9999.99;
			for (int i=0;i<vector.length;i++){
				if(aux>=vector[i] && vector[i]>=0){
					position = positions[i];
					aux = vector[i];
					lastposition = i;
				}
			}
		}	
		return position;
	}
	
}
