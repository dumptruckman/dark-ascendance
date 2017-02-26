package com.dumptruckman.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.graphics.TextureFactory;
import com.dumptruckman.graphics.Textures;
import com.dumptruckman.components.TextureComponent;
import com.dumptruckman.components.PositionComponent;
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

    ComponentMapper<PositionComponent> positionMap;
    ComponentMapper<TextureComponent> textureMap;

    private SpriteBatch batch;
    private List<Integer> sortedEntities;
    ShaderProgram program;
    private OrthographicCamera camera;

    private TextureRegion backgroundTexture = TextureFactory.getBackground().getTexture(Textures.STAR_FIELD);

    public TextureRenderingSystem(OrthographicCamera camera) {
        super(PositionComponent.class, TextureComponent.class);
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

        for (Integer entityId : sortedEntities) {
            processEntity(entityId, deltaInSec);
        }

        batch.end();
    }

    protected void processEntity(int entityId, float deltaInSec) {
        PositionComponent position = positionMap.get(entityId);
        TextureRegion region = textureMap.get(entityId).getRegion();

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
