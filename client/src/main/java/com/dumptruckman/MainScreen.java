package com.dumptruckman;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.components.BackgroundComponent;
import com.dumptruckman.components.PositionComponent;
import com.dumptruckman.components.TextureComponent;
import com.dumptruckman.components.TextureComponent.RenderLayer;
import com.dumptruckman.entities.Background;
import com.dumptruckman.entities.UIButton;
import com.dumptruckman.graphics.TextureFactory;
import com.dumptruckman.graphics.TexturePack;
import com.dumptruckman.graphics.Textures;
import com.dumptruckman.systems.BackgroundRenderingSystem;
import com.dumptruckman.systems.TextureRenderingSystem;
import recs.Entity;
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
        BackgroundRenderingSystem backgroundRenderingSystem = new BackgroundRenderingSystem(camera);
        TextureRenderingSystem textureRenderingSystem = new TextureRenderingSystem(camera);

        gameStateManager.addSystemsToAllStates(backgroundRenderingSystem);
        gameStateManager.addSystemsToAllStates(textureRenderingSystem);

        world.addEntity(new Background(Textures.STAR_FIELD, RenderLayer.GAME_BACKGROUND));

        UIButton playButton = new UIButton("Play", RenderLayer.UI_FOREGROUND_1, 0, 0).scaleFont(2);

        world.addEntity(playButton);

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
