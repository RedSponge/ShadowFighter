package com.redsponge.shadows.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.redsponge.shadows.ShadowGame;
import com.redsponge.shadows.uril.Constants;

import java.util.Random;

public class Enemy extends Entity {

    private Vector2 pos;
    private Vector2 vel;
    private boolean shadow;
    private double counter;
    private float speed;
    private boolean dead;
    private Random random;

    public Enemy() {
        dead = false;
        counter = 0;
        shadow = false;
        random = new Random();

        boolean left = random.nextBoolean();
        shadow = random.nextBoolean();

        speed = (float) (Math.random() * 50) + 25;

        if(left) {
            pos = new Vector2(0, 0);
            vel = new Vector2(speed, 0);
        }
        else {
            pos = new Vector2(Constants.WORLD_SIZE.x, 0);
            vel = new Vector2(-speed, 0);
        }
        pos.y = Constants.FLOOR_LEVEL + Constants.ENEMY_HEIGHT / 2;
    }

    @Override
    public void update(float delta) {
        counter+=0.1f;
        vel.y = (float) Math.sin(counter) * 10;
        pos.mulAdd(vel, delta);

        Rectangle hitbox = new Rectangle(pos.x - Constants.ENEMY_WIDTH / 2, pos.y - Constants.ENEMY_HEIGHT / 2, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
        if(hitbox.overlaps(ShadowGame.INSTANCE.screen.getPlayer().getHitbox())) {
            ShadowGame.INSTANCE.screen.gameover = true;
        } else if(hitbox.overlaps(ShadowGame.INSTANCE.screen.getPlayer().getHit()) && shadow == ShadowGame.INSTANCE.screen.getPlayer().isShadow()) {
            dead = true;
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(shadow? Color.GRAY:Color.YELLOW);
        renderer.rect(pos.x - Constants.ENEMY_WIDTH / 2, pos.y - Constants.ENEMY_HEIGHT / 2, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
        renderer.set(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.rect(pos.x - Constants.ENEMY_WIDTH / 2, pos.y - Constants.ENEMY_HEIGHT / 2, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT);
        renderer.end();
    }

    public boolean isDead() {
        return dead;
    }
}
