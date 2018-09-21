package com.redsponge.shadows.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.redsponge.shadows.uril.Constants;
import com.redsponge.shadows.uril.Controls;
import com.redsponge.shadows.uril.Util;

public class Player extends Entity {

    private Vector2 pos;
    private Rectangle hit, hitbox;
    private long hitTimer;
    private boolean shadow;

    public Player() {
        hitbox = new Rectangle(Constants.WORLD_SIZE.x / 2 - Constants.PLAYER_WIDTH / 2, Constants.FLOOR_LEVEL, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        hit = new Rectangle(0, 0, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        shadow = false;
    }

    @Override
    public void update(float delta) {
        if(Controls.LEFT.isJustPressed()) {
            hit.set(hitbox.x - Constants.PLAYER_WIDTH / 2, hitbox.y, hitbox.width, hitbox.height);
            hitTimer = TimeUtils.nanoTime();
        }
        if(Controls.RIGHT.isJustPressed()) {
            hit.set(hitbox.x + Constants.PLAYER_WIDTH / 2, hitbox.y, hitbox.width, hitbox.height);
            hitTimer = TimeUtils.nanoTime();
        }
        if(Controls.TOGGLE.isJustPressed()) shadow = !shadow;

        if(Util.secondsSince(hitTimer) > 0.1f) {
            hit.setPosition(-1000, -1000);
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(shadow?Color.DARK_GRAY:Color.YELLOW);
        renderer.rect(hit.x, hit.y, hit.width, hit.height);
        renderer.setColor(shadow?Color.GRAY:Color.GREEN);
        renderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        renderer.setColor(Color.RED);
        renderer.end();
    }

    public Rectangle getHit() {
        return hit;
    }

    public boolean isShadow() {
        return shadow;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
