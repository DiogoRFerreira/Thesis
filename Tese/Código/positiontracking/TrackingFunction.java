package positiontracking;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class TrackingFunction {

	KalmanFilter filter;
	
	public TrackingFunction(){
		
		/*
		A - state transition matrix
		B - control input matrix
		H - measurement matrix
		Q - process noise covariance matrix
		R - measurement noise covariance matrix
		P - error covariance matrix
		
		Q is in state space, and R is in measurement space
		Q tells how much variance and covariance there is. The diagonal of Q contains the variance of each state variable, 
			and off diagonal contain the covariances between the different state variables (e.g. velocity in x vs position in y).
		R contains the variance of your measurement. In the above example, our measurement might just be speed from the speedometer. 
			Suppose it's reading has a standard deviation of 0.2 mph. Then R=[0.2^2]=[0.04]. 
			Squared because variance is the square of the standard deviation.
		*/
		RealMatrix A;
		RealMatrix B;
		RealMatrix H;
		RealMatrix Q;
		RealMatrix R;
		RealMatrix P0;
		RealVector x;

		/*if(dimension == 4){
			A = new Array2DRowRealMatrix(new double[][] { { 1,0,0,0 },{ 0,1,0,0 },{ 0,0,1,0 },{ 0,0,0,1} });
			// no control input
			B = null;
			H = new Array2DRowRealMatrix(new double[][] {{ 1,0,0,0 },{ 0,1,0,0 },{ 0,0,0,0 },{ 0,0,0,0 }});		
			//Inicial position estimate (x,y)
			x = new ArrayRealVector(new double[] { 0,0,0,0 });
			Q = new Array2DRowRealMatrix(new double[][] {{ 0.5,0,0,0 },{ 0,0.5,0,0 },{ 0,0,1,0 },{ 0,0,1,0 }});
			R = new Array2DRowRealMatrix(new double[][] {{ 0.1,0,0,0 },{ 0,0.1,0,0 },{ 0,0,0.1,0 },{ 0,0,0,0.1 }});	
			//matriz covariancia inicial - se nao forem conhecidas colocar um valor alto na diagonal
			P0 = new Array2DRowRealMatrix(new double[][] {{ 100,0,0,0 },{ 0,100,0,0 },{ 0,0,100,0 },{ 0,0,0,100 }});*/
		//}else{
			A = new Array2DRowRealMatrix(new double[][] { { 1,0 }, { 0,1 } });
			// no control input
			B = null;
			H = new Array2DRowRealMatrix(new double[][] {{ 1, 0},{0,1}});
			//Inicial position estimate (x,y)
			x = new ArrayRealVector(new double[] { 0, 0 });
			Q = new Array2DRowRealMatrix(new double[][] {{0, 0},{ 0, 0}});
			R = new Array2DRowRealMatrix(new double[][] {{400, 0},{ 0, 400}});
			//matriz covariancia inicial - se nao forem conhecidas colocar um valor alto na diagonal
			P0 = new Array2DRowRealMatrix(new double[][] { { 100, 0 }, { 0, 100 } });
		//}
		ProcessModel pm = new DefaultProcessModel(A, B, Q, x, P0);
		MeasurementModel mm = new DefaultMeasurementModel(H, R);
		filter = new KalmanFilter(pm, mm);
	}
	
	public double[] calculatePosition(double x,double y){
		filter.predict();

		// obtain measurement vector z
		RealVector z = new ArrayRealVector(new double[] {x,y});

	   // correct the state estimate with the latest measurement
	   filter.correct(z);
	   
	   return filter.getStateEstimation();
	}
	/*
	public double[] calculatePosition(double x,double y,double vx, double vy){
		filter.predict();

		// obtain measurement vector z
		RealVector z = new ArrayRealVector(new double[] {x,y,vx,vy});

	   // correct the state estimate with the latest measurement
	   filter.correct(z);
	   
	   return filter.getStateEstimation();
	}*/
	
	
	
	public static void main(String[] args){
		TrackingFunction object = new TrackingFunction();
		double[] position;
		for(int i=0;i<100;i++){
			if(i==50){
				position = object.calculatePosition(100, 25);
			}else{
				position = object.calculatePosition(i, 25);
			}
			System.out.println("Iteration:"+i+" x:"+position[0]+"/"+" y:"+position[1]);
		}
		/*
		TrackingFunction object2 = new TrackingFunction(4);
		double[] position2;
		for(int i=0;i<100;i++){
			if((100+i)%2==1){
				position2 = object2.calculatePosition(100+i, 100*i,i,i);
			}else{
				position2 = object2.calculatePosition(100-i, 100*i,0.1,0.1);
			}
			if(i==50){
				position2 = object2.calculatePosition(30000, 100*i,0.1,0.1);
			}
			System.out.println("Iteration:"+i+" x:"+position2[0]+" y:"+position2[1]+" vx:"+position2[2]+" vy:"+position2[3]);
		}*/
	}
}