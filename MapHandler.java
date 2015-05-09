import hsa.*;

public class MapHandler
{
	private static boolean [][] _map;
	private static float _gridLength = 25;
	private static int _xSize, _ySize;
	
	//************************
	//loadMap(String file)
	//Loads the map from file
	//************************
	public static void loadMap(String file){
		TextInputFile f = new TextInputFile(file);

		_xSize = f.readInt();
		_ySize = f.readInt();
		_map = new boolean [_xSize] [_ySize];

		for (int y = 0; y < _ySize; y++){
			for(int x = 0; x < _xSize; x++){
				int value = f.readInt();
				_map [x] [y] = (value == 1);
			}
		}
	}
	
	//************************
	//isWall(int x, int y)
	//Returns if the specified cord is a wall
	//************************
	public static boolean isWall(float x, float y){
		int xGrid = MathHelper.catSeg(x, _gridLength);
		int yGrid = MathHelper.catSeg(y, _gridLength);

		if (xGrid >= 0 && xGrid < _xSize &&
				yGrid >= 0 && yGrid < _ySize ){
			return _map [xGrid] [yGrid];
		}

		return true;
	}

	//************************
	//castRay(float x, float y, float degree)
	//Returns distance to wall from x,y at angle degree.
	//************************
	public static float castRay(float x, float y, float degree){
		float [] array = new float [2];
		array [0] = 999999999;
		array [1] = 999999999;
		int count = 0;
		degree = MathHelper.normalizeAngle(degree);
		float angle = (float)Math.toRadians(degree);

		if (degree > 0 && degree < 180){
			array [count] = _castRayNorth(x, y, angle);
			count++;
		}

		if (degree > 90 && degree < 270){
			array [count] = _castRayWest(x,y,angle);
			count++;
		}

		if (degree > 180 && degree < 360){
			array [count] = _castRaySouth(x, y , angle);
			count++;
		}

		if (degree > 270 || degree < 90){
			array [count] = _castRayEast(x, y, angle);
		}

		float distance = MathHelper.smallNumber(array[0], array[1]);

		return distance;
	}
	
	//*=*=*=*=*=*=*=*=*=
	//_castRay$DIRECTION$(float x, float y, float angle)
	//Casts rays in each of the directions. Used to simplify implimentation.
	//Each only handles ray casts in its direction. Otherwise it will crash.
	//VVVVVVVVVVVVVVVVVV
	private static float _castRayNorth(float x, float y, float angle) {
		boolean isWall = false;
		float yDistence = 0, xDistence = 0;
		int xCheck = MathHelper.catSeg(x, _gridLength);;
		int yCheck = MathHelper.catSeg(y, _gridLength);

		while (isWall == false) {
			yCheck--;
			yDistence = y - ((yCheck + 1) * _gridLength);

			if (angle != Math.PI * 0.5) {
				xDistence = yDistence / (float) Math.tan(angle);
				xCheck = MathHelper.catSeg(x + xDistence, _gridLength);
				if(xCheck<0 || xCheck>=_xSize){
				    return 999999999;
				}
			}

			isWall = _map[xCheck][yCheck];
		}

		float distance = MathHelper.distance(xDistence, yDistence);

		return distance;
	}

	private static float _castRaySouth(float x, float y, float angle) {
		boolean isWall = false;
		float yDistence = 0, xDistence = 0;
		int xCheck = MathHelper.catSeg(x, _gridLength);
		int yCheck = MathHelper.catSeg(y, _gridLength);

		while (isWall == false) {
			yCheck++;
			yDistence = y - (yCheck * _gridLength);

			if (angle != Math.PI * 1.5) {
				xDistence = yDistence / (float) Math.tan(angle);
				xCheck = MathHelper.catSeg(x + xDistence, _gridLength);
				if(xCheck<0 || xCheck>=_xSize){
				    return 999999999;
				}
			}

			isWall = _map[xCheck][yCheck];
		}

		float distance = MathHelper.distance(xDistence, yDistence);

		return distance;
	}

	private static float _castRayWest(float x, float y, float angle) {
		boolean isWall = false;
		float yDistence = 0, xDistence = 0;
		int xCheck = MathHelper.catSeg(x, _gridLength);
		int yCheck = MathHelper.catSeg(y, _gridLength);

		while (isWall == false) {
			xCheck--;
			xDistence = ((xCheck + 1) * _gridLength) - x;

			if (angle != Math.PI) {
				yDistence = -xDistence * (float) Math.tan(angle);
				yCheck = MathHelper.catSeg(y + yDistence, _gridLength);
				if(yCheck<0 || yCheck>=_ySize){
				    return 999999999;
				}
			}

			isWall = _map[xCheck][yCheck];
		}

		float distance = MathHelper.distance(xDistence, yDistence);

		return distance;
	}

	private static float _castRayEast(float x, float y, float angle) {
		boolean isWall = false;
		float yDistence = 0, xDistence = 0;
		int xCheck = MathHelper.catSeg(x, _gridLength);
		int yCheck = MathHelper.catSeg(y, _gridLength);

		while (isWall == false) {
			xCheck++;
			xDistence = (xCheck * _gridLength) - x;

			if (angle != 0) {
				yDistence = -xDistence * (float) Math.tan(angle);
				yCheck = MathHelper.catSeg(y + yDistence, _gridLength);
				if(yCheck<0 || yCheck>=_ySize){
				    return 999999999;
				}
			}

			isWall = _map[xCheck][yCheck];
		}

		float distance = MathHelper.distance(xDistence, yDistence);

		return distance;
	}
	//^^^^^^^^^^^^^^^^^^
}
