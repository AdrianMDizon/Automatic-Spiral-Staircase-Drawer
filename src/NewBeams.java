// NewBeams.java: Adapted from "Beams.java" from the textbook.
// by: Adrian Dizon

import java.io.*;

public class NewBeams {
   public static void main(FileWriter fw, String[] args) throws IOException {
	   
	   int stairs = 1;
	   double alphaDeg = 5;
	   String fileName = "defaultStairsName.dat";
	   
	   if (args.length != 3) {
			System.out.println(
				"Supply stairs (> 0), alpha (in degrees)\n" + 
				"and a filename as program arguments.\n");
			System.exit(1);
	    }
      
      try {
			stairs = Integer.valueOf(args[0]).intValue();
			alphaDeg = Double.valueOf(args[1]).doubleValue();
			fileName = args[2];
			if (stairs <= 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println("number of stairs must be an integer > 0");
			System.out.println("alpha must be a real number");
			System.exit(1);
		}
      
      new BeamsObj(stairs, alphaDeg * Math.PI / 180, fw);
   }
}

class BeamsObj {

   BeamsObj(int n, double alpha, FileWriter fw)
         throws IOException {
      int m = 0;
      int vertex = 0;
      
      Point3D[] P = new Point3D[9];
      double a = 30; //length
      double b = 5; //width
      double c = 7; //edge of cylinder
      
      P[1] = new Point3D(a, -b, 0);
      P[2] = new Point3D(a, b, 0);
      P[3] = new Point3D(c, b, 0);
      P[4] = new Point3D(c, -b, 0);
      P[5] = new Point3D(a, -b, 1);
      P[6] = new Point3D(a, b, 1);
      P[7] = new Point3D(c, b, 1);
      P[8] = new Point3D(c, -b, 1);
      
      for (int k = 0; k < n; k++) { // Beam k:
    	  double phi = k * alpha, cosPhi = Math.cos(phi), sinPhi = Math.sin(phi);
          m = 8 * k;
          for (int i = 1; i <= 8; i++) {
             double x = P[i].x, y = P[i].y;
             float x1 = (float) (x * cosPhi - y * sinPhi), 
                   y1 = (float) (x * sinPhi + y * cosPhi), 
                   z1 = (float) (P[i].z + (3*k));
             vertex = m+i+40;
             fw.write((vertex) + " " + x1 + " " + y1 + " " + z1 + "\r\n");
         } 
      }     
      
      //Stairs Faces
      fw.write("Faces:\r\n");
      for (int k = 0; k < n; k++) { // Beam k again:
         int t = 8 * k;
         face(t, 41, 42, 46, 45, fw);
         face(t, 44, 48, 47, 43, fw);
         face(t, 45, 46, 47, 48, fw);
         face(t, 41, 44, 43, 42, fw);
         face(t, 42, 43, 47, 46, fw);
         face(t, 41, 45, 48, 44, fw);
      }
      //fw.close();
   }

   void face(int t, int a, int b, int c, int d, FileWriter fw) throws IOException {
      a += t;
      b += t;
      c += t;
      d += t;
      fw.write(a + " " + b + " " + c + " " + d + ".\r\n");
   }
}