package com.badlogic.drop.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class Line {
	public final Color colour = new Color(1, 1, 1, 0);
	private int default_x1 = 50+86;
	private int default_x2 = 750-86;
	public int x1 = default_x1;
	public int x2 = default_x2;
	public final int y = 50;
	
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
			final Bucket bucketOnLine = bucketsOnLine.get(i);
			if (bucket.getPosX() < bucketOnLine.getPosX()) {
				// To the left
				switch (bucket.value.compare(bucketOnLine.value)) {
				case LESS:
				case EQUAL:
					returnValue = true;
					break;
				case GREATER:
					return false;
				default:
					break;
				}
			} else if (bucket.getPosX() > bucketOnLine.getPosX()) {
				// To the right
				switch (bucket.value.compare(bucketOnLine.value)) {
				case LESS:
					return false;
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
	
	public boolean isStuckOnLine(Bucket bucket) {
		return bucketsOnLine.contains(bucket);
	}
	
	public void reset() {
		x1 = default_x1;
		x2 = default_x2;
		bucketsOnLine.clear();
		numbucketsOnLine = 0;
	}
}
