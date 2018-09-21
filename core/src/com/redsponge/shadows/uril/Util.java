package com.redsponge.shadows.uril;

import com.badlogic.gdx.utils.TimeUtils;

public class Util {

    public static float secondsSince(long nano) {
        return (TimeUtils.nanoTime() - nano) / 1000000000f;
    }

}
