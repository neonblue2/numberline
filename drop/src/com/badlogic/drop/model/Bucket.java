package com.badlogic.drop.model;

import com.badlogic.drop.model.Value.Equality;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bucket implements Comparable<Bucket> {
	private Texture image;
	private final Vector2 pos;
	private final Vector2 dim;
	
	private boolean inInvalidArea;
	
	public final Value value;
	
	private boolean inPosition;
	
	public Bucket(Value value, int x, int y, int w, int h) {
		image = new Texture(Gdx.files.internal("bucket.png"));
		this.value = value;
		pos = new Vector2(x, y);
		dim = new Vector2(w, h);
		inInvalidArea = false;
		inPosition = false;
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
	
	public boolean isInInvalidArea() {
		return inInvalidArea;
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
	
	public void setInvalidArea(boolean b) {
		inInvalidArea = b;
	}
	
	public void setIsInPosition() {
		inPosition = true;
	}
	
	public boolean isInPosition() {
		return inPosition;
	}

	@Override
	public int compareTo(Bucket b) {
		int returnValue = 0;
		if (value.compare(b.value) == Equality.LESS) {
			returnValue = -1;
		} else if (value.compare(b.value) == Equality.GREATER) {
			returnValue = 1;
		}
		return returnValue;
	}
}
