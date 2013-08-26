package com.dumptruckman.darkascendance.core.graphics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

/**
 * A partial abstraction to manage textures in a TextureAtlas.
 */
public class TexturePack {

    private final TextureAtlas textureAtlas;
    private final Map<String, AtlasRegion> textureRegionMap;

    TexturePack(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        Array<AtlasRegion> atlasRegionArray = textureAtlas.getRegions();
        this.textureRegionMap = new HashMap<String, AtlasRegion>(atlasRegionArray.size);
        for (AtlasRegion atlasRegion : atlasRegionArray) {
            this.textureRegionMap.put(atlasRegion.name, atlasRegion);
        }
    }

    /**
     * Gets a texture by name or null if non-existent.
     */
    public AtlasRegion getTexture(String name) {
        return textureRegionMap.get(name);
    }

    public void dispose() {
        textureAtlas.dispose();
    }
}
