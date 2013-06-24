package com.badlogic.drop;

import com.badlogic.drop.view.GameScreen;
import com.badlogic.gdx.Game;

public class Drop extends Game {
	@Override
	public void create() {
	    setScreen(new GameScreen());
	}
}
