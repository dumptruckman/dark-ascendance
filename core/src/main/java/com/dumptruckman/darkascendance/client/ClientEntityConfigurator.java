package com.dumptruckman.darkascendance.client;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.client.components.Graphics;
import com.dumptruckman.darkascendance.client.components.Player;
import com.dumptruckman.darkascendance.client.graphics.TextureFactory;
import com.dumptruckman.darkascendance.client.graphics.Textures;
import com.dumptruckman.darkascendance.shared.NetworkEntity;
import recs.EntityWorld;

public class ClientEntityConfigurator {

    private EntityWorld world;
    private TextureFactory textureFactory;

    public ClientEntityConfigurator(EntityWorld world, TextureFactory textureFactory) {
        this.world = world;
        this.textureFactory = textureFactory;
    }

    public Entity setupPlayerShip(NetworkEntity networkEntity, OrthographicCamera camera) {
        Entity entity = networkEntity.addComponentsToEntity(new Entity());

        entity.addComponent(new Player(camera));
        entity.addComponent(new Graphics(textureFactory.getMainTexturePack().getTexture(Textures.BASIC_SHIP)));

        return entity;
    }

    public Entity setupOtherPlayerShip(NetworkEntity networkEntity) {
        Entity entity = networkEntity.addComponentsToEntity(new Entity());

        entity.addComponent(new Graphics(textureFactory.getMainTexturePack().getTexture(Textures.BASIC_SHIP)));

        return entity;
    }
}
