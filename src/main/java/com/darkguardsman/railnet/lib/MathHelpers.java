package com.darkguardsman.railnet.lib;

/**
 * series of helper methods
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/20/18.
 */
public final class MathHelpers {

    
    /**
     * Gets the distance between 2 angles
     * @TODO check if existing function exists    
     * @param angleA
     * @param angleB
     * @return
     */
    public static Double getAngleDistance(Double angleA, Double angleB) {
		Double difference = wrap(angleA-angleB,360);
		if(difference < 180) {
			return difference;
		}else {
			return 360 - difference;
		}
    }

    /**
     * Wraps a value between zero and a limit
     *
     * @param value - value to wrap
     * @param limit - upper limit
     * @return wrapped value
     */
    public static double wrap(double value, int limit) {
      	 //Works the same as % for wrapping but keeps the decimal points
        int div = (int) value / limit;
        if (value > 0) {
        	value -= div * limit;
        } else if (value < 0) {
        	value += div * limit;
        }
        //Less then zero, wrap back around
        while (value < 0) {
        	value += limit;
        }
        return value;
    }
    public static int wrap(int value, int limit) {
    	return (int) wrap((double)value,limit);
    }

    /**
     * Position on the X axis for the given angle
     *
     * @param radius - radius of the circle
     *               * @param angle - angle, in degrees
     *               * @return position
     */
    public static double getXPositionOnCircle(double radius, double angle) {
        return radius * Math.cos(Math.toRadians(angle));
    }

    /**
     * Position on the Y axis for the given angle
     *
     * @param radius - radius of the circle
     * @param angle  - angle, in degrees
     * @return position
     */
    public static double getYPositionOnCircle(double radius, double angle) {
        return radius * Math.sin(Math.toRadians(angle));
    }


    /**
     * Get distance between angles. Order of angles does
     * not matter as it will find the shortest distance
     * in either direction around a circle.
     *
     * @param a - first angle, degrees
     * @param b - second angle, degrees
     * @return angle delta in degrees
     */
    public static int getAngleDelta(int a, int b) {
        return Math.min((a - b + 360) % 360, (b - a + 360) % 360);
    }
}
