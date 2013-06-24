package com.badlogic.drop.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class Line {
	public static final Color colour = new Color(1, 1, 1, 0);
	public static final int x1 = 50;
	public static final int x2 = 750;
	public static final int y = 50;
	
	private int numbucketsOnLine;
	
	private ArrayList<Bucket> bucketsOnLine;
	
	public Line() {
		numbucketsOnLine = 0;
		
		bucketsOnLine = new ArrayList<Bucket>();
	}
	
	public int getNumBucketsOnLine() {
		return numbucketsOnLine;
	}
	
	public void addBucket(Bucket bucket) {
		numbucketsOnLine++;
		
		bucketsOnLine.add(bucket);
	}
}
