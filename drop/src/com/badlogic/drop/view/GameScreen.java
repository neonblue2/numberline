package com.badlogic.drop.view;

import com.badlogic.drop.controller.BucketController;
import com.badlogic.drop.model.Bucket;
import com.badlogic.drop.model.Level;
import com.badlogic.drop.model.Line;
import com.badlogic.drop.model.Value.Type;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen implements Screen {
	public static final int NUM_OF_BUCKETS = 4;
	
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	
	private BucketController bucketController;
	
	private OrthographicCamera camera;
	private ShapeRenderer lineRenderer;
	private SpriteBatch batch;
	
	private Level[] levels;
	private int currentLevel;
	
	private final BitmapFont valueText = new BitmapFont();
	
	private final Texture trainFront = new Texture(Gdx.files.internal("front.png"));
	private final Texture trainBack = new Texture(Gdx.files.internal("back.png"));
			
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
	    final Line line = bucketController.getLine();
	    
	    if (bucketController.isLevelEnd() && line.x2+86 >= 0) {
	    	line.x1 -= 10;
	    	line.x2 -= 10;
	    } else if (line.x2+86 < 0) {
	    	// Reset
	    	dispose();
	    	if (currentLevel < levels.length) {
	    		bucketController.newLevelStarted();
		    	line.reset();
		    	currentLevel++;
		    	levels[currentLevel].start();
	    	} else {
	    		// Game over
	    		// Show end screen
	    	}
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
	    batch.setColor(Color.WHITE);
	    batch.draw(trainFront, line.x1-86, line.y-9, 86, 18);
	    batch.draw(trainBack, line.x2, line.y-9, 86, 18);
	    for (final Bucket b : bucketController.getBuckets()) {
	    	// Set the filter colour of the bucket to red if in an incorrect position
	    	if (b.isInInvalidArea()) {
	    		batch.setColor(Color.RED);
	    	}
	    	// Set the position of the text on the bucket
	    	float textXPos = b.getPosX() + (b.getDimX() / 2);
    		final float textYPos = b.getPosY() + (b.getDimY() / 2);
    		// Alter the bucket and text x positions if the game has ended
	    	if (bucketController.isLevelEnd() && line.x2 >= 0) {
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
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
	    
	    batch = new SpriteBatch();
	    
	    bucketController = new BucketController(camera);
	    
	    lineRenderer = new ShapeRenderer();
	    
	    valueText.setColor(Color.RED);
	    
	    levels = new Level[15];
	    Type type = Type.WHOLE;
	    for (int i = 0; i < levels.length; i++) {
	    	if (i < levels.length/3) {
	    		type = Type.WHOLE;
	    	} else if (i < 2 * levels.length/3) {
	    		type = Type.DECIMAL;
	    	} else if (i < levels.length) {
	    		type = Type.FRACTION;
	    	}
	    	levels[i] = new Level(type, bucketController);
	    }
	    levels[0].start();
	    currentLevel = 0;
	}
	
	@Override
	public void dispose() {
		for (int i = 0; i < bucketController.getBuckets().size(); i++) {
			bucketController.getBuckets().get(i).disposeTexture();
		}
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
