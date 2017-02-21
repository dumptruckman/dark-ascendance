package com.dumptruckman.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureFactory {

    public static TexturePack getBackground() {
        PixmapPacker packer = new PixmapPacker(1024, 1024, Format.RGBA8888, 0, false);

        Pixmap pixmap = new Pixmap(Gdx.files.internal("star-field.png"));

        packer.pack(Textures.STAR_FIELD, pixmap);

        TextureAtlas atlas = packer.generateTextureAtlas(TextureFilter.Nearest, TextureFilter.Nearest, true);
        return new TexturePack(atlas);
    }

    private TexturePack mainTexturePack;

    public TextureFactory() {
        this.mainTexturePack = createMainTexturePack();
    }

    private TexturePack createMainTexturePack() {
        PixmapPacker packer = new PixmapPacker(1024, 1024, Format.RGBA8888, 0, false);

        Pixmap pixmap = new Pixmap(Gdx.files.internal("basic-ship.png"));

        packer.pack(Textures.BASIC_SHIP, pixmap);

        TextureAtlas atlas = packer.generateTextureAtlas(TextureFilter.Linear, TextureFilter.Linear, true);
        return new TexturePack(atlas);
    }

    public TexturePack getMainTexturePack() {
        return mainTexturePack;
    }

}
