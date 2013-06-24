package com.badlogic.drop.view;

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
	
	private BucketController bucketController;
	
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
	    
	    if (bucketController.getCurrentBucket() > BucketController.UNTOUCHED_BUCKET_ADDRESS) {
	    	final Bucket b = bucketController.getBuckets().get(bucketController.getCurrentBucket());
	    	batch.draw(b.getImage(), b.getPosX(), b.getPosY(), b.getDimX(), b.getDimY());
	    	bucketController.getBuckets().remove(bucketController.getCurrentBucket());
	    	bucketController.getBuckets().add(b);
	    	bucketController.setCurrentBucket(bucketController.getBuckets().size() - 1);
	    }
	    for (int i = 0; i < bucketController.getBuckets().size(); i++) {
	    	batch.draw(bucketController.getBuckets().get(i).getImage(), bucketController.getBuckets().get(i).getPosX(), bucketController.getBuckets().get(i).getPosY(), bucketController.getBuckets().get(i).getDimX(), bucketController.getBuckets().get(i).getDimY());
	    }
	    batch.end();
	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
	    
	    batch = new SpriteBatch();
	    
	    bucketController = new BucketController(this, camera);
	    
	    final int x = (CAMERA_WIDTH / 2) - (CAMERA_HEIGHT / 2);
	    final int y = 100;
	    final int w = 96;
	    final int h = 96;
	    
	    for (int i = 0; i < NUM_OF_BUCKETS; i++) {
	    	int x2 = x * (i + 1);
	    	Bucket bucket = new Bucket(i, x2, y, w, h);
	    	bucketController.getBuckets().add(bucket);
	    }
	    
	    lineRenderer = new ShapeRenderer();
	}
	
	@Override
	public void dispose() {
		for (int i = 0; i < bucketController.getBuckets().size(); i++) {
			bucketController.getBuckets().get(i).disposeTexture();
		}
	    batch.dispose();
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
