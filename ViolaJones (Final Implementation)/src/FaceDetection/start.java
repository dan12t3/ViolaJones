package FaceDetection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class start extends JFrame {
	
	private static JTextField filename = new JTextField();
	private static JTextField dir = new JTextField();
	private static JScrollPane scrollPane;
	private static JLabel label;
	public static String link;
	private static double scale = 0.25;
	private static int windowSize = 24;
	private static TestPane windowPanel;

	private static ArrayList<StrongClassifier> StrongList = new ArrayList<>();

	public static void run(String link) throws IOException {
		
		BufferedImage image;
 		// Read from a file
 		image = ImageIO.read(new File(link));
		Thread t = new Thread(new Runnable() {
	         public void run()
	         {
	              // Insert some method call here.
	        	 
	     		
	     		int i=0;
	     		int size = 24;
	     		int windowSize=size;
	     		double factor = 1.25;
	     		
	     		ArrayList<Rectangle> squares = new ArrayList<Rectangle>();
	     		
	     		outerloop:
	     		while(true){
	     			
	     			if(Math.ceil(size*Math.pow(factor, i)) <= image.getHeight() && Math.ceil(size*Math.pow(factor, i)) <= image.getWidth()){
	     				windowSize=(int) Math.ceil(size*Math.pow(factor, i));
	     			}
	     			else break;
	     			
	     			for (int height = 0; height < image.getHeight() - windowSize; height += 1) {// increment
	     				for (int width = 0; width < image.getWidth() - windowSize; width += 1) {// increment
	     					
	     					BufferedImage window = image.getSubimage(width, height, windowSize, windowSize);
	     					// intImage.setImage(window);

	     					
	     					Example testImage = new Example(window);
	     					
	     				//	if(windowSize>24){
	     					//	System.out.println(windowSize);
	     						//squares.add(new Rectangle(width,height,windowSize,windowSize));
     							//drawSquare(image,width,height,windowSize);
     							//windowPanel.drawFaces(squares);
	     				//	}
	     					
	     					for(int j=0; j<StrongList.size();j++){
	     						if(StrongList.get(j).classify(testImage, Math.pow(factor, i))!=1){
	     							break;
	     						}
	     						
	     						if(j==StrongList.size()-1){
	     							//draw square
	     							//System.out.println("face found");
	     							squares.add(new Rectangle(width,height,windowSize,windowSize));
	     							//drawSquare(image,width,height,windowSize);
	     							windowPanel.drawFaces(squares);
	     							
	     							
	     							//break outerloop;


	     						}
	     						
	     					}
	     					
	     					

	     				}
	     			}
	     			i++;
	     		}
	     		
	     		System.out.println("All faces detected");
	        	 
	         }
	
		});
		
		t.start();
		
		
		
		
	}
	
	

	public start() {
		JPanel panel = new JPanel();
		Container cp = getContentPane();
		JButton button = new JButton();
		JButton start = new JButton();
		button.setText("Browse");
		button.addActionListener(new CustomActionListener());
		start.setText("Find Faces");
		start.addActionListener(new FindFaceListener());

		
		
		
		dir.setEditable(false);
		filename.setEditable(false);

		ImageIcon image = new ImageIcon(link);
		label = new JLabel(image);
		scrollPane = new JScrollPane(label);
		panel.setLayout(new GridLayout(2, 1));
		panel.add(button);
		panel.add(start);
		// panel.add(filename);
		// panel.add(dir);

		cp.add(panel, BorderLayout.NORTH);

	}

	public static void run(JFrame frame, int width, int height) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	class CustomActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(start.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				// filename.setText(c.getSelectedFile().getName());
				// dir.setText(c.getSelectedFile().getAbsolutePath().toString());
				link = c.getSelectedFile().getAbsolutePath().toString();

				//ImageIcon image = new ImageIcon(link);
				//label.setIcon(image);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				add(scrollPane, BorderLayout.CENTER);
				windowPanel = new TestPane();
				getContentPane().add(windowPanel);
				pack();

			}

			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("You pressed cancel");
				dir.setText("");
			}
		}
	}

	class FindFaceListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				run(link);
				//getContentPane().add(new TestPane());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			}

		}
	}

	class TestPane extends JPanel {
		
		  private BufferedImage myImage;
		  private ArrayList<Rectangle> faces = new ArrayList<>();
		  
		  
		 
		  public TestPane() {
		    try {
		      myImage = ImageIO.read(new File(link));
		    } catch (Exception ex) {
		      ex.printStackTrace();
		    }
		    
		    
		    
		    Rectangle face = new Rectangle(10,10,10,10);
		    faces.add(face);

		  }
		  
		  public void drawFaces(ArrayList<Rectangle> x){
			  faces = x;
			  
			  
			  this.repaint();
		  }

		  @Override
		  public Dimension getPreferredSize() {
		    return myImage == null ? new Dimension(200, 200) : new Dimension(
		        myImage.getWidth(), myImage.getHeight());
		  }

		  @Override
		  protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		  

		    Graphics2D g2d = (Graphics2D) g;
		    //Graphics2D g2d = (Graphics2D) g;
		    
		    /*for(int i=0;i<faces.size();i++){
		    	g2d.setColor(Color.red);
		    	g2d.draw(faces.get(i));
		    }*/
		    
		    
		    if (myImage != null) {
		      int x = (getWidth() - myImage.getWidth()) / 2;
		      int y = (getHeight() - myImage.getHeight()) / 2;
		      g2d.drawImage(myImage, x, y, this);

		      g2d.setColor(Color.RED);
		      g2d.translate(x, y);
		      for(int i = 0; i< faces.size(); i++){
		    	  g2d.draw(faces.get(i));
		      }
		    }
		    g2d.dispose();
		  }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int i = 0; i < 4; i++) {
			//System.out.println("yo");
			File file = new File("cascade" + i + ".txt");
			try {
				StrongClassifier strong = new StrongClassifier(file);
				StrongList.add(strong);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		run(new start(), 700, 700);
		
		
	}

}
