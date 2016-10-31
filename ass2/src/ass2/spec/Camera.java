package ass2.spec;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class Camera implements MouseMotionListener, KeyListener {

	private double rotateX = 0;
    private double rotateY = 0;
    private Point myMousePoint = null;
    private static final int ROTATION_SCALE = 1;
	
    private double[] position = {0,-2,-10};
    
    public double[] getPos(){
    	return this.position;
    }
    
    
	public Camera() {
		// TODO Auto-generated constructor stub
	}

	//Camera Stuff
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Mouse Dragged");
		Point p = e.getPoint();

        if (myMousePoint != null) {
            int dx = p.x - myMousePoint.x;
            int dy = p.y - myMousePoint.y;

            rotateY += dx * ROTATION_SCALE;
            rotateX += dy * ROTATION_SCALE;
        }
       
        myMousePoint = p;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		 myMousePoint = e.getPoint();		
	}

	
	
	//unused methods
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		  
	     case KeyEvent.VK_UP:
		     
			  position[2] += 0.1;
			  System.out.println(position[2]);
			  break;
			  
		 case KeyEvent.VK_DOWN:
		       
			  position[2] -= 0.1;
			  System.out.println(position[2]);
			  break;
	
			  
		 case KeyEvent.VK_RIGHT:
		       
			  position[0] -= 0.1;
			  System.out.println(position[2]);
			  break;
			  
		 case KeyEvent.VK_LEFT:
		       
			  position[0] += 0.1;
			  System.out.println(position[2]);
			  break;
			  
		 default:
			 break;
		
		}
	}

	public double xRot() {
		return this.rotateX;
	}

	public double yRot() {
		return this.rotateY;
	}



	
}
