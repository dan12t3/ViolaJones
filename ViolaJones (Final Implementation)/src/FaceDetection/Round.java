package FaceDetection;

import java.util.ArrayList;

public class Round {
	
	ArrayList<Double> weights; //1 to #of examples
	WeakClassifier best;
	double a;
	double b;
	
	
	public Round(){

	}
	
	
	public void initialize(ArrayList<Example> data){
		
		weights = new ArrayList<Double>();
		
		for(int i=0;i<data.size();i++){
			weights.add(data.get(i).getWeight());
		}
		
	}
	

	public ArrayList<Double> getWeights(){
		return weights;
	}
	
	public void setWeights(ArrayList<Double> x){
		weights = x;
	}
	
	public void setBest(WeakClassifier x){
		best = x;
	}
	
	public WeakClassifier getBest(){
		return best;
	}
	
	public void setA(double a){
		this.a = a;
	}

}
