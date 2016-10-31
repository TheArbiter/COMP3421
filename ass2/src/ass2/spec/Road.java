package ass2.spec;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

/**
 * COMMENT: Comment Road 
 *
 * @author malcolmr
 */
public class Road {

    private List<Double> myPoints;
    private double myWidth;
    
    double epsion = 0.001;
    
    //texture data
    private Texture textures[] = new Texture[2];
    /** 
     * Create a new road starting at the specified point
     */
    public Road(double width, double x0, double y0) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        myPoints.add(x0);
        myPoints.add(y0);
    }

    /**
     * Create a new road with the specified spine 
     *
     * @param width
     * @param spine
     */
    public Road(double width, double[] spine) {
        myWidth = width;
        myPoints = new ArrayList<Double>();
        for (int i = 0; i < spine.length; i++) {
            myPoints.add(spine[i]);
        }
    }

    /**
     * The width of the road.
     * 
     * @return
     */
    public double width() {
        return myWidth;
    }

    /**
     * Add a new segment of road, beginning at the last point added and ending at (x3, y3).
     * (x1, y1) and (x2, y2) are interpolated as bezier control points.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
    public void addSegment(double x1, double y1, double x2, double y2, double x3, double y3) {
        myPoints.add(x1);
        myPoints.add(y1);
        myPoints.add(x2);
        myPoints.add(y2);
        myPoints.add(x3);
        myPoints.add(y3);        
    }
    
    /**
     * Get the number of segments in the curve
     * 
     * @return
     */
    public int size() {
        return myPoints.size() / 6;
    }

    /**
     * Get the specified control point.
     * 
     * @param i
     * @return
     */
    public double[] controlPoint(int i) {
        double[] p = new double[2];
        p[0] = myPoints.get(i*2);
        p[1] = myPoints.get(i*2+1);
        return p;
    }
    
    /**
     * Get a point on the spine. The parameter t may vary from 0 to size().
     * Points on the kth segment take have parameters in the range (k, k+1).
     * 
     * @param t
     * @return
     */
    public double[] point(double t) {
        int i = (int)Math.floor(t);
        t = t - i;
        
        i *= 6;
        
        double x0 = myPoints.get(i++);
        double y0 = myPoints.get(i++);
        double x1 = myPoints.get(i++);
        double y1 = myPoints.get(i++);
        double x2 = myPoints.get(i++);
        double y2 = myPoints.get(i++);
        double x3 = myPoints.get(i++);
        double y3 = myPoints.get(i++);
        
        double[] p = new double[2];

        p[0] = b(0, t) * x0 + b(1, t) * x1 + b(2, t) * x2 + b(3, t) * x3;
        p[1] = b(0, t) * y0 + b(1, t) * y1 + b(2, t) * y2 + b(3, t) * y3;        
        
        return p;
    }
    
    /**
     * Calculate the Bezier coefficients
     * 
     * @param i
     * @param t
     * @return
     */
    private double b(int i, double t) {
        
        switch(i) {
        
        case 0:
            return (1-t) * (1-t) * (1-t);

        case 1:
            return 3 * (1-t) * (1-t) * t;
            
        case 2:
            return 3 * (1-t) * t * t;

        case 3:
            return t * t * t;
        }
        
        // this should never happen
        throw new IllegalArgumentException("" + i);
    }

    
    
    
    
    // draw the road
    //x y profile of arrow in CCW order
	double vertices[][] = {{0,0},
						   {0.5,0.3}, 
						   {0.2,0.3},
						   {0.2,1}, 
						   {-0.2,1},
						   {-0.2,0.3},
						   {-0.5,0.3}
			};
	
	//When we want to draw the actual faces of the polygon
	//we must break it up into 2 parts since it is concave
	int faces[][] = {{0,1,6},    //indexes of the points in the vertices array for the triangle part
			         {2,3,4,5}}; //indexs for the quad part
    
    public void drawSelf(GLAutoDrawable drawable, double h, double step){
    	  
        GL2 gl = drawable.getGL().getGL2();
        
        // Material property vectors.
        float matAmbAndDif1[] = {0.7f, 0.2f, 0.7f, 1.0f};
        float matAmbAndDif2[] = {0f, 1f, 0f, 1.0f};
        float matSpec1[] = {0.2f, 0.2f, 0.2f, 1f};
    
        float matShine[] = {150.0f};
        
        //Set front and back to have different colors to make debugging easier
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, matAmbAndDif1,0);
        gl.glMaterialfv(GL2.GL_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, matAmbAndDif2,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpec1,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, matShine,0);
        
        gl.glBegin(GL2.GL_TRIANGLE_STRIP);
        // TODO NEED TO DRAW ROAD
        // TAKE A LOOK AT W7
        
        for(double j = 0; j < this.size(); j=j+step){
            
        	double[] point = this.point(j);
        	double[] tangentPoint = this.tangentPoint(j);
        	double[] normal = new double[]{-tangentPoint[1], tangentPoint[0]};
        	double width = (this.width()/2);
        	
        	normalize(normal);
        	
        	gl.glNormal3d(0, 0, 1);
        	gl.glTexCoord2d(-width*normal[0]+point[0], -width*normal[1] + point[1]); 
    		gl.glVertex3d(-width*normal[0]+point[0], h + epsion, -width*normal[1] + point[1]);
    		gl.glTexCoord2d(width*normal[0]+point[0], width*normal[1] + point[1]); 
    		gl.glVertex3d(width*normal[0]+point[0], h + epsion, width*normal[1] + point[1]);
    		
        }gl.glEnd();
        
        /*      
        //The front of our arrow extrusion will be on the z=-1 plane
        //The back will be on the z = -2 plane
        double zFront = -1;
        double zBack = -2;
        
        
        //Draw the front face
	    for(int j=0; j < faces.length; j++){
	        gl.glBegin(GL2.GL_POLYGON);{
	        	for(int i = 0 ;i< faces[j].length; i++){
	        		gl.glNormal3d(0,0,1);
	        		gl.glVertex3d(vertices[faces[j][i]][0],vertices[faces[j][i]][1],zFront);       		
	        	}	            
	        }gl.glEnd();	        
	    }
	    
	        
	    //Draw the back face 
	    for(int j = 0; j < faces.length; j++ ){
	    	gl.glBegin(GL2.GL_POLYGON);{
                //Draw face in reverse order so CWW order since it is facing the back
	    		for(int i = faces[j].length -1; i>=0; i--){
	    			gl.glNormal3d(0,0,-1);
	    			gl.glVertex3d(vertices[faces[j][i]][0],vertices[faces[j][i]][1],zBack);       		
	    		}

	    	}gl.glEnd();
	    }
	    
	    //We will go through each point in the arrow,
        //create a copy of the point at zBack instead of zFront
        //and connect it to its neighbouring point and transformed neighbouring point
        //using quads
	    
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        
        gl.glBegin(GL2.GL_QUADS);{
	    	for(int i=0; i< vertices.length; i++){
	    		//The final point needs to be connected back to point at index 0
	    		int i1 = (i+1)%vertices.length;


	    		double p0[] = {vertices[i][0],vertices[i][1],zFront,1};	        		
	    		double p1[] = {vertices[i][0],vertices[i][1],zBack,1};	

	    		double p2[] = {vertices[i1][0],vertices[i1][1],zFront,1};
	            double p3[] = {vertices[i1][0],vertices[i1][1],zBack,1};
	    		

	    		//We only want flat shading so we just use one normal per face
	    		//Since our quads are planar, using simple method with 3 points is ok
	    		gl.glNormal3dv(VMath.normal(p0,p1,p2),0);
	    		gl.glVertex3dv(p0,0);	        		
	    		gl.glVertex3dv(p1,0);
	    		gl.glVertex3dv(p3,0);	  				          
	    		gl.glVertex3dv(p2,0);	
	    		
	        				             		
	        }
	        		
	    }gl.glEnd();
	    */
    }
    
	private double bt(int i, double t) {
	    
	    switch(i) {
	    
	    case 0:
	        return (1-t) * (1-t);
	
	    case 1:
	        return 2 * (1-t) * t;
	        
	    case 2:
	        return t * t ;
	    }
	    
	    // this should never happen
	    throw new IllegalArgumentException("" + i);
	}
	
	/**
	 * Get a point on the spine. The parameter t may vary from 0 to size().
	 * Points on the kth segment take have parameters in the range (k, k+1).
	 * 
	 * @param t
	 * @return
	 */
	public double[] tangentPoint(double t) {
	    int i = (int)Math.floor(t);
	    t = t - i;
	    
	    i *= 6;
	    
	    double x0 = myPoints.get(i++);
	    double y0 = myPoints.get(i++);
	    double x1 = myPoints.get(i++);
	    double y1 = myPoints.get(i++);
	    double x2 = myPoints.get(i++);
	    double y2 = myPoints.get(i++);
	    double x3 = myPoints.get(i++);
	    double y3 = myPoints.get(i++);
	    
	    double[] tp = new double[3];
	
	    tp[0] = (bt(0, t) * (x1-x0) + bt(1, t) * (x2-x1) + bt(2, t) * (x3-x2))*3;
	    tp[1] = (bt(0, t) * (y1-y0) + bt(1, t) * (y2-y1) + bt(2, t) * (y3-y2))*3;        
	    
	    return tp;
	}
	
	//Nomalize function taken from litcone.java
	public void normalize(double v[]){
		double d = Math.sqrt(v[0]*v[0]+v[1]*v[1]);
		if(d != 0.0){
			v[0]/=d;
			v[1]/=d;
		}
	}
	
	//w8 example shader
	public void load(GL2 gl){
		textures[0] = new Texture(gl,"filepath",".jpg",true);
	
	}


	public void draw(GLAutoDrawable drawable) {
		drawSelf(drawable, 4, 5);
		
	}
}
