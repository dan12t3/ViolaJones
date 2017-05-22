package FaceDetection;

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
	
	public WeakClassifier(int type, int width, int height, int x, int y, double theta, int toggle){

		this.type = type;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.theta = theta;
		polarity = toggle;
		
	}
	
	public double getError(){
		return error;
	}
	
	public int classify(Example img, double factor){
		int f = findFeatures(type, (int)Math.floor(width*factor), (int)Math.floor(height*factor), (int)Math.floor(x*factor), (int)Math.floor(y*factor), img);
		
		if((polarity*f) > polarity*theta) return 1;
		else return 0;

	}
	
	public int findFeatures(int i, int width, int height, int x, int y, Example integralImage){
		//calculating feature value
		
		int A, B, C, D, E, F, G, H, I, midpoint;
		int black = 0, white = 0;
		
		if (i == 0) {
			midpoint = (width / 2) - 1;

			if (x - 1 >= 0 && y - 1 > 0)
				A = integralImage.getValue(x - 1, y - 1);
			else
				A = 0;
			if (x - 1 >= 0)
				B = integralImage.getValue(x - 1, y + height - 1);
			else
				B = 0;
			if (y - 1 >= 0)
				C = integralImage.getValue(x + midpoint, y - 1);
			else
				C = 0;
			D = integralImage.getValue(x + midpoint, y + height - 1);

			black = D + A - C - B;

			if (y - 1 >= 0)
				E = integralImage.getValue(x + width - 1, y - 1);
			else
				E = 0;
			F = integralImage.getValue(x + width - 1, y + height - 1);

			white = F + C - E - D;
			
			
			
			// System.out.println(black-white);
			// if(black-white == 0) return;
			// return;
		} else if (i == 1) {
			midpoint = (height / 2) - 1;
			if (x - 1 >= 0)
				A = integralImage.getValue(x - 1, y + midpoint);
			else
				A = 0;
			if (x - 1 >= 0)
				B = integralImage.getValue(x - 1, y + height - 1);
			else
				B = 0;
			C = integralImage.getValue(x + width - 1, y + midpoint);
			D = integralImage.getValue(x + width - 1, y + height - 1);
			black = D + A - B - C;

			if (x - 1 >= 0 && y - 1 >= 0)
				E = integralImage.getValue(x - 1, y - 1);
			else
				E = 0;
			if (y - 1 >= 0)
				F = integralImage.getValue(x + width - 1, y - 1);
			else
				F = 0;

			white = C + E - A - F;
			// System.out.println(black-white);

			// return;
		} else if (i == 2) {
			if (x - 1 >= 0 && y - 1 >= 0)
				A = integralImage.getValue(x - 1, y - 1);
			else
				A = 0;
			if (x - 1 >= 0)
				B = integralImage.getValue(x - 1, y + height - 1);
			else
				B = 0;
			if (y - 1 >= 0)
				C = integralImage.getValue(x + (width / 3) - 1, y - 1);
			else
				C = 0;
			D = integralImage.getValue(x + (width / 3) - 1, y + height - 1);
			if (y - 1 >= 0)
				E = integralImage.getValue(x + (2 * width / 3), y - 1);
			else
				E = 0;
			F = integralImage.getValue(x + (2 * width / 3) - 1, y + height - 1);
			if (y - 1 >= 0)
				G = integralImage.getValue(x + width - 1, y - 1);
			else
				G = 0;
			H = integralImage.getValue(x + width - 1, y + height - 1);

			black = F + C - D - E;
			white = (D + A - B - C) + (H + E - F - G);

			// System.out.println(black-white);
			// return;
		} else if (i == 3) {
			if (x - 1 >= 0 && y - 1 >= 0)
				A = integralImage.getValue(x - 1, y - 1);
			else
				A = 0;
			if (y - 1 >= 0)
				B = integralImage.getValue(x + width - 1, y - 1);
			else
				B = 0;
			if (x - 1 >= 0)
				C = integralImage.getValue(x - 1, y + (height / 3) - 1);
			else
				C = 0;
			D = integralImage.getValue(x + width - 1, y + (height / 3) - 1);
			if (x - 1 >= 0)
				E = integralImage.getValue(x - 1, y + (2 * height / 3) - 1);
			else
				E = 0;
			F = integralImage.getValue(x + width - 1, y + (2 * height / 3) - 1);
			if (x - 1 >= 0)
				G = integralImage.getValue(x - 1, y + (height) - 1);
			else
				G = 0;
			H = integralImage.getValue(x + width - 1, y + height - 1);

			black = F + C - E - D;
			white = (D + A - D - C) + (H + E - F - G);
			// System.out.println(black-white);
			// return;
		} else {
			if (x - 1 >= 0 && y - 1 >= 0)
				A = integralImage.getValue(x - 1, y - 1);
			else
				A = 0;
			if (x - 1 >= 0)
				B = integralImage.getValue(x - 1, y + (height / 2) - 1);
			else
				B = 0;
			if (x - 1 >= 0)
				C = integralImage.getValue(x - 1, y + height - 1);
			else
				C = 0;
			if (y - 1 >= 0)
				D = integralImage.getValue(x + (width / 2) - 1, y - 1);
			else
				D = 0;
			E = integralImage.getValue(x + (width / 2) - 1, y + (height / 2) - 1);
			F = integralImage.getValue(x + (width / 2) - 1, y + height - 1);
			if (y - 1 >= 0)
				G = integralImage.getValue(x + width - 1, y - 1);
			else
				G = 0;
			H = integralImage.getValue(x + width - 1, y + (height / 2) - 1);
			I = integralImage.getValue(x + width - 1, y + height - 1);
			black = (F + B - E - C) + (H + D - G - E);
			white = (E + A - D - B) + (I + E - H - F);
			
			

			// if(y == 1){System.out.println(black-white);
			// return;
		}
		return black-white;
	}

}
