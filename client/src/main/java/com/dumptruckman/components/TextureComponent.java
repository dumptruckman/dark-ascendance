package com.dumptruckman.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import recs.Component;

public class TextureComponent extends Component {

    public static enum RenderLayer {
        GAME_BACKGROUND,
        UI_BACKGROUND,
        UI_FOREGROUND_1
    }

    private TextureRegion region;
    private RenderLayer layer;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private String text;

    public TextureComponent(TextureRegion region, RenderLayer layer) {
        this.region = region;
        this.layer = layer;
    }

    public TextureComponent(String text, RenderLayer layer) {
        this.font = new BitmapFont();
        this.glyphLayout = new GlyphLayout(font, text);
        this.text = text;
        this.layer = layer;
    }

    public TextureComponent(TextureRegion region, String text, RenderLayer layer) {
        this.region = region;
        this.font = new BitmapFont();
        this.text = text;
        this.layer = layer;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public BitmapFont getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public GlyphLayout getGlyphLayout() {
        return glyphLayout;
    }

    public RenderLayer getLayer() {
        return layer;
    }
}
