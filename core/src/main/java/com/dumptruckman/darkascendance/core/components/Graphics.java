package com.dumptruckman.darkascendance.core.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import recs.Component;

public class Graphics extends Component {

    public static enum RenderLayer {
        BACKGROUND,
        BOTTOM_SHAPES
    }

    public RenderLayer renderLayer;
    public TextureRegion region;

    public Graphics(TextureRegion region, RenderLayer renderLayer) {
        this.region = region;
        this.renderLayer = renderLayer;
    }

    public Graphics(TextureRegion region) {
        this(region, RenderLayer.BOTTOM_SHAPES);
    }
}
