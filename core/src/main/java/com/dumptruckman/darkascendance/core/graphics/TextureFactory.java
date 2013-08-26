package com.dumptruckman.darkascendance.core.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureFactory {

    public static TexturePack getMainTexturePack() {
        PixmapPacker packer = new PixmapPacker(1024, 1024, Format.RGBA8888, 1, false);

        Pixmap pixmap = new Pixmap(Gdx.files.internal("basic-ship.png"));

        packer.pack(Textures.BASIC_SHIP, pixmap);

        TextureAtlas atlas = packer.generateTextureAtlas(TextureFilter.Nearest, TextureFilter.Nearest, true);
        return new TexturePack(atlas);
    }
}
