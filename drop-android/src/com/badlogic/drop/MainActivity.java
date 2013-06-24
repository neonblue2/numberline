package com.badlogic.drop;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;				// Use OpenGL ES 2 to allow images without a power-of-2 size
        cfg.useAccelerometer = false;	// Disable the accelerometer to conserve battery
        cfg.useCompass = false;			// Disable the compass to conserve battery
          
        initialize(new Drop(), cfg);
    }
}
