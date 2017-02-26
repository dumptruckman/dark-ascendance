package com.dumptruckman.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.components.BackgroundComponent;
import com.dumptruckman.components.TextureComponent;
import recs.ComponentMapper;
import recs.EntitySystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BackgroundRenderingSystem extends EntitySystem {

    ComponentMapper<TextureComponent> textureMap;

    private SpriteBatch batch;
    private List<Integer> sortedEntities;
    private OrthographicCamera camera;

    public BackgroundRenderingSystem(OrthographicCamera camera) {
        super(TextureComponent.class, BackgroundComponent.class);
        this.camera = camera;
        batch = new SpriteBatch();
        sortedEntities = new ArrayList<Integer>();
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Integer entityId : sortedEntities) {
            processEntity(entityId, deltaInSec);
        }

        batch.end();
    }

    protected void processEntity(int entityId, float deltaInSec) {
        TextureRegion region = textureMap.get(entityId).getRegion();
        if (region != null) { // should never be the case for backgrounds
            drawBackground(region);
        }
    }

    private void drawBackground(TextureRegion backgroundTexture) {
        float bgWidth = backgroundTexture.getRegionWidth();
        float bgHeight = backgroundTexture.getRegionHeight();
        float camWidth = (int) camera.viewportWidth;
        float camHeight = (int) camera.viewportHeight;
        float camX = (int) camera.position.x - (camWidth / 2);
        float camY = (int) camera.position.y - (camHeight / 2);

        float drawX = MathUtils.floor(camX / bgWidth) * bgWidth;
        float drawY = MathUtils.floor(camY / bgHeight) * bgHeight;
        for (float x = drawX; x <= camX + camWidth; x += bgWidth) {
            for (float y = drawY; y <= camY + camHeight; y += bgHeight) {
                batch.draw(backgroundTexture, x, y);
            }
        }
    }

    @Override
    protected void addEntity(final int id) {
        sortedEntities.add(id);
        Collections.sort(sortedEntities, new Comparator<Integer>() {
            @Override
            public int compare(Integer e1, Integer e2) {
                TextureComponent s1 = textureMap.get(e1);
                TextureComponent s2 = textureMap.get(e2);
                return s1.getLayer().compareTo(s2.getLayer());
            }
        });
    }

    @Override
    protected void removeEntity(final int id) {
        sortedEntities.remove((Object) id);
    }
}
