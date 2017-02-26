package com.dumptruckman.entities;

import com.dumptruckman.components.BackgroundComponent;
import com.dumptruckman.components.TextureComponent;
import com.dumptruckman.components.TextureComponent.RenderLayer;
import com.dumptruckman.graphics.TextureFactory;
import recs.Entity;

public class Background extends Entity {

    TextureComponent textureComponent;
    BackgroundComponent backgroundComponent;

    public Background(String texture, RenderLayer layer) {
        textureComponent = new TextureComponent(TextureFactory.getBackground().getTexture(texture), layer);
        backgroundComponent = new BackgroundComponent();
    }
}
