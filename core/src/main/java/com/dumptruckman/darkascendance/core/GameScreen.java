package com.dumptruckman.darkascendance.core;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.systems.PlayerInputSystem;
import com.dumptruckman.darkascendance.core.systems.TextureRenderingSystem;
import com.dumptruckman.darkascendance.network.Test;
import com.dumptruckman.darkascendance.util.TickRateController;

import java.util.Observable;

public class GameScreen extends Observable implements Screen {

    private GameLogic gameLogic;
    private TextureRenderingSystem textureRenderingSystem;
    private PlayerInputSystem playerInputSystem;
    private EntityFactory entityFactory;
    private OrthographicCamera camera;
    private TickRateController tickRateController = new TickRateController(GameLogic.TICK_LENGTH_MILLIS);

    FPSLogger fpsLogger = new FPSLogger();

    public GameScreen(float screenWidth, float screenHeight) {
        this.camera = new OrthographicCamera(screenWidth, screenHeight);

        World world = new World();
        gameLogic = new GameLogic(world, true);

        textureRenderingSystem = world.setSystem(new TextureRenderingSystem(camera), true);
        playerInputSystem = world.setSystem(new PlayerInputSystem(), true);
        gameLogic.intializeLogicSystems();

        world.initialize();

        entityFactory = new EntityFactory(TextureFactory.getMainTexturePack());

        entityFactory.createBasicShip(world, camera).addToWorld();
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        gameLogic.getWorld().setDelta(delta);

        playerInputSystem.process();

        if (tickRateController.hasTickElapsed()) {
            gameLogic.processTick(tickRateController.getDelta());
            tickRateController.prepareForNextTick();
            notifyObservers(new Test().setMessage("Tick"));
            setChanged();
        } else {
            gameLogic.interpolate();
        }

        textureRenderingSystem.process();
        fpsLogger.log();
    }

    @Override
    public void resize(final int i, final int i2) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }
}
