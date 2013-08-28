package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
