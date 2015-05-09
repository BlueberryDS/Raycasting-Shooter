import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;

public class Charactor
{   
	private float _x = 0, _y = 0, _angle = 0;
	private int _hp = 0; 
	
	private float _fireAngle = 20.0f,_fhp = 0.0f;
	
	private Image _imageFront;
	private Image _imageBack;
	private Image _imageRight;
	private Image _imageLeft;
	private Image _imageFR;
	private Image _imageFL;
	private Image _imageBR;
	private Image _imageBL;
	
	//************************
	//CONSTRUCTOR
	//Sets values about the charactor.
	//************************
	public Charactor(float x, float y, float angle, int hp){
	    _x = x;
	    _y = y;
	    _angle = angle;
	    _hp = hp;
	    _fhp = (float)hp;
	}
	
	//************************
	//update(float forward, float side, float turn)
	//Update the charactor on the frame. To be called once per frame
	//Moves the charactor.
	//************************
	public void update(float forward, float side, float turn) { //Angles are all reversed right now here...I dunno why.
	    _angle -= turn;
	    _angle = MathHelper.normalizeAngle(_angle);
	
	    float xDelta = (float)(forward * Math.cos(Math.toRadians(360 - _angle))) + (float)(side * Math.cos(Math.toRadians(270 - _angle)));
	    float yDelta = (float)(forward * Math.sin(Math.toRadians(360 - _angle))) + (float)(side * Math.sin(Math.toRadians(270 - _angle)));
	    
	    int xSign = xDelta < 0 ? -1 : 1;
	    int ySign = yDelta < 0 ? -1 : 1;
	    
	    if (!MapHandler.isWall(_x + 5 * xSign, _y + 5 * ySign)&& !MapHandler.isWall(_x + xDelta, _y + yDelta)){
		    _x += xDelta;
		    _y += yDelta;
	    }

	}

	//************************
	//ChangeHp(int d)
	//Changes the hp value
	//************************
	public void changeHp(int d) {
	    _hp += d;
	}
	

	
	//************************
	//GetHp()
	//Gets the hp value
	//************************
	public int getHp() {
	    return _hp;
	}
	
	public float getHpFloat() {
	    return _hp/_fhp;
	}

	//************************
	//fire(Charactor chr)
	//Trys to fire at another charactor, and if it hits, remove hp.
	//************************
	public void fire(Charactor chr) {
	    float xOffset = chr.getX() - _x;
	    float yOffset = chr.getY() - _y;
	    float distance = MathHelper.distance(xOffset, yOffset);
	    float angleTo = 360-MathHelper.convertCompToAngle(yOffset, xOffset);
	    float angleDiff=MathHelper.diffAngle(angleTo, _angle);
	    float damage = -(angleDiff-_fireAngle)*(angleDiff+_fireAngle)/50;
	    if(damage>0 && (MapHandler.castRay(_x, _y, angleTo) > distance)){
		 chr.changeHp((int)-damage);
	    }
	}

	//************************
	//isDead()
	//Returns false if charactor has no HP.
	//************************
	public boolean isDead(){
	    return _hp < 0;
	}

	//************************
	//getX()
	//Returns x position.
	//************************
	public float getX(){
	    return _x;
	}

	//************************
	//getY();
	//Returns y position.
	//************************
	public float getY(){
	    return _y;
	}
	
	//************************
	//getAngle()
	//Gets the heading of the charactor.
	//************************
	public float getAngle(){
	    return _angle;
	}
	
	//************************
	//geFiretAngle()
	//Gets the firing span of the charactor.
	//************************
	public float getFireAngle(){
	    return _fireAngle;
	}
	
	//************************
	//setImage
	//Sets the image file containing charactor sprites.
	//************************
	public void setImage(String file){
	    try {
		File f = new File(file + "\\Front.png"); 
		_imageFront = ImageIO.read(f);
		
		f = new File(file + "\\Back.png"); 
		_imageBack = ImageIO.read(f);
		
		f = new File(file + "\\Right.png"); 
		_imageRight = ImageIO.read(f);
		
		f = new File(file + "\\Left.png"); 
		_imageLeft = ImageIO.read(f);
		
		f = new File(file + "\\FL.png"); 
		_imageFL = ImageIO.read(f);
		
		f = new File(file + "\\FR.png"); 
		_imageFR = ImageIO.read(f);
		
		f = new File(file + "\\BL.png"); 
		_imageBL = ImageIO.read(f);
		
		f = new File(file + "\\BR.png"); 
		_imageBR = ImageIO.read(f);
	    }
	    catch (java.io.IOException e){
	    }
	}
	
	//************************
	//getImage(Charactor chr)
	//Returns the image to be seen from chr's position. (This is incompletely implimented.)
	//************************
	public Image getImage(Charactor chr){
	    float angle1 = chr.getAngle();
	    float angle2 = _angle;
	    float diff = MathHelper.diffAngle(angle2,angle1);
	    if(diff<=22.5&& diff>=-22.5){
		return _imageBack;
	    }
	    else if(diff>-67.5 && diff<-22.5){
		return _imageBL;
	    }
	    else if(diff>=-112.5 && diff<=-67.5){
		return _imageLeft;
	    }
	    else if(diff>-157.5 && diff<-112.5){
		return _imageFL;
	    }
	    else if(diff>22.5 && diff<67.5){
		return _imageBR;
	    }
	    else if(diff>=67.5 && diff<=112.5){
		return _imageRight;
	    }
	    else if(diff>112.5 && diff<157.5){
		return _imageFR;
	    }
	    else{
		return _imageFront;
	    }
	}
}
