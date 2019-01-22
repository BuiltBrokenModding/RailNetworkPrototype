package com.darkguardsman.railnet.api.math;

public class Helper {
	public static Double getAngleDistance(Double angleA, Double angleB) {
		Double difference = ((angleA - angleB) + 360) % 360;
		if(difference < 180) {
			return difference;
		}else {
			return 360 - difference;
		}
	}
}
