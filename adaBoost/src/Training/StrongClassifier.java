package Training;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class StrongClassifier {
	
	ArrayList<Round> weakClass;
	
	public StrongClassifier(ArrayList<Round> T){
		
		weakClass = T;
		
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
			
			System.out.println(weakClass.size());
			
			for(int i=0; i<weakClass.size();i++){
				
				System.out.println(weakClass.get(i).a);
				System.out.println(weakClass.get(i).best.type);
				System.out.println(weakClass.get(i).best.width);
				System.out.println(weakClass.get(i).best.height);
				System.out.println(weakClass.get(i).best.x);
				System.out.println(weakClass.get(i).best.y);
				System.out.println(weakClass.get(i).best.theta);
				System.out.println(weakClass.get(i).best.polarity);
				
				
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
