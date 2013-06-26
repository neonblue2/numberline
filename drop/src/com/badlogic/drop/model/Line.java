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
	
	public boolean addBucket(Bucket bucket) {
		boolean returnValue = false;;
		for (int i = 0; i < bucketsOnLine.size(); i++) {
			if (bucket.getPosX() < bucketsOnLine.get(i).getPosX()) {
				// To the left
				switch (bucket.value.compare(bucketsOnLine.get(i).value)) {
				case LESS:
				case EQUAL:
					returnValue = true;
					break;
				default:
					break;
				}
			} else if (bucket.getPosX() > bucketsOnLine.get(i).getPosX()) {
				// To the right
				switch (bucket.value.compare(bucketsOnLine.get(i).value)) {
				case EQUAL:
				case GREATER:
					returnValue = true;
					break;
				default:
					break;
				}
			}
		}
		
		if (returnValue || numbucketsOnLine == 0) {
			numbucketsOnLine++;
			bucketsOnLine.add(bucket);
			returnValue = true;
		}
		
		return returnValue;
	}
	
	public boolean isOnLine(Bucket bucket) {
		return bucketsOnLine.contains(bucket);
	}
}
