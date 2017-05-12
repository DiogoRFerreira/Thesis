package diogoferreira.positioningmethods;

public class NN {
	protected FingerprintingFunction function;
	
	public NN(FingerprintingFunction function) {
		this.function=function;
	}
	
	public double[] calculateEuclideandistance(){
		double[] vector = new double[function.offlinerssi.length];
		//Euclidean distance vector
		for(int i=0;i<function.offlinerssi.length;i++){
			double sum = 0;
			for(int j=0;j<function.rssi.length;j++){
				if(function.rssi[j]!=0) sum += Math.pow(function.rssi[j]-function.offlinerssi[i][j],2); 
			}
			vector[i] = Math.sqrt(sum);
		}
		return vector;
	}
	
	public double[] estimatePosition(){
		double[] position = new double[2];//x,y
		
		double[] vector = calculateEuclideandistance();
		
		//Selects Nearest Neighbour
		double aux = 0;
		for (int i=0;i<vector.length;i++){
			if(i==0){
				position = function.positions[i];
				aux=vector[i];
			}
			if(aux>=vector[i]){
				position = function.positions[i];
				aux = vector[i];
			}
		}
			
		return position;
	}

}
