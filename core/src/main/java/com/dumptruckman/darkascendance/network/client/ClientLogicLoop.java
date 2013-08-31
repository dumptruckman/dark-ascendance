package com.dumptruckman.darkascendance.network.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.Entity;
import com.dumptruckman.darkascendance.core.GameLogic;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.network.client.systems.PlayerCameraSystem;
import com.dumptruckman.darkascendance.network.client.systems.PlayerInputSystem;
import com.dumptruckman.darkascendance.network.client.systems.ServerEntityHandlingSystem;
import com.dumptruckman.darkascendance.network.client.systems.SnapshotApplicationSystem;
import com.dumptruckman.darkascendance.network.client.systems.TextureRenderingSystem;
import com.dumptruckman.darkascendance.network.NetworkEntity;
import com.dumptruckman.darkascendance.network.NetworkSystemInjector;
import com.dumptruckman.darkascendance.network.server.GameServer;
import recs.EntityWorld;

import java.util.concurrent.ConcurrentHashMap;

public class ClientLogicLoop extends GameLogic implements Screen {

    private static ConcurrentHashMap<Integer, Entity> serverEntityIdMap = new ConcurrentHashMap<Integer, Entity>();

    private TextureRenderingSystem textureRenderingSystem;
    private OrthographicCamera camera;
    private ClientEntityConfigurator entityConfigurator;

    FPSLogger fpsLogger = new FPSLogger();

    public ClientLogicLoop(NetworkSystemInjector networkSystemInjector, float screenWidth, float screenHeight) {
        super(new EntityWorld());
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.entityConfigurator = new ClientEntityConfigurator(getWorld(), new TextureFactory());
        enableInterpolation();

        getWorld().addSystem(new SnapshotApplicationSystem(GameServer.SNAPSHOT_RATE));
        getWorld().addSystem(new PlayerInputSystem(TICK_LENGTH_SECONDS));
        getWorld().addSystem(new PlayerCameraSystem());
        //addLogicSystems();
        textureRenderingSystem = new TextureRenderingSystem(camera);
        getWorld().addSystem(textureRenderingSystem);
        networkSystemInjector.addSystemsToWorld(getWorld());
        getWorld().addSystem(new ServerEntityHandlingSystem());
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        processGameLogic(delta);
        //fpsLogger.log();
    }

    public void addPlayerShipToWorld(NetworkEntity networkEntity) {
        Entity entity = entityConfigurator.setupPlayerShip(networkEntity, camera);
        safelyAddEntity(networkEntity.getEntityId(), entity);
    }

    private void safelyAddEntity(int serverEntityId, Entity entity) {
        serverEntityIdMap.put(serverEntityId, entity);
        ServerEntityHandlingSystem.addEntity(entity);
    }

    public void addOtherPlayerShipToWorld(NetworkEntity networkEntity) {
        Entity entity = entityConfigurator.setupOtherPlayerShip(networkEntity);
        safelyAddEntity(networkEntity.getEntityId(), entity);
    }

    public void removeEntityFromWorld(NetworkEntity networkEntity) {
        Entity entity = serverEntityIdMap.get(networkEntity.getEntityId());
        ServerEntityHandlingSystem.removeEntity(entity);
        serverEntityIdMap.remove(networkEntity.getEntityId());
    }

    public static Entity getEntityByServerEntityId(int entityId) {
        return serverEntityIdMap.get(entityId);
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
