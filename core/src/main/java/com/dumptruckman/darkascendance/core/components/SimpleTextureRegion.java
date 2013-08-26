package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SimpleTextureRegion extends Component {

    public static enum RenderLayer {
        BOTTOM_SHAPES
    }

    public RenderLayer renderLayer;
    public TextureRegion region;

    public SimpleTextureRegion(TextureRegion region, RenderLayer renderLayer) {
        this.region = region;
        this.renderLayer = renderLayer;
    }

    public SimpleTextureRegion(TextureRegion region) {
        this(region, RenderLayer.BOTTOM_SHAPES);
    }
}
