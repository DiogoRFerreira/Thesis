package positionestimation;

public class KNN extends NN{

	protected int k;
	
	public KNN(FingerprintingFunction function,int k) {
		super(function);
		if(function.offlinerssi.length < k) {
			throw new IllegalArgumentException("K needs to be lower than the number of fingerprints.");
		}
		this.k=k;
	}

	@Override
	public double[] estimatePosition() {
		double[] position = new double[2];//x,y	
		
		double[] vector = calculateEuclideandistance();
		
		//Find k Nearest Neighbors - vector
		double[] kvector = new double[k]; //lowest k values of the vector
		double[][] kpositions = new double[k][2]; //Positions of the lowest k vector values
		for(int i=0;i<k;i++){
			int auxposition = getMinimumIndex(vector);
			kvector[i]=vector[auxposition];
			vector[auxposition]=99999;//High-value
			kpositions[i]=function.positions[auxposition];
		}
		
		//Calculate position
		double x=0, y=0;
		for(int i=0;i<k;i++){
			x += kpositions[i][0];
			y += kpositions[i][1];
		}
		position[0] = x/k;
		position[1] = y/k;
		
		return position;
	}
	
	public int getMinimumIndex(double[] vector){
		int position = 0;
		
		double aux = vector[0];
		for(int i=1;i<vector.length;i++){
			if(aux >= vector[i]){
				aux=vector[i];
				position=i;
			}
		}
		return position;
	}
}



