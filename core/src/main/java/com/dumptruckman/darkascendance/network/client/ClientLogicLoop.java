package com.dumptruckman.darkascendance.network.client;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.GameLogic;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.systems.PlayerInputSystem;
import com.dumptruckman.darkascendance.core.systems.TextureRenderingSystem;
import com.dumptruckman.darkascendance.network.NetworkEntity;
import com.dumptruckman.darkascendance.network.systems.NetworkSystemInjector;

public class ClientLogicLoop extends GameLogic implements Screen {

    private TextureRenderingSystem textureRenderingSystem;
    private OrthographicCamera camera;
    private ClientEntityConfigurator entityConfigurator;

    FPSLogger fpsLogger = new FPSLogger();

    public ClientLogicLoop(NetworkSystemInjector networkSystemInjector, float screenWidth, float screenHeight) {
        super(new World());
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.entityConfigurator = new ClientEntityConfigurator(getWorld(), new TextureFactory());
        enableInterpolation();

        textureRenderingSystem = getWorld().setSystem(new TextureRenderingSystem(camera), true);
        getWorld().setSystem(new PlayerInputSystem(TICK_LENGTH_SECONDS));
        addLogicSystems();
        networkSystemInjector.addSystemsToWorld(getWorld());
        initializeWorld();
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        processGameLogic(delta);

        textureRenderingSystem.process();
        fpsLogger.log();
    }

    public void addPlayerShipToWorld(NetworkEntity networkEntity) {
        entityConfigurator.setupPlayerShip(networkEntity, camera).addToWorld();
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
