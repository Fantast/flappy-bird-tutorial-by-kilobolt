package com.dpaulenk.zombie.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dpaulenk.zombie.model.Bird;
import com.dpaulenk.zombie.model.GameWorld;
import com.dpaulenk.zombie.model.Grass;
import com.dpaulenk.zombie.model.Pipe;
import com.dpaulenk.zombie.tween.Value;
import com.dpaulenk.zombie.tween.ValueAccessor;
import com.dpaulenk.zombie.ui.SimpleButton;
import com.dpaulenk.zombie.utils.AssetLoader;
import com.dpaulenk.zombie.utils.InputHandler;
import com.dpaulenk.zombie.utils.ScrollHandler;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;


public class GameRenderer {

    private final GameWorld world;

    // Game Objects
    private final Bird bird;
    // Game Objects
    private final ScrollHandler scroller;
    private final Grass frontGrass;
    private final Grass backGrass;
    private final Pipe pipe1;
    private final Pipe pipe2;
    private final Pipe pipe3;

    // Game Assets
    private final TextureRegion bg;
    private final TextureRegion grass;
    private final TextureRegion birdMid;
    private final TextureRegion birdDown;
    private final TextureRegion birdUp;
    private final TextureRegion skullUp;
    private final TextureRegion skullDown;
    private final TextureRegion bar;
    private final TextureRegion ready;
    private final TextureRegion zbLogo;
    private final TextureRegion gameOver;
    private final TextureRegion highScore;
    private final TextureRegion scoreboard;
    private final TextureRegion star;
    private final TextureRegion noStar;
    private final TextureRegion retry;
    private final Animation birdAnimation;

    private final int gameHeight;
    private final int centerY;

    private final OrthographicCamera cam;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batcher;

    private final Color transitionColor;

    // Buttons
    private final List<SimpleButton> menuButtons;
    private final TweenManager manager = new TweenManager();
    private final Value alpha = new Value();

    public GameRenderer(GameWorld world, int gameHeight, int centerY) {
        this.world = world;

        this.gameHeight = gameHeight;
        this.centerY = centerY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        birdDown = AssetLoader.birdDown;
        birdUp = AssetLoader.birdUp;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
        ready = AssetLoader.ready;
        zbLogo = AssetLoader.zbLogo;
        gameOver = AssetLoader.gameOver;
        highScore = AssetLoader.highScore;
        scoreboard = AssetLoader.scoreboard;
        retry = AssetLoader.retry;
        star = AssetLoader.star;
        noStar = AssetLoader.noStar;

        bird = world.getBird();

        scroller = world.getScroller();

        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
        backGrass = scroller.getBackGrass();
        frontGrass = scroller.getFrontGrass();

        menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

        transitionColor = new Color();
        prepareTransition(255, 255, 255, .5f);
    }

    private void drawGrass() {
        // Draw the grass
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawSkulls() {

        batcher.draw(skullUp, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {
        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), centerY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), centerY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), centerY + 66 - (pipe3.getHeight() + 45));
    }

    private void drawBirdCentered(float runTime) {
        batcher.draw(birdAnimation.getKeyFrame(runTime), 59, bird.getY() - 15,
                bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
    }

    private void drawBird(float runTime) {

        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

        } else {
            batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(),
                    bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }

    }

    private void drawMenuUI() {
        batcher.draw(zbLogo, 136 / 2 - 56, centerY - 50,
                zbLogo.getRegionWidth() / 1.2f, zbLogo.getRegionHeight() / 1.2f);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }
    }

    private void drawScoreboard() {
        batcher.draw(scoreboard, 22, centerY - 30, 97, 37);

        batcher.draw(noStar, 25, centerY - 15, 10, 10);
        batcher.draw(noStar, 37, centerY - 15, 10, 10);
        batcher.draw(noStar, 49, centerY - 15, 10, 10);
        batcher.draw(noStar, 61, centerY - 15, 10, 10);
        batcher.draw(noStar, 73, centerY - 15, 10, 10);

        if (world.getScore() > 2) {
            batcher.draw(star, 73, centerY - 15, 10, 10);
        }

        if (world.getScore() > 17) {
            batcher.draw(star, 61, centerY - 15, 10, 10);
        }

        if (world.getScore() > 50) {
            batcher.draw(star, 49, centerY - 15, 10, 10);
        }

        if (world.getScore() > 80) {
            batcher.draw(star, 37, centerY - 15, 10, 10);
        }

        if (world.getScore() > 120) {
            batcher.draw(star, 25, centerY - 15, 10, 10);
        }

        String score = String.valueOf(world.getScore());
        int length = score.length();

        AssetLoader.whiteFont.draw(batcher, score, 104 - (2 * length), centerY - 20);

        String highScore = String.valueOf(AssetLoader.getHighScore());
        int length2 = highScore.length();
        AssetLoader.whiteFont.draw(batcher, highScore, 104 - (2.5f * length2), centerY - 3);

    }

    private void drawRetry() {
        batcher.draw(retry, 36, centerY + 10, 66, 14);
    }

    private void drawReady() {
        batcher.draw(ready, 36, centerY - 50, 68, 14);
    }

    private void drawGameOver() {
        batcher.draw(gameOver, 24, centerY - 50, 92, 14);
    }

    private void drawScore() {
        String score = String.valueOf(world.getScore());
        int length = score.length();
        AssetLoader.shadow.draw(batcher, score, 68 - (3 * length), centerY - 82);
        AssetLoader.font.draw(batcher, score, 68 - (3 * length), centerY - 83);
    }

    private void drawHighScore() {
        batcher.draw(highScore, 22, centerY - 50, 96, 14);
    }

    public void render(float delta, float runTime) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, centerY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, centerY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, centerY + 77, 136, 52);

        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();

        batcher.draw(bg, 0, centerY + 23, 136, 43);

        drawPipes();

        batcher.enableBlending();
        drawSkulls();

        if (world.isRunning()) {
            drawBird(runTime);
            drawScore();
        } else if (world.isReady()) {
            drawBird(runTime);
            drawReady();
        } else if (world.isMenu()) {
            drawBirdCentered(runTime);
            drawMenuUI();
        } else if (world.isGameOver()) {
            drawScoreboard();
            drawBird(runTime);
            drawGameOver();
            drawRetry();
        } else if (world.isHighScore()) {
            drawScoreboard();
            drawBird(runTime);
            drawHighScore();
            drawRetry();
        }

        drawGrass();

        batcher.end();
        drawTransition(delta);
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        Tween.to(alpha, -1, duration).target(0).ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g, transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL10.GL_BLEND);
        }
    }
}