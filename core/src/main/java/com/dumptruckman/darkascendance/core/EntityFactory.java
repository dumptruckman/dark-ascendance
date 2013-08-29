package com.dumptruckman.darkascendance.core;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.components.*;
import com.dumptruckman.darkascendance.core.components.Graphics;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.graphics.TexturePack;
import com.dumptruckman.darkascendance.core.graphics.Textures;

public class EntityFactory {

    private final TexturePack mainTexturePack;
    private final World world;

    EntityFactory(World world) {
        this.mainTexturePack = TextureFactory.getMainTexturePack();
        this.world = world;
    }

    public Entity createBasicShip(OrthographicCamera camera) {
        Entity e = world.createEntity();

        e.addComponent(new Position());
        e.addComponent(new Graphics(mainTexturePack.getTexture(Textures.BASIC_SHIP)));
        e.addComponent(new Player(camera));
        e.addComponent(new Velocity());
        e.addComponent(new Thrusters());

        return e;
    }
}
