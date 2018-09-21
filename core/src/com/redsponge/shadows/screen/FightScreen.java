package com.redsponge.shadows.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.redsponge.shadows.entity.Enemy;
import com.redsponge.shadows.entity.Entity;
import com.redsponge.shadows.entity.Player;
import com.redsponge.shadows.uril.Constants;
import com.redsponge.shadows.uril.Util;

public class FightScreen extends GameScreen {

    private Player player;
    private DelayedRemovalArray<Entity> entities;
    private FitViewport viewport;
    private OrthographicCamera camera;

    private int difficulty;
    private float spawnRate;
    private long startTime;
    private BitmapFont font;
    public boolean gameover;

    private long lastSpawnTime;

    public FightScreen(SpriteBatch batch, ShapeRenderer renderer) {
        super(batch, renderer);
        difficulty = 0;

        lastSpawnTime = TimeUtils.nanoTime();

        viewport = new FitViewport(Constants.WORLD_SIZE.x, Constants.WORLD_SIZE.y);
        camera = (OrthographicCamera) viewport.getCamera();
        font = new BitmapFont();
        init();
    }

    public void init() {
        player = new Player();
        entities = new DelayedRemovalArray<Entity>();
        entities.add(new Enemy());
        entities.add(new Enemy());
    }

    @Override
    public void update(float delta) {
        if(gameover) return;
        player.update(delta);
        for (Entity entity : entities) {
            entity.update(delta);
            if(((Enemy)entity).isDead()) {
                entities.removeValue(entity, true);
            }
        }

        spawnRate = 5f / difficulty;

        if(Util.secondsSince(lastSpawnTime) > spawnRate) {
            entities.add(new Enemy());
            lastSpawnTime = TimeUtils.nanoTime();
        }

        if(Util.secondsSince(startTime) > 10) {
            System.out.println("DIFF UP");
            difficulty++;
            startTime = TimeUtils.nanoTime();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();

        batch.setProjectionMatrix(camera.combined);
        renderer.setProjectionMatrix(camera.combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0, 0.5f, 1f, 1);
        renderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        renderer.setColor(Color.BLACK);
        renderer.rectLine(0, Constants.FLOOR_LEVEL, viewport.getWorldWidth(), Constants.FLOOR_LEVEL, 20);
        renderer.end();


        player.render(batch, renderer);

        for (Entity entity : entities) {
            entity.render(batch, renderer);
        }

        batch.begin();
        font.draw(batch, "Difficulty: " + difficulty, 0, 500);
        batch.end();

        if(gameover) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(0.2f, 0.2f, 0.2f, 0.5f);
            renderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            renderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();
            font.getData().setScale(4);
            font.draw(batch, "GAME OVER!", viewport.getWorldWidth() / 2 - 200, viewport.getWorldHeight() / 2);
            font.getData().setScale(1);
            batch.end();
        }
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
