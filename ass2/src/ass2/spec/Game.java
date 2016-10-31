package ass2.spec;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;

import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;




/**
 * COMMENT: Comment Game 
 *
 * @author malcolmr
 */
public class Game extends JFrame implements GLEventListener{
	
    private Terrain myTerrain;
    private Camera camera;
    
    public Game(Terrain terrain) {
    	super("Assignment 2");
        myTerrain = terrain;
    }
    
    /** 
     * Run the game.
     *
     */
    public void run() {
    	
        // initialisation
    	  GLProfile glp = GLProfile.getDefault();
          GLCapabilities caps = new GLCapabilities(glp);
          GLJPanel panel = new GLJPanel(caps);
          
          
          // add a GL Event listener to handle rendering
          this.camera = new Camera();
          panel.addGLEventListener(this);
          panel.addMouseMotionListener(this.camera);
          panel.addKeyListener(this.camera);

          panel.setFocusable(true);
          
          // Add an animator to call 'display' at 60fps        
          FPSAnimator animator = new FPSAnimator(60);
          animator.add(panel);
          animator.start();

          getContentPane().add(panel);
          setSize(800, 600);        
          setVisible(true);
          setDefaultCloseOperation(EXIT_ON_CLOSE);   
            
    }
    
    /**
     * Load a level file and display it.
     * 
     * @param args - The first argument is a level file in JSON format
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Terrain terrain = LevelIO.load(new File(args[0]));
        Game game = new Game(terrain);
        game.run();
    }

	@Override
	public void display(GLAutoDrawable drawable) {
    	//setup stuff
    	GL2 gl = drawable.getGL().getGL2();
    	gl.glClearColor(0f, 0f, 0f, 1);
    	
    	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    	gl.glMatrixMode(GL2.GL_MODELVIEW);
    	gl.glLoadIdentity();
    	
    	//Set camera 
    	double[] pos = camera.getPos();
   		gl.glTranslated(pos[0],pos[1],pos[2]);
     	gl.glRotated(camera.xRot(), 1, 0, 0);
    	gl.glRotated(camera.yRot(), 0, 1, 0);
    	gl.glScaled(0.7, 0.7, 0.7);
    	

    	gl.glPushMatrix();
	    // Draw terrain
        myTerrain.draw(drawable);
        gl.glPopMatrix();
    	//draw object
    	//gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    	//this.myTerrain.draw(drawable);
    	//gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); 
	}


	
	//Display Stuff
	@Override
	public void init(GLAutoDrawable drawable) {
    	GL2 gl = drawable.getGL().getGL2();
    	gl.glEnable(GL2.GL_DEPTH_TEST);
    	
        // enable lighting
        gl.glEnable(GL2.GL_LIGHTING);
        
        // turn on a light. Use default settings.
        gl.glEnable(GL2.GL_LIGHT0);
        
        // normalise normals (!)
        // this is necessary to make lighting work properly
        gl.glEnable(GL2.GL_NORMALIZE);
        
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        
        GLU glu = new GLU();
        glu.gluPerspective(60.0, (float)width/(float)height, 1.0, 60.0);
        //glu.gluLookAt(0.0, 0.0, 7.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}
	


}
