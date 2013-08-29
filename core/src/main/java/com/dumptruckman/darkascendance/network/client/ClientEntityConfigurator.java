package com.dumptruckman.darkascendance.network.client;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dumptruckman.darkascendance.core.components.Graphics;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.graphics.Textures;

public class ClientEntityConfigurator {

    private TextureFactory textureFactory;

    public ClientEntityConfigurator(TextureFactory textureFactory) {
        this.textureFactory = textureFactory;
    }

    public Entity setupPlayerShip(Entity entity, OrthographicCamera camera) {
        entity.addComponent(new Player(camera));
        entity.addComponent(new Graphics(textureFactory.getMainTexturePack().getTexture(Textures.BASIC_SHIP)));

        return entity;
    }
}
