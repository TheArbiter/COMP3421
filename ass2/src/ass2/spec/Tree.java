package ass2.spec;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

/**
 * COMMENT: Comment Tree 
 *
 * @author malcolmr
 */
public class Tree {

    private double[] myPos;
    
    //texture data
    private Texture textures[] = new Texture[2];
    
    public Tree(double x, double y, double z) {
        myPos = new double[3];
        myPos[0] = x;
        myPos[1] = y;
        myPos[2] = z;
    }
    
    public double[] getPosition() {
        return myPos;
    }
    
    public void load(GL2 gl){
    	//Texture of the trunk
    	textures[0] = new Texture(gl,"textures/trunk.jpg",".jpg",true);
    	// Texture of Sphere
        textures[1] = new Texture(gl,"textures/leaves.jpg",".jpg",true);
    }
    
    
    public void draw(GLAutoDrawable drawable){
    	
    	
    	
		GL2 gl = drawable.getGL().getGL2();
		gl.glPushMatrix();
		gl.glTranslated(myPos[0], myPos[1], myPos[2]);

    	drawSelf(drawable,2,0.2,1);
    	gl.glPopMatrix();
    }
    
    private void drawSelf(GLAutoDrawable drawable, double h, double d, double d_leaves) {
    	
		GL2 gl = drawable.getGL().getGL2();
    	
    	int maxStacks = 100;
    	double y1 = 0;
    	double y2 = h;
    	double x0,z0,x1,z1;
    	
    	//Code from lec slide w5 
    	float matAmbAndDifL[] = {0.3f, 0.16f, 0.15f, 1.0f};
    	float matSpecL[] = { 0.0f, 0.0f, 0.0f, 1.0f };
    	float matShineL[] = { 50.0f };

        // Material properties of sphere.
    	gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, matAmbAndDifL,0);
    	gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpecL,0);
    	gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, matShineL,0);
    	        
    	//w8 code for GL_TRIANGLE_STRIP modified for GL_TRIANGLE_FAN and for QUAD_STRIP        
    	gl.glBegin(GL2.GL_TRIANGLE_FAN);{
    		 gl.glNormal3d(0,-1,0);
    		 gl.glVertex3d(0,y1,0);
             for (int j = 0; j <= maxStacks ; j++){
                 x1 = d*Math.cos((double)j*2*Math.PI/maxStacks);
                 z1 = d*Math.sin((double)j*2*Math.PI/maxStacks);
                 gl.glVertex3d(x1,y1,z1);
             }
    	}
    	gl.glEnd();
    	
    	gl.glBegin(GL2.GL_TRIANGLE_FAN);{
      		gl.glNormal3d(0,1,0);
      		gl.glVertex3d(0,y2,0);
            for (int j = 0; j <= maxStacks ; j++){
                x0 = d*Math.cos((double)j*2*Math.PI/maxStacks);
                z0 = d*Math.sin((double)j*2*Math.PI/maxStacks);
                gl.glVertex3d(x0,y2,z0);
            }
	   	}
	   	gl.glEnd();
	   	
    	gl.glBegin(GL2.GL_QUAD_STRIP);
        {
            
            for (int i = 0; i <= maxStacks ; i++){
                x0 = d*Math.cos((double)i * 2*Math.PI/maxStacks);
                z0 = d*Math.sin((double)i * 2*Math.PI/maxStacks);
                
                float s =  i/(float)maxStacks; 
                
                gl.glNormal3d(x0, 0, z0);
                gl.glTexCoord2d(s, 0); 
                gl.glVertex3d(x0, y1, z0);
                gl.glTexCoord2d(s, 1);
                gl.glVertex3d(x0, y2, z0);
            }

        }
        
        gl.glEnd();
        
    	//Code from lec slide w5 
        float matAmbAndDifLeaf[] = {0.10f, 0.30f, 0.10f, 1.0f};
    	float matSpecLeaf[] = { 0.3f, 0.3f, 0.3f, 1.0f };
    	float matShineLeaf[] = { 5.0f };

        // Material properties of sphere.
    	gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, matAmbAndDifLeaf,0);
    	gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpecLeaf,0);
    	gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, matShineLeaf,0);
        
    	//Draw Spheres
        gl.glPushMatrix();
	        gl.glTranslated(0, h, 0);
	        gl.glRotated(90, 1, 0, 0);
	        int stack = 20;
	        int slice = 20;
	       	sphere(gl, d_leaves, slice, stack, true);
        gl.glPopMatrix();
    }
    
    
    public static void sphere(GL2 gl, double radius, int maxSlices, int maxStacks, boolean texCoords) {
        double x1,x2,y1,y2,z1,z2;
        
		for (int j = 0; j < maxStacks; j++) {
			gl.glBegin(GL2.GL_QUAD_STRIP);
			for (int i = 0; i <= maxSlices; i++) {
				x1 = Math.cos((2*Math.PI/maxSlices) * i)
				 	* Math.cos((Math.PI/maxStacks) * j - Math.PI/2);
				y1 = Math.sin((2*Math.PI/maxSlices) * i)
				 	* Math.cos((Math.PI/maxStacks) * j - Math.PI/2);
				z1 = Math.sin((Math.PI/maxStacks) * j - Math.PI/2);
				
				x2 = Math.cos((2*Math.PI/maxSlices) * i)
					 * Math.cos((Math.PI/maxStacks) * (j+1) - Math.PI/2);
				y2 = Math.sin((2*Math.PI/maxSlices) * i)
					 * Math.cos((Math.PI/maxStacks) * (j+1) - Math.PI/2);
				z2 = Math.sin((Math.PI/maxStacks) * (j+1) - Math.PI/2);
				
				gl.glNormal3d(x2,y2,z2);
				
				if (texCoords){
				    double tcoord =  1.0/maxStacks * (j+1);
				    double scoord = 1.0/maxSlices * i;
					gl.glTexCoord2d(scoord,tcoord);
				}
				gl.glVertex3d(radius*x2,radius*y2,radius*z2);
				
				gl.glNormal3d(x1,y1,z1);
				if (texCoords){
					double tcoord =  1.0/maxStacks * j;
				    double scoord = 1.0/maxSlices * i;
					gl.glTexCoord2d(scoord,tcoord);
				}
				gl.glVertex3d(radius*x1,radius*y1,radius*z1);
			}
			gl.glEnd();
		}
	} 
}
