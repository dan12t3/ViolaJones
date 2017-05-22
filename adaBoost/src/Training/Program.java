package Training;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int m = 13233;
		int l = 10018;

		//each example get its own weight
		//
		
		ArrayList<Example> Ptraining = new ArrayList<Example>();
		ArrayList<Example> Ptesting = new ArrayList<Example>();
		
		ArrayList<Example> Ntraining = new ArrayList<Example>();
		ArrayList<Example> Ntesting = new ArrayList<Example>();
		//split dataset
		//13234
		for(int i=2;i<=13234;i++){
			System.out.println("yo");

			BufferedImage img;
			try{//get image
				File imgf = new File("normalizedFaces/face" + i + ".jpg");
				img = ImageIO.read(imgf);
				Example x = new Example(img,1);
				
				if(i>=Math.ceil(m*0.2)+2){
					Ptesting.add(x);
				}else{
					Ptraining.add(x);
				}
				
				
				
				
			}
			
			
			catch (IOException e) {
			  }
			
		}
		//10019
		for(int i=0;i<=10019;i++){

			System.out.println("to");
			BufferedImage img;
			try{//get image
				File imgf = new File("normalizedBackground/" + i + ".jpg");
				img = ImageIO.read(imgf);
				Example x = new Example(img,-1);
			
				if(i>=Math.ceil(l*0.5)){
					Ntesting.add(x);
				}else{
					Ntraining.add(x);
				}
			}
			catch (IOException e) {
			  }
			
		}
		
		//ArrayList<Example> training = new ArrayList<Example>();
		//training.addAll(Ptraining);
		//training.addAll(Ntraining);
		
		//ArrayList<Example> testing = new ArrayList<Example>();
		//testing.addAll(Ptesting);
		//testing.addAll(Ntesting);
		
	
		
		
		ArrayList<StrongClassifier> cascades = new ArrayList<StrongClassifier>();
		
		StrongClassifier Eval;
		System.out.println("--Started AdaBoost--");
		//total 4 cascades //4 strong classifiers
		double falsePositives =0;
		double falseNegatives = 0;
		for(int i=2;i<=128;i=i*2){
			
			
			//System.out.println(x);
			Eval=adaBoost(i,Ptraining,Ntraining);
			
			//save classifier
			Eval.save(cascades.size());
			cascades.add(Eval);
			
			for(int j=0;j<Ptesting.size();j++){
				
				if(Eval.classify(Ptesting.get(j),1)==0){
					falseNegatives++;
				}

			}
			
			falseNegatives = falseNegatives/Ptesting.size();
			
			int NegSize = Ntesting.size();

			for(int j=0;j<Ntesting.size();j++){
				
				if(Eval.classify(Ntesting.get(j),1)==1){
					falsePositives++;
				}else{
					Ntesting.remove(j);
					j--;
				}

			}
			
			
			
			System.out.println("NegSize:" + NegSize);
			
			falsePositives = falsePositives/NegSize;
			
			System.out.println("FP:" + falsePositives);
			System.out.println("FN:" + falseNegatives);
			
			int NegSize2 = Ntraining.size();

			for(int j=0;j<Ntraining.size();j++){
				
				if(Eval.classify(Ntraining.get(j),1)==1){
					//falsePositives++;
				}else{
					Ntraining.remove(j);
					j--;
				}

			}
			
			//save data
			
			try(FileWriter fw = new FileWriter("results.txt", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				
				String results = "" + cascades.size() + ","+ Ptraining.size() + ","+ NegSize2 + ","+ Ptesting.size() + ","+ NegSize + ","+ falseNegatives + ","+ falsePositives + ","+ Ntesting.size() + ","+ Ntraining.size();
				    //out.println("the text");
				    //more code
				    out.println(results);
				    //more code
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
			
			//open file
			//cascade # -- cascade.size
			//positive training examples Ptraining.size
			//negative training examples (negsize2)
			
			
			
			//positive eval examples Ptesting.size
			//negative eval examples (negsize)
			
			//fp on evalset -- falseNegatives
			//fn on evalset -- false Positives
			
			//eval on negative testing - Ntesting.size
			//eval on negative training examples (size Ntraining)
			
			//total negative examples to train next cascade on Ntraining.size + Ntesting.size
			
			//append
			//close
			
			
			//Scanner test = new Scanner(System.in);
			//test.nextLine();
		
			if(i==128) break;
		
			

			
			
			//Ntesting.addAll(Ntraining);
			
			
			
			
			//input new image, high resolution
			BufferedImage negImg = null;
			try{//get image
				File imgf = new File("normalizedBackground2/" + cascades.size() + ".jpg");
				negImg = ImageIO.read(imgf);
			}catch(IOException e){
				
			}
			
			//Ntraining.clear();
			Ntraining.addAll(Ntesting);
			
			//System.out.println("going in");
			
			Ntesting = slidingWindow(negImg,Eval,Ntraining.size());
			
			//System.out.println("coming out");
			NegSize = (int) (Ntesting.size()*0.5);
			
			for(int i1=NegSize;i1<Ntesting.size();i1++){
				Ntraining.add(Ntesting.remove(i1));
				i1--;
			}
			
			
			
			
			
			
			//start with 24x24 window
			//goes through the entire image
			//scale window by 1.25 = 32x32 and scale all the algo parameters
			//find all
			
			//input new image, high resolution
			//generate new negative training scaling every subwindow to 24x24.
			//use the classfier to eliminate training examples
			//loop
			
			
		}
		
		System.out.println("Finised Training!!");
		//cascade 1 - 70% positive examples for training and 70% negative
		 //finds one cascade
		
		//returns a strong classifier and saves it to file
		//30% for eval
		
		//input new image, high resolution
		//generate new negative training scaling every subwindow to 24x24.
		
		//
		//cascade 2,
		
		
		
		

	}
	
	public static ArrayList<Example> slidingWindow(BufferedImage image,StrongClassifier x,int negsize) {
	
		
		ArrayList<Example> falPos = new ArrayList<Example>();
		
		
		// not sure how much to increment each window by. Need to add leftover
		// pixels at last iteration
		int i=0;
		int size = 24;
		int windowSize=size;
		double factor = 1.25;
		
		
		while(true){
			
			if(Math.ceil(size*Math.pow(factor, i)) <= image.getHeight() || Math.ceil(size*Math.pow(factor, i)) <= image.getWidth()){
				windowSize=(int) Math.ceil(size*Math.pow(factor, i));
			}
			else break;
				
			
			
			/*if(Math.ceil(windowSize*1.25) <= image.getHeight() || Math.ceil(windowSize*1.25) <= image.getWidth()){
				windowSize=(int) Math.ceil(windowSize*1.25);
			}else{
				break;
			}*/
			
			for (int height = 0; height < image.getHeight() - windowSize; height += 1) {// increment
				// height
				// by
				// 10
				// pixelss

				for (int width = 0; width < image.getWidth() - windowSize; width += 1) {// increment
				// width
				// by
				// 10
				// pixels


					BufferedImage window = image.getSubimage(width, height, windowSize, windowSize);
//intImage.setImage(window);

					//System.out.println("sliding");

					Example testImage = new Example(window,0);
					if(testImage.classification != x.classify(testImage,Math.pow(factor, i))){ //input factor for classify
						//scale window(image) to 24x24
						BufferedImage scaled = scaledImage(window,24,24);
						Example scaledExmp = new Example(scaled,0);//make new example
						falPos.add(scaledExmp);//save it 
						System.out.println(falPos.size());
						
						if(falPos.size()/2 + negsize > 5000) return falPos;
						
					}

				}

			}
			
			
			
			i++;
			
		}
		
		
		
		

		return falPos;

	}
	
	private static BufferedImage scaledImage(BufferedImage srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
	
	public static Certificate findError(ArrayList<Integer> f, ArrayList<Double> w, ArrayList<Integer> e/*both are size n*/){
		
		//insertion sort
		//iffy implementation
		
		ArrayList<ErrorData> edata = new ArrayList<ErrorData>();
		
		//System.out.println(f.size());
		//System.out.println(w.size());
		//System.out.println(e.size());
		
		
		
		for(int i=0; i<f.size();i++){
			edata.add(new ErrorData(f.get(i),w.get(i),e.get(i)));
		}
		
		Collections.sort(edata);
		
		//ArrayList<Integer> f1 = (ArrayList<Integer>)f.clone();
		//ArrayList<Double> weights = (ArrayList<Double>)w.clone();
		//ArrayList<Integer> eO =(ArrayList<Integer>)e.clone();
		
		
		
		/*
		for(int i=0;i<f.size();i++){
			int m = i;
			while(m>0 && f1.get(m-1) > f1.get(m)){
				//swap
				int tempf = f1.get(m);
				double tempw = weights.get(m);
				int tempe = eO.get(m);
				
				f1.set(m, f1.get(m-1));
				weights.set(m, weights.get(m-1));
				eO.set(m, eO.get(m-1));
				
				f1.set(m-1, tempf);
				weights.set(m-1, tempw);
				eO.set(m-1, tempe);
			
				m--;
			}
			
		}*/
		//sort by feature values(IMPORTANT)
		
		//initialization
		double threshold;
		double error=2; //arbitrary large number
		double margin=0;
		int toggle = 5;
		double WaTP=0; //sum of weights above threshhold for positives
		double WbTP=0; //sum of weight below threshold for positives
		
		double WaTN=0; //sum of weights above threshhold for negatives
		double WbTN=0; //sum of weight below threshold for negatives
		
		
		
		double minfeature = Integer.MAX_VALUE;
		//for(int i=0;i<edata.size();i++){
			//if(edata.get(i).feature < minfeature) minfeature = edata.get(i).feature;
		//}
		threshold = edata.get(0).feature-1;
		
		for(int i=0; i<edata.size();i++){
			if(edata.get(i).feature > threshold){
				if(edata.get(i).expected==1){
					WaTP += edata.get(i).weight;
				}else{
					WaTN += edata.get(i).weight;
				}

			}else if (edata.get(i).feature< threshold){
				//if(edata.get(i).expected==1){
					WbTP =0;
				//}else{
					WbTN =0;
				//}
			}
			
		}
		
		
		int j=0;
		
		double primeError;
		double primeMargin = margin;
		int primeToggle;
		double primeThreshold = threshold; 
		
		
		while(true){
			
			double posError = WbTP + WaTN;
			double negError = WaTP + WbTN;
			
			//System.out.println(posError);
			//System.out.println(negError);
			
			//Scanner test = new Scanner(System.in);
			//test.nextLine();
			
			if (posError<negError){
				primeError=posError;
				primeToggle = 1;
			}else{
				primeError=negError;
				primeToggle=-1;
				
			}
			
			if(primeError<error || (primeError==error && primeMargin > margin)){
				error = primeError;
				threshold = primeThreshold;
				margin = primeMargin;
				toggle = primeToggle;
			}
			
			if(j==edata.size()-1) break;
			j++;
			while(true){
				if (edata.get(j).expected==0){
					WbTN+=edata.get(j).weight;
					WaTN-=edata.get(j).weight;
				}else{
					WbTP+=edata.get(j).weight;
					WaTP-=edata.get(j).weight;
					
				}
				
				if(j==edata.size()-1 || edata.get(j).feature != edata.get(j+1).feature){
					break;
				}else{
					j++;
				}
				
				
			}
			
			if(j==edata.size()-1){
				primeThreshold = Integer.MIN_VALUE;
				for(int i=0;i<edata.size();i++){
					if(edata.get(i).feature>primeThreshold) primeThreshold =edata.get(i).feature;
				}
				primeThreshold++;
				primeMargin = 0;
				
			}else{
				primeThreshold = (edata.get(j).feature + edata.get(j+1).feature)/2;
				primeMargin = edata.get(j+1).feature-edata.get(j).feature;
			}
			
			
			
			
			
			
			
		}
		
		return new Certificate(threshold, error,margin,toggle);
		
		
		
		
		//returns threshold(theta)
		//toggle(polarity)
		//error
		//feature
		
		
		
	}
	
	public static int findFeatures(int i, int width, int height, int x, int y, Example integralImage){
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
	
	
	public static WeakClassifier minErrorClassifier(ArrayList<Example> data, ArrayList<Double> weights, ArrayList<Integer> eO){ //weight for each example
		System.out.println("--Retreieve Best Weakest Classifier--");
		
		
		
		
		WeakClassifier best = null;
		int numFeatures = 5;
		int windowSize = 24;
		int features[][] = { { 2, 1 }, { 1, 2 }, { 3, 1 }, { 1, 3 }, { 2, 2 } };
		double minError = Integer.MAX_VALUE;
		double margin =0;

		int sizeY = 0, sizeX = 0;


		int count =0;
		
		
		
		
		// number of different features ---> 5
		for (int i = 0; i < numFeatures; i++) {
			sizeX = features[i][0];
			sizeY = features[i][1];
			// Expands width till it reaches window size
			for (int width = sizeX; width <= windowSize; width += sizeX) {
				// expands height till it reaches window size
				// System.out.println("NEW WIDTH: "+ width);

				for (int height = sizeY; height <= windowSize; height += sizeY) {
					// System.out.println("NEW HEIGHT: "+ height);

					// loops till it reaches end of width of window minus
					// current width length
					for (int x = 0; x <= windowSize - width; x++) {
						// loops till reaches end of height of window minus
						// current height length
						// System.out.println("NEW X POSITION: "+ x);

						for (int y = 0; y <= windowSize - height; y++) {
							
							System.out.println("--Getting Error for Feature "+ count +"--");
							count++;
							
							ArrayList<Integer> f1 = new ArrayList<Integer>(); //feature 1 for every image example - size n
							for(int l=0;l<data.size();l++){

								f1.add(findFeatures(i,width,height,x,y,data.get(l)));
							}
							
							//System.out.println(f1.get(0));
							
							//Scanner test = new Scanner(System.in);
							//test.nextLine();
	
							Certificate currentFeature;
							
							currentFeature = findError(f1,weights,eO);
						//	System.out.println(currentFeature.error);
							//System.out.println(currentFeature.margin);
							//System.out.println(currentFeature.threshold);
							//System.out.println(currentFeature.toggle);
							
							if(currentFeature.error<minError || (currentFeature.margin>margin && currentFeature.error == minError )){
								//stores values
								
								
								minError = currentFeature.error;
								
								best = new WeakClassifier(i,width,height,x,y,currentFeature.error,currentFeature.threshold,currentFeature.toggle);
								
								
								
							}
							System.out.println("Current Best error: " + best.error);
							
							//get lowest weighted error for that 1 feature out of 1600000
							//get error of feature classifier using n sets for 1/1600000 feature
							//the objective then is to find lowest weigted error out of all 160000 features
							//store the parameters for the lowest weighted error and return it as a WeakClassifier
							
							
							
						}
						
					}
					
				}
			}
		}
		
		//iterate through all the possible features of 24x24 (roughly 160000)

		
		
		return best;
	}
	
	public static StrongClassifier adaBoost(int T, ArrayList<Example> Pdata, ArrayList<Example> Ndata){
		ArrayList<Integer> expectedOutput = new ArrayList<Integer>();
		
		
		
		for(int i=0; i<Pdata.size();i++){
			Pdata.get(i).initializeWeights(Pdata.size());
			expectedOutput.add(1);
		}
		
		for(int i=0; i<Ndata.size();i++){
			Ndata.get(i).initializeWeights(Ndata.size());
			expectedOutput.add(0);
		}
		
		ArrayList<Example> data = new ArrayList<Example>();
		data.addAll(Pdata);
		data.addAll(Ndata);
		
		//System.out.println(data.get(0).getWeight());
		
		System.out.println("--Weights Initialized--");
		
		//int T = 1; //we're going to try reducing it to 4

		
		ArrayList<Round> tR = new ArrayList<Round>();
		

		Round tRounds = new Round();
		tRounds.initialize(data);
		tR.add(tRounds);
		
		for(int t=0; t<T;t++){ //number of features in a single stage of the cascade
			
			WeakClassifier best = minErrorClassifier(data,tR.get(t).getWeights(),expectedOutput);
			double a;
			double b;
			System.out.println("--BEST ERROR: " + best.error);
			
			if(best.getError() == 0){
				tR.get(t).setBest(best);
				break;
			}else{
				b = best.getError()/(1-best.getError());
				//a = (Math.log((1-best.error)/best.error))/2;
				a = Math.log(1/b);
				ArrayList<Double> weights = new ArrayList<Double>();
				
				for(int i=0;i<data.size();i++){
					int e;
					if(best.classify(data.get(i),1)==data.get(i).classification){
						e=0;
					}else{
						e=1;
					}

					double w = tRounds.getWeights().get(i)*Math.pow(b, 1-e);
					weights.add(w);
				}
				
				tR.get(t).a = a;
				tR.get(t).b = b;
				tR.get(t).setBest(best);
				tR.get(t).best = best;
				
				if(t==T-1) break;
				
				tRounds = new Round();
				tRounds.setWeights(weights);
				
				tR.add(tRounds);
				
				
				
			}
			
			
			
		}
		
		StrongClassifier strongBest = new StrongClassifier(tR);
		
		
		return strongBest;
		
	}

}
