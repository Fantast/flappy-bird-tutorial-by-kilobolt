package com.dpaulenk.zombie.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dpaulenk.zombie.ZBGame;
import com.dpaulenk.zombie.tween.SpriteAccessor;
import com.dpaulenk.zombie.utils.AssetLoader;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class SplashScreen extends ScreenAdapter {
    private final ZBGame game;
    private TweenManager manager;
    private SpriteBatch batcher;
    private Sprite sprite;

    public SplashScreen(ZBGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        sprite = new Sprite(AssetLoader.logo);
        sprite.setColor(1, 1, 1, 0);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float desiredWidth = width * .7f;
        float scale = desiredWidth / sprite.getWidth();

        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setPosition(
                (width / 2) - (sprite.getWidth() / 2),
                (height / 2) - (sprite.getHeight() / 2)
        );

        setupTween();

        batcher = new SpriteBatch();
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen());
            }
        };

        Tween.to(sprite, SpriteAccessor.ALPHA, .8f)
                .target(1)
                .ease(TweenEquations.easeInOutSine)
                .repeatYoyo(1, 1.4f)
                .setCallback(callback)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        sprite.draw(batcher);
        batcher.end();
    }
}
