import java.awt.Color;

import hsa.*;

public class MainTest
{
	public static void main (String[] args){
		ControlsConsole c = new ControlsConsole();
		c.println(MathHelper.diffAngle(1,359));
		c.println(MathHelper.diffAngle(359, 1));
		c.println(MathHelper.diffAngle(90,100));
		// MapHandler.loadMap("map (2).txt");
		// Charactor chr1 = new Charactor();//movement test for charactor, and for MapHandler.IsWall
		// chr1.setValues(65.0f, 65.0f, 90, 100);
		// 
		//  //test for MapHandler  
		// for (int i = 0; i < 100000; i++){
		//         chr1.update(1.0f, 0.0f, (float)(-90+(Math.random() * 180)));
		//         int x = (int)(MapHandler.castRay(chr1.getX(), chr1.getY(), chr1.getAngle()) * Math.cos(Math.toRadians(chr1.getAngle())) + chr1.getX());
		//         int y = (int)(MapHandler.castRay(chr1.getX(), chr1.getY(), chr1.getAngle()) * Math.sin(Math.toRadians(chr1.getAngle())) + chr1.getY());
		//         c.drawRect(x, y, 1, 1);
		// }
		// 
		// 
		// 
		// 
		// 
		// c.setColor(Color.blue);
		// 
		// for(int i = 0; i < 10000; i++){
		//         chr1.update(5.0f, 0.0f, (float)(-90+(Math.random() * 180)));
		//         c.drawRect((int)chr1.getX() + 125,(int)chr1.getY(), 1, 1);
		// }
		// 
		// 
		
	}
} 
