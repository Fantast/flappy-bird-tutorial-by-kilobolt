package com.dpaulenk.zombie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.dpaulenk.zombie.render.GameRenderer;
import com.dpaulenk.zombie.model.GameWorld;
import com.dpaulenk.zombie.utils.InputHandler;

public class GameScreen extends ScreenAdapter {
    private final GameWorld world;
    private final GameRenderer renderer;

    private float runTime = 0;

    public GameScreen() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int centerY = (int) (gameHeight / 2);

        world = new GameWorld(centerY);
        renderer = new GameRenderer(world, (int) gameHeight, centerY);

        Gdx.input.setInputProcessor(new InputHandler(world.getBird()));
    }

    @Override
    public void render(float delta) {
        runTime += delta;

        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void dispose() {
        // Leave blank
    }
}