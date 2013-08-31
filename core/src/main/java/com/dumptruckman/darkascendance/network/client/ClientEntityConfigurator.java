package com.dumptruckman.darkascendance.network.client;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.components.Graphics;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.graphics.Textures;
import com.dumptruckman.darkascendance.network.NetworkEntity;
import com.dumptruckman.darkascendance.recs.Entity;
import com.dumptruckman.darkascendance.recs.EntityWorld;

public class ClientEntityConfigurator {

    private EntityWorld world;
    private TextureFactory textureFactory;

    public ClientEntityConfigurator(EntityWorld world, TextureFactory textureFactory) {
        this.world = world;
        this.textureFactory = textureFactory;
    }

    public Entity setupPlayerShip(NetworkEntity networkEntity, OrthographicCamera camera) {
        Entity entity = networkEntity.addComponentsToEntity(new Entity());
        world.addEntity(entity);

        entity.addComponent(new Player(camera));
        entity.addComponent(new Graphics(textureFactory.getMainTexturePack().getTexture(Textures.BASIC_SHIP)));

        return entity;
    }
}
