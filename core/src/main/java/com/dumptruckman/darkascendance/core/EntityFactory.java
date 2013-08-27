package com.dumptruckman.darkascendance.core;

import com.artemis.Entity;
import com.artemis.World;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Rotation;
import com.dumptruckman.darkascendance.core.components.Sample;
import com.dumptruckman.darkascendance.core.components.SimpleTextureRegion;
import com.dumptruckman.darkascendance.core.components.Thrust;
import com.dumptruckman.darkascendance.core.components.Velocity;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.graphics.TexturePack;
import com.dumptruckman.darkascendance.core.graphics.Textures;

public class EntityFactory {

    private final TexturePack mainTexturePack;
    private final TexturePack background = TextureFactory.getBackground();

    EntityFactory(TexturePack mainTexturePack) {
        this.mainTexturePack = mainTexturePack;

    }

    public Entity createSampleMovingSquare(World world) {
        Entity e = world.createEntity();

        e.addComponent(new Position());
        e.addComponent(new SimpleTextureRegion(mainTexturePack.getTexture(Textures.BASIC_SHIP)));
        e.addComponent(new Player());
        e.addComponent(new Velocity());
        e.addComponent(new Thrust());
        e.addComponent(new Rotation());

        return e;
    }

    public Entity createBackground(World world) {
        Entity e = world.createEntity();

        e.addComponent(new Position());
        e.addComponent(new SimpleTextureRegion(background.getTexture(Textures.STAR_FIELD), SimpleTextureRegion.RenderLayer.BACKGROUND));

        return e;
    }
}
