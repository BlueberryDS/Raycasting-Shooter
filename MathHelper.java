public class MathHelper //I don't feel like documenting this...its pretty self explanitory
{
	public static int catSeg(float value, float segLength){
		return (int)(value / segLength);
	}

	public static float smallNumber(float a, float b){
		if (a<b){
			return a;
		}
		return b;
	}

	public static float distance(float x, float y){
		return (float)(Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));
	}

	public static float normalizeAngle(float angle){
		while (angle < 0){
			angle += 360;
		}
		while (angle > 360){
			angle -= 360;
		}
		return angle;
	}

	public static float angleTo(float x, float y, float x2, float y2){
		float xOffset = x2 - x;
		float yOffset = y2 - y;
		return MathHelper.convertCompToAngle(xOffset, yOffset);
	}

	public static float convertCompToAngle(float rise, float run){
		float angle;
		if (run != 0){
			angle = (float)(Math.toDegrees(Math.atan(rise / run)));
			if(run < 0){
				angle += 180;
			}
		}
		else if (rise > 0)
			angle = 90;
		else
			angle = 270;

		angle = MathHelper.normalizeAngle(angle);

		return angle;
	}



	public static float diffAngle(float angle1,float angle2){
	       
	float diffangle = angle1-angle2;

	while(diffangle>180){
	    diffangle=diffangle-360;
	}
	while(diffangle<-180){
	    diffangle=diffangle+360;
	}
	return diffangle;
	}
}
