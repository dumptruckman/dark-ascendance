package com.dumptruckman.darkascendance.network.client;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.EntityFactory;
import com.dumptruckman.darkascendance.core.GameLogic;
import com.dumptruckman.darkascendance.core.systems.PlayerInputSystem;
import com.dumptruckman.darkascendance.core.systems.TextureRenderingSystem;

public class ClientLogicLoop extends GameLogic implements Screen {

    private TextureRenderingSystem textureRenderingSystem;
    private PlayerInputSystem playerInputSystem;
    private EntityFactory entityFactory;
    private OrthographicCamera camera;

    FPSLogger fpsLogger = new FPSLogger();

    public ClientLogicLoop(float screenWidth, float screenHeight) {
        super(new World(), true);
        this.camera = new OrthographicCamera(screenWidth, screenHeight);

        textureRenderingSystem = getWorld().setSystem(new TextureRenderingSystem(camera), true);
        playerInputSystem = getWorld().setSystem(new PlayerInputSystem(), true);
        intializeLogicSystems();

        getWorld().initialize();

        entityFactory = new EntityFactory(getWorld());

        entityFactory.createBasicShip(camera).addToWorld();
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        getWorld().setDelta(delta);

        playerInputSystem.process();

        if (hasTickElapsed()) {
            processTick(getTickRateDelta());
            prepareForNextTick();
            setChanged();
        } else {
            interpolate();
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
