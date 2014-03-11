package com.dpaulenk.zombie;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "zombie-bird";
        cfg.useGL20 = false;
        cfg.width = 1080 / 3;
        cfg.height = 1920 / 3;

        new LwjglApplication(new ZBGame(), cfg);
    }
}
