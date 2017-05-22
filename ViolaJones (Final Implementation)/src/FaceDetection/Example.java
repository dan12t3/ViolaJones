package FaceDetection;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Example {
	
	int classification; //1 - face//0-nonface
	Double weight;
	int negposExamples;
	ArrayList<Double> features; //place holder
	int[][] IntegralImage;
	
	
	public Example(BufferedImage window, int classi){

		classification = classi;

		int w = window.getWidth();
		int h = window.getHeight();

		IntegralImage = new int[w][h];

		int test[][] = new int[w][h];
		
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				
				int pixel = window.getRGB(x, y);
				test[x][y] = pixel;
				int A = (x > 0 && y > 0) ? IntegralImage[x - 1][y - 1] : 0;
				int B = (x > 0) ? IntegralImage[x - 1][y] : 0;
				int C = (y > 0) ? IntegralImage[x][y - 1] : 0;
				IntegralImage[x][y] = -A + B + C
						+ (((pixel & 0x00FF0000) >> 16) + ((pixel & 0x0000FF00) >> 8) + (pixel & 0x000000FF)) / 3;
			}
		}
		
		
		
	}
	
	public Example(BufferedImage window){


		int w = window.getWidth();
		int h = window.getHeight();

		IntegralImage = new int[w][h];

		int test[][] = new int[w][h];
		
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				
				int pixel = window.getRGB(x, y);
				test[x][y] = pixel;
				int A = (x > 0 && y > 0) ? IntegralImage[x - 1][y - 1] : 0;
				int B = (x > 0) ? IntegralImage[x - 1][y] : 0;
				int C = (y > 0) ? IntegralImage[x][y - 1] : 0;
				IntegralImage[x][y] = -A + B + C
						+ (((pixel & 0x00FF0000) >> 16) + ((pixel & 0x0000FF00) >> 8) + (pixel & 0x000000FF)) / 3;
			}
		}
		
		
		
	}
	
	
	
	public void initializeWeights(int number){
		weight = 1.0/(2*number);
		
	}
	
	public double getWeight(){
		return weight;
	}

	
	public int getValue(int x, int y){
		return IntegralImage[x][y];
	}

	
	
	
	

}
