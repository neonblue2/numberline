package com.badlogic.drop.controller;

import java.util.ArrayList;

import com.badlogic.drop.model.Bucket;
import com.badlogic.drop.model.Line;
import com.badlogic.drop.view.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class BucketController implements InputProcessor {
	public static final int UNTOUCHED_BUCKET_ADDRESS = -1;
	private final float[] INVALID_OLD_TOUCH_POS = {-1, -1, -1};
	
	private OrthographicCamera camera;
	
	private ArrayList<Bucket> buckets;
	private int currentBucketIndex;
	
	private Line line;
	
	private Vector3 oldTouchPos;
	
	public BucketController(GameScreen gameScreen, OrthographicCamera camera) {
		this.camera = camera;
		
		buckets = new ArrayList<Bucket>();
		currentBucketIndex = UNTOUCHED_BUCKET_ADDRESS;
		
		line = new Line();
		
		oldTouchPos = new Vector3(INVALID_OLD_TOUCH_POS);
		
		Gdx.input.setInputProcessor(this);
	}
	
	private void moveCurrentBucket(final Vector3 touchPos) {
		if (line.isOnLine(getCurrentBucket())) {
    		if (touchPos.x < Line.x1) {
    			touchPos.x = Line.x1;
    		} else if (touchPos.x > Line.x2) {
    			touchPos.x = Line.x2;
    		}
    	} else {
    		getCurrentBucket().setPosY(getCurrentBucket().getPosY() + (touchPos.y - oldTouchPos.y));
    		// Where the buckets stick
    		if (isOnLine(getCurrentBucket())) {
    			getCurrentBucket().setPosY(Line.y - getCurrentBucket().getDimY() / 2);
    			if (!line.addBucket(getCurrentBucket())) {
    				getCurrentBucket().setImage(new Texture(Gdx.files.internal("bucket.png")));
    			}
    		} else {
    			getCurrentBucket().setImage(new Texture(Gdx.files.internal("droplet.png")));
    		}
    	}
		getCurrentBucket().setPosX(getCurrentBucket().getPosX() + (touchPos.x - oldTouchPos.x));
		oldTouchPos.set(touchPos);
	}
	
	private boolean touchedBucket(final int i, final Vector3 touchPos) {
		if ((touchPos.x >= buckets.get(i).getPosX() && touchPos.x <= (buckets.get(i).getPosX() + buckets.get(i).getDimX()))) {
    		if ((touchPos.y >= buckets.get(i).getPosY() && touchPos.y <= (buckets.get(i).getPosY() + buckets.get(i).getDimY()))) {
    			return true;
    		}
		}
		return false;
	}
	
	private boolean isOnLine(final Bucket bucket) {
		return (bucket.getPosY() + bucket.getDimY() / 2) <= Line.y;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 touchPos = new Vector3(screenX, screenY, 0);
		camera.unproject(touchPos);
		for (int i = buckets.size() - 1; i >= 0; i--) {
			if (touchedBucket(i, touchPos)) {
				currentBucketIndex = i;
				oldTouchPos.set(touchPos);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		currentBucketIndex = UNTOUCHED_BUCKET_ADDRESS;
		oldTouchPos.set(INVALID_OLD_TOUCH_POS);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (currentBucketIndex > UNTOUCHED_BUCKET_ADDRESS) {
    		Vector3 touchPos = new Vector3(screenX, screenY, 0);
        	camera.unproject(touchPos);
    		moveCurrentBucket(touchPos);
    	}
		return true;
	}
	
	public ArrayList<Bucket> getBuckets() {
		return buckets;
	}
	
	public int getCurrentBucketIndex() {
		return currentBucketIndex;
	}
	
	public void setCurrentBucketIndex(final int currentBucketIndex) {
		this.currentBucketIndex = currentBucketIndex;
	}
	
	public Bucket getCurrentBucket() {
		return buckets.get(currentBucketIndex);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
