package com.redsponge.shadows.uril;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public enum Controls {

    RIGHT(Input.Keys.RIGHT),
    LEFT(Input.Keys.LEFT),
    TOGGLE(Input.Keys.SPACE);

    private int[] keys;

    Controls(int... keys) {
        this.keys = keys;
    }

    public boolean isPressed() {
        for (int key : keys) {
            if(Gdx.input.isKeyPressed(key)) return true;
        }
        return false;
    }

    public boolean isReleased() {
        return !isPressed();
    }

    public boolean isJustPressed() {
        for (int key : keys) {
            if(Gdx.input.isKeyJustPressed(key)) return true;
        }
        return false;
    }
}
