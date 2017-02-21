package com.dumptruckman;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.systems.TextureRenderingSystem;
import recs.EntityWorld;

public class MainScreen implements Screen {

    private float screenWidth, screenHeight;
    private GameStateManager gameStateManager;
    private EntityWorld world;
    private OrthographicCamera camera;

    public MainScreen(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        world = new EntityWorld();
        gameStateManager = new GameStateManager(world);
    }

    @Override
    public void show() {
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        TextureRenderingSystem textureRenderingSystem = new TextureRenderingSystem(camera);

        gameStateManager.addSystemsToGameState(GameState.MAIN_MENU, textureRenderingSystem);
        gameStateManager.setGameState(GameState.MAIN_MENU);
    }

    @Override
    public void render(float delta) {
        world.process(delta);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
