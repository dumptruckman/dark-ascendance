package com.dumptruckman.darkascendance.core;

import com.artemis.Entity;
import com.artemis.World;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Sample;
import com.dumptruckman.darkascendance.core.components.SimpleTextureRegion;
import com.dumptruckman.darkascendance.core.components.Velocity;
import com.dumptruckman.darkascendance.core.graphics.TexturePack;
import com.dumptruckman.darkascendance.core.graphics.Textures;

public class EntityFactory {

    private final TexturePack mainTexturePack;

    EntityFactory(TexturePack mainTexturePack) {
        this.mainTexturePack = mainTexturePack;
    }

    public Entity createSampleMovingSquare(World world) {
        Entity e = world.createEntity();

        e.addComponent(new Position());
        e.addComponent(new SimpleTextureRegion(mainTexturePack.getTexture(Textures.BASIC_SHIP)));
        e.addComponent(new Player());
        e.addComponent(new Velocity());

        return e;
    }
}
