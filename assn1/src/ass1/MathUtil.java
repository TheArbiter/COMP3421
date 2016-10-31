package ass1;


/**
 * A collection of useful math methods 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class MathUtil {

    /**
     * Normalise an angle to the range [-180, 180)
     * 
     * @param angle 
     * @return
     */
    static public double normaliseAngle(double angle) {
        return ((angle + 180.0) % 360.0 + 360.0) % 360.0 - 180.0;
    }

    /**
     * Clamp a value to the given range
     * 
     * @param value
     * @param min
     * @param max
     * @return
     */

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    /**
     * Multiply two matrices
     * 
     * @param p A 3x3 matrix
     * @param q A 3x3 matrix
     * @return
     */
    public static double[][] multiply(double[][] p, double[][] q) {

        double[][] m = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                   m[i][j] += p[i][k] * q[k][j]; 
                }
            }
        }

        return m;
    }

    /**
     * Multiply a vector by a matrix
     * 
     * @param m A 3x3 matrix
     * @param v A 3x1 vector
     * @return
     */
    public static double[] multiply(double[][] m, double[] v) {

        double[] u = new double[3];

        for (int i = 0; i < 3; i++) {
            u[i] = 0;
            for (int j = 0; j < 3; j++) {
                u[i] += m[i][j] * v[j];
            }
        }

        return u;
    }



    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    

    /**
     * TODO: A 2D translation matrix for the given offset vector
     * 
     * @param pos
     * @return
     */
    public static double[][] translationMatrix(double[] v) {
    	
    	//gl.glTranslated (dx, dy, dz) 
        return new double[][]{{1,0,v[0]},{0,1,v[1]},{0,0,1}};
    }

    /**
     * TODO: A 2D rotation matrix for the given angle
     * 
     * @param angle in degrees
     * @return
     */
    public static double[][] rotationMatrix(double angle) {
    	double radian = angle*Math.PI/180;
    	
    	//gl.glRotated (angle, x, y, z)
    	return new double[][]{{Math.cos(radian),-Math.sin(radian),0},
        	{Math.sin(radian),Math.cos(radian),0},
        	{0,0,1}};
    }

    /**
     * TODO: A 2D scale matrix that scales both axes by the same factor
     * 
     * @param scale
     * @return
     */
    public static double[][] scaleMatrix(double scale) {

    	//gl.glScaled(sx,sy,sz)
        return new double[][]{{scale,0,0},{0,scale,0},{0,0,1}};
    }

    /**
     * A 2D transform matrix for the given 
     * translation,rotation and scale matrices
     * 
     * @return
     */
    public static double[][] transformationMatrix(double[][] translation, 
    		double[][] rotation, double[][] scale) {
        double[][] transformationMatrix = new double[3][3];
        // C = TRS
        transformationMatrix = (multiply(multiply(translation,rotation),scale));
    	return transformationMatrix;
    }
    
    // ===========================================
    // INVERSE FUNCTIONS
    // ===========================================

    /**
     * TODO: A 2D inverse translation matrix for the given offset vector
     * 
     * @param pos
     * @return
     */
    public static double[][] inverseTranslationMatrix(double[] v) {
    	//(-dx,-dy)
        return new double[][]{{1,0,-v[0]},{0,1,-v[1]},{0,0,1}};
    }

    /**
     * TODO: A 2D inverse rotation matrix for the given angle
     * 
     * @param angle in degrees
     * @return
     */
    public static double[][] inverseRotationMatrix(double angle) {
    	//(-theta)
    	double radian = -angle*Math.PI/180;
    	return new double[][]{{Math.cos(radian),-Math.sin(radian),0},
        	{Math.sin(radian),Math.cos(radian),0},
        	{0,0,1}};
    }

    /**
     * TODO: A 2D inverse scale matrix that scales both axes by the same factor
     * 
     * @param scale
     * @return
     */
    public static double[][] inverseScaleMatrix(double scale) {
    	//(1.0/sx,1.0/sy) 1.0 because its double
        return new double[][]{{1.0/scale,0,0},{0,1.0/scale,0},{0,0,1}};
    }

    /**
     * A 2D transform matrix for the given 
     * translation,rotation and scale matrices
     * 
     * @return
     */
    public static double[][] inverseTransformationMatrix(double[][] inverseScale, 
    		double[][] inverseRotation, double[][] inverseTranslation) {
        double[][] inverseTransformationMatrix = new double[3][3];
        
        // (S^-1)(R^-1)(T^-1)
        inverseTransformationMatrix = (multiply(multiply(inverseScale,inverseRotation),inverseTranslation));
    	return inverseTransformationMatrix;
    }

   
}
