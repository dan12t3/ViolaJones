package FaceDetection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class StrongClassifier {
	
	ArrayList<Round> weakClass;
	
	public StrongClassifier(ArrayList<Round> T){
		
		weakClass = T;
		
	}
	
	public StrongClassifier(File textFileName) throws FileNotFoundException{
		String line;
		String[] e;
		weakClass = new ArrayList<Round>();
		
		Scanner fileScanner = new Scanner(textFileName);
		while (fileScanner.hasNextLine()){
			line = fileScanner.nextLine();
			e = line.split(",");
			Round round = new Round();
			WeakClassifier weak = new WeakClassifier(Integer.parseInt(e[1]), Integer.parseInt(e[2]), Integer.parseInt(e[3]), Integer.parseInt(e[4]), Integer.parseInt(e[5]), Double.parseDouble(e[6]), Integer.parseInt(e[7]));
			round.setBest(weak);
			round.setA(Double.parseDouble(e[0]));
			
			weakClass.add(round);
			
		}

	}
	
	public int classify(Example x,double factor){
		double pos=0;
		double neg=0;
		
		for(int i=0;i<weakClass.size();i++){
			pos += (weakClass.get(i).a * weakClass.get(i).best.classify(x,factor));
			neg += weakClass.get(i).a;
			
		}
		
		neg = neg/2;
		
		if(pos>= neg) return 1;
		else return 0;

	}
	
	public void save(int x){
		
		try {
			PrintWriter writer = new PrintWriter("cascade"+ x +".txt", "UTF-8");
			
			
			for(int i=0; i<weakClass.size();i++){
				
				
				
				String tosave = "" + weakClass.get(i).a + "," + weakClass.get(i).best.type + "," + weakClass.get(i).best.width + "," + weakClass.get(i).best.height + ","+ weakClass.get(i).best.x + ","+ weakClass.get(i).best.y + ","+ weakClass.get(i).best.theta + ","+ weakClass.get(i).best.polarity;
				writer.println(tosave);
			}
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
	}

}
