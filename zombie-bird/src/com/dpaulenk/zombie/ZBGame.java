package com.dpaulenk.zombie;

import com.badlogic.gdx.Game;
import com.dpaulenk.zombie.screens.GameScreen;
import com.dpaulenk.zombie.screens.SplashScreen;
import com.dpaulenk.zombie.utils.AssetLoader;

public class ZBGame extends Game {

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}