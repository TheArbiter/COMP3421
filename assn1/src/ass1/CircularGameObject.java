package ass1;
import com.jogamp.opengl.GL2;

public class CircularGameObject extends GameObject{

    private double[] myFillColour;
    private double[] myLineColour;
    private double[] myCentre = new double[2];
    private double myRadius;

	//Create a CircularGameObject with centre 0,0 and radius 1
	public CircularGameObject(GameObject parent, double[] fillColour,
	                                             double[] lineColour){
		super(parent);

        myFillColour = fillColour;
        myLineColour = lineColour;
        myCentre[0] = 0;
        myCentre[1] = 0;
        myRadius = 1;
	}

	//Create a CircularGameObject with centre 0,0 and a given radius
	public CircularGameObject(GameObject parent, double radius,double[] fillColour,
	                                                           double[] lineColour){
		super(parent);
		
        myFillColour = fillColour;
        myLineColour = lineColour;
        myCentre[0] = 0;
        myCentre[1] = 0;
        myRadius = radius;
	}

	/**
	 * @return the myFillColour
	 */
	public double[] getMyFillColour() {
		return myFillColour;
	}

	/**
	 * @param myFillColour the myFillColour to set
	 */
	public void setMyFillColour(double[] myFillColour) {
		this.myFillColour = myFillColour;
	}

	/**
	 * @return the myLineColour
	 */
	public double[] getMyLineColour() {
		return myLineColour;
	}

	/**
	 * @param myLineColour the myLineColour to set
	 */
	public void setMyLineColour(double[] myLineColour) {
		this.myLineColour = myLineColour;
	}

	/**
	 * @return the centre
	 */
	public double[] getCentre() {
		return myCentre;
	}

	/**
	 * @param centre the centre to set
	 */
	public void setCentre(double[] centre) {
		this.myCentre = centre;
	}

	/**
	 * @return the myRadius
	 */
	public double getMyRadius() {
		return myRadius;
	}

	/**
	 * @param myRadius the myRadius to set
	 */
	public void setMyRadius(double myRadius) {
		this.myRadius = myRadius;
	}
		
	@Override
    public void drawSelf(GL2 gl) {
		
		if(getMyFillColour() != null){
    		int count = 32;
    		//fill the polygon with colour
    		gl.glColor4d(myFillColour[0], myFillColour[1], myFillColour[2], myFillColour[3]);
    		gl.glBegin(GL2.GL_POLYGON);
    		for(int i = 0; i < count; i++)
    			gl.glVertex2d(getPoints()[i][0],getPoints()[i][1]);
    		gl.glEnd();
    	}
    	
    	if(getMyLineColour() != null){
    		int count = 32;
    		//draw the outline with colour
    		gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);
    		gl.glBegin(GL2.GL_LINE_LOOP);
    		for(int i = 0; i < count; i++)
    			gl.glVertex2d(getPoints()[i][0],getPoints()[i][1]);
    		gl.glEnd();
    	}
	}
	
	public double[][] getPoints() {        
		
		//32 sided polygon
		int numVertices = 32; //try 4

		double myPoints[][] = new double[numVertices][2];
		double angle = 0;
		double angleIncrement = 2*Math.PI/numVertices;
		
		for(int i=0; i < numVertices; i++){
        	angle = i* angleIncrement;
        	double x = myRadius * Math.cos(angle);
        	double y = myRadius * Math.sin(angle);
        	myPoints[i][0] = myCentre[0] + x;
        	myPoints[i][1] = myCentre[1] + y;
        	//angle += angleIncrement;
        }
		return myPoints;
    }
	
}
