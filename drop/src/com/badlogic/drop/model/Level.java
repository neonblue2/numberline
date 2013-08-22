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
		
		final int x = (GameScreen.CAMERA_WIDTH / 2) - (GameScreen.CAMERA_HEIGHT / 2);
	    final int y = 100;
	    final int w = 72;
	    final int h = 18;
	    
	    for (int i = 0; i < GameScreen.NUM_OF_BUCKETS; i++) {
	    	int x2 = x * (i + 1);
	    	// TEST CODE
	    	final Type[] valueTypes = {Type.WHOLE, Type.DECIMAL, Type.FRACTION};
	    	final int typeIndex = new Random().nextInt(3);
	    	final Type valueType = valueTypes[typeIndex];
	    	final int numerator = new Random().nextInt(10);
	    	int denominator = 1;
	    	if (valueType != Type.WHOLE) {
	    		denominator = new Random().nextInt(49) + 1;
	    	}
	    	// TODO: REMOVE TEST CODE
	    	Bucket bucket = new Bucket(new Value(valueType, numerator, denominator), x2, y, w, h);
	    	BUCKETCONTROLLER.getBuckets().add(bucket);
	    }
	}
}
