package ass2.spec;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;



/**
 * COMMENT: Comment HeightMap 
 *
 * @author malcolmr
 */
public class Terrain {

    private Dimension mySize;
    private double[][] myAltitude;
    private List<Tree> myTrees;
    private List<Road> myRoads;
    private float[] mySunlight;

    //texture data
    private Texture textures[] = new Texture[2];

    /**
     * Create a new terrain
     *
     * @param width The number of vertices in the x-direction
     * @param depth The number of vertices in the z-direction
     */
    public Terrain(int width, int depth) {
        mySize = new Dimension(width, depth);
        myAltitude = new double[width][depth];
        myTrees = new ArrayList<Tree>();
        myRoads = new ArrayList<Road>();
        mySunlight = new float[3];
    }
    
    public Terrain(Dimension size) {
        this(size.width, size.height);
    }

    public Dimension size() {
        return mySize;
    }

    public List<Tree> trees() {
        return myTrees;
    }

    public List<Road> roads() {
        return myRoads;
    }

    public float[] getSunlight() {
        return mySunlight;
    }

    /**
     * Set the sunlight direction. 
     * 
     * Note: the sun should be treated as a directional light, without a position
     * 
     * @param dx
     * @param dy
     * @param dz
     */
    public void setSunlightDir(float dx, float dy, float dz) {
        mySunlight[0] = dx;
        mySunlight[1] = dy;
        mySunlight[2] = dz;        
    }
    
    /**
     * Resize the terrain, copying any old altitudes. 
     * 
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        mySize = new Dimension(width, height);
        double[][] oldAlt = myAltitude;
        myAltitude = new double[width][height];
        
        for (int i = 0; i < width && i < oldAlt.length; i++) {
            for (int j = 0; j < height && j < oldAlt[i].length; j++) {
                myAltitude[i][j] = oldAlt[i][j];
            }
        }
    }

    /**
     * Get the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public double getGridAltitude(int x, int z) {
        return myAltitude[x][z];
    }

    /**
     * Set the altitude at a grid point
     * 
     * @param x
     * @param z
     * @return
     */
    public void setGridAltitude(int x, int z, double h) {
        myAltitude[x][z] = h;
    }


    /**
     * Get the altitude at an arbitrary point. 
     * Non-integer points should be interpolated from neighbouring grid points
     * 
     * TO BE COMPLETED
     * 
     * @param x
     * @param z
     * @return
     */
    public double altitude(double x, double z) {
    	
    	if((int)x == x || (int)z == z)
    		return(getGridAltitude((int)x, (int)z));
    	
    	int[] lP = {(int) Math.floor(x), (int) Math.floor(z)};
    	int[] uP = {(int) Math.ceil(x), (int) Math.ceil(z)};

        double a = (getGridAltitude(uP[0], uP[1]) - getGridAltitude(lP[0], uP[1]))/(uP[0]- lP[0]);
        double b = (getGridAltitude(uP[0], lP[1]) - getGridAltitude(lP[0], lP[1]))/(uP[0]-lP[0]);
         
        double ia = (a * (x - lP[0]) + getGridAltitude(lP[0], uP[1]));
        double ib = (b * (x - lP[0]) + getGridAltitude(lP[0], lP[1]));

        return((ib-ia)/(uP[1]-lP[1]) * (z - lP[1]) + ia);
    }

    
    /**
     * Add a tree at the specified (x,z) point. 
     * The tree's y coordinate is calculated from the altitude of the terrain at that point.
     * 
     * @param x
     * @param z
     */
    public void addTree(double x, double z) {
        double y = altitude(x, z);
        Tree tree = new Tree(x, y, z);
        myTrees.add(tree);
    }


    /**
     * Add a road. 
     * 
     * @param x
     * @param z
     */
    public void addRoad(double width, double[] spine) {
        Road road = new Road(width, spine);
        myRoads.add(road);        
    }

    //Textures
    public void load(GL2 gl){
    	textures[0] = new Texture(gl,"textures/grass.jpg",".jpg",true);

    	for(Tree t: this.myTrees){
    		t.load(gl);
    	}
    }

    public void draw(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glActiveTexture(GL2.GL_TEXTURE0); 	
    	gl.glEnable(GL2.GL_TEXTURE_2D);
    	gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[0].getTextures());  
    	
    	// Materials and Color of Trees
        float ambDiff[] = {0.105f, 0.702f, 0.24f, 1.0f};
        float spec[] = { 0f, 0f, 0f, 1.0f };
        float shine[] = { 1.0f };
        
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, ambDiff,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, spec,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, shine,0);
    	
    	gl.glPolygonMode(GL.GL_BACK,GL2.GL_LINE);
    	
		
    	//draw the mesh
		for(int z = 0; z<myAltitude.length-1; z++)
			for(int x = 0; x<myAltitude.length-1; x++){


				double[] p1 = new double[] {x+1, altitude(x+1, z), z};
				double[] p2 = new double[] {x, altitude(x, z), z};
				double[] p3 = new double[] {x, altitude(x, z+1), z+1};
				double[] normal = VMath.normal(p1, p2, p3);
				System.out.println(altitude(x, z));
				gl.glBegin(GL2.GL_TRIANGLES);
	    			gl.glNormal3d(normal[0],normal[1],normal[2]);
	    			gl.glVertex3d(p1[0], p1[1], p1[2]); 
	    			gl.glVertex3d(p2[0], p2[1], p2[2]);
	    			gl.glVertex3d(p3[0], p3[1], p3[2]);
    		    gl.glEnd();
    		    
    		    
    		    
				p1 = new double[] {x+1, getGridAltitude(x+1, z), z};
				p2 = new double[] {x, getGridAltitude(x, z+1), z+1};
				p3 = new double[] {x+1, getGridAltitude(x+1, z+1), z+1};
				normal = VMath.normal(p1, p2, p3);
    		    gl.glBegin(GL2.GL_TRIANGLES);
	    			gl.glNormal3d(normal[0],normal[1],normal[2]);
	    			gl.glVertex3d(p1[0], p1[1], p1[2]); 
	    			gl.glVertex3d(p2[0], p2[1], p2[2]);
	    			gl.glVertex3d(p3[0], p3[1], p3[2]);
    			gl.glEnd();
		    
				
			}

		gl.glDisable(GL2.GL_TEXTURE_2D);
  	    gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_FALSE);
  	    
    	//draw the trees
    	for(Tree t : this.myTrees){
    		t.draw(drawable);
    	}
    	
    	//draw the roads
    	for(Road r : this.myRoads){
    		r.draw(drawable);
    	}	
    	
    	
    }
    



	
	

}
