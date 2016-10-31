package ass2.spec;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class Player implements KeyListener{
	private double[] position = {0,0,0};

	
	private double RUN_SPEED = 20;
	private double TURN_SPEED = 160;


	private double curSpeed;
	
	
	
	
	
	
	
	
	public void checkInput(){
		
		//movement
		if(keyStates[0]){
			this.curSpeed = RUN_SPEED;
		}else if(keyStates[1]){
			this.curSpeed = - RUN_SPEED;
		}else{
			this.curSpeed = 0;
		}
		
		if(keyStates[2]){
			;
		}else if(keyStates[3]){
			;
		}
	}
	
	
	
	
	
	
	
	// {UP,DOWN,LEFT,RIGHT}
	boolean[] keyStates = {false,false,false,false};
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
	     case KeyEvent.VK_UP:
	    	 keyStates[0] = true; break;	  
		 case KeyEvent.VK_DOWN:
	    	 keyStates[1] = true; break;
		 case KeyEvent.VK_LEFT:
	    	 keyStates[2] = true; break;  
		 case KeyEvent.VK_RIGHT:
	    	 keyStates[3] = true; break;  
		 default:
			 break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
	     case KeyEvent.VK_UP:
	    	 keyStates[0] = false; break;	  
		 case KeyEvent.VK_DOWN:
	    	 keyStates[1] = false; break;
		 case KeyEvent.VK_LEFT:
	    	 keyStates[2] = false; break;  
		 case KeyEvent.VK_RIGHT:
	    	 keyStates[3] = false; break;  
		 default:
			 break;
		}
	}
	
	
}
