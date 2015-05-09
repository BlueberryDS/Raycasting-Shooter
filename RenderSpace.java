import hsa.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;

public class RenderSpace
{
    private BufferedImage _image;
    private Graphics _g;

    private int _drawHeight, _drawWidth;

    private float _projectionDistance = 277;
    private float _wallHeight = 25;
    private int _groundHeight;

    private Charactor[] _chrList = null;
    private Charactor _viewPoint;

    private float _pixilsPerUnit = 10;

    private float _shadingFactor = 5;
    private float _shadingRate = 0.5f;
    private float _shadingOffset = 0.0f;


    private class Column{
	public Color color;
	public int columnNum;
	public int height;
	public float distance;
    }


    private Column[] _columns;

    //************************
    //CONSTRUCTOR
    //Sets the size of the renderspace, and constructs stuff.
    //************************
    public RenderSpace (int width, int height){
	_drawWidth = width; //Set Dimensions
	_drawHeight = height;

	_groundHeight = (int) (_drawHeight * (1.0f / 2.0f)); //Set Position of floor

	_columns = new Column [_drawWidth]; //instantiate the array of Columns

	for (int i = 0 ; i < _drawWidth ; i++) //Instantiate each column
	    _columns [i] = new Column ();

	_image = new BufferedImage (_drawWidth, _drawHeight, BufferedImage.TYPE_INT_RGB); //create the empty image
	_g = _image.createGraphics ();
    }


    //************************
    //addCharactor(Charactor chr)
    //Adds a charactor to the internal list of charactors to render on screen.
    //************************
    public void addCharactor (Charactor chr){
	if (_chrList == null){ //if array is non-existant, make array
	    _chrList = new Charactor [1];
	    _chrList [0] = chr;
	}
	else{ //if the array is already there, make a copy and replace
	    Charactor[] chrList = new Charactor [_chrList.length + 1];

	    for (int i = 0 ; i < _chrList.length ; i++){
		chrList [i] = _chrList [i];
	    }

	    chrList [chrList.length - 1] = chr;
	    _chrList = chrList;
	}
    }


    //************************
    //setCharactor (Charactor chr)
    //Sets the charactor who's viewpoint the screen will be drawn from.
    //************************
    public void setCharactor (Charactor chr){
	_viewPoint = chr;
    }


    //************************
    //render()
    //Renders the screen to the internal image buffer.
    //************************
    public void render (){
	_g.setColor (Color.blue);
	_g.fillRect (0, 0, _drawWidth, _drawHeight);

	_g.setColor (Color.green);
	_g.fillRect (0, _groundHeight, _drawWidth, _drawHeight);
	

	
	_renderRays ();
	_renderCharactors ();
	
	_g.setColor(Color.black);
	int radius= (int)(Math.tan(Math.toRadians(_viewPoint.getFireAngle()))*_projectionDistance);
	_g.drawOval(-radius + _drawWidth/2 , -radius + _drawHeight/2 , 2*radius , 2*radius);
	
	_g.setColor(Color.red);
	_g.drawRect(10,(int)(_drawHeight*0.3),20,(int)(_drawHeight*0.3));
	int hpheight=(int)(_viewPoint.getHpFloat()*_drawHeight*0.3);
	_g.fillRect(10,(int)(_drawHeight*0.6-hpheight),20,(int)(hpheight));
	
	if (_viewPoint.isDead()){
	    _g.setColor(Color.red);
	    _g.fillRect(0, 0, _drawWidth, _drawHeight);
	}
    }


    //************************
    //getImage()
    //Returns the internal image buffer.
    //************************
    public Image getImage (){
	return _image;
    }


    //*=*=*=*=*=*=*=*=*=*=*=*
    //_renderRays()
    //Casts the rays, and renders them to the internal buffer.
    //*=*=*=*=*=*=*=*=*=*=*=*
    private void _renderRays (){
	float x = _viewPoint.getX (); //gets the position, angle of the _viewPoint char
	float y = _viewPoint.getY ();
	float angle = _viewPoint.getAngle ();

	for (int i = 0 ; i < _drawWidth ; i++){ //loop through each column
	    _columns [i].columnNum = i;

	    float degree = angle + (float) Math.toDegrees (Math.atan ((i - (_drawWidth / 2.0f)) / _projectionDistance)); //calculate the angle through this column

	    float distance = MapHandler.castRay (x, y, degree);

	    float cleanedDistance = degree - angle; //Cleans the distance using a cos distortion removal method. Calculation split into 2 steps.
	    cleanedDistance = (float) (distance * (Math.cos (Math.toRadians (cleanedDistance))));
	    
	    _columns[i].distance = distance;
	    
	    _columns [i].height = (int) ((_projectionDistance / cleanedDistance) * _wallHeight / 2.0f); //Calculates a halfheight of wall.
	    _columns [i].color = _distanceShade (cleanedDistance);
	}

	for (int i = 0 ; i < _drawWidth ; i++){ //draws all to internal buffer
	    _g.setColor (_columns [i].color);
	    _g.drawLine (i, _drawHeight / 2, i, (_drawHeight / 2) + _columns [i].height);
	    _g.drawLine (i, _drawHeight / 2, i, (_drawHeight / 2) - _columns [i].height);
	}
    }


    //*=*=*=*=*=*=*=*=*=*=*=*
    //_distanceShade
    //Returns shaded color of wall based on distance.
    //*=*=*=*=*=*=*=*=*=*=*=*
    private Color _distanceShade (float distance){
	float b = 1.0f;

	if (distance != 0){
	    b = (float) (_shadingFactor / ((Math.pow (distance, _shadingRate)))) - _shadingOffset;
	    if (b > 1.0f){
		b = 1.0f;
	    }
	}

	return Color.getHSBColor (0.094117647f, 1.0f, b);
    }

    //*=*=*=*=*=*=*=*=*=*=*=*
    //_renderCharactors ()
    //Draws all charactors in _chrList
    //*=*=*=*=*=*=*=*=*=*=*=*
    private void _renderCharactors ()
    {
	for (int j = 0 ; j < _chrList.length ; j++){
	    float x1 = _viewPoint.getX ();
	    float y1 = _viewPoint.getY ();
	    float angle = (float) (Math.toRadians (_viewPoint.getAngle ()));
	    
	    float x2 = _chrList [j].getX ();
	    float y2 = _chrList [j].getY ();
	    
	    float distance = (float) MathHelper.distance(x2 - x1, y2 - y1);
	    
	    float proj = (float) ((x2 - x1) * Math.cos (-angle) + (y2 - y1) * Math.sin (-angle));
	    float degree = (float) (Math.atan ((y2 - y1) / (x2 - x1)));
	    Image img = _chrList [j].getImage (_viewPoint);
	    float height = Math.abs(1.1f * (_projectionDistance / proj) * _wallHeight / 2.0f);
	    float width = height * (img.getWidth(null)/(float)img.getHeight(null));
	    degree = (float) (-angle - degree);
	    if (proj > 0 && distance > 5){ //this is a duck tape fix for a memory overflow
		int left = (int) ((_projectionDistance * Math.tan (degree) + _drawWidth / 2) - (width / 2));
		img = img.getScaledInstance ((int)width, (int) height, Image.SCALE_DEFAULT);
		_g.setColor (Color.black);
		int y = (int) ((height / (_pixilsPerUnit) * -1) + _drawHeight / 2);
		if (left < _drawWidth && left + width > 0 && y < _drawHeight && y > 0)
		    _g.drawImage (img, left, y, null);
		for (int i = left ; i < left + width; i++){
		    if (i >= 0 && i < _drawWidth && _columns [i].distance < distance){
			_g.setColor (_columns [i].color);
			_g.drawLine (i, (_drawHeight / 2) - _columns [i].height, i, (_drawHeight / 2) + _columns [i].height);

		    }
		}
	    }
	}
    }
}
