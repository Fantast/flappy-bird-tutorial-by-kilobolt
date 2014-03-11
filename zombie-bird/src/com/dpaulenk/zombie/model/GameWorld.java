package com.dpaulenk.zombie.model;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.dpaulenk.zombie.render.GameRenderer;
import com.dpaulenk.zombie.utils.AssetLoader;
import com.dpaulenk.zombie.utils.ScrollHandler;

public class GameWorld {

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    private GameRenderer renderer;

    private final int centerY;

    private final Bird bird;
    private final ScrollHandler scroller;

    private final Rectangle ground;

    private GameState currentState;

    private float runTime = 0;
    private int score = 0;

    public GameWorld(int centerY) {
        this.centerY = centerY;

        bird = new Bird(33, centerY - 5, 17, 12);

        scroller = new ScrollHandler(this, centerY + 66);

        ground = new Rectangle(0, centerY + 66, 136, 11);

        currentState = GameState.MENU;
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case MENU:
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    private void updateReady(float delta) {
        bird.updateReady(runTime);
        scroller.updateReady(delta);
    }

    private void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        bird.update(delta);
        scroller.update(delta);

        if (scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
            renderer.prepareTransition(255, 255, 255, .3f);
//            renderer.prepareTransition(255, 0, 0, .3f);

            AssetLoader.fall.play();
        }

        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            if (bird.isAlive()) {
                AssetLoader.dead.play();
                renderer.prepareTransition(255, 255, 255, .3f);
//                renderer.prepareTransition(128, 0, 0, .3f);

                bird.die();
            }

            scroller.stop();
            bird.deccelerate();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }
    }


    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void restart() {
        score = 0;
        bird.onRestart(centerY - 5);
        scroller.onRestart();
        ready();
    }

    public Bird getBird() {
        return bird;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public int getCenterY() {
        return centerY;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
}
