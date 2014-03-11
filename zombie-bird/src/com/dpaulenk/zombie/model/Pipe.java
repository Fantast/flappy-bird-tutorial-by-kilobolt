package com.dpaulenk.zombie.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Pipe extends Scrollable {
    private static Random rand = new Random();

    public static final int VERTICAL_GAP = 45;
    public static final int SKULL_WIDTH = 24;
    public static final int SKULL_HEIGHT = 11;

    private final Rectangle skullUp;
    private final Rectangle skullDown;
    private final Rectangle barUp;
    private final Rectangle barDown;

    private final float groundY;

    private boolean isScored;

    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);

        this.groundY = groundY;

        skullUp = new Rectangle();
        skullDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
                groundY - (position.y + height + VERTICAL_GAP));

        // Our skull width is 24. The bar is only 22 pixels wide. So the skull
        // must be shifted by 1 pixel to the left (so that the skull is centered
        // with respect to its bar).

        // This shift is equivalent to: (SKULL_WIDTH - width) / 2
        skullUp.set(
                position.x - (SKULL_WIDTH - width) / 2,
                position.y + height - SKULL_HEIGHT,
                SKULL_WIDTH,
                SKULL_HEIGHT
        );
        skullDown.set(
                position.x - (SKULL_WIDTH - width) / 2,
                barDown.y,
                SKULL_WIDTH,
                SKULL_HEIGHT
        );
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        height = rand.nextInt(90) + 15;
        isScored = false;
    }

    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            Circle birdCircle = bird.getBoundingCircle();
            return Intersector.overlaps(birdCircle, barUp)
                    || Intersector.overlaps(birdCircle, barDown)
                    || Intersector.overlaps(birdCircle, skullUp)
                    || Intersector.overlaps(birdCircle, skullDown);
        }
        return false;
    }

    public Rectangle getSkullUp() {
        return skullUp;
    }

    public Rectangle getSkullDown() {
        return skullDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean isScored) {
        this.isScored = isScored;
    }
}
