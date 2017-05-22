package Training;

public class WeakClassifier {
	
	//feature values
	int type;
	int width;
	int height;
	int x;
	int y;
	
	//
	
	
	
	
	int polarity;
	double theta;
	double error;
	

	 
	public WeakClassifier(int type, int width, int height, int x, int y, double error, double theta, int toggle){

		this.type = type;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.error = error;
		this.theta = theta;
		polarity = toggle;
		
	}
	
	public double getError(){
		return error;
	}
	
	public int classify(Example img, double factor){
		int f = Program.findFeatures(type, (int)Math.ceil(width*factor), (int)Math.ceil(height*factor), (int)Math.ceil(x*factor), (int)Math.ceil(y*factor), img);
		
		if((polarity*f) > polarity*theta) return 1;
		else return 0;

	}

}
