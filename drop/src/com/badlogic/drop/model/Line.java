package com.badlogic.drop.model;

import com.badlogic.gdx.graphics.Color;

public class Line {
	public static final Color colour = new Color(1, 1, 1, 0);
	public static final int x1 = 50;
	public static final int x2 = 750;
	public static final int y = 50;
	
	private int bucketsOnLine;
	
	public Line() {
		bucketsOnLine = 0;
	}
}
