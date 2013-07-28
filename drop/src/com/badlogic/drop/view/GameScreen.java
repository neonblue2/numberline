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
	public final int NUM_OF_BUCKETS = 4;
	
	private BucketController bucketController;
	
	private OrthographicCamera camera;
	private ShapeRenderer lineRenderer;
	private SpriteBatch batch;
	
	private final BitmapFont valueText = new BitmapFont();
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
	    final Line line = bucketController.getLine();
	    
	    if (bucketController.hasGameEnded() && line.x2 >= 0) {
	    	line.x1 -= 10;
	    	line.x2 -= 10;
	    } else if (line.x2 <= 0) {
	    	// Reset
	    	dispose();
	    	bucketController.getBuckets().clear();
	    	show();
	    }
	    
	    lineRenderer.setProjectionMatrix(camera.combined);
		lineRenderer.begin(ShapeType.Line);
		lineRenderer.setColor(line.colour);
		lineRenderer.line(line.x1, line.y, line.x2, line.y);
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
	    	// Set the filter colour of the bucket
	    	if (b.isInInvalidArea()) {
	    		batch.setColor(Color.RED);
	    	} else {
	    		batch.setColor(Color.WHITE);
	    	}
	    	// Set the position of the text on the bucket
	    	float textXPos = b.getPosX() + (b.getDimX() / 2);
    		final float textYPos = b.getPosY() + (b.getDimY() / 2);
    		// Alter the bucket and text x positions if the game has ended
	    	if (bucketController.hasGameEnded() && line.x2 >= 0) {
		    	b.setPosX(b.getPosX()-10);
		    	textXPos -= 10;
		    }
	    	// Draw the bucket and its text
	    	batch.draw(b.getImage(), b.getPosX(), b.getPosY(), b.getDimX(), b.getDimY());
	    	if (b.value.type != Type.FRACTION) {
	    		valueText.draw(batch, b.value.toString(), textXPos, textYPos);
	    	} else {
	    		final String[] valueArray = b.value.toString().split("/");
	    		valueText.draw(batch, valueArray[0], textXPos, textYPos + (valueText.getLineHeight() / 10));
	    		valueText.draw(batch, "_", textXPos, textYPos);
	    		valueText.draw(batch, valueArray[1], textXPos, textYPos - (9 * valueText.getLineHeight() / 10));
	    	}
	    }
	    batch.end();
	}

	@Override
	public void show() {
		final int CAMERA_WIDTH = 800;
		final int CAMERA_HEIGHT = 480;
		
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
	    
	    batch = new SpriteBatch();
	    
	    bucketController = new BucketController(this, camera);
	    
	    final int x = (CAMERA_WIDTH / 2) - (CAMERA_HEIGHT / 2);
	    final int y = 100;
	    final int w = 72;
	    final int h = 75;
	    
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
	    
	    valueText.setColor(Color.RED);
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
