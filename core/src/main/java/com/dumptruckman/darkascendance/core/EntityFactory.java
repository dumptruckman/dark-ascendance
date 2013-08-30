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

    private World world;

    EntityFactory(World world) {
        this.world = world;
    }

    public Entity createBasicShip() {
        Entity entity = world.createEntity();

        entity.addComponent(new Position());
        entity.addComponent(new Velocity());
        entity.addComponent(new Thrusters());
        entity.addComponent(new Controls());

        return entity;
    }
}
