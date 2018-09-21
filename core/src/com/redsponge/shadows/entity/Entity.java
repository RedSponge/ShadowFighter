package com.redsponge.shadows.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Entity {

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch, ShapeRenderer renderer);

}
