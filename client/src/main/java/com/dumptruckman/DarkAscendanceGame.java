package com.dumptruckman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class DarkAscendanceGame extends Game {

    private Screen mainScreen;

    public DarkAscendanceGame(float screenWidth, float screenHeight) {
        mainScreen = new MainScreen(screenWidth, screenHeight);
    }

    @Override
    public void create () {
        setScreen(mainScreen);
    }
}
