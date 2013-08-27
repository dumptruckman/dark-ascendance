package com.dumptruckman.darkascendance.core;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Rotation;
import com.dumptruckman.darkascendance.core.components.SimpleTextureRegion;
import com.dumptruckman.darkascendance.core.components.Thrust;
import com.dumptruckman.darkascendance.core.components.Velocity;
import com.dumptruckman.darkascendance.core.graphics.TexturePack;
import com.dumptruckman.darkascendance.core.graphics.Textures;

public class EntityFactory {

    private final TexturePack mainTexturePack;

    EntityFactory(TexturePack mainTexturePack) {
        this.mainTexturePack = mainTexturePack;

    }

    public Entity createBasicShip(World world, OrthographicCamera camera) {
        Entity e = world.createEntity();

        e.addComponent(new Position());
        e.addComponent(new SimpleTextureRegion(mainTexturePack.getTexture(Textures.BASIC_SHIP)));
        e.addComponent(new Player(camera));
        e.addComponent(new Velocity());
        e.addComponent(new Thrust());
        e.addComponent(new Rotation());

        return e;
    }
}
