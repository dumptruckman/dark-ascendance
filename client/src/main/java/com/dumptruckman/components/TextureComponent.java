package com.dumptruckman.components;

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

    public TextureComponent(TextureRegion region, RenderLayer layer) {
        this.region = region;
        this.layer = layer;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public RenderLayer getLayer() {
        return layer;
    }
}
