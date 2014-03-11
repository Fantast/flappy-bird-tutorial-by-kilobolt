package com.dpaulenk.zombie.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.graphics.Texture.TextureFilter;

public class AssetLoader {

    public static Preferences prefs;

    public static Sound dead;
    public static Sound coin;
    public static Sound flap;
    public static Sound fall;

    public static BitmapFont font;
    public static BitmapFont whiteFont;
    public static BitmapFont shadow;

    public static Texture logoTexture;
    public static Texture texture;

    public static TextureRegion logo;
    public static TextureRegion zbLogo;
    public static TextureRegion playButtonUp;
    public static TextureRegion playButtonDown;

    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion highScore;
    public static TextureRegion scoreboard;
    public static TextureRegion star;
    public static TextureRegion noStar;
    public static TextureRegion retry;

    public static TextureRegion bg;
    public static TextureRegion grass;

    public static Animation birdAnimation;
    public static TextureRegion bird;
    public static TextureRegion birdDown;
    public static TextureRegion birdUp;

    public static TextureRegion skullUp;
    public static TextureRegion skullDown;
    public static TextureRegion bar;

    public static void load() {
        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f, -.25f);
        whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"));
        whiteFont.setScale(.1f, -.1f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);

        logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
        logoTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        logo = new TextureRegion(logoTexture, 0, 0, 512, 128);

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        ready = new TextureRegion(texture, 59, 83, 34, 7);
        ready.flip(false, true);

        retry = new TextureRegion(texture, 59, 110, 33, 7);
        retry.flip(false, true);

        gameOver = new TextureRegion(texture, 59, 92, 46, 7);
        gameOver.flip(false, true);

        scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
        scoreboard.flip(false, true);

        star = new TextureRegion(texture, 152, 70, 10, 10);
        star.flip(false, true);

        noStar = new TextureRegion(texture, 165, 70, 10, 10);
        noStar.flip(false, true);

        highScore = new TextureRegion(texture, 59, 101, 48, 7);
        highScore.flip(false, true);

        zbLogo = new TextureRegion(texture, 0, 55, 135, 24);
        zbLogo.flip(false, true);

        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown.flip(false, true);

        bird = new TextureRegion(texture, 153, 0, 17, 12);
        bird.flip(false, true);

        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);

        birdAnimation = new Animation(0.06f, birdDown, bird, birdUp);
        birdAnimation.setPlayMode(Animation.LOOP_PINGPONG);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        // Create by flipping existing skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);

        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("ZombieBird");

        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            setHighScore(0);
        }
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        dead.dispose();
        coin.dispose();
        flap.dispose();
        fall.dispose();

        font.dispose();
        whiteFont.dispose();
        shadow.dispose();
        texture.dispose();
        logoTexture.dispose();
    }
}
