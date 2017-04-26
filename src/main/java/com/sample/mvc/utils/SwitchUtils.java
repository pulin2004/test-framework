package com.sample.mvc.utils;

public class SwitchUtils {

	public static int SwitchSample(int i,int b)
	{
		int c = 0;
		switch (b) {
		case 0:
			c = 0;
			break;
		case 1:
			c =i;
			break;
		case 2:
			c = i*i;
			break;
		case 3:
			c = i*i*i;
			break;
		case 4:
			c = i*i*i*i;
			break;
		case 5:
			c = (int) Math.pow(i,b);
		default:
			c = (int) Math.pow(i,b);
			break;
		}
		return c ;
	}
}
