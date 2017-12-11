package comdxc.vpc.automation.roughWork;

import java.util.Random;

public class replaceSpace {

	public static void main(String[] args) {
		int a =randInt(1,5);
		System.out.println(a);

	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

}
