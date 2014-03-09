package com.dpaulenk.zombie.render;

import com.badlogic.gdx.Gdx;
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
import com.dpaulenk.zombie.utils.AssetLoader;
import com.dpaulenk.zombie.utils.ScrollHandler;


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
    private final Animation birdAnimation;

    private final int gameHeight;
    private final int centerY;

    private final OrthographicCamera cam;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batcher;

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

        bird = world.getBird();

        scroller = world.getScoller();

        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
        backGrass = scroller.getBackGrass();
        frontGrass = scroller.getFrontGrass();
    }

    public void render(float runTime) {
        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
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

        // Begin SpriteBatch
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(bg, 0, centerY + 23, 136, 43);

        // 1. Draw Grass
        drawGrass();

        // 2. Draw Pipes
        drawPipes();

        batcher.enableBlending();

        // 3. Draw Skulls (requires transparency)
        drawSkulls();

        if (bird.shouldntFlap()) {
            batcher.draw(birdMid,
                    bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());

        } else {
            batcher.draw(birdAnimation.getKeyFrame(runTime),
                    bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }

        // End SpriteBatch
        batcher.end();
    }

    private void drawGrass() {
        // Draw the grass
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawSkulls() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.

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
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.
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
}
