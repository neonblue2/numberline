package com.badlogic.drop.controller;

import java.util.ArrayList;

import com.badlogic.drop.model.Bucket;
import com.badlogic.drop.model.Line;
import com.badlogic.drop.view.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class BucketController implements InputProcessor {
	public static final int UNTOUCHED_BUCKET_ADDRESS = -1;
	
	private GameScreen gameScreen;
	
	private OrthographicCamera camera;
	
	private ArrayList<Bucket> buckets;
	private int currentBucket;
	
	private Line line;
	
	public BucketController(GameScreen gameScreen, OrthographicCamera camera, ArrayList<Bucket> buckets) {
		this.gameScreen = gameScreen;
		
		this.camera = camera;
		
		this.buckets = buckets;
		currentBucket = UNTOUCHED_BUCKET_ADDRESS;
		
		line = new Line();
		
		Gdx.input.setInputProcessor(this);
	}
	
	private void moveCurrentBucket(final Vector3 touchPos) {
		if (line.isOnLine(buckets.get(currentBucket))) {
    		if (touchPos.x < Line.x1) {
    			touchPos.x = Line.x1;
    		} else if (touchPos.x > Line.x2) {
    			touchPos.x = Line.x2;
    		}
    	} else {
    		buckets.get(currentBucket).setPosY(touchPos.y - buckets.get(currentBucket).getDimY() / 2);
    		// Where the buckets stick
    		if (isOnLine(buckets.get(currentBucket)) && line.addBucket(buckets.get(currentBucket))) {
    			buckets.get(currentBucket).setPosY(Line.y - buckets.get(currentBucket).getDimY() / 2);
    		}
    	}
		buckets.get(currentBucket).setPosX(touchPos.x - buckets.get(currentBucket).getDimX() / 2);
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
				currentBucket = i;
				gameScreen.setCurrentBucket(currentBucket);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		currentBucket = UNTOUCHED_BUCKET_ADDRESS;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (currentBucket > UNTOUCHED_BUCKET_ADDRESS) {
    		Vector3 touchPos = new Vector3(screenX, screenY, 0);
        	camera.unproject(touchPos);
    		moveCurrentBucket(touchPos);
    	}
		return true;
	}
	
	public void setCurrentBucket(final int currentBucket) {
		this.currentBucket = currentBucket;
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
