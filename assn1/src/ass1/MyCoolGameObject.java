package ass1;

import com.jogamp.opengl.GL2;

public class MyCoolGameObject extends GameObject{

	private double[] myFillColour;
    private double[] myLineColour;
    private static final double[] defaultFillColour = {0.75,0.4,0.2,1};
    private static final double[] defaultLineColour = {0.8,0.0,0.0,1};
    private static final double[] defaultWheelColour = {0.9,0.9,0.9,1};
    private CircularGameObject w1;
    private CircularGameObject w2;
    
	public MyCoolGameObject() {
		this(GameObject.ROOT);
	}
	
	public MyCoolGameObject(GameObject parent) {
		super(parent);
		// TODO Auto-generated constructor stub
		myFillColour = defaultFillColour;
		myLineColour = defaultLineColour;
		
		double[] pos1 = {-0.6,-0.4};
		double[] pos2 = {0.6,-0.4};
		w1 = new CircularGameObject(this, 0.25, defaultWheelColour, defaultLineColour);
		w1.setPosition(pos1[0],pos1[1]);
		w2 = new CircularGameObject(this, 0.25, defaultWheelColour, defaultLineColour);
		w2.setPosition(pos2[0],pos2[1]);
	}

	public double[] getMyFillColour() {
		return myFillColour;
	}

	public void setMyFillColour(double[] myFillColour) {
		this.myFillColour = myFillColour;
	}

	public double[] getMyLineColour() {
		return myLineColour;
	}

	public void setMyLineColour(double[] myLineColour) {
		this.myLineColour = myLineColour;
	}
	
	@Override
	public void drawSelf(GL2 gl) {
		
		// First draw the car body
		if (myFillColour!=null) {
    		gl.glColor4d(myFillColour[0], myFillColour[1], myFillColour[2], myFillColour[3]);
    		gl.glBegin(GL2.GL_QUADS);
    		gl.glVertex2d(-1,-0.4);
    		gl.glVertex2d(1,-0.4);
    		gl.glVertex2d(1,0);
    		gl.glVertex2d(-1,0);
    		
    		gl.glVertex2d(-0.5,0);
    		gl.glVertex2d(1,0);
    		gl.glVertex2d(0.9,0.3);
    		gl.glVertex2d(-0.4,0.3);
    		gl.glEnd();
		}
		// Next draw the outline of the car
		if (myLineColour!=null){
			gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);
    		gl.glBegin(GL2.GL_LINE_LOOP);
    		gl.glVertex2d(-1,0);
    		gl.glVertex2d(-1,-0.4);
    		gl.glVertex2d(1,-0.4);
    		gl.glVertex2d(1,0);
    		gl.glVertex2d(0.9,0.3);
    		gl.glVertex2d(-0.4,0.3);
    		gl.glVertex2d(-0.5,0);
    		gl.glEnd();
		}
		
		// Adding a window
		gl.glColor3d(0.2,0.2,0.2);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex2d(-0.35, 0);
		gl.glVertex2d(0.5, 0);
		gl.glVertex2d(0.5, 0.2);
		gl.glVertex2d(-0.2833, 0.2);
		gl.glEnd();
		
	}

}
