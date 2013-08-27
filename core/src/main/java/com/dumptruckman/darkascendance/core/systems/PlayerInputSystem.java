package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrust;
import com.dumptruckman.darkascendance.core.components.Velocity;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
    private static final float THRUST_INC = 80f;
    private static final float ROTATION_SPEED = 80F;
    private static final float FireRate = 0.1f;

    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Thrust> tm;
    @Mapper ComponentMapper<Velocity> vm;

    private boolean up = false, down, left, right;
    private boolean shoot;
    private float timeToFire;

    private OrthographicCamera camera;
    private Vector3 mouseVector;

    public PlayerInputSystem() {//OrthographicCamera camera) {
        super(Aspect.getAspectForAll(Position.class, Thrust.class, Player.class, Velocity.class));
        this.camera = camera;
        this.mouseVector = new Vector3();
    }

    @Override
    protected void initialize() {
            Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void process(Entity e) {
        Position position = pm.get(e);
        Thrust thrust = tm.get(e);
        Velocity velocity = vm.get(e);

        mouseVector.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        if(up) {
            thrust.forwardThrust = MathUtils.clamp(thrust.forwardThrust + (world.getDelta() * THRUST_INC), 0, thrust.maxFowardThrust);
        } else {
            thrust.forwardThrust = 0F;
        }
        if(down) {
            position.attainRotation(velocity.getRotationRequiredToReverseVelocity(), (world.getDelta() * ROTATION_SPEED));
        }

        if(left) {
            position.setRotation(position.r + (world.getDelta() * ROTATION_SPEED));
        }
        if(right) {
            position.setRotation(position.r - (world.getDelta() * ROTATION_SPEED));
        }

        if(shoot) {
            if(timeToFire <= 0) {
                //EntityFactory.createPlayerBullet(world, position.x - 27, position.y + 2).addToWorld();
                //EntityFactory.createPlayerBullet(world, position.x+27, position.y+2).addToWorld();
                timeToFire = FireRate;
            }
        }
        if(timeToFire > 0) {
            timeToFire -= world.delta;
            if(timeToFire < 0) {
                timeToFire = 0;
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
            if(keycode == Input.Keys.A) {
                    left = true;
            }
            else if(keycode == Input.Keys.D) {
                    right = true;
            }
            else if(keycode == Input.Keys.W) {
                    up = true;
            }
            else if(keycode == Input.Keys.S) {
                    down = true;
            }

            return true;
    }

    @Override
    public boolean keyUp(int keycode) {
            if(keycode == Input.Keys.A) {
                    left = false;
            }
            else if(keycode == Input.Keys.D) {
                    right = false;
            }
            else if(keycode == Input.Keys.W) {
                    up = false;
            }
            else if(keycode == Input.Keys.S) {
                    down = false;
            }

            return true;
    }

    @Override
    public boolean keyTyped(char character) {
            return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
            if(button == Input.Buttons.LEFT) {
                    shoot = true;
            }
            return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
            if(button == Input.Buttons.LEFT) {
                    shoot = false;
            }
            return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
            return false;
    }

    @Override
    public boolean mouseMoved(final int i, final int i2) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
            return false;
    }

}
