package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {

    private static final float THRUST_INC = 80f;
    private static final float ROTATION_SPEED = 80F;
    private static final float FireRate = 0.1f;

    @Mapper
    ComponentMapper<Position> positionMap;
    @Mapper
    ComponentMapper<Thrusters> thrustMap;
    @Mapper
    ComponentMapper<Velocity> velocityMap;

    private boolean up = false;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean shoot;
    private float timeToFire;

    private Vector3 mouseVector;

    public PlayerInputSystem() {
        super(Aspect.getAspectForAll(Position.class, Player.class, Velocity.class));
        this.mouseVector = new Vector3();
    }

    @Override
    protected void initialize() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void process(Entity entity) {
        Position position = positionMap.get(entity);
        Velocity velocity = velocityMap.get(entity);
        Thrusters thrusters = thrustMap.getSafe(entity);

        mouseVector.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        if (thrusters != null) {
            if(up) {
                thrusters.setThrustLevel(1F);
            } else {
                thrusters.setThrustLevel(0F);
            }
        }
        if(down) {
            position.attainRotation(velocity.getRotationRequiredToReverseVelocity(), (world.getDelta() * ROTATION_SPEED));
        }
        if(left) {
            position.setRotation(position.getRotation() + (world.getDelta() * ROTATION_SPEED));
        }
        if(right) {
            position.setRotation(position.getRotation() - (world.getDelta() * ROTATION_SPEED));
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
        } else if(keycode == Input.Keys.D) {
            right = true;
        } else if(keycode == Input.Keys.W) {
            up = true;
        } else if(keycode == Input.Keys.S) {
            down = true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.A) {
            left = false;
        } else if(keycode == Input.Keys.D) {
            right = false;
        } else if(keycode == Input.Keys.W) {
            up = false;
        } else if(keycode == Input.Keys.S) {
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
