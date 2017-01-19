package com.dumptruckman.darkascendance.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.client.systems.CommandSendSystem;
import com.dumptruckman.darkascendance.client.systems.SnapshotInterpolationSystem;
import com.dumptruckman.darkascendance.client.systems.SnapshotProcessingSystem;
import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.shared.GameLogic;
import com.dumptruckman.darkascendance.client.graphics.TextureFactory;
import com.dumptruckman.darkascendance.client.systems.PlayerCameraSystem;
import com.dumptruckman.darkascendance.client.systems.PlayerInputSystem;
import com.dumptruckman.darkascendance.client.systems.ServerEntityHandlingSystem;
import com.dumptruckman.darkascendance.client.systems.TextureRenderingSystem;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.shared.network.NetworkEntity;
import com.dumptruckman.darkascendance.shared.network.NetworkSystemInjector;
import com.dumptruckman.darkascendance.server.GameServer;
import recs.EntityWorld;

import java.util.concurrent.ConcurrentHashMap;

public class ClientLogicLoop extends GameLogic implements Screen {

    private static final float COMMAND_RATE = 0.050F;

    private static ConcurrentHashMap<Integer, Entity> serverEntityIdMap = new ConcurrentHashMap<Integer, Entity>();

    private TextureRenderingSystem textureRenderingSystem;
    private OrthographicCamera camera;
    private ClientEntityConfigurator entityConfigurator;

    FPSLogger fpsLogger = new FPSLogger();

    public ClientLogicLoop(NetworkSystemInjector networkSystemInjector, float screenWidth, float screenHeight) {
        super(new EntityWorld(), false);
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.entityConfigurator = new ClientEntityConfigurator(getWorld(), new TextureFactory());

        networkSystemInjector.addHighPrioritySystemsToWorld(getWorld());
        getWorld().addSystem(new SnapshotProcessingSystem());
        getWorld().addSystem(new SnapshotInterpolationSystem());
        getWorld().addSystem(new PlayerInputSystem(TICK_LENGTH_SECONDS));
        getWorld().addSystem(new PlayerCameraSystem());
        addLogicSystems();
        textureRenderingSystem = new TextureRenderingSystem(camera);
        getWorld().addSystem(textureRenderingSystem);
        getWorld().addSystem(new CommandSendSystem(this, COMMAND_RATE));
        getWorld().addSystem(new ServerEntityHandlingSystem());
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    public void controlsPrepared(Controls playerControls) {
        setChanged();
        notifyObservers(MessageFactory.playerInputState(playerControls));
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
