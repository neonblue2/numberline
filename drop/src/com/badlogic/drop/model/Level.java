package com.badlogic.drop.model;

import java.util.Random;

import com.badlogic.drop.controller.BucketController;
import com.badlogic.drop.model.Value.Type;
import com.badlogic.drop.view.GameScreen;

public class Level {
	private final int LEVEL_NUMBER;
	private final Type LEVEL_TYPE;
	private final BucketController BUCKETCONTROLLER;
	
	public Level(final int LEVEL_NUMBER, final Type LEVEL_TYPE, final BucketController BUCKETCONTROLLER) {
		this.LEVEL_NUMBER = LEVEL_NUMBER;
		this.LEVEL_TYPE = LEVEL_TYPE;
		this.BUCKETCONTROLLER = BUCKETCONTROLLER;
	}
	
	public void start() {
		BUCKETCONTROLLER.getBuckets().clear();
		
		//final int x = (GameScreen.CAMERA_WIDTH / 2) - (GameScreen.CAMERA_HEIGHT / 2);
		int x = (int) Bucket.width;
	    final int y = 125;
	    
	    int numMod = 0;
	    int denMod = 0;
	    
	    // TODO: Fix this. Should not depend on 15 levels.
	    switch (LEVEL_NUMBER) {
		case 1:
		case 2:
		case 6:
		case 7:
		case 11:
		case 12:
			// Easy
			numMod = 10;
			denMod = 9;
			break;
		case 3:
		case 4:
		case 8:
		case 9:
		case 13:
		case 14:
			// Medium
			numMod = 20;
			denMod = 24;
			break;
		case 5:
		case 10:
		case 15:
			// Hard
			numMod = 30;
			denMod = 49;
			break;
		default:
			break;
		}
	    
	    for (int i = 0; i < GameScreen.NUM_OF_BUCKETS; i++) {
	    	final int numerator = new Random().nextInt(numMod);
	    	int denominator = 1;
	    	if (LEVEL_TYPE != Type.WHOLE) {
	    		denominator = new Random().nextInt(denMod) + 1;
	    	}
	    	Bucket bucket = new Bucket(new Value(LEVEL_TYPE, numerator, denominator), x, y);
	    	BUCKETCONTROLLER.getBuckets().add(bucket);
	    	x += Bucket.width + 75;
	    }
	}
}
