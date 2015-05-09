import java.awt.Color;

import hsa.*;

public class Main
{
	public static void main (String[] args){
		ControlsConsole c = new ControlsConsole(25, 160);
		MapHandler.loadMap("map.txt");
		boolean gameRunning = true;
		
		Charactor chr1 = new Charactor(60.0f,60.0f, 0, 300);
		Charactor chr2 = new Charactor(60.0f, 60.0f, 0, 300);
		chr1.setImage("Charactor");
		chr2.setImage("Charactor");
		
		RenderSpace screen1 = new RenderSpace(640, 500);
		RenderSpace screen2 = new RenderSpace(640, 500);
		
		screen2.setCharactor(chr2);
		screen2.addCharactor(chr1);
		screen1.setCharactor(chr1);
		screen1.addCharactor(chr2);
		
		long frameTimeMils = System.currentTimeMillis();
		long previousFrameTimeMils = System.currentTimeMillis();
		
		while(gameRunning){
		
		    frameTimeMils = System.currentTimeMillis();
		    float frameTime = (frameTimeMils - previousFrameTimeMils) / 1000.0f;
		    previousFrameTimeMils = frameTimeMils;
		    
		    screen2.render();
		    screen1.render();
		    
		   c.drawImage(screen1.getImage(), 0, 0, null);
		    
		    c.setColor(Color.black);
		    c.drawLine(640, 0, 640, 499);

		    c.drawImage(screen2.getImage(), 641, 0, null);
		    
		    {//player1
			float forward = 0, side = 0, angle = 0; 
			if (c.isKeyDown('Q')){
			    angle = 100.0f * frameTime;
			}
			if (c.isKeyDown('E')){
			    angle = -100.0f * frameTime;
			}
			if (c.isKeyDown('W')){
			    forward = 50.0f * frameTime;
			}
			if (c.isKeyDown('S')){
			    forward = -50.0f * frameTime;
			}
			if (c.isKeyDown('D')){
			    side = 50.0f * frameTime;
			}
			if (c.isKeyDown('A')){
			    side = -50.0f * frameTime;
			}
			if (c.isKeyDown('X')){
			    chr1.fire(chr2);
			}
			chr1.update(forward, side, angle);
		    }
		    
		    {//player 2
			float forward = 0, side = 0, angle = 0;
			if (c.isKeyDown('Y')){
			    angle = 100.0f * frameTime;
			}
			if (c.isKeyDown('I')){
			    angle = -100.0f * frameTime;
			}
			if (c.isKeyDown('U')){
			    forward = 50.0f * frameTime;
			}
			if (c.isKeyDown('J')){
			    forward = -50.0f * frameTime;
			}
			if (c.isKeyDown('K')){
			    side = 50.0f * frameTime;
			}
			if (c.isKeyDown('H')){
			    side = -50.0f * frameTime;
			}
			if (c.isKeyDown('N')){
			    chr2.fire(chr1);
			}
			chr2.update(forward, side, angle);
		    }
		}
		
	}
} 
