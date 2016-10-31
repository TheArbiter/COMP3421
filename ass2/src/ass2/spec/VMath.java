package ass2.spec;

public class VMath {

    public static double [] cross(double u [], double v[]){
    	double crossProduct[] = new double[3];
    	crossProduct[0] = u[1]*v[2] - u[2]*v[1];
    	crossProduct[1] = u[2]*v[0] - u[0]*v[2];
    	crossProduct[2] = u[0]*v[1] - u[1]*v[0];
    
    	return crossProduct;
    }
    
    public static double[] normal(double[] p0, double p1[], double p2[]){
    	double [] u = {p1[0] - p0[0], p1[1] - p0[1], p1[2] - p0[2]};
    	double [] v = {p2[0] - p0[0], p2[1] - p0[1], p2[2] - p0[2]};
    	double [] normal = cross(u,v);
    	return normalise(normal);
    }
    
    public static double [] normalise(double [] n){
    	double  mag = getMagnitude(n);
    	double norm[] = {n[0]/mag,n[1]/mag,n[2]/mag};
    	return norm;
    }
    
    public static double getMagnitude(double [] n){
    	double mag = n[0]*n[0] + n[1]*n[1] + n[2]*n[2];
    	mag = Math.sqrt(mag);
    	return mag;
    }
    
    public static double[] multiply(double[][] m, double[] v) {
      
        double[] u = new double[4];

        for (int i = 0; i < 4; i++) {
            u[i] = 0;
            for (int j = 0; j < 4; j++) {
                u[i] += m[i][j] * v[j];
            }
        }
        
        return u;
    }
    
}
