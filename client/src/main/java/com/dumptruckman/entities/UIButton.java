package com.dumptruckman.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.dumptruckman.components.PositionComponent;
import com.dumptruckman.components.TextureComponent;
import com.dumptruckman.components.TextureComponent.RenderLayer;
import recs.Entity;

public class UIButton extends Entity {

    TextureComponent textureComponent;
    PositionComponent position;

    public UIButton(String text, RenderLayer layer, float x, float y) {
        textureComponent = new TextureComponent(text, layer);
        GlyphLayout layout = textureComponent.getGlyphLayout();
        x = x + (-layout.width) / 2;
        y = y + (layout.height) / 2;
        position = new PositionComponent().setX(x).setY(y);

    }

    public UIButton scaleFont(float scale) {
        textureComponent.getFont().getData().setScale(scale);
        GlyphLayout layout = textureComponent.getGlyphLayout();
        layout.setText(textureComponent.getFont(), textureComponent.getText());
        float x = position.getX() + (-layout.width) / 2;
        float y = position.getY() + (layout.height) / 2;
        position.setX(x).setY(y);
        return this;
    }
}
