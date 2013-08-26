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

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
    private static final float VerticalThrusters = 5f;
    private static final float VerticalMaxSpeed = 200;
    private static final float ROTATION_SPEED = 5F;
    private static final float FireRate = 0.1f;

    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Thrust> tm;

    private boolean up, down, left, right;
    private boolean shoot;
    private float timeToFire;

    private float destinationX, destinationY;
    private OrthographicCamera camera;
    private Vector3 mouseVector;

    public PlayerInputSystem() {//OrthographicCamera camera) {
            super(Aspect.getAspectForAll(Position.class, Thrust.class, Player.class));
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

        mouseVector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        //camera.unproject(mouseVector);

        destinationX = mouseVector.x;
        destinationY = mouseVector.y;

        //float angleInRadians = Utils.angleInRadians(position.x, position.y, destinationX, destinationY);

        //position.x += TrigLUT.cos(angleInRadians) * 500f * world.getDeltaFloat();
        //position.y += TrigLUT.sin(angleInRadians) * 500f * world.getDeltaFloat();

        //position.x = mouseVector.x;
        //position.y = mouseVector.y;

        if(up) {
            thrust.vectorY = MathUtils.clamp(velocity.vectorY + (world.getDelta() * VerticalThrusters), -VerticalMaxSpeed, VerticalMaxSpeed);
        }
        if(down) {
            velocity.vectorY = MathUtils.clamp(velocity.vectorY-(world.getDelta()*VerticalThrusters), -VerticalMaxSpeed, VerticalMaxSpeed);
        }

        if(left) {
            position.ro
                //velocity.vectorX = MathUtils.clamp(velocity.vectorX-(world.getDelta()*HorizontalThrusters), -HorizontalMaxSpeed, HorizontalMaxSpeed);
        }
        if(right) {
                //velocity.vectorX = MathUtils.clamp(velocity.vectorX+(world.getDelta()*HorizontalThrusters), -HorizontalMaxSpeed, HorizontalMaxSpeed);
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
