package ass1;
import com.jogamp.opengl.GL2;

public class LineGameObject extends GameObject{

	private double[] myLineColour;
	private double[] coordinate1 = new double[2];
	private double[] coordinate2 = new double[2];
	
	//Create a LineGameObject from (0,0) to (1,0)
	public LineGameObject(GameObject parent, double[] lineColour){
		super(parent);
		myLineColour = lineColour;
		coordinate1[0] = 0; //from
		coordinate1[1] = 0; //(0,0)
		coordinate2[0] = 1; //from
		coordinate2[1] = 0; //(1,0)
	}

	//Create a LineGameObject from (x1,y1) to (x2,y2)
	public LineGameObject(GameObject parent,  double x1, double y1,
	                                          double x2, double y2,
	                                          double[] lineColour){
		super(parent);
		myLineColour = lineColour;
		coordinate1[0] = x1; //from
		coordinate1[1] = y1; //(x1,y1)
		coordinate2[0] = x2; //from
		coordinate2[1] = y2; //(x2,y2)
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
	 * @return the coordinate1
	 */
	public double[] getCoordinate1() {
		return coordinate1;
	}

	/**
	 * @param coordinate1 the coordinate1 to set
	 */
	public void setCoordinate1(double[] coordinate1) {
		this.coordinate1 = coordinate1;
	}

	/**
	 * @return the coordinate2
	 */
	public double[] getCoordinate2() {
		return coordinate2;
	}

	/**
	 * @param coordinate2 the coordinate2 to set
	 */
	public void setCoordinate2(double[] coordinate2) {
		this.coordinate2 = coordinate2;
	}
	
    /**
     * Get the line
     * 
     * @return
     */
    public double[][] getPoints() {        
        return new double[][]{{coordinate1[0],coordinate1[1]},{coordinate2[0],coordinate2[1]}};
    }

    /**
     * Set the lin
     * 
     * @param points
     */
    public void setPoints(double[][] points) {
        coordinate1[0] = points[0][0];
        coordinate1[1] = points[0][1];
        coordinate2[0] = points[1][0];
        coordinate2[1] = points[1][1];
    }

    @Override
    public void drawSelf(GL2 gl) {

        // TODO: Write this method    	
    	if(getMyLineColour() != null){
    		//int count = getPoints().length/2;
    		//draw the outline with colour
    		gl.glColor4d(myLineColour[0], myLineColour[1], myLineColour[2], myLineColour[3]);
    		gl.glBegin(GL2.GL_LINES);
    		//for(int i = 0; i < count; i++)
    			//gl.glVertex2d(myPoints[i*2],myPoints[i*2+1]);
    		gl.glVertex2d(coordinate1[0],coordinate1[1]);
    		gl.glVertex2d(coordinate2[0],coordinate2[1]);
    		gl.glEnd();
    	}

    }

}
