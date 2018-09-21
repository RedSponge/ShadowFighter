package com.redsponge.shadows.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameScreen extends ScreenAdapter {

    protected SpriteBatch batch;
    protected ShapeRenderer renderer;

    public GameScreen(SpriteBatch batch, ShapeRenderer renderer) {
        this.batch = batch;
        this.renderer = renderer;
    }

    @Override
    public void render(float delta) {
        update(delta);
        render();
    }

    public abstract void update(float delta);

    public abstract void render();
}
