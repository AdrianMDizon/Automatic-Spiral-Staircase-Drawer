// Stairs.java
// by: Adrian Dizon

// Adapted from "Cylinder.java" from the textbook    

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Stairs{	


	static FileWriter fw;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		int stairs = 1;
		double alphaDeg;
		String fileName = "defaultStairsName.dat";
		
		if (args.length != 3) {
			System.out.println(
				"Supply stairs (> 0), alpha (in degrees)\n" + 
				"and a filename as program arguments.\n");
			System.exit(1);
	    }
		
		try {
			stairs = Integer.valueOf(args[0]).intValue();
			//a = Double.valueOf(args[1]).doubleValue();
			alphaDeg = Double.valueOf(args[1]).doubleValue();
			fileName = args[2];
			if (stairs <= 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println("number of stairs must be an integer > 0");
			System.out.println("alpha must be a real number");
			System.exit(1);
		}
		
		fw = new FileWriter(fileName);
		
		float rOuter = 7, rInner = 0;
		
		
		int height;
		
		//changes height of cylinder based on how many stairs are to be generated
		if (stairs > 105)
			height = 400;
		else if (stairs > 88)
			height = 320;
		else if (stairs > 71)
			height = 280;
		else if (stairs > 54)
			height = 220;
		else if (stairs > 17)
			height = 100;
		else
			height = 50;
		 	
		int n = 20; //faces of the cylinder
		
		int n2 = 2 * n, n3 = 3 * n, n4 = 4 * n;
	    double delta = 2 * Math.PI / n;
	    
	    //this loop writes out all the vertices of the cylinder
	    for (int i = 1; i <= n; i++) {
	       double alpha = i * delta, cosa = Math.cos(alpha), sina = Math.sin(alpha);
	         
	       for (int inner = 0; inner < 2; inner++) {
	          double r = (inner == 0 ? rOuter : rInner);
	            
	          if (r > 0)
	             for (int bottom = 0; bottom < 2; bottom++) {
	                int k = (2 * inner + bottom) * n + i;
	                // Vertex numbers for i = 1:
	                // Top: 1 (outer) 2n+1 (inner)
	                // Bottom: n+1 (outer) 3n+1 (inner)
	                wi2(k); // w = write, i = int, r = real
	                wr(r * cosa); wr(r * sina); //x and y
	                wi((1 - bottom) * height); // bottom: z = 0; top: z = 1
	                fw.write("\r\n");
	             }
	        }	
	     }
	     
	    //Calls the NewBeams function to list the vertexes of the stairs as well as begin the "Faces:" portion of the .dat file
	    NewBeams.main(fw,args); 

	     //Assigns the faces of the cylinder
	    
	     // Top boundary face:
	     for (int i = 1; i <= n; i++) 
	    	 wi(i);
	     if (rInner > 0) {
	    	 wi(-n3); // Invisible edge, see Section 7.5
	    	 for (int i = n3 - 1; i >= n2 + 1; i--) wi(i);
	    	 wi(n3); wi(-n); // Invisible edge again.
	     }   
	     fw.write(".\r\n");
	      
	     // Bottom boundary face:
	     for (int i = n2; i >= n + 1; i--) 
	    	 wi(i);
	     if (rInner > 0) {
	    	 wi(-(n3 + 1));
	    	 for (int i = n3 + 2; i <= n4; i++) wi(i);
	    	 wi(n3 + 1); wi(-(n + 1));
	     }
	     fw.write(".\r\n");
	      
	     // Vertical, rectangular faces:
	     for (int i = 1; i <= n; i++) {
	        int j = i % n + 1;
	        // Outer rectangle:
	        wi(j); wi(i); wi(i + n); wi(j + n); fw.write(".\r\n");
	        if (rInner > 0) { // Inner rectangle:
	           wi(i + n2); wi(j + n2); wi(j + n3); wi(i + n3);
	            fw.write(".\r\n");
	        }
	     }
	     fw.close();      
	}   

	   static void wi(int x) throws IOException {
		   fw.append(" " + String.valueOf(x));
	   }
	   
	   static void wi2(int x) throws IOException {
		   fw.write(" " + String.valueOf(x));
	   }

	   static void wr(double x) throws IOException {
		   if (Math.abs(x) < 1e-9) x = 0;
		   fw.append(" " + String.valueOf((float) x));
		   // float instead of double to reduce the file size
	   }
	   
	   static void delay() throws InterruptedException {Thread.sleep(9000);}
	}

