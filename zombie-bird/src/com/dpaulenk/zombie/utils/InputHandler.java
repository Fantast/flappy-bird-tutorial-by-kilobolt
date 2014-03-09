package com.dpaulenk.zombie.utils;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.dpaulenk.zombie.model.Bird;

public class InputHandler extends InputAdapter {
    private final Bird bird;

    public InputHandler(Bird bird) {
        this.bird = bird;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        bird.onClick();
        return true;
    }
}
