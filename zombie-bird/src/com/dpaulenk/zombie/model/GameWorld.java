package com.dpaulenk.zombie.model;

import com.badlogic.gdx.math.Rectangle;
import com.dpaulenk.zombie.utils.ScrollHandler;

public class GameWorld {
    private final Bird bird;
    private final ScrollHandler scoller;

    public GameWorld(int centerY) {
        bird = new Bird(33, centerY - 5, 17, 12);
        scoller = new ScrollHandler(centerY + 66);
    }

    public void update(float delta) {
        bird.update(delta);
        scoller.update(delta);
    }

    public Bird getBird() {
        return bird;
    }

    public ScrollHandler getScoller() {
        return scoller;
    }
}
