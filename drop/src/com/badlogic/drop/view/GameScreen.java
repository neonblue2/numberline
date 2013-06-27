package com.badlogic.drop.view;

import java.util.Random;

import com.badlogic.drop.controller.BucketController;
import com.badlogic.drop.model.Bucket;
import com.badlogic.drop.model.Line;
import com.badlogic.drop.model.Value;
import com.badlogic.drop.model.Value.Type;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	
	private final BitmapFont valueText = new BitmapFont();
	
	public GameScreen() {
		valueText.setColor(Color.RED);
	}
	
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
		
		if (bucketController.getCurrentBucketIndex() > BucketController.UNTOUCHED_BUCKET_ADDRESS) {
	    	final Bucket b = bucketController.getCurrentBucket();
	    	bucketController.getBuckets().remove(bucketController.getCurrentBucketIndex());
	    	bucketController.getBuckets().add(b);
	    	bucketController.setCurrentBucketIndex(bucketController.getBuckets().size() - 1);
	    }
	    
	    batch.setProjectionMatrix(camera.combined);		// Use the coordinate system specified by the camera
	    batch.begin();
	    for (final Bucket b : bucketController.getBuckets()) {
	    	batch.draw(b.getImage(), b.getPosX(), b.getPosY(), b.getDimX(), b.getDimY());
	    	final float xPos = b.getPosX() + (b.getDimX() / 2);
    		final float yPos = b.getPosY() + (b.getDimY() / 2);
	    	if (b.value.type != Type.FRACTION) {
	    		valueText.draw(batch, b.value.toString(), xPos, yPos);
	    	} else {
	    		final String[] valueArray = b.value.toString().split("/");
	    		valueText.draw(batch, valueArray[0], xPos, yPos + (valueText.getLineHeight() / 10));
	    		valueText.draw(batch, "_", xPos, yPos);
	    		valueText.draw(batch, valueArray[1], xPos, yPos - (9 * valueText.getLineHeight() / 10));
	    	}
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
	    	// TEST CODE
	    	final Type[] valueTypes = {Type.WHOLE, Type.DECIMAL, Type.FRACTION};
	    	final int typeIndex = new Random().nextInt(3);
	    	final Type valueType = valueTypes[typeIndex];
	    	final int numerator = new Random().nextInt(10);
	    	int denominator = 1;
	    	if (valueType != Type.WHOLE) {
	    		denominator = new Random().nextInt(49) + 1;
	    	}
	    	// TODO: REMOVE TEST CODE
	    	Bucket bucket = new Bucket(new Value(valueType, numerator, denominator), x2, y, w, h);
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
