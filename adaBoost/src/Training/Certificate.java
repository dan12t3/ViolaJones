package Training;

public class Certificate {
	
	double threshold;
	double error;
	double margin;
	int toggle;
	
	public Certificate(double t, double e, double m, int toggle){
		
		
		threshold = t;
		error = e;
		margin = m;
		this.toggle = toggle;
	}

}
