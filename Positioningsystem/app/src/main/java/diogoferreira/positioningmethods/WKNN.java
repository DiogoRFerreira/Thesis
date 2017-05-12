package diogoferreira.positioningmethods;

public class WKNN extends KNN{
	
	protected double[] kweight;
	protected double weightsum;
	
	public WKNN(FingerprintingFunction function,int k) {
		super(function,k);
		kweight = new double[k];
		weightsum = 0;
	}

	public void calculateWeights(double[] kvector){
		//Calculate weight
		for(int i=0;i<k;i++){
			if(kvector[k-1]!= kvector[0]){
				kweight[i]=(kvector[k-1]-kvector[i])/(kvector[k-1]-kvector[0]);
				weightsum += kweight[i];
			}else{
				kweight[i]=1;
				weightsum += kweight[i];
			}
		}
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
		
		calculateWeights(kvector);
		
		//Calculate position
		double x=0, y=0;
		for(int i=0;i<k;i++){
			x += kpositions[i][0]*kweight[i];
			y += kpositions[i][1]*kweight[i];
		}
		position[0] = x/weightsum;
		position[1] = y/weightsum;
		
		return position;
	}
}