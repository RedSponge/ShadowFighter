package com.redsponge.shadows;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.redsponge.shadows.screen.FightScreen;

public class ShadowGame extends Game {

	private SpriteBatch batch;
	private ShapeRenderer renderer;

	public FightScreen screen;

	@Override
	public void create() {
		INSTANCE = this;

		batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		screen = new FightScreen(batch, renderer);
		setScreen(screen);
	}

	@Override
	public FightScreen getScreen() {
		return screen;
	}

	public static ShadowGame INSTANCE;
}
