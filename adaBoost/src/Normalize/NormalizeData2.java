package Normalize;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NormalizeData2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int k=1; k<=5;k++){
			System.out.println(k);
			BufferedImage img = null;
			
			try{//get image
				File imgf = new File("Background2/" + k + ".jpg");
				img = ImageIO.read(imgf);
			}
			catch (IOException e) {
			  }
			
			int h = img.getHeight();
			int w = img.getWidth();
			
			for(int i=0;i<w;i++){
				for(int j=0; j<h;j++){
					Color x = new Color(img.getRGB(i, j));

					double lumAvg = 0.07*x.getBlue() + 0.72*x.getGreen() + 0.21*x.getRed();
					int avg = (int) lumAvg;
					x = new Color(avg,avg,avg);
					img.setRGB(i, j, x.getRGB());
				}
			}
			
			//BufferedImage sImg = scaledImage(img,24,24);
			
			try {
			    // retrieve image
			    BufferedImage bi = img;
			    File outputfile = new File("normalizedBackground2/"+k+".jpg");
			    ImageIO.write(bi, "jpeg", outputfile);
			} catch (IOException e) {

			}
			
		}
		

	}

	private static BufferedImage scaledImage(BufferedImage srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}
}
