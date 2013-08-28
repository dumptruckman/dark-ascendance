package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.SimpleTextureRegion;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.graphics.Textures;

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

    @Mapper
    ComponentMapper<Position> positionMap;
    @Mapper
    ComponentMapper<SimpleTextureRegion> textureMap;

    private SpriteBatch batch;
    private List<Entity> sortedEntities;
    ShaderProgram program;
    private OrthographicCamera camera;

    private TextureRegion backgroundTexture = TextureFactory.getBackground().getTexture(Textures.STAR_FIELD);

    public TextureRenderingSystem(OrthographicCamera camera) {
        super(Aspect.getAspectForAll(Position.class, SimpleTextureRegion.class));
        this.camera = camera;
    }

    @Override
    protected void initialize() {
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
        sortedEntities = new ArrayList<Entity>();
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        //program.setUniformMatrix("u_projTrans", batch.getTransformMatrix());
        batch.begin();

        drawBackground();
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

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {

        for(Entity entity : sortedEntities) {
            process(entity);
        }
    }

    protected void process(Entity entity) {
        if(positionMap.has(entity)) {
            Position position = positionMap.getSafe(entity);
            TextureRegion region = textureMap.get(entity).region;

            int halfWidth = region.getRegionWidth() / 2;
            int halfHeight = region.getRegionHeight() / 2;
            batch.draw(region, position.getX() - halfWidth, position.getY() - halfHeight, halfWidth, halfHeight, region.getRegionWidth(), region.getRegionHeight(), 1f, 1f, position.getRotation());
        }
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void inserted(Entity e) {
        sortedEntities.add(e);
        Collections.sort(sortedEntities, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                SimpleTextureRegion s1 = textureMap.get(e1);
                SimpleTextureRegion s2 = textureMap.get(e2);
                return s1.renderLayer.compareTo(s2.renderLayer);
            }
        });
    }

    @Override
    protected void removed(Entity e) {
        sortedEntities.remove(e);
    }
}
