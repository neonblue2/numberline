package com.badlogic.drop.view;

import java.util.ArrayList;

import com.badlogic.drop.controller.BucketController;
import com.badlogic.drop.model.Bucket;
import com.badlogic.drop.model.Line;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen implements Screen {
	private final int CAMERA_WIDTH = 800;
	private final int CAMERA_HEIGHT = 480;
	
	private final int NUM_OF_BUCKETS = 3;
	
	private ArrayList<Bucket> buckets;
	private BucketController bucketController;
	private int currentBucket;
	
	private OrthographicCamera camera;
	private ShapeRenderer lineRenderer;
	private SpriteBatch batch;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
	    lineRenderer.setProjectionMatrix(camera.combined);
		lineRenderer.begin(ShapeType.Line);
		lineRenderer.setColor(Line.colour);
		lineRenderer.line(Line.x1, Line.y, Line.x2, Line.y);
		lineRenderer.end();
	    
	    batch.setProjectionMatrix(camera.combined);		// Use the coordinate system specified by the camera
	    batch.begin();
	    for (int i = 0; i < buckets.size(); i++) {
	    	if (i != currentBucket) {
	    		batch.draw(buckets.get(i).getImage(), buckets.get(i).getPosX(), buckets.get(i).getPosY(), buckets.get(i).getDimX(), buckets.get(i).getDimY());
	    	}
	    }
	    if (currentBucket > BucketController.UNTOUCHED_BUCKET_ADDRESS) {
	    	final Bucket b = buckets.get(currentBucket);
	    	batch.draw(b.getImage(), b.getPosX(), b.getPosY(), b.getDimX(), b.getDimY());
	    	buckets.remove(currentBucket);
	    	buckets.add(b);
	    	currentBucket = buckets.size() - 1;
	    	bucketController.setCurrentBucket(currentBucket);
	    }
	    batch.end();
	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
	    
	    batch = new SpriteBatch();
	    
	    buckets = new ArrayList<Bucket>();
	    bucketController = new BucketController(this, camera, buckets);
	    currentBucket = BucketController.UNTOUCHED_BUCKET_ADDRESS;
	    
	    final int x = (CAMERA_WIDTH / 2) - (CAMERA_HEIGHT / 2);
	    final int y = 100;
	    final int w = 96;
	    final int h = 96;
	    
	    for (int i = 0; i < NUM_OF_BUCKETS; i++) {
	    	int x2 = x * (i + 1);
	    	Bucket bucket = new Bucket(i, x2, y, w, h);
	    	buckets.add(bucket);
	    }
	    
	    lineRenderer = new ShapeRenderer();
	}
	
	@Override
	public void dispose() {
		for (int i = 0; i < buckets.size(); i++) {
			buckets.get(i).disposeTexture();
		}
	    batch.dispose();
	}
	
	public void setCurrentBucket(final int currentBucket) {
		this.currentBucket = currentBucket;
	}

	@Override
	public void resize(int width, int height) {}
	
	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}
}
