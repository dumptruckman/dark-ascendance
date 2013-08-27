package com.dumptruckman.darkascendance.core;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.systems.MovementSystem;
import com.dumptruckman.darkascendance.core.systems.PlayerInputSystem;
import com.dumptruckman.darkascendance.core.systems.SampleMovementSystem;
import com.dumptruckman.darkascendance.core.systems.TextureRenderingSystem;
import com.dumptruckman.darkascendance.core.systems.ThrustSystem;

public class GameScreen implements Screen {

    private World world;
    private TextureRenderingSystem textureRenderingSystem;
    private EntityFactory entityFactory;

    public GameScreen(float screenWidth, float screenHeight) {
        this.world = new World();

        world.setSystem(new SampleMovementSystem());
        textureRenderingSystem = world.setSystem(new TextureRenderingSystem(), true);
        world.setSystem(new PlayerInputSystem());
        world.setSystem(new ThrustSystem());
        world.setSystem(new MovementSystem());

        world.initialize();

        entityFactory = new EntityFactory(TextureFactory.getMainTexturePack());

        entityFactory.createBackground(world).addToWorld();
        entityFactory.createSampleMovingSquare(world).addToWorld();
    }

    @Override
    public void render(final float delta) {
        Gdx.gl20.glClear(GL10.GL_COLOR_BUFFER_BIT);

        world.setDelta(delta);

        world.process();

        textureRenderingSystem.process();
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
