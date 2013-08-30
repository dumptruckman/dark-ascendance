package com.dumptruckman.darkascendance.network.client;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.components.Graphics;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.graphics.Textures;
import com.dumptruckman.darkascendance.network.NetworkEntity;

public class ClientEntityConfigurator {

    private World world;
    private TextureFactory textureFactory;

    public ClientEntityConfigurator(World world, TextureFactory textureFactory) {
        this.world = world;
        this.textureFactory = textureFactory;
    }

    public Entity setupPlayerShip(NetworkEntity networkEntity, OrthographicCamera camera) {
        Entity entity = networkEntity.addComponentsToEntity(world.createEntity());

        entity.addComponent(new Player(camera));
        entity.addComponent(new Graphics(textureFactory.getMainTexturePack().getTexture(Textures.BASIC_SHIP)));

        return entity;
    }
}
