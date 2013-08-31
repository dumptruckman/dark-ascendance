package com.dumptruckman.darkascendance.client.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.darkascendance.client.components.Graphics;
import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.client.graphics.TextureFactory;
import com.dumptruckman.darkascendance.client.graphics.Textures;
import recs.ComponentMapper;
import recs.EntitySystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TextureRenderingSystem extends EntitySystem {

    /**
     * Default SpriteBatch vertex shader.  Minimum required to work.
     */
    final String VERT = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "uniform mat4 u_projTrans;\n" //
            + "varying vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "\n" //
            + "void main()\n" //
            + "{\n" //
            + "  v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
            + "  v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
            + "  gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
            + "}\n";

    /**
     * Default SpriteBatch fragment shader.  Minimum required to work.
     */
    final String FRAG = "#ifdef GL_ES\n" //
            + "#define LOWP lowp\n" //
            + "precision mediump float;\n" //
            + "#else\n" //
            + "#define LOWP \n" //
            + "#endif\n" //
            + "varying LOWP vec4 v_color;\n" //
            + "varying vec2 v_texCoords;\n" //
            + "uniform sampler2D u_texture;\n" //
            + "void main()\n"//
            + "{\n" //
            + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
            + "}";

    ComponentMapper<Position> positionMap;
    ComponentMapper<Graphics> textureMap;

    private SpriteBatch batch;
    private List<Integer> sortedEntities;
    ShaderProgram program;
    private OrthographicCamera camera;

    private TextureRegion backgroundTexture = TextureFactory.getBackground().getTexture(Textures.STAR_FIELD);

    public TextureRenderingSystem(OrthographicCamera camera) {
        super(Position.class, Graphics.class);
        this.camera = camera;

        //load our shader program and sprite batch
        try {
            //program = new ShaderProgram(VERT, FRAG);


            //Good idea to log any warnings if they exist
            //if (program.getLog().length()!=0)
            //    System.out.println(program.getLog());

            //create our sprite batch
            //batch = new SpriteBatch(20, program);
            batch = new SpriteBatch();
        } catch (Exception e) {
            e.printStackTrace();
            batch = new SpriteBatch();
        }
        sortedEntities = new ArrayList<Integer>();
    }


    @Override
    protected void processSystem(final float deltaInSec) {
        batch.setProjectionMatrix(camera.combined);
        //program.setUniformMatrix("u_projTrans", batch.getTransformMatrix());
        batch.begin();

        drawBackground();

        for (Integer entityId : sortedEntities) {
            processEntity(entityId, deltaInSec);
        }

        batch.end();
    }

    private void drawBackground() {
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

    protected void processEntity(int entityId, float deltaInSec) {
        Position position = positionMap.get(entityId);
        TextureRegion region = textureMap.get(entityId).region;

        int halfWidth = region.getRegionWidth() / 2;
        int halfHeight = region.getRegionHeight() / 2;
        batch.draw(region, position.getX() - halfWidth, position.getY() - halfHeight, halfWidth, halfHeight, region.getRegionWidth(), region.getRegionHeight(), 1f, 1f, position.getRotation());
    }

    @Override
    protected void addEntity(final int id) {
        sortedEntities.add(id);
        Collections.sort(sortedEntities, new Comparator<Integer>() {
            @Override
            public int compare(Integer e1, Integer e2) {
                Graphics s1 = textureMap.get(e1);
                Graphics s2 = textureMap.get(e2);
                return s1.renderLayer.compareTo(s2.renderLayer);
            }
        });
    }

    @Override
    protected void removeEntity(final int id) {
        sortedEntities.remove((Object) id);
    }
}
