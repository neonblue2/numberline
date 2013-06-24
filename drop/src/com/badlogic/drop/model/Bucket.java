package com.badlogic.drop.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bucket {
	private Texture image;
	private final Vector2 pos;
	private final Vector2 dim;
	
	private int value;
	
	public Bucket(int x, int y, int w, int h) {
		image = new Texture(Gdx.files.internal("bucket.png"));
		pos = new Vector2(x, y);
		dim = new Vector2(w, h);
	}
	
	public void disposeTexture() {
		image.dispose();
	}
	
	public Texture getImage() {
		return image;
	}

	public float getPosX() {
		return pos.x;
	}
	
	public float getPosY() {
		return pos.y;
	}

	public float getDimX() {
		return dim.x;
	}
	
	public float getDimY() {
		return dim.y;
	}
	
	public void setImage(final Texture image) {
		disposeTexture();
		this.image = image;
	}
	
	public void setPosX(final float x) {
		pos.x = x;
	}
	
	public void setPosY(final float y) {
		pos.y = y;
	}

	public void setDimW(final float w) {
		dim.x = w;
	}
	
	public void setDimH(final float h) {
		dim.y = h;
	}
}
