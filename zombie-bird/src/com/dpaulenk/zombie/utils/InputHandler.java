package com.dpaulenk.zombie.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.dpaulenk.zombie.model.Bird;
import com.dpaulenk.zombie.model.GameWorld;
import com.dpaulenk.zombie.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class InputHandler extends InputAdapter {
    private final GameWorld world;
    private final Bird bird;

    private List<SimpleButton> menuButtons;

    private SimpleButton playButton;

    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        bird = world.getBird();

        int centerY = world.getCenterY();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(
                136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                centerY + 50,
                29,
                16,
                AssetLoader.playButtonUp,
                AssetLoader.playButtonDown
        );
        menuButtons.add(playButton);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (world.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
        } else if (world.isReady()) {
            world.start();
            bird.onClick();
        } else if (world.isRunning()) {
            bird.onClick();
        }

        if (world.isGameOver() || world.isHighScore()) {
            world.restart();
        }
    

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (world.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                world.ready();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {

        // Can now use Space Bar to play the game
        if (keycode == Input.Keys.SPACE) {

            if (world.isMenu()) {
                world.ready();
            } else if (world.isReady()) {
                world.start();
            }

            bird.onClick();

            if (world.isGameOver() || world.isHighScore()) {
                world.restart();
            }
        }

        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }
}
